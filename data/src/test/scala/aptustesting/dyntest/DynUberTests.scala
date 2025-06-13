package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynUberTests extends TestSuite {

  // ---------------------------------------------------------------------------
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(apply()) } // TODO: port all to actual tests

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    DynScalaTest()

    // ---------------------------------------------------------------------------
    mult.DynsTest()
    mult.DynzTest()
    mult.DynsGroupingTest()

    // ---------------------------------------------------------------------------
    DynMoreTests()
    DynOutputTableStringsTests() } }

// ===========================================================================
