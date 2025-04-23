package aptustesting
package dyntest

// ===========================================================================
object DynAccessorsTest {
  import aptus.dyn._

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    assert(_Sngl1            .string (foo) ==      bar)
    assert(_Sngl1            .string_(foo) == Some(bar))
    assert(_Sngl1.remove(foo).string_(foo) == None)

    assert(_Sngl1z            .strings (foo) ==       List(bar1, bar2))
    assert(_Sngl1z            .strings_(foo) == Some(List(bar1, bar2)))
    assert(_Sngl1z.remove(foo).strings_(foo) == None)

    Try { _Sngl1 .strings (foo) }.check(Error.AccessAsSpecificType, BasicType._String)
    Try { _Sngl1 .strings_(foo) }.check(Error.AccessAsSpecificType, BasicType._String)
    Try { _Sngl1z.string  (foo) }.check(Error.AccessAsSpecificType, BasicType._String)
    Try { _Sngl1z.string_ (foo) }.check(Error.AccessAsSpecificType, BasicType._String) } }

// ===========================================================================
