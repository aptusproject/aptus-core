package aptus
package experimental
package dyn
package aliases

// ===========================================================================
trait DynDataAliases {
  @publik type Dyn  = data.sngl.Dyn
  @publik type Dyns = data.mult.list.Dyns
  @publik type Dynz = data.mult.iter.Dynz

  @publik val Dyn  = data.sngl.Dyn
  @publik val Dyns = data.mult.list.Dyns
  @publik val Dynz = data.mult.iter.Dynz

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