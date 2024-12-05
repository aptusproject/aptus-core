package gallia
package basic

// ===========================================================================
/** minimal version of gallia's counterpart */
trait BasicTypeHelper
      extends ClassTagRelated {
    protected type T

    // ---------------------------------------------------------------------------
    // needed for inferring
    /*protected[dyn] */def isBoundedNumber: Boolean = this.isInstanceOf[gallia.basic.BoundedNumber]

    // ---------------------------------------------------------------------------
    protected def accessorNameModifier(value: FullNameString): String = ??? }

  // ===========================================================================
  /** minimal version of gallia's counterpart */
  trait ClassTagRelated {
    protected type T

    // ---------------------------------------------------------------------------
    protected val _ctag: CT[                T  ]
    protected val nctag: CT[       Iterable[T] ]
    protected val octag: CT[       Option  [T] ]
    protected val pctag: CT[Option[Iterable[T]]]

    // ---------------------------------------------------------------------------
    protected val ordA: Ordering[T]
    protected val ordD: Ordering[T] }

// ===========================================================================
