package aptus
package experimental
package dyn
package data
package sngl

import ops.common.{HasDataEntityErrorFormatter, CanInferSchema}

// ===========================================================================
trait DynEntryMapping {
    self: DynData =>
private type _Self = Dyn
  private[dyn] def     mapEntries(f: Entry =>              Entry) : _Self = data    .map(f).dyn
  private[dyn] def flatMapEntries(f: Entry => IterableOnce[Entry]): _Self = data.flatMap(f).dyn }

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
  override final protected[dyn] def deef: DataEntityErrorFormatter =
    DataEntityErrorFormatter.fromSingle(this) }

// ===========================================================================
