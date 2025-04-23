package aptus
package aptdata
package aspects

// ===========================================================================
trait DynFluentBuilding { self: DynBuilding =>
    def dyn(entry1: Entry, more: Entry*): Dyn = build(entry1 +: more) }

  // ---------------------------------------------------------------------------
  trait DynsFluentBuilding { self: DynsBuilding =>
    def dyns(dyn1: Sngl, more: Sngl*): Dyns = (dyn1 +: more).toList.dyns }

  // ---------------------------------------------------------------------------
  trait DynzFluentBuilding { self: DynzBuilding =>
    def dynz(dyn1: Sngl, more: Sngl*): Dynz = Dyns.dyns(dyn1, more: _*).asIterator }

// ===========================================================================
