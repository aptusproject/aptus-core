package aptus
package aptdata
package io
package table

import meta.basic.UnparameterizedBasicType
import io.inferring.table.TypeGuessing

// ===========================================================================
case class CellConf(
    nullValues: Seq[String] = Seq(CellConf.DefaultNullValue),

    /** order matters, acts dumb if more than one such separator within the same value */
    arraySeparators: Seq[String] = Seq(CellConf.DefaultArraySeparator)) {

  private val noNulls : Boolean = nullValues     .isEmpty
  private val noArrays: Boolean = arraySeparators.isEmpty

  // ---------------------------------------------------------------------------
  private val nullValueSet      : Set[String]    = nullValues.toSet
  private val soleArraySeparator: Option[String] = if (arraySeparators.size == 1) Some(arraySeparators.head) else None

  // ===========================================================================
  def inferContainerOnly(value: String): Container =
      /**/ if (isNull (value)) Container._Opt
      else if (isArray(value)) Container._Nes
      else                     Container._One

  // ---------------------------------------------------------------------------
  def inferInfo(value: String): Info =
      /**/ if (isNull (value)) Info.nonUnion(_Optional, SubInfo(_Single,   BasicType._String))
      else if (isArray(value)) Info.nonUnion(_Required, SubInfo(_Multiple, arrayType(splitArray(value))))
      else                     Info.nonUnion(_Required, SubInfo(_Single,   TypeGuessing(value)))

    // ---------------------------------------------------------------------------
    private def arrayType(values: Seq[String]): BasicType =
      values
        .map(TypeGuessing.apply)
        .distinct
        .pipe(meta.basic.combine)

  // ===========================================================================
  def valueSet(value: String): Set[String] =
    if (!isArray(value)) value.in.noneIf(isNull).toSet
    else                 value.in.noneIf(isNull).toSet.flatMap(splitArray)

  // ===========================================================================
  def transformBasicValue(tipe: BasicType)(value: String): AnyValue =
    tipe match {
      case _: BasicType._Enm           => meta.basic.BasicType._Enm.parseString(value)
      case    BasicType._Boolean       => inferring.table.BooleanDetector.forceBoolean(value)
      case x: UnparameterizedBasicType => x.parseString(value) }

  // ===========================================================================
  /*   */ def isNull (value: String): Boolean = !noNulls  && nullValueSet.contains(value) /* no trimming intentionally */
  private def isArray(value: String): Boolean = !noArrays && arraySeparators.exists(value.contains)

  // ---------------------------------------------------------------------------
  def splitArray(value: String): Seq[String] =
    soleArraySeparator match {
      case Some(sep) => value.splitBy(sep)
      case None =>
        arraySeparators.foldLeft(Seq(value)) { (curr, sep) =>
          curr.flatMap(_.splitBy(sep)) } /* hopefully only one sep per value */ } }

// ===========================================================================
object CellConf {
  val Default = CellConf()

  // ---------------------------------------------------------------------------
  lazy val DefaultNullValue      = ""
  lazy val DefaultArraySeparator = "," }

// ===========================================================================