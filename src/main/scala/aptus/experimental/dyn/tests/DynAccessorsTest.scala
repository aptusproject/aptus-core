package aptus
package experimental
package dyntest

// ===========================================================================
object DynAccessorsTest {
  import dyn._
  import Dyn .dyn
  import Dyns.dyns

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    assert(_Sngl1.string(foo) == bar) } }

// ===========================================================================