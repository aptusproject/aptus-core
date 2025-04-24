/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package inferring

import aptus.aptdata.meta.basic.BasicType.{_Enm => _, _}
import scala.{Any => AnySingleValue}

// ===========================================================================
class SchemaInferrer[$Jbo](
    entries   : $Jbo => Seq[(Symbol/*BKey*/, AnyValue)],
    nestingOpt: AnySingleValue => Option[$Jbo]) {
  import SchemaInferrerUtils._

  // ===========================================================================
  def klass(values: Seq[$Jbo]): Cls = values.iterator.pipe(klass)

  // ---------------------------------------------------------------------------
  def klass(values: Iterator[$Jbo]): Cls =
    values
      .map(klass(_))
      .reduceLeft(_ combine _)

  // ---------------------------------------------------------------------------
  def klass(values: aptus.CloseabledIterator[$Jbo]): Cls =
    values
      .map(klass(_))
      .reduceLeft(_ combine _)

  // ---------------------------------------------------------------------------
  def klass(
o: aptus.aptdata.sngl.DynData): Cls =
    o .galliaPairs
      .map { case (key, value) =>
        Fld(key, info(value)) }
      .pipe(Cls.apply)

  // ---------------------------------------------------------------------------
  def klass(o: $Jbo): Cls =
    o .pipe(entries)
      .map { case (key, value) =>
        Fld(key, info(value)) }
      .pipe(Cls.apply)

  // ===========================================================================
  def info(value: AnyValue): Info =
      value match {
        case seq: Seq[_] =>
          // a201113123227 - no heterogenous arrays; TODO: t250424143819 - also for dyn?
          Info.nes(//FIXME: pes/nes issue?
            if (nestingOpt(seq.head).nonEmpty) seq.map(_.asInstanceOf[$Jbo]).pipe(klass)
            else                               valueType(seq.head))
        case _ =>
          Info.one(valueType(value)) }

    // ---------------------------------------------------------------------------
    @PartialTypeMatching
    @NumberAbstraction
    def valueType(value: AnySingleValue): ValueType =
      nestingOpt(value) match {
        case Some(nesting) => klass(nesting)
        case None          => value match {
          case _: String     => _String
          case x: Double     => // 201119115427
            if (x.isValidInt /* Long? */) _Int // _Long?
            else                          _Double
          case _: Int        => _Int
          case _: Boolean    => _Boolean

          case _: Byte       => _Byte
          case _: Short      => _Short
          case _: Long       => _Long

          case x: Float      => // 201119115427
            if (x.isValidInt /* Long? */) _Int
            else                          _Float

          case _: BigInt     => _BigInt
          case _: BigDec     => _BigDec

          case _: Date       => _Date
          case _: DateTime   => _DateTime
          case _: Instant    => _Instant

          case _: ByteBuffer => _Binary

          case x: EnumValue  => _Enm(Seq(x))
          case x: enumeratum.EnumEntry => _Enm(Seq(EnumValue(x.entryName))) } } }

// ===========================================================================