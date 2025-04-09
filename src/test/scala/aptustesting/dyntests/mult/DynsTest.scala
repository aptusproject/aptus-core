package aptustesting
package dyntest
package mult

// ===========================================================================
object DynsTest {
  import aptus.experimental.dyn._
  import Dyn.{Empty, Dummy, Dummy2, dummy, dyn}
  import Dyns.dyns
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    //TODO
  }
}

// ===========================================================================
