package aptus
package aptdata
package lowlevel

// ===========================================================================
object DataParsing {

  // ---------------------------------------------------------------------------
  @inline def parseLocalDate     (value: String) = value.time.parseLocalDate     /* aptus' */
  @inline def parseLocalTime     (value: String) = value.time.parseLocalTime     /* aptus' */
  @inline def parseLocalDateTime (value: String) = value.time.parseLocalDateTime /* aptus' */

  @inline def parseOffsetDateTime(value: String) = value.time.parseOffsetDateTime /* aptus' */
  @inline def parseZonedDateTime (value: String) = value.time.parseZonedDateTime  /* aptus' */
  @inline def parseInstant       (value: String) = value.time.parseInstant        /* aptus' */

  // ---------------------------------------------------------------------------
  def parseBinary(value: String): ByteBuffer =
    value
      .stripPrefixGuaranteed(DataFormatting.Base64StringPrefix)
      .unBase64
      .pype(java.nio.ByteBuffer.wrap) }

// ===========================================================================
