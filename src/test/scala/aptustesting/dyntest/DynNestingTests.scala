package aptustesting
package dyntest

// ===========================================================================
object DynNestingTest {
  import aptus.experimental.dyn._
  import Dyn .dyn
  import Dyns.dyns

  // ===========================================================================
  private val f1 = "f1"
  private val f2 = "f2"
  private val F  = "F"
  private val g  = "g"

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    _Sngl3.isPresent(p |> foo).checkTrue ()
    _Sngl3.isPresent(p |> FOO).checkFalse()

    // ---------------------------------------------------------------------------
    dyn(f1 -> foo , f2 -> foo , g -> 1)
        .nest(f1).under(F)
      .check(dyn(f2 -> foo, g -> 1, F -> dyn(f1 -> foo)))
  }
}

// ===========================================================================
