package aptus
package experimental
package dyn
package data
package mult
package iter

import dyn.ops._
import dyn.data.mult.iter.{Dynz => _Self}
import common.CommonOutputter

// ===========================================================================
/** a241128152444 - does not HAVE to fit in memory (unlike `Dyns`), but more limited in terms of operations
 * unlike gallia's counterpart, one cannot just "fork" Dynz directly: must "checkpoint" it manually */
case class Dynz(
      protected[dyn] val values: CloseabledIterator[Dyn])
    extends ops.mult.HasAllMultiple[_Self]

       with ops.mult.MultipleTrait[_Self]
       with ops.mult.MultipleGroupingDynzImpl /* eg groupBy (concrete) */

       with CommonOutputter          /* eg .write("/my/file") */
       with io.out.DynzTables        /* tables formatting */
with aspects.DynzSchemaInferrer2 {

    /** typically only in case of error, else it closes itself upon full consumption: see underlying CloseabledIterator */
    def closeUnderlying() = { values.close() }

    // ---------------------------------------------------------------------------
    /** costly if big */ def keySetVeryCostly: Set[Key] = keysVeryCostly.toSet
    /** costly if big */ def keysVeryCostly  : Seq[Key] = ??? // TODO - t241203145246

    // ---------------------------------------------------------------------------
    override final protected      lazy val empty               : _Self = _Self.empty // parent as "def" else scl3 error: error overriding value empty in trait MultipleOpsTrait of type aptus.experimental.dyn.data.mult.iter.Dynz
    override final protected           def const(values: Sngls): _Self = _Self.apply(values)
    override final protected[dyn]      def valuesIterator      : Sngls = values

    // ---------------------------------------------------------------------------
    override def equals(that: Any): Boolean = Error.CantCompareDynz.thro

    // ---------------------------------------------------------------------------
    def toStatic[DC <: Product: aptreflect.lowlevel.ReflectionTypesAbstraction.WTT]: CloseabledIterator[DC] = _toStatic[DC]

    // ---------------------------------------------------------------------------
    // TODO: choose
    def asList: Dyns = values.consumeAll().dyns
    def asDyns: Dyns = values.consumeAll().dyns

    // ===========================================================================
    def join   (that: Dyns): _Self = ??? // TODO: t241203101550 - since Dyns fits in memory (see a241203101826)
    def coGroup(that: Dyns): _Self = ??? // TODO: t241203101551 - since Dyns fits in memory (see a241203101826)
    // pivot: must use Dyns for now

    def bringAll(that: Dyns): _Self = ???

    // ---------------------------------------------------------------------------
    def sort(by: Any): _Self = ??? // TODO: t241203101529 - requires external sort (see t241203101839)
    def join   (that: _Self)(on: Any): _Self = ??? // TODO: t241203101530 - requires external sort (see t241203101839)
    def coGroup(that: _Self)(on: Any): _Self = ??? // TODO: t241203101531 - requires external sort (see t241203101839)

    // ===========================================================================
    override final def formatCompactJson: String = asList.formatCompactJson // TODO: t241030113645 - allow streaming here as well
    override final def formatPrettyJson : String = asList.formatPrettyJson

    // ---------------------------------------------------------------------------
    override final def write(s: OutputFilePath): OutputFilePath = io.out.DynOut.write(this)(s) }

  // ===========================================================================
  object Dynz
    extends aspects.DynzBuilding
       with aspects.DynzFluentBuilding
       with aspects.DynzDummies {
    /* only for builder */
    private[dyn] def _build(values: CloseabledIterator[Dyn]): Dynz =
      new Dynz(values) }


// ===========================================================================
