package aptus
package aptutils

import java.time._
import java.time.format.DateTimeFormatter

// ===========================================================================
object TimeUtils {

  // ===========================================================================
  def currentZoneOffset() = OffsetDateTime.now().getOffset() // eg for Toronto, will be -4 or -5 hours (based on daylight saving time) - don't cache

  // ---------------------------------------------------------------------------
  lazy val DefaultZoneId = ZoneId.systemDefault() // eg "America/Toronto" (-5 hours)

  // ---------------------------------------------------------------------------
  lazy val IsoFormatterInstant        = DateTimeFormatter.ISO_INSTANT
  lazy val IsoFormatterLocalDateTime  = DateTimeFormatter.ISO_LOCAL_DATE_TIME
  lazy val IsoFormatterOffsetDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME
  lazy val IsoFormatterZonedDateTime  = DateTimeFormatter.ISO_ZONED_DATE_TIME

  lazy val IsoFormatterLocalDate      = DateTimeFormatter.ISO_LOCAL_DATE
  lazy val IsoFormatterLocalTime      = DateTimeFormatter.ISO_LOCAL_TIME

  // ===========================================================================
  def elapsed[A](block: => A): (A, Long) = {
    val start  = System.currentTimeMillis()
    val result = block
    val end    = System.currentTimeMillis()

    (result, end - start) }

}

// ===========================================================================
