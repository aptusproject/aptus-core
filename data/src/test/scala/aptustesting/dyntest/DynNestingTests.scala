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
    test(_Sngl3.isPresent(p |> foo).checkTrue ())
    test(_Sngl3.isPresent(p |> FOO).checkFalse())

    // ---------------------------------------------------------------------------
    test(dyn(f1 -> foo , f2 -> foo , g -> 1)
        .nest(f1).under(F)
      .check(dyn(f2 -> foo, g -> 1, F -> dyn(f1 -> foo)))) } }

// ===========================================================================
