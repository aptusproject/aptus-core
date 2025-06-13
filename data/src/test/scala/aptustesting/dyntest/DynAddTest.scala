package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynAddTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(_Sngl1.add(      qux ->       T).check(_Sngl1_T))
    test(_Sngl1.add(key = qux, value = T).check(_Sngl1_T))

    test(_Sngl1_NoBaz.add(aptus.listMap(baz -> 1, qux -> T)).check(_Sngl1_T))
    test(_Sngl1_NoBaz.add(              baz -> 1, qux -> T) .check(_Sngl1_T))

    test(Try(_Sngl1.add(foo -> BAR)).check(Error.DuplicateKeys))

    test(_Sngl1.add(qux ->     T).check(_Sngl1_T))
    test(_Sngl1.add(qux).value(T).check(_Sngl1_T))
    test(_Mult1.add(qux ->     T).check(_Mult1_T))
    test(_Mult1.add(qux).value(T).check(_Mult1_T))

    test(_Sngl3.transformNesting(p).using(_.add(qux -> T)).check(dyn(p -> dyn(foo -> bar, baz -> 1, qux -> T))))
    test(_Sngl3.add(p |> qux).value(T)                    .check(dyn(p -> dyn(foo -> bar, baz -> 1, qux -> T))))

    // ===========================================================================
    test("add vs replace vs put") {
      // add: must not exist
      test(Try {
        _Sngl1.add(foo).value("BAR") } .checkIllegalArgument("E241120111033"))
      test(
        _Sngl1.add(FOO).value("BAR").check(dyn(foo -> bar, baz -> 1, FOO -> "BAR")))

      // ---------------------------------------------------------------------------
      // replace: must exist
      test(_Sngl1.replace(foo).withValue("BAR").check(dyn(foo -> "BAR", baz -> 1)))
      test(Try {
           _Sngl1.replace(FOO).withValue("BAR") }.checkGuaranteeError())

      // ---------------------------------------------------------------------------
      // put: may or may not exist
      test(_Sngl1.put(foo).value("BAR").check(dyn(foo -> "BAR", baz -> 1)))
      test(_Sngl1.put(FOO).value("BAR").check(dyn(foo -> bar, baz -> 1, FOO -> "BAR"))) } } }

// ===========================================================================