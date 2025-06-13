package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynRenameTest extends TestSuite {
  import aptus.dyn._
  import DynTargetDataTest.Input

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(_Sngl1.rename(foo ~> FOO, baz ~> BAZ).check(
      dyn(FOO -> bar, BAZ ->  1)))

    // ---------------------------------------------------------------------------
    test(_Sngl1.rename(foo).to(FOO).check(_Sngl1_FOO))
    test(_Mult1.rename(foo).to(FOO).check(_Mult1c))

    test(_Sngl1.rename(foo ~> FOO).check(_Sngl1_FOO))
    test(_Mult1.rename(foo ~> FOO).check(_Mult1c))

    test(_Sngl3.rename(p |> foo).to(FOO).check(_Sngl3c))

    test(_Sngl1.rename(_.firstKey).to(FOO).check(_Sngl1_FOO))

    // ---------------------------------------------------------------------------
    test(Input.rename(
        "a" ~> "A",
        "p1" |> "c" ~> "C")
      .check(
        dyn(
          "A"  -> 1,
          "b"  -> 2,
          "p1" -> dyn("C" -> 3),
          "p2" -> dyn("d" -> 4, "e" -> 5),
          "p3" -> dyn("p" -> dyn("f" -> 6)),
          "extra" -> 0))) } }

// ===========================================================================

