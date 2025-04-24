package aptus
package aptdata
package sngl

import ops.common.HasDataEntityErrorFormatter

// ===========================================================================
trait DynEntryMapping {
    self: DynData =>
private type _Self = Dyn
  private[aptdata] def     mapEntries(f: Entry =>              Entry) : _Self = data    .map(f).dyn
  private[aptdata] def flatMapEntries(f: Entry => IterableOnce[Entry]): _Self = data.flatMap(f).dyn }

// ---------------------------------------------------------------------------
trait DynEntriesTransforming {
    self: DynData =>
private type _Self = Dyn
  def transformEntries(f: Entries   => Entries)  : _Self = entries.pipe(f).values.pipe(Dyn.build)
  def transformData   (f: EntryList => EntryList): _Self = data   .pipe(f)       .pipe(Dyn.build) }

// ---------------------------------------------------------------------------
//TODO: centralize with Mult's
trait DynDataEntityErrorFormatterProvider
      extends HasDataEntityErrorFormatter [Dyn] {
    self: DynJsonWriter =>
  override final protected[aptdata] def deef: DataEntityErrorFormatter =
    DataEntityErrorFormatter.fromSingle(this) }

// ===========================================================================
