package aptustesting

import aptus._
import utest._

// ===========================================================================
object NumberTests extends TestSuite {

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test("stats") {
      test(compare(List(2, 3, 1).mean                   , 2.0))
      test(compare(List(2, 3, 1).median                 , 2))
      test(compare(List(2, 3, 1).percentile(50)         , 2))
      test(compare(List(2, 3, 1).stdev.formatDecimals(2), "0.82"))
      test(compare(List(2, 3, 1).minMax, (1, 3)))
      test(compare(List(2, 3, 1).range , 2))
      test(compare(List(2, 3, 1).IQR   , 2.0)) }

    // ===========================================================================
    test(isTrue(3 .isInBetween(fromInclusive = 1, toExclusive = 5)))
    test(isFlse(30.isInBetween(fromInclusive = 1, toExclusive = 5)))

    test(isTrue(3L .isInBetween(fromInclusive = 1, toExclusive = 5)))
    test(isFlse(30L.isInBetween(fromInclusive = 1, toExclusive = 5)))

    // ---------------------------------------------------------------------------
    test("assertRanges") {
      test("ints") {
        test(noop         (3 .assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(failAssertion(30.assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(noop         (3 .assertRangeInclusive(fromInclusive = 1, toInclusive = 5)))
        test(failAssertion(30.assertRangeInclusive(fromInclusive = 1, toInclusive = 5)))

        test(noop         (1 .assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(noop         (1 .assertRangeInclusive(fromInclusive = 1, toInclusive = 5)))
        test(failAssertion(5 .assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(noop         (5 .assertRangeInclusive(fromInclusive = 1, toInclusive = 5))) }

      // ---------------------------------------------------------------------------
      test("longs") {
        test(noop         (3L .assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(failAssertion(30L.assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(noop         (3L .assertRangeInclusive(fromInclusive = 1, toInclusive = 5)))
        test(failAssertion(30L.assertRangeInclusive(fromInclusive = 1, toInclusive = 5)))

        test(noop         (1L .assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(noop         (1L .assertRangeInclusive(fromInclusive = 1, toInclusive = 5)))
        test(failAssertion(5L .assertRange         (fromInclusive = 1, toExclusive = 5)))
        test(noop         (5L .assertRangeInclusive(fromInclusive = 1, toInclusive = 5))) } }
  }

}

// ===========================================================================