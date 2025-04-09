package aptustesting

import utest._

// ===========================================================================
object DynTestsWrapperHack extends TestSuite {
  // TODO: port them to proper test
  val tests = Tests {
    test { compare( aptustesting.dyntest.DynUberTests.apply(), ()) } } }

// ===========================================================================
