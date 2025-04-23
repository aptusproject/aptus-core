package aptus
package experimental
package dyn
package data
package aspects

// ===========================================================================
trait DynBuilding {
                                     val Empty                          : Dyn = sngl.DynBuilder.build(Nil) // allowed, unlike in gallia (see a250423153425)
    /** checks uniqueness of keys */ def build (values: Iterable[Entry]): Dyn = sngl.DynBuilder.build(values)
    /** by-pass uniqueness check */  def byPass(values: Iterable[Entry]): Dyn = sngl.DynBuilder.build(values, byPassUniquenessCheck = true) }

  // ---------------------------------------------------------------------------
  trait DynsBuilding {
    lazy val empty                        : Dyns = build(Iterable.empty) // allowed, unlike in gallia (see a250423153435)
         def build(values: Iterable[Sngl]): Dyns = values.toList.pipe(data.Dyns._build) /* TODO consider offering Vector version too? (t241120102540) */ }

  // ---------------------------------------------------------------------------
  trait DynzBuilding {
    lazy val empty                                  : Dynz = data.Dyns.empty.asIterator // allowed, unlike in gallia (see a250423153435)
         def build(values: CloseabledIterator[Sngl]): Dynz = values.pipe(data.Dynz._build)  }

// ===========================================================================
