package aptus
package experimental
package dyn
package ops

// ===========================================================================
/** type Mult = Dyns|Dynz, as opposed to Dyn (aka Sngl) */
package object mult {
  type CoLookup[A, B] = aptmisc.CoLookup[A, B]
  val  CoLookup       = aptmisc.CoLookup

  // ===========================================================================
  trait HasEndoMap    [Mult] { @abstrct def     endoMap   (f: Sngl => Sngl)          : Mult }
  trait HasEndoFlatMap[Mult] { @abstrct def endoFlatMap   (f: Sngl => Iterable[Sngl]): Mult }
  trait HasExoMap     [Mult] { @abstrct def      exoMap[T](f: Sngl => T)             : IteratoR[T] }

  // ---------------------------------------------------------------------------
  // two-way
  trait HasIteratorConstructor[Mult] { @abstrct protected      def const(values: Sngls): Mult }
  trait HasValuesIterator     [Mult] { @abstrct protected[dyn] def valuesIterator: Sngls }

  // ===========================================================================
  // super traits

  trait HasAllMultiple[Mult]
      extends HasEndoMap        [Mult]
         with HasIteratorRelated[Mult]

    // ---------------------------------------------------------------------------
    trait HasIteratorRelated[Mult]
        extends HasValuesIterator [Mult]
           with HasIteratorConstructor[Mult] }

// ===========================================================================
