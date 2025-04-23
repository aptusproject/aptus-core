package aptus
package aptdata
package ops
package mult

// ===========================================================================
trait MultipleGroupingDynzImpl
      extends MultipleGroupingTrait[Dynz] {
    self: HasValuesIterator[Dynz] =>
  // TODO: t241128123352 - groupBy for iterator (needs external sort)
  final override           def  groupBy(key: Key)                    : Dynz = ???
  final override protected def _groupXByY(groupee: Key, grouper: Key): Dynz = ??? }

// ===========================================================================
