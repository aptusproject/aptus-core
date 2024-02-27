package aptustesting

import aptus._
import utest._

// ===========================================================================
object NumberTests extends TestSuite {

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(compare(List(2, 3, 1).mean                   , 2.0))
    test(compare(List(2, 3, 1).median                 , 2))
    test(compare(List(2, 3, 1).percentile(50)         , 2))
    test(compare(List(2, 3, 1).stdev.formatDecimals(2), "0.82"))
    test(compare(List(2, 3, 1).minMax, (1, 3)))
    test(compare(List(2, 3, 1).range , 2))
    test(compare(List(2, 3, 1).IQR   , 2.0))
  }

}

// ===========================================================================