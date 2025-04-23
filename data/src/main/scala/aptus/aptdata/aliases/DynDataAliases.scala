package aptus
package aptdata
package aliases

// ===========================================================================
trait DynDataPublicAliases {
  @publik type Dyn  = sngl.Dyn
  @publik type Dyns = mult.list.Dyns
  @publik type Dynz = mult.iter.Dynz

  @publik val Dyn  = sngl.Dyn
  @publik val Dyns = mult.list.Dyns
  @publik val Dynz = mult.iter.Dynz }

// ===========================================================================
trait DynDataPrivateAliases {
  private[aptdata] type Valew = domain.valew.Valew
  private[aptdata] val  Valew = domain.valew.Valew

  private[aptdata] type NakedValue = Any // NOT           Valew
  private[aptdata] type NakedValew = Any // NakedValue or Valew

  // ---------------------------------------------------------------------------
  private[aptdata] type Entry = domain.Entry
  private[aptdata] val  Entry = domain.Entry

  private[aptdata] type Entries = domain.Entries
  private[aptdata] val  Entries = domain.Entries

  private[aptdata] type EntryList = List[Entry]

  // ---------------------------------------------------------------------------
  // sometimes these makes things clearer (eg in constrast with Dyn{s,z})
  private[aptdata] type Sngl = sngl.Dyn
  private[aptdata] val  Sngl = sngl.Dyn

  private[aptdata] type Sngls = CloseabledIterator[Sngl] /* TODO: create actual wrapper? */ }

// ===========================================================================