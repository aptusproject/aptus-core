package aptustesting
package dyntest
package misc

import utest._

// ===========================================================================
object DynValewGetterAccessorsTest extends TestSuite with DynTestData {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(_Sngl1. string  (foo).check(bar))
    test(_Sngl1. string  (Path.leaf(foo)).check(bar))
    test(_Sngl3 .string  (      p |> foo).check(bar))
    test(_Sngl33.string  (p2 |> p |> foo).check(bar))

    test(_Sngl1. text  (foo).check(bar))
    test(_Sngl1. text  (Path.leaf(foo)).check(bar))
    test(_Sngl3 .text  (      p |> foo).check(bar))
    test(_Sngl33.text  (p2 |> p |> foo).check(bar))

    test(Try { _Sngl1z.text  (foo) }.checkException(classOf[java.lang.IllegalArgumentException], "E250502125928", "multiple values"))

    test(_Sngl1z.texts     (foo).check(Seq("bar1", "bar2")))
    test(_Sngl4z.texts(p |> foo).check(Seq("bar1", "bar2")))

    test(_Mult11.text  (      p |> foo).check("bar1,bar2"))

    // ---------------------------------------------------------------------------
    test("heterogenous1")(dyn(p -> Seq(       _Sngl1, "foo")).text(p |> foo).check("bar"))
    test("heterogenous2")(dyn(p -> Seq("foo", _Sngl1))       .text(p |> foo).check("bar"))

    // ---------------------------------------------------------------------------
    test(dyn(p2 -> dyns(dyn(p -> _Sngl2), dyn(p -> _Sngl2b))).texts(p2).check(Seq(
      """[{"p":{"foo":["bar1","bar2"],"baz":1}},{"p":{"foo":["bar2","bar1"],"baz":2}}]""")))

    test(dyn(p2 -> dyns(dyn(p -> _Sngl2), dyn(p -> _Sngl2b))).texts      (p2 |> p |> foo).check(Seq(bar1, bar2, bar2, bar1)))
    test(dyn(p2 -> dyns(dyn(p -> _Sngl2), dyn(p -> _Sngl2b))).nakedValues(p2 |> p |> foo).check(Seq(bar1, bar2, bar2, bar1))) } }

// ===========================================================================

