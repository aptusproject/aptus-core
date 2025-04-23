package aptustesting
package dyntest

// ===========================================================================
object DynInputTests {
  import aptus.dyn._

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {

    JsonObjectFilePath
      .dyn
        .replace(foo).withValue("BAR")
      .check {
        dyn(foo -> "BAR", baz -> 1.0 /* because of JSON number tax - see a241125114540 */) }

    // ---------------------------------------------------------------------------
    RowFilePath
      .dyn
        .upperCase(foo)
      .check {
        dyn(foo -> "BAR", baz -> "1" /* no type inferrence by default - see a241125114008 */) }

    // ===========================================================================
    val expected: aptus.aptdata.ops.ConvertOps[Dyns] = _Mult1.upperCase(foo).convert(baz)

    // ---------------------------------------------------------------------------
    JsonArrayFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toDouble /* because of JSON number tax - see a241125114540 */ }

    // ---------------------------------------------------------------------------
    JsonLinesFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toDouble /* because of JSON number tax - see a241125114540 */ }

    // ---------------------------------------------------------------------------
    TsvFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toStr /* no type inferrence by default - see a241125114008 */ }


    // ---------------------------------------------------------------------------
    TsvWithCRFilePath
      .dyns
        .upperCase(foo)
      .check { expected.toStr /* no type inferrence by default - see a241125114008 */ } } }

// ===========================================================================
