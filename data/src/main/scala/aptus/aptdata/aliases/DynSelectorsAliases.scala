package aptus
package aptdata
package aliases

// ===========================================================================
trait DynSelectorsAliases extends aptus.aptdata.meta.selectors.SelectorsTrait {
  // TODO: make public?
  private[aptus] type TargetSelector = aptdata.meta.selectors.TargetSelector
  private[aptus] val  TargetSelector = aptdata.meta.selectors.TargetSelector

  // ---------------------------------------------------------------------------
  // keep those public

  type Key   = aptdata.meta.selectors.Key
  type Ren   = aptdata.meta.selectors.Ren
  type Rens  = aptdata.meta.selectors.Rens
  type Path  = aptdata.meta.selectors.Path

  val Key    = aptdata.meta.selectors.Key
  val Ren    = aptdata.meta.selectors.Ren
  val Rens   = aptdata.meta.selectors.Rens
  val Path   = aptdata.meta.selectors.Path

  // ---------------------------------------------------------------------------
  private[aptus] type Sel = domain.Sel.Sel
  private[aptus] val  Sel = domain.Sel }

// ===========================================================================