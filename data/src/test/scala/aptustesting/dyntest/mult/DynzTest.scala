package aptustesting
package dyntest
package mult

// ===========================================================================
object DynzTest {
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    Try {
    _Mult1.asDynz.increment(foo).asDyns }.check(Error.TransformSpecificType, IntegerLike) } }

// ===========================================================================
