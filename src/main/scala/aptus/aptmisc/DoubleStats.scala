package aptus
package aptmisc

// ===========================================================================
// TODO: add a fuller version with size, kurtosis, skewness, geometric/quadratic means, ...
/** for low-tech stats (e.g. data exploration) */
case class DoubleStats(
    min   : Double,
    mean  : Double, // arthmetic's
    stdev : Double, // population's
    median: Double,
    IQR   : Double,
    max   : Double) {

  override def toString: String = formatDefault
    def formatDefault: String =
      Seq(
          "min"    -> min,
          "mean"   -> mean,
          "stdev"  -> stdev,
          "median" -> median,
          "IQR"    -> IQR,
          "max"    -> max)
        .mkString(", ") }

// ===========================================================================