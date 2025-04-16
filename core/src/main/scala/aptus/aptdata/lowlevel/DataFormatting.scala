package aptus
package aptdata
package lowlevel

import java.time.format.DateTimeFormatter

// ===========================================================================
@TypeMatching object DataFormatting {
  @inline def formatBoolean(value: Boolean): String = value.toString /* to "true" or "false" */

  // ---------------------------------------------------------------------------
  // TODO: stick to printf way? must still trim spaces with %d and trailing zeros with %f
  def formatInt   (value: Int)   : String  = value         .formatExplicit
  def formatByte  (value: Byte)  : String  = value.toInt   .formatExplicit
  def formatShort (value: Short) : String  = value.toInt   .formatExplicit
  def formatLong  (value: Long)  : String  = value         .formatExplicit

  def formatDouble(value: Double): String  = value         .formatExplicit
  def formatFloat (value: Float) : String  = value.toDouble.formatExplicit

  // ===========================================================================
  def formatBigInt(value: BigInt): String = value.bigInteger.toString /* stable */
  def formatBigDec(value: BigDec): String = value.bigDecimal.toString /* stable */

  // ===========================================================================
  def formatDate    (value: Date)    : String = DateTimeFormatter.ISO_DATE            .format(value)
  def formatDateTime(value: DateTime): String = DateTimeFormatter.ISO_LOCAL_DATE_TIME .format(value)
  def formatInstant (value: Instant) : String = DateTimeFormatter.ISO_INSTANT         .format(value)

  // ===========================================================================
  def formatBinary(bytes: ByteBuffer): String = bytes.array.toBase64.prepend(Base64StringPrefix)

    // ---------------------------------------------------------------------------
    private[lowlevel] val Base64StringPrefix = "base64:" }

// ===========================================================================
