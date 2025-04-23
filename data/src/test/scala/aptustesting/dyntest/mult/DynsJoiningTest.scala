package aptustesting
package dyntest
package mult

// ===========================================================================
object DynsJoiningTest {
  import aptus.dyn._

  // ===========================================================================
  private val bar1_T = dyn(foo -> bar1, qux -> T)
  private val bar1_F = dyn(foo -> bar1, qux -> F)
  private val bar2_T = dyn(foo -> bar2, qux -> T)
  private val bar3_T = dyn(foo -> bar3, qux -> T)

  // ---------------------------------------------------------------------------
  private val that0 = dyns(bar1_T, bar1_F)
    private val that1 = that0.append(bar3_T)
    private val that2 = that0.union(dyns(bar2_T, bar3_T))
  
  // ---------------------------------------------------------------------------
  private val expectedJoin =
      dyns(
        _Sngl1x1.add(qux -> T),
        _Sngl1x1.add(qux -> F) )

    // ---------------------------------------------------------------------------
    private val expectedBringAll =
      expectedJoin.append(
        _Sngl1x2.add(qux -> T))

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    _Mult1.join(that1, via = foo).check(expectedJoin)
    _Mult1.join(that1)           .check(expectedJoin) /* costly, favor providing join key whenever possible */

    _Mult1.bringAll(that2, via = foo).check(expectedBringAll)
    _Mult1.bringAll(that2)           .check(expectedBringAll) /* costly, favor providing join key whenever possible */ } }

// ===========================================================================
