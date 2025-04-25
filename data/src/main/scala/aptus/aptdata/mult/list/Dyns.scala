package aptus
package aptdata
package mult
package list

import ops._
import list.{Dyns => _Self}
import common.CommonOutputter

// ===========================================================================
/** MUST fit in memory (unlike `Dynz`); order matters (250423153434), can be empty (a250423153435) */
case class Dyns private[Dyns] (
      protected[aptdata] val values: Seq[Dyn])
    extends ops.mult.HasAllMultiple[_Self]

       with ops.mult.MultipleTrait[_Self]
       with ops.mult.MultipleGroupingDynsImpl /* eg groupBy, ... (concrete) */
       with ops.mult.MultipleJoiningDynsImpl /* eg leftJoin, ... (concrete) */

       with CommonOutputter          /* eg .write("/my/file") */
       with aspects.DynsSchemaInferrer
       with CanConvertFromTable {

/** mostly to convert doubles to int when applicable ("JSON number tax")  - overkill in aptus (vs gallia) */
def fromJson: Dyns = inferSchema.pipe { c => endoMap { o => io.AptusGsonToAptusData.convertRecursively(c)(o) } }

// TODO: t241203213031 - all the missing accessors

//TODO: codegen - t241203151355
def doubles(key: Key): Seq[Double] = exoMap(_.double(key)).toList
def doubles          : Seq[Double] = ???//  TODO: t241203151505 exoMap(_.double(_.soleKey)).toList

    // ---------------------------------------------------------------------------
    override final protected      lazy val empty               : _Self = _Self.empty
    override final protected           def const(values: Sngls): _Self = values.consumeAll().pipe(_Self.apply)
    override final protected[aptdata]      def valuesIterator      : Sngls = CloseabledIterator.fromSeq(values)

    // ---------------------------------------------------------------------------
    def toStatic[DC <: Product: aptreflect.lowlevel.ReflectionTypesAbstraction.WTT]: List[DC] = _toStatic[DC].consumeAll()

    // ---------------------------------------------------------------------------
    // TODO: choose
    def asIterator: Dynz = valuesIterator.dynz
    def asDynz    : Dynz = valuesIterator.dynz

    // ===========================================================================
    /** costly if big */ lazy val valueList: Seq[Dyn] = values.toList

    // ---------------------------------------------------------------------------
    /** costly if big */ def keySetCostly: Set[Key] = keysCostly.toSet
    /** costly if big */ def keysCostly  : Seq[Key] =
      valueList
        .foldLeft(Seq.empty[Key]) { (distinctKeys, row) =>
          (distinctKeys ++ row.keys).distinct }

    // ===========================================================================
    // bringAll, join --> see MultipleJoiningDynsImpl
    def sort (by: Any): _Self = ??? // TODO: t241203101539
    def pivot(on: Any): Dyns = ??? // TODO: t241203101542

    // ===========================================================================
    override final def write(s: OutputFilePath): OutputFilePath = io.out.DynOut.write(this)(s)

    override final def formatCompactJson: String = io.AptusJsonFormatting.formatCompactMultiple(this)
    override final def  formatPrettyJson: String = io.AptusJsonFormatting.formatPrettyMultiple (this)

    //TODO: csv
    def formatTsv  : String = io.out.DynOut2.formatTable(this)(sep = "\t")
      .joinln.newline /* TODO: stream */
    def formatTable: String = formatTsv }

  // ===========================================================================
  object Dyns
    extends aspects.DynsBuilding
       with aspects.DynsFluentBuilding
       with aspects.DynsDummies {
    /* only for builder */
    private[aptdata] def _build(values: Seq[Dyn]): Dyns =
      new Dyns(values) }

// ===========================================================================
