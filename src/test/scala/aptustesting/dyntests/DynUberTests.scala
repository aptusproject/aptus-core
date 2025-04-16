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
    DynTest()

    // ---------------------------------------------------------------------------
    mult.DynsTest()
    mult.DynzTest()
    mult.DynMultipleWrappingTest()
    mult.DynsGroupingTest()
    mult.DynMultipleTest ()

    DynSelectorsTests() /* eg transform(_.soleKey).using(...) */
    DynAccessorsTest ()
    DynNestingTest()
    DynTensorsTest()

    // ---------------------------------------------------------------------------
    DynVeryBasicTest ()
    DynTransformsTest()
    DynTransformDerivedTest ()
    DynHeterogenousTypesTest()
    DynMoreTests()

    // ---------------------------------------------------------------------------
    DynInferringTest()

    // ---------------------------------------------------------------------------
    DynInputTests()
    DynOutputStringTests()
    DynOutputTableStringsTests()
    DynOutputFileTests() } }

// ===========================================================================
