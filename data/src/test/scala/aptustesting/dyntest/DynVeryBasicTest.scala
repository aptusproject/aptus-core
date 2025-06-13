package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynVeryBasicTest extends TestSuite {

  val tests = Tests {
    test("merging") {
      test(Try(_Sngl1      .merge(_Sngl1))      .check(Error.DuplicateKeys))
      test(Try(_Sngl1_NoBaz.merge(_Sngl1))      .check(Error.DuplicateKeys))
      test(Try(_Sngl1      .merge(_Sngl1_NoFoo)).check(Error.DuplicateKeys))
      test(    _Sngl1_NoBaz.merge(_Sngl1_NoFoo) .check(_Sngl1)) } } }

// ===========================================================================
