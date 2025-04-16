package aptus
package experimental
package dyn
package aliases

// ===========================================================================
private[aptus] trait DynAliases
    extends DynDataAliases
       with DynSelectorsAliases
       with DynErrorAliases {
  type Schema = aptus.aptdata.meta.schema.Cls
  val  Schema = aptus.aptdata.meta.schema.Cls

  // ---------------------------------------------------------------------------
  type BasicType = aptus.aptdata.meta.basic.BasicType
  val  BasicType = aptus.aptdata.meta.basic.BasicType

  // ---------------------------------------------------------------------------
  private[dyn] type ValueF = Valew => NakedValue

  // ===========================================================================
  private[dyn] type FormattedRow   = String
  private[dyn] type FormattedTable = String }

// ===========================================================================
