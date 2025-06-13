package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynAccessorsTest extends TestSuite {
  import aptus.dyn._

  // ===========================================================================
  val tests = Tests {
    test(assert(_Sngl1            .string (foo) ==      bar))
    test(assert(_Sngl1            .string_(foo) == Some(bar)))
    test(assert(_Sngl1.remove(foo).string_(foo) == None))

    test(assert(_Sngl1z            .strings (foo) ==       List(bar1, bar2)))
    test(assert(_Sngl1z            .strings_(foo) == Some(List(bar1, bar2))))
    test(assert(_Sngl1z.remove(foo).strings_(foo) == None))

    test(Try { _Sngl1 .strings (foo) }.check(Error.AccessAsSpecificType, BasicType._String))
    test(Try { _Sngl1 .strings_(foo) }.check(Error.AccessAsSpecificType, BasicType._String))
    test(Try { _Sngl1z.string  (foo) }.check(Error.AccessAsSpecificType, BasicType._String))
    test(Try { _Sngl1z.string_ (foo) }.check(Error.AccessAsSpecificType, BasicType._String)) } }

// ===========================================================================
