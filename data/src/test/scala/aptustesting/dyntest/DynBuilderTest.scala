package aptustesting
package dyntest

// ===========================================================================
object DynBuilderTest {
  import aptus.experimental.dyn._
  import Dyn.dyn

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    dyn(foo -> bar, baz -> (None: Option[Int])).check(dyn(foo -> bar))
    dyn(foo -> bar, baz -> Some(1))            .check(dyn(foo -> bar, baz -> 1))

    dyn(foo -> bar, baz -> 1).transform(baz).using(_.int.in.noneIf(_ <= 1)).check(dyn(foo -> bar))
    dyn(foo -> bar, baz -> 1).transform(baz).using(_.int.in.noneIf(_ >  1)).check(dyn(foo -> bar, baz -> 1)) // does nothing
  }
}

// ===========================================================================
