package aptustesting
package dyntest
package mult

// ===========================================================================
object DynsGroupingTest {
  import aptus.dyn._

  // ===========================================================================
  private val GroupingInput: Dyns  = z9.append(dyn(foo -> "bar1", baz -> 3))

  // ---------------------------------------------------------------------------
  private val Output1: Dyns  = dyns(
    dyn(foo -> "bar1", _group -> Seq(dyn(baz -> 1), dyn(baz -> 3))),
    dyn(foo -> "bar2", _group -> Seq(dyn(baz -> 2))))

  // ---------------------------------------------------------------------------
  private val Output2: Dyns  = dyns(
    dyn(foo -> "bar1", baz -> Seq(1, 3)),
    dyn(foo -> "bar2", baz -> Seq(2)))

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    GroupingInput.groupBy(foo)        .check(Output1)
    GroupingInput.group  (baz).by(foo).check(Output2) } }

// ===========================================================================
