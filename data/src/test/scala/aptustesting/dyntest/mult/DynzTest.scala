package aptustesting
package dyntest
package mult

import utest._

// ===========================================================================
object DynzTest extends TestSuite {
  val tests = Tests {
    test(Try {
    _Mult1.asDynz.increment(foo).asDyns }.check(Error.TransformSpecificType, IntegerLike)) } }

// ===========================================================================
