package aptus
package experimental
package dyn

import aptdata.meta.selectors.TargetSelectorShorthands

// ===========================================================================
package object domain {
  trait NumberLike[N]

  // ===========================================================================
  object Sel {
    type Sel = TargetSelectorShorthands.type => TargetSelector
    def apply(sel: Sel): TargetSelector = sel(TargetSelectorShorthands) }

  // ===========================================================================
  private[domain] implicit class Fractional_[F: Fractional](frac: Fractional[F]) {
      // not sure why only exists for fromInt... must be doing something wrong here...
      def from(value: Any): F = frac.parseString(value.toString).get }

    // ---------------------------------------------------------------------------
    private[domain] implicit class Numeric_[N: Numeric](num: Numeric[N]) {
      // not sure why only exists for fromInt... must be doing something wrong here...
      def from(value: Any): N = num.parseString(value.toString).get } }

// ===========================================================================