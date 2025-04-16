package aptus
package experimental
package dyn
package aliases

// ===========================================================================
/*private[aptus] */trait DynDataAliases {
  type Dyn  = data.sngl.Dyn
  type Dyns = data.mult.list.Dyns
  type Dynz = data.mult.iter.Dynz

  val Dyn  = data.sngl.Dyn
  val Dyns = data.mult.list.Dyns
  val Dynz = data.mult.iter.Dynz

  // ---------------------------------------------------------------------------
  private[aptus] type Valew = domain.valew.Valew
  private[aptus] val  Valew = domain.valew.Valew

  private[aptus] type NakedValue = Any // NOT           Valew
  private[aptus] type NakedValew = Any // NakedValue or Valew

  // ---------------------------------------------------------------------------
  private[aptus] type Entry = domain.Entry
  private[aptus] val  Entry = domain.Entry

  private[aptus] type Entries = domain.Entries
  private[aptus] val  Entries = domain.Entries

  private[aptus] type EntryList = List[Entry]

  // ---------------------------------------------------------------------------
  // sometimes these makes things clearer (eg in constrast with Dyn{s,z})
  private[aptus] type Sngl = data.sngl.Dyn
  private[aptus] val  Sngl = data.sngl.Dyn

  private[aptus] type Sngls = CloseabledIterator[Sngl] /* TODO: create actual wrapper? */ }

// ===========================================================================