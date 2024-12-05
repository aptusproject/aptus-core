package aptus
package experimental
package dyn
package aliases

// ===========================================================================
trait DynSelectorsAliases {
  // TODO: public?
  private[dyn] type TargetSelector = domain.selectors.TargetSelector
  private[dyn] val  TargetSelector = domain.selectors.TargetSelector

  // ---------------------------------------------------------------------------
  // keep those public

  type Key   = domain.selectors.Key
  type Ren   = domain.selectors.Ren
  type Rens  = domain.selectors.Rens
  type Path  = domain.selectors.Path

  val Key    = domain.selectors.Key
  val Ren    = domain.selectors.Ren
  val Rens   = domain.selectors.Rens
  val Path   = domain.selectors.Path

  type             Sel = domain.Sel.Sel
  private[dyn] val Sel = domain.Sel }

// ===========================================================================