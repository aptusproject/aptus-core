package aptustesting
package dyntest
package misc

import utest._

// ===========================================================================
object DynValewGetterAccessorsTest extends TestSuite with DynTestData {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(_Sngl1. string  (foo)           .check(bar))
    test(_Sngl1. string  (Path.leaf(foo)).check(bar))
    test(_Sngl3 .string  (      p |> foo).check(bar))
    test(_Sngl33.string  (p2 |> p |> foo).check(bar))

    test(_Sngl1. texts  (foo)           .check1(bar))
    test(_Sngl1. texts  (Path.leaf(foo)).check1(bar))
    test(_Sngl3 .texts  (      p |> foo).check1(bar))
    test(_Sngl33.texts  (p2 |> p |> foo).check1(bar))

    test(_Sngl1z.texts     (foo).checkN("bar1", "bar2"))
    test(_Sngl4z.texts(p |> foo).checkN("bar1", "bar2"))

    test(_Mult11.texts  (      p |> foo).checkN("bar1", "bar2"))

    // ---------------------------------------------------------------------------
    test("heterogenous1")(dyn(p -> Seq(       _Sngl1, "foo")).texts(p |> foo).check1("bar"))
    test("heterogenous2")(dyn(p -> Seq("foo", _Sngl1))       .texts(p |> foo).check1("bar"))

    // ---------------------------------------------------------------------------
    test(dyn(p2 -> dyns(dyn(p -> _Sngl2), dyn(p -> _Sngl2b))).texts(p2).checkN(
      """{"p":{"foo":["bar1","bar2"],"baz":1}}""",
      """{"p":{"foo":["bar2","bar1"],"baz":2}}"""))

    test(dyn(p2 -> dyns(dyn(p -> _Sngl2), dyn(p -> _Sngl2b))).texts      (p2 |> p |> foo).checkN(bar1, bar2, bar2, bar1))
    test(dyn(p2 -> dyns(dyn(p -> _Sngl2), dyn(p -> _Sngl2b))).nakedValues(p2 |> p |> foo).checkN(bar1, bar2, bar2, bar1))

    // ===========================================================================
    test("nakedValue(s)") {
      test(    _Sngl1 .nakedValue(     foo).check(bar))
      test(    _Sngl3 .nakedValue(p |> foo).check(bar))
      test(Try(_Sngl1z.nakedValue(     foo)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl4z.nakedValue(p |> foo)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl1 .nakedValue(     FOO)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl1z.nakedValue(     FOO)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl3 .nakedValue(p |> FOO)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl4z.nakedValue(p |> FOO)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl3 .nakedValue(P |> foo)).checkIllegalArgument("E250618153017"))
      test(Try(_Sngl4z.nakedValue(P |> foo)).checkIllegalArgument("E250618153017"))

      // ---------------------------------------------------------------------------
      test(_Sngl1 .nakedValues(     foo).check1(bar))
      test(_Sngl1z.nakedValues(     foo).checkN(bar1, bar2))
      test(_Sngl3 .nakedValues(p |> foo).check1(bar))
      test(_Sngl4z.nakedValues(p |> foo).checkN(bar1, bar2)) } } }

// ===========================================================================

