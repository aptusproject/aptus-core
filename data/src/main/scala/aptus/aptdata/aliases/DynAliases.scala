package aptus
package aptdata
package aliases

// ===========================================================================
private[aptus] trait DynAliases
    extends DynDataPublicAliases  // eg: Dyn, Dyns, ...
       with DynDataPrivateAliases // eg: Valew, ...
       with DynSelectorsAliases   // eg: Key, Ren, ...
       with DynErrorAliases {     // eg: Error, ...

  type Schema = aptus.aptdata.meta.schema.Cls
  val  Schema = aptus.aptdata.meta.schema.Cls

  // ---------------------------------------------------------------------------
  private[aptdata] type ValueF = Valew => NakedValue

  // ===========================================================================
  private[aptdata] type FormattedRow   = String
  private[aptdata] type FormattedTable = String }

// ===========================================================================
