package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynReorderTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  private val xx = dyn(baz -> 1, foo -> bar)
  private val yy = _Sngl3.add(qux -> true)

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1.reorderKeys(_.reverse).check(xx))

    test(yy.reorderKeys           (_.reverse).check(dyn(qux -> true, p -> _Sngl1))) // worth keeping?
    test(yy.reorderKeysRecursively(_.reverse).check(dyn(qux -> true, p -> xx)))

    test(yy                                          .equivalent(yy).checkTrue())
    test(yy.reorderKeysRecursively(_.reverse).pipe(yy.equivalent)   .checkTrue()) } }

// ===========================================================================

