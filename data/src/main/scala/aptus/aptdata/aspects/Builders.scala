package aptus
package aptdata
package aspects

// ===========================================================================
trait DynBuilding {
    /** checks uniqueness of keys */ def build (values: Iterable[Entry]): Dyn = sngl.DynBuilder.build(values)
    /** by-pass uniqueness check */  def byPass(values: Iterable[Entry]): Dyn = sngl.DynBuilder.build(values, byPassUniquenessCheck = true) }

  // ---------------------------------------------------------------------------
  trait DynsBuilding {
    lazy val empty                        : Dyns = build(Iterable.empty) // allowed, unlike in gallia (see a250423153435)
         def build(values: Iterable[Sngl]): Dyns = values.toList.pipe(Dyns._build) /* TODO consider offering Vector version too? (t241120102540) */ }

  // ---------------------------------------------------------------------------
  trait DynzBuilding {
    lazy val empty                                  : Dynz = Dyns.empty.asIterator // allowed, unlike in gallia (see a250423153435)
         def build(values: CloseabledIterator[Sngl]): Dynz = values.pipe(Dynz._build)  }

// ===========================================================================
