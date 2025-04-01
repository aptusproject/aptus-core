package aptus
package aptdata
package lowlevel

// ===========================================================================
object DataValueTransforming {
  def   intFromDouble(d: Double): Int   = d.toInt  .ensuring(_.toDouble == d)
  def  byteFromDouble(d: Double): Byte  = d.toByte .ensuring(_.toDouble == d)
  def shortFromDouble(d: Double): Short = d.toShort.ensuring(_.toDouble == d)

  // ===========================================================================
  def doubleFitsFloat(d: Double): Boolean =
      d <= java.lang.Float.MAX_VALUE &&
      d >= java.lang.Float.MIN_VALUE

    // ---------------------------------------------------------------------------
    def doubleFitsLong(d: Double): Boolean =
      d <= java.lang.Long.MAX_VALUE &&
      d >= java.lang.Long.MIN_VALUE

  // ===========================================================================
   def longFromDouble(d: Double): Long  = d.ensuring(doubleFitsLong(_)) .toLong.ensuring(_.toDouble == d)
  def floatFromDouble(d: Double): Float = d.ensuring(doubleFitsFloat(_)).toFloat /* note: precision may also be affected */ }

// ===========================================================================
