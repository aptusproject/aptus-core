package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynNestingTest extends TestSuite {
  import aptus.dyn._

  // ===========================================================================
  private val f1 = "f1"
  private val f2 = "f2"
  private val F  = "F"
  private val g  = "g"

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1.noop(_.nest(FOO).under(qux)))

    // ---------------------------------------------------------------------------
    test(_Sngl1_NoBaz.nest(foo).under(p)
      .check(dyn(p -> _Sngl1_NoBaz)))

    // ---------------------------------------------------------------------------
    test(_Sngl1_T.nest(foo).under(p)
      .check(dyn(baz -> 1, qux -> T, p -> _Sngl1_NoBaz)))

    // ---------------------------------------------------------------------------
    test(_Sngl1_T
        .nest(foo, baz).under(p)
      .check(dyn(qux -> T, p -> _Sngl1))) }
}

// ===========================================================================
