package aptus
package aptdata
package sngl

// ===========================================================================
trait DynReordering {
    self: DynDataWithGetter
    with aptus.aptdata.ops.OpsBorrowedFromGallia =>
  private type _Self = DynReordering

  // ---------------------------------------------------------------------------
  /** equality minus field ordering */
  final def equivalent(that: _Self): Boolean = this.sorted == that.sorted

  // ===========================================================================
  final def sorted: _Self = reorderKeysRecursively(_.sorted)

  // ---------------------------------------------------------------------------
  def reorderKeys           (f: SKeysFunction): _Self = f(skeys).map { key => key -> forceKey(key) }.dyn // worth keeping?
  def reorderKeysRecursively(f: SKeysFunction): _Self = _reorderKeysRecursively(_.skeys.pipe(f).pipe(Keyz._fromStrings))(o = this) }

// ===========================================================================