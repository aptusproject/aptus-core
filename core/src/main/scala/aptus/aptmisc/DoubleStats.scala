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

    def coefficientOfVariation: Double = stdev / mean

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
  object DoubleStats {
    def formatHeaderFields: Seq[String] = // eg for convenient {C,T}SV creation
      Seq(
        "min",
          "mean"  , "stdev",
          "median", "IQR",
        "max") }

// ===========================================================================