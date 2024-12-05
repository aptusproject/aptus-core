package aptus
package experimental
package dyntest


// ===========================================================================
object DynTest {
  import dyn._
  import Dyn .dyn
  import Dyns.dyns

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
                Dyn .dummy("foo") .check {            Dyn .dummy("foo")  }
                Dyns.dummy("foo") .check {            Dyns.dummy("foo")  }
     Dyns.dummy(Dyn .dummy("foo")).check { Dyns.dummy(Dyn .dummy("foo")) }

    // ---------------------------------------------------------------------------
    // because Iterators don't equal
    Try(Dyns.dummy(Dyn .dummy("foo")).asDynz == Dyns.dummy(Dyn.dummy("foo")).asDynz).check(Error.CantCompareDynz) } }

// ===========================================================================