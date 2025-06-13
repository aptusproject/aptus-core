package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynRetainTest extends TestSuite {
  import aptus.dyn._
  import Dyn.Empty
  import DynTargetDataTest.Input

  // ---------------------------------------------------------------------------
  val Output1 =
    dyn(
      "a"  -> 1,
      "B"  -> 2,
      "p1" -> dyn("c" -> 3),
      "p2" -> dyn("d" -> 4, "E" -> 5),
      "p3" -> dyn("p" -> dyn("f" -> 6)))

  // ---------------------------------------------------------------------------
  val Output2 =
    dyn(
      "a"  -> 1,
      "B"  -> 2,
      "p1" -> dyn("c" -> 3),
      "p2" -> dyn("d" -> 4, "E" -> 5))

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1.retain(foo)       .check(_Sngl1_NoBaz))
    test(_Sngl1.retain(_.firstKey).check(_Sngl1_NoBaz))
    test(_Sngl1.retain(_. lastKey).check(_Sngl1_NoFoo))

    test(_Sngl1.retain(foo, baz)  .check(_Sngl1))
    test(_Sngl1.retain(_.allKeys) .check(_Sngl1))

    test(_Sngl1.retain(FOO)       .check(Empty))
    test(_Sngl1.retain(foo ~> FOO).check(dyn(FOO -> bar)))
    test(_Sngl1.retain(foo ~> FOO, baz ~> BAZ)  .check(dyn(FOO -> bar, BAZ -> 1)))

    test(_Sngl1.retainMultiple(Set(foo, baz)).check(_Sngl1))

    test(_Sngl3.add(qux -> T).retain(p |> foo).check(dyn(p -> _Sngl1_NoBaz)))

    // ===========================================================================
    test(Input.retain(
        "a",
        "b"  ~> "B",
        "p1" |> "c",
        "p2" |> "d",
        "p2" |> "e" ~> "E",
        "p3" |> "p" |> "f")
      .check(Output1))

    // ---------------------------------------------------------------------------
    test(Input.retain(
        "a",
        "b"  ~> "B",
        "p1" |> "c",
        "p2" |> "d",
        "p2" |> "e" ~> "E",
        "p3" |> "P" /* should be lower case */ |> "f")
      .check(Output2))

    // ---------------------------------------------------------------------------
    test("not nesting")(Try { Input.retain(
        "a" |> "c", // not nesting
        "p3") }
      .checkIllegalArgument("E250529135752"))

    // ---------------------------------------------------------------------------
    test("inconsistency")(Try { Input.retain(

        // inconsistent
        "a",
        "a" |> "c",

        "p3") }
      .checkIllegalArgument("E250529135612")) } }

// ===========================================================================

