package aptus

// ===========================================================================
package object dyn
    extends aptus.experimental.dyn.DynPackage {
  def dyn (entry1: Entry, more: Entry*): Dyn = Dyn.build(entry1 +: more) }

// ===========================================================================