package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynInputTests extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(JsonObjectFilePath
      .dyn
        .replace(foo).withValue("BAR")
      .check {
        dyn(foo -> "BAR", baz -> 1.0 /* because of JSON number tax - see a241125114540 */) })

    // ---------------------------------------------------------------------------
    test(RowFilePath
      .dyn
        .upperCase(foo)
      .check {
        dyn(foo -> "BAR", baz -> "1" /* no type inferrence by default - see a241125114008 */) })

    // ===========================================================================
    val expected: aptus.aptdata.ops.ConvertOps[Dyns] = _Mult1.upperCase(foo).convert(baz)

    // ---------------------------------------------------------------------------
    test(JsonArrayFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toDouble /* because of JSON number tax - see a241125114540 */ })

    // ---------------------------------------------------------------------------
    test(JsonLinesFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toDouble /* because of JSON number tax - see a241125114540 */ })

    // ---------------------------------------------------------------------------
    test(TsvFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toStr /* no type inferrence by default - see a241125114008 */ })


    // ---------------------------------------------------------------------------
    test(TsvWithCRFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toStr /* no type inferrence by default - see a241125114008 */ }) } }

// ===========================================================================
