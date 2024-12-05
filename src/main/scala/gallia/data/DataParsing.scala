package gallia
package data

// ===========================================================================
object DataParsing {
  import adaptor.GalliaScalaAliases._
  import aptus.{byteBuffer, String_}
  import util.chaining._

/*private[data] */val Base64StringPrefix = "base64:"

  // ---------------------------------------------------------------------------
  // borrowed from gallia (TODO: t241130165320 - refactor)
                                            @inline def parseLocalDate     (value: String) = value.time.parseLocalDate     /* aptus' */
                                            @inline def parseLocalTime     (value: String) = value.time.parseLocalTime     /* aptus' */
                                            @inline def parseLocalDateTime (value: String) = value.time.parseLocalDateTime /* aptus' */

                                            @inline def parseOffsetDateTime(value: String) = value.time.parseOffsetDateTime /* aptus' */
                                            @inline def parseZonedDateTime (value: String) = value.time.parseZonedDateTime  /* aptus' */
                                            @inline def parseInstant       (value: String) = value.time.parseInstant        /* aptus' */

                                            // ---------------------------------------------------------------------------
                                            def parseBinary(value: String): ByteBuffer =
                                              value
                                                .stripPrefixGuaranteed(Base64StringPrefix)
                                                .unBase64
                                                .pipe(byteBuffer) }

// ===========================================================================
