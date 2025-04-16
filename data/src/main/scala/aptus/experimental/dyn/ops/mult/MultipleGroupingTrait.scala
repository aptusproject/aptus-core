package aptus
package experimental
package dyn
package ops
package mult

// ===========================================================================
trait MultipleGroupingTrait[Mult] {
  @abstrct       def groupBy(key    : Key): Mult
  @nonovrd final def group  (groupee: Key): _GroupXByY =
    // boilerplate
    new _GroupXByY(groupee)
      class _GroupXByY private[dyn] (groupee: Key) {
        def by(grouper: Key) =
          _groupXByY(groupee, grouper) }

  // ---------------------------------------------------------------------------
  @abstrct protected def _groupXByY(groupee: Key, grouper: Key): Mult }

// ===========================================================================
