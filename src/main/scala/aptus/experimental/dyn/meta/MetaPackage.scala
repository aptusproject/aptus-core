package aptus
package experimental
package dyn

// ===========================================================================
//package meta {
//  package object basic {
//    type BasicType = _root_.gallia.basic.BasicType
//    val  BasicType = _root_.gallia.basic.BasicType }}

// ===========================================================================
//package object meta
package object meta
    extends ClsCompanionTrait /* keep? */
       with FldCompanionTrait {
  /*private[dyn] */type InfoLike = Fld // not so in gallia

  // ---------------------------------------------------------------------------
  /*private[dyn] */type Multiple = Boolean
  /*private[dyn] */type Optional = Boolean

  // ===========================================================================
  private[meta] trait ClsItems { self: Cls =>
    override final protected val const  = Cls.apply(_)
    override final           val values = fields }

  // ===========================================================================
  private[meta] trait     ClsTraits extends     ClsFormatting with     ClsOps { self: Cls => }
  private[meta] trait     FldTraits extends     FldFormatting with     FldOps { self: Fld => }
  private[meta] trait    InfoTraits extends    InfoFormatting with    InfoOps { self: Info => }
  private[meta] trait SubInfoTraits extends SubInfoFormatting with SubInfoOps { self: SubInfo => }

  // ===========================================================================
  private[meta] def formatOptional(value: Optional): String = if (value) "_Optional" else "_Required"
  private[meta] def formatMultiple(value: Multiple): String = if (value) "_Multiple" else "_Single" }

// ===========================================================================
