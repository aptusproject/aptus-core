package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(           Dyn .dummy("foo") .check {            Dyn .dummy("foo")  })
    test(           Dyns.dummy("foo") .check {            Dyns.dummy("foo")  })
    test(Dyns.dummy(Dyn .dummy("foo")).check { Dyns.dummy(Dyn .dummy("foo")) })

    // ---------------------------------------------------------------------------
    // because Iterators don't equal
    test(Try(Dyns.dummy(Dyn .dummy("foo")).asDynz == Dyns.dummy(Dyn.dummy("foo")).asDynz).check(Error.CantCompareDynz))

    // ===========================================================================
    test("heterogenous arrays") { // not recommended (ambiguous semantics) but valid
      test(dyn(p -> Seq(       _Sngl1, "foo")).formatCompactJson.check("""{"p":[{"foo":"bar","baz":1},"foo"]}"""))
      test(dyn(p -> Seq("foo", _Sngl1))       .formatCompactJson.check("""{"p":["foo",{"foo":"bar","baz":1}]}"""))

      // ---------------------------------------------------------------------------
      test("""{"p":[{"foo":"bar","baz":1},"foo"]}""".dyn.check(dyn(p -> Seq(       _Sngl1, "foo"))))
      test("""{"p":["foo",{"foo":"bar","baz":1}]}""".dyn.check(dyn(p -> Seq("foo", _Sngl1)))) } } }

// ===========================================================================
