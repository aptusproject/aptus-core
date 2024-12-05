package aptus
package experimental
package dyn
package data
package mult
package list

import dyn.ops._
import dyn.data.mult.list.{Dyns => _Self}
import common.CommonOutputter

// ===========================================================================
/** MUST fit in memory (unlike `Dynz`) */
case class Dyns private[Dyns] (protected[dyn] val values: Seq[Dyn])
    extends ops.mult.HasAllMultiple[_Self]

       with ops.mult.MultipleTrait[_Self]
       with ops.mult.MultipleGroupingDynsImpl /* eg groupBy, ... (concrete) */
       with ops.mult.MultipleJoiningDynsImpl /* eg leftJoin, ... (concrete) */

       with CommonOutputter          /* eg .write("/my/file") */
       with aspects.DynsSchemaInferrer
       with CanConvertFromTable {

/** mostly to convert doubles to int when applicable ("JSON number tax")  - overkill in aptus (vs gallia)*/
def fromJson: Dyns = inferSchema.pipe(x => endoMap(_root_.gallia.gson.GsonToGalliaData.convertRecursively(x)))

// TODO: t241203213031 - all the missing accessors

//TODO: codegen - t241203151355
def doubles(key: Key): Seq[Double] = exoMap(_.double(key)).toList
def doubles          : Seq[Double] = ???//  TODO: t241203151505 exoMap(_.double(_.soleKey)).toList



    // ---------------------------------------------------------------------------
    override final protected      lazy val empty               : _Self = _Self.empty
    override final protected           def const(values: Sngls): _Self = new Bug(values).consumeAll.pipe(_Self.apply)
    override final protected[dyn]      def valuesIterator      : Sngls = values.iterator.toIteratoR

    // ---------------------------------------------------------------------------
    // TODO: choose
    def asIterator: Dynz = values.iterator.dynz
    def asDynz    : Dynz = values.iterator.dynz

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

    override final def formatCompactJson: String = _root_.gallia.gson.BorrowedGsonTo.formatCompact(this)
    override final def  formatPrettyJson: String = _root_.gallia.gson.BorrowedGsonTo.formatPretty(this)

    def formatTable: String = io.out.DynOut2.formatTable(this)(sep = "\t")
      .joinln.newline /* TODO: stream */ }

  // ===========================================================================
  object Dyns
    extends aspects.DynsBuilding
       with aspects.DynsFluentBuilding
       with aspects.DynsDummies

// ===========================================================================
