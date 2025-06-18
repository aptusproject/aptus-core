package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynConvertTests extends TestSuite {
  import aptus.dyn._

  // ===========================================================================
  val tests = Tests {
    test(dyn(foo -> "9", baz -> 1).convertToInt(foo)      .check(dyn(foo -> 9  , baz ->  1)))
    test(dyn(foo -> "9", baz -> 1).convert     (foo).toInt.check(dyn(foo -> 9  , baz ->  1)))
    test(dyn(foo -> bar, baz -> 1).convert     (baz).toStr.check(dyn(foo -> bar, baz -> "1")))

    test(_Mult1.convert(baz).toStr.check(dyns(dyn(foo -> bar1, baz -> "1"), dyn(foo -> bar2, baz -> "2")))) } }

// ===========================================================================
