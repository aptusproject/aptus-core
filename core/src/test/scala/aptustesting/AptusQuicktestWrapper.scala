package aptustesting

import utest._

// ===========================================================================
object AptusQuicktestWrapper extends TestSuite {
  val tests = Tests {
    test { compare(AptusQuicktest.apply(), ()) } } }

// ===========================================================================
