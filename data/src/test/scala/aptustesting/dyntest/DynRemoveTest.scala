package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynRemoveTest extends TestSuite {
  import aptus.dyn._
  import Dyn.Empty
  import DynTargetDataTest.Input

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(_Sngl1.remove(baz)       .check(_Sngl1_NoBaz))
    test(_Sngl1.remove(_.firstKey).check(_Sngl1_NoFoo))
    test(_Sngl1.remove(_. lastKey).check(_Sngl1_NoBaz))

    test(_Sngl1.remove(foo, baz)  .check(Empty))
    test(_Sngl1.remove(_.allKeys) .check(Empty))
    test(_Sngl1.remove(FOO)       .check(_Sngl1))

    test(_Sngl1.removeMultiple(Set(foo, baz)).check(Empty))

    test(_Sngl3.remove(p |> foo).check(dyn(p -> _Sngl1_NoFoo)))

    // ===========================================================================
    test {
      val exp = dyn(
        "b"  -> 2,
        "p1" -> dyn("c" -> 3),
        "p2" -> dyn("e" -> 5),
        "p3" -> dyn("p" -> dyn("f" -> 6)),
        "extra" -> 0)

      // ---------------------------------------------------------------------------
      test(Input.remove(
          "a",
          "p2" |> "d")
        .check(exp) )

      // ---------------------------------------------------------------------------
      test(Input.removeGuaranteed(
          "a",
          "p2" |> "d")
        .check(exp) )


      // ---------------------------------------------------------------------------
      test(Try { Input.removeGuaranteed(
          "a",
          "p2" |> "D") }
        .checkGuaranteeError() ) }

    // ---------------------------------------------------------------------------
    test(Input.remove("p3" |> "p")
      .check(
        dyn(
          "a"  -> 1,
          "b"  -> 2,
          "p1" -> dyn("c" -> 3),
          "p2" -> dyn("d" -> 4, "e" -> 5),
          "extra" -> 0)) )

    // ---------------------------------------------------------------------------
    test(Input.remove(
        "a",
        "b",
        "p1" |> "c",
        "p2" |> "d",
        "p2" |> "e",
        "p3" |> "p" |> "f")
      .check(dyn("extra" -> 0))) } }

// ===========================================================================

