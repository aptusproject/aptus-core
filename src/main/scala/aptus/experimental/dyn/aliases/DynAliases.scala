package aptus
package experimental
package dyn
package aliases

// ===========================================================================
private[dyn] trait DynAliases
    extends DynDataAliases
       with DynSelectorsAliases
       with DynErrorAliases {

  type Schema = meta.Cls
  val  Schema = meta.Cls

  // ---------------------------------------------------------------------------
  type BasicType = _root_.gallia.basic.BasicType
  val  BasicType = _root_.gallia.basic.BasicType

//  private[dyn] type IntegerLike[_] = domain.IntegerLike[_]
//  private[dyn] type    RealLike[_] = domain.   RealLike[_]

  // ---------------------------------------------------------------------------
  private[dyn] type ValueF = Valew => NakedValue

  // ===========================================================================
  private[dyn] type Keys   = Seq[Key]
  private[dyn] type KeySet = Set[Key]

  private[dyn] type SKey    = String
  private[dyn] type SKeys   = Seq[SKey]
  private[dyn] type SKeySet = Set[SKey]

  // ---------------------------------------------------------------------------
  private[dyn] type SKeyPred       = SKey  => Boolean
  private[dyn] type SKeysSelection = SKeys => SKeys
  private[dyn] type SKeysFunction  = SKeys => SKeys

  // ===========================================================================
  private[dyn] type FormattedRow   = String
  private[dyn] type FormattedTable = String }

// ===========================================================================
