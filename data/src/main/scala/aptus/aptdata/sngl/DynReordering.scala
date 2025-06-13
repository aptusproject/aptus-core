package aptus
package aptdata
package sngl

// ===========================================================================
trait DynReordering { self: Dyn =>
  override final def reorderKeys           (f: SKeysFunction): Dyn = f(skeys).map { key => key -> forceKey(key) }.dyn // worth keeping?
  override final def reorderKeysRecursively(f: SKeysFunction): Dyn =
    f(skeys)
      .map { skey =>
        entries
          .findValue(_.skey == skey)
          .force // guaranteed by use of skeys above
          .transformValew2 { _.fold2
            { _.reorderKeysRecursively(f) }
            { _.reorderKeysRecursively(f) } } }
          .dyn }

// ===========================================================================