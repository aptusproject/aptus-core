package aptus
package experimental
package dyn
package aliases

// ===========================================================================
private[dyn] trait DynDataAliases {
  type Dyn  = data.sngl.Dyn
  type Dyns = data.mult.list.Dyns
  type Dynz = data.mult.iter.Dynz

  val Dyn  = data.sngl.Dyn
  val Dyns = data.mult.list.Dyns
  val Dynz = data.mult.iter.Dynz

  // ---------------------------------------------------------------------------
  type Valew = domain.valew.Valew
  val  Valew = domain.valew.Valew

  private[dyn] type NakedValue = Any // NOT           Valew
  private[dyn] type NakedValew = Any // NakedValue or Valew

  // ---------------------------------------------------------------------------
  private[dyn] type Entry = domain.Entry
  private[dyn] val  Entry = domain.Entry

  private[dyn] type Entries = domain.Entries
  private[dyn] val  Entries = domain.Entries

  private[dyn] type EntryList = List[Entry]

  // ---------------------------------------------------------------------------
  // sometimes these makes things clearer (eg in constrast with Dyn{s,z})
  private[dyn] type Sngl = data.sngl.Dyn
  private[dyn] val  Sngl = data.sngl.Dyn

  private[dyn] type Sngls = CloseabledIterator[Sngl] /* TODO: create actual wrapper? */ }

// ===========================================================================