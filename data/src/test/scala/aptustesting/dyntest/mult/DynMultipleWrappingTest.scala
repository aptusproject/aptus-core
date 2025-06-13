package aptustesting
package dyntest
package mult

import utest._

// ===========================================================================
object DynMultipleWrappingTest extends TestSuite {
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(_Sngl1             .transformString(foo).using(_.reverse)  .check(_Sngl1_rab))
    test(_Mult1             .transformString(foo).using(_.reverse)  .check(_Mult1e))
    test(_Mult1.testDynz { _.transformString(foo).using(_.reverse) }.check(_Mult1e)) } }

// ===========================================================================
