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
  type RPath = aptdata.meta.selectors.RPath

  val Key    = aptdata.meta.selectors.Key
  val Ren    = aptdata.meta.selectors.Ren
  val Rens   = aptdata.meta.selectors.Rens
  val Path   = aptdata.meta.selectors.Path
  val RPath  = aptdata.meta.selectors.RPath

  type Keyz   = aptdata.meta.selectors.Keyz
  val  Keyz   = aptdata.meta.selectors.Keyz

  // ---------------------------------------------------------------------------
  private[aptus] type Sel = domain.Sel.Sel
  private[aptus] val  Sel = domain.Sel

  private[aptus] type Sel2 = meta.selectors.SelectOneShorthands.type =>
    meta.selectors.TargetSelector /* 250611162910 - can't be SelectOne else can't use .guaranteePresence */

  // ===========================================================================
  /** Explicit target: Key, Ren, Path, RPath - most common case */
  type Target = aptdata.meta.selectors.Target

    /** No-renaming target: Key or Path - mostly just for .remove() */
    type NoRenarget  = aptdata.meta.selectors.NoRenarget

    /** Renaming Target: Ren or RPath - mostly just for .rename() */
    type Renarget = aptdata.meta.selectors.Renarget

    // ---------------------------------------------------------------------------
    type Targets = aptdata.meta.selectors.Targets
    val  Targets = aptdata.meta.selectors.Targets

      // ---------------------------------------------------------------------------
      type NoRenargets  = aptdata.meta.selectors.NoRenargets
      val  NoRenargets  = aptdata.meta.selectors.NoRenargets

      type Renargets = aptdata.meta.selectors.Renargets
      val  Renargets = aptdata.meta.selectors.Renargets }

// ===========================================================================