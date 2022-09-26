package aptus
package aptutils

import scala.util.chaining._

// ===========================================================================
object MathUtils {
  
  def variancePopulation[A](coll: Seq[A], mean: Double)(implicit num: Numeric[A]): Double = _variance(coll, mean, correction = false)
  def varianceSample    [A](coll: Seq[A], mean: Double)(implicit num: Numeric[A]): Double = _variance(coll, mean, correction = true )
    
  def stdevPopulation   [A](coll: Seq[A], mean: Double)(implicit num: Numeric[A]): Double = _variance(coll, mean, correction = false).pipe(math.sqrt)
  def stdevSample       [A](coll: Seq[A], mean: Double)(implicit num: Numeric[A]): Double = _variance(coll, mean, correction = true ).pipe(math.sqrt)
  
    private def _variance[A](coll: Seq[A], mean: Double, correction: Boolean)(implicit num: Numeric[A]): Double =
      ((coll
            .iterator
            .map(num.toDouble)
            .map(_ - mean)
            .map(math.pow(_, 2))
            .sum) /
          coll.size + (if (correction) -1 else 0))

  // ---------------------------------------------------------------------------
  def percentile[A](coll: Seq[A], n: Double)(implicit num: Numeric[A]): Double =
    coll
      .map(num.toDouble)
      .toArray
      .pipe(new org.apache.commons.math3.stat.descriptive.DescriptiveStatistics(_))
      .getPercentile(n.require(_ >= 0).require(_ <= 100))

  // ===========================================================================
  def trimmedMean      [T : Numeric](diffs: Seq[T]): Double = _trimmed(diffs).mean
  def trimmedMedian    [T : Numeric](diffs: Seq[T]): Double = _trimmed(diffs).median          
  def trimmedStdev     [T : Numeric](diffs: Seq[T]): Double = _trimmed(diffs).stdev
  def trimmedCumulative[T : Numeric](diffs: Seq[T]): T      = _trimmed(diffs).sum

    // ---------------------------------------------------------------------------
    private def _trimmed[T : Numeric](diffs: Seq[T]): Seq[T] = {
      val fullSize    = diffs.size
      val outlierSize = (fullSize * 0.05).toInt
      diffs
        .sorted
        .drop(outlierSize)
        .dropRight(outlierSize) }

}

// ===========================================================================
