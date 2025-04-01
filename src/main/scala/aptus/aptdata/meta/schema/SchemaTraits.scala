package aptus
package aptdata
package meta
package schema

// ===========================================================================
// aptus dyn and gallia will have different extensions for those (much lighter for the former)
private[aptus] trait ClsTraits extends ClsOps with ClsFormatting {
    _self: Cls =>
    lazy val self = _self
    override final val const  = Cls.apply _
    override final val values = fields }

  // ---------------------------------------------------------------------------
  trait FldTraits     extends FldOps     with FldFormatting     { _self: Fld     => lazy val self = _self }
  trait InfoTraits    extends InfoOps    with InfoFormatting    { _self: Info    => lazy val self = _self }
  trait Info1Traits   extends Info1Ops   with Info1Formatting   { _self: Info1   => lazy val self = _self }
  trait SubInfoTraits extends SubInfoOps with SubInfoFormatting { _self: SubInfo => lazy val self = _self }

// ===========================================================================
