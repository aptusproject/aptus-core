package aptus
package experimental
package dyntest

// ===========================================================================
object DynUberTests {
  def main(args: Array[String]): Unit = { apply() }

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
    mult.DynMultipleTest()

    DynSelectorsTests() /* eg transform(_.soleKey).using(...) */
    DynAccessorsTest()
    DynNestingTest()
    DynTensorsTest()

    // ---------------------------------------------------------------------------
    DynVeryBasicTest()
    DynTransformsTest()
    DynTransformDerivedTest()
    DynHeterogenousTypesTest()
    DynMoreTests()

    // ---------------------------------------------------------------------------
    DynInferringTest()

    // ---------------------------------------------------------------------------
    DynInputTests()
    io.DynDataClassesTest()
    DynOutputStringTests()
    DynOutputTableStringsTests()
    DynOutputFileTests() } }

// ===========================================================================