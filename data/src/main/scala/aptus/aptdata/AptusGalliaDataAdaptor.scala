package aptus
package aptdata

// ===========================================================================
trait AptusGalliaDataAdaptor
    extends AptusDataTraits { // Date, BigDec, Fld, Info, _Optional, TypeMatching, ...

  private[aptus] type Obj = sngl.Dyn
  private[aptus] val  Obj = sngl.Dyn

  private[aptus] type Objs = mult.list.Dyns
  private[aptus] val  Objs = mult.list.Dyns }

// ===========================================================================
