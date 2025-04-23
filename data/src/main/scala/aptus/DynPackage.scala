package aptus

import aptus.aptdata.domain.Entry

// ===========================================================================
package object dyn
    extends aptus.aptdata.AptdataPackage {
  def dyn (entry1: Entry, more: Entry*): Dyn = Dyn.build(entry1 +: more) }

// ===========================================================================