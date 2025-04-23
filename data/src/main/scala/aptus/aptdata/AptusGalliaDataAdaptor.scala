package aptus
package aptdata

// ===========================================================================
trait AptusGalliaDataAdaptor
  extends AptusDataTraits   // Date, BigDec, Fld, Info, _Optional, TypeMatching, ...
     with AptusGalliaCommonAliases // Obj & Objs

// ===========================================================================
trait AptusGalliaCommonAliases {
  private[aptus] type Obj = aptus.experimental.dyn.data.sngl.Dyn
  private[aptus] val  Obj = aptus.experimental.dyn.data.sngl.Dyn

  private[aptus] type Objs = aptus.experimental.dyn.data.mult.list.Dyns
  private[aptus] val  Objs = aptus.experimental.dyn.data.mult.list.Dyns }

// ===========================================================================
