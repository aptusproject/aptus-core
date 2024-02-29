package aptustesting

import aptus._
import utest._

// ===========================================================================
object StatsTests extends TestSuite {

  val tests = Tests {
    test(compare(Seq.empty[Double].statsOpt, None))

    // ---------------------------------------------------------------------------
    val statsOfOne: aptmisc.DoubleStats = Seq(2).statsOpt.get

    test(compare(statsOfOne.min   , 2))
    test(compare(statsOfOne.mean  , 2.0))
    test(compare(statsOfOne.stdev , 0.0))
    test(compare(statsOfOne.median, 2))
    test(compare(statsOfOne.IQR   , 0))
    test(compare(statsOfOne.max   , 2 ))

    // ---------------------------------------------------------------------------
    val statsInts: aptmisc.DoubleStats = Seq(3, 1, 2).statsOpt.get

    test(compare(statsInts.min   , 1))
    test(compare(statsInts.mean  , 2.0))
    test(compare(statsInts.stdev .formatDecimals(1), "0.8"))
    test(compare(statsInts.median, 2   ))
    test(compare(statsInts.IQR   , 2   ))
    test(compare(statsInts.max   , 3 ))

    // ---------------------------------------------------------------------------
    val statsDoubles: aptmisc.DoubleStats = Seq(3.3, 1.1, 2.2).statsOpt.get

    test(compare(statsDoubles.min                     ,  1.1))
    test(compare(statsDoubles.mean  .formatDecimals(1), "2.2"))
    test(compare(statsDoubles.stdev .formatDecimals(1), "0.9"))
    test(compare(statsDoubles.median.formatDecimals(1), "2.2"))
    test(compare(statsDoubles.IQR   .formatDecimals(1), "2.2"))
    test(compare(statsDoubles.max                     ,  3.3 )) } }

// ===========================================================================
