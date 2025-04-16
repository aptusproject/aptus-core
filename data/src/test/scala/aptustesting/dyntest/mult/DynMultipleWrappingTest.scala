package aptustesting
package dyntest
package mult

// ===========================================================================
object DynMultipleWrappingTest {
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    _Sngl1             .transformString(foo).using(_.reverse)  .check(_Sngl1e)
    _Mult1             .transformString(foo).using(_.reverse)  .check(_Mult1e)
    _Mult1.testDynz { _.transformString(foo).using(_.reverse) }.check(_Mult1e)
  }
}

// ===========================================================================
