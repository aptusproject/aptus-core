package aptus
package experimental
package dyn
package ops
package mult

import data.sngl.Dyn.dyn

// ===========================================================================
trait MultipleGroupingDynsImpl
      extends MultipleGroupingTrait[Dyns] /* eg abstract groupBy(x) */ {
    self: HasValuesIterator[Dyns] =>

  // ---------------------------------------------------------------------------
//preprocess for more than one for now - TODO: as docs
  override final def groupBy(key: Key): Dyns =
    valuesIterator
      .map { dyn =>
        dyn.nakedValueOpt(key) -> dyn.remove(key) }
      .groupByKeyWithListMap
      .view
      .map { case (groupingValueOpt: Option[NakedValue], groupedValues: Seq[Dyn]) =>
        groupingValueOpt
          .map { groupingValue => dyn(key -> groupingValue, _group -> groupedValues) }
          .getOrElse            { dyn(                      _group -> groupedValues) } }
      .dyns

  // ---------------------------------------------------------------------------
  override protected final def _groupXByY(groupee: Key, grouper: Key): Dyns =
    valuesIterator
      .map { dyn =>
        dyn.nakedValueOpt(grouper) -> dyn.any(groupee) }
      .groupByKeyWithListMap
      .view
      .map { case (groupingValueOpt: Option[NakedValue], groupedValues: Seq[NakedValue]) =>
        groupingValueOpt
          .map { groupingValue => dyn(grouper -> groupingValue, groupee -> groupedValues) }
          .getOrElse            { dyn(                          groupee -> groupedValues) } }
      .dyns }

// ===========================================================================
