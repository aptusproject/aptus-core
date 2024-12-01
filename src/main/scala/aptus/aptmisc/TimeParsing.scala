package aptus
package aptmisc

import util.chaining._
import aptutils.TimeUtils._
import java.time.format.DateTimeFormatter.ofPattern

// ===========================================================================
final class TimeParsing private[aptus] (str: String) {
  def parseInstant        : Instant        = java.time.Instant.from(                               IsoFormatterInstant.parse(str))
  def parseLocalDateTime  :  LocalDateTime = java.time. LocalDateTime.parse(str.replace(" ", "T"), IsoFormatterLocalDateTime) // https://stackoverflow.com/questions/9531524/in-an-iso-8601-date-is-the-t-character-mandatory
  def parseOffsetDateTime : OffsetDateTime = java.time.OffsetDateTime.parse(str,                   IsoFormatterOffsetDateTime)
  def parseZonedDateTime  :  ZonedDateTime = java.time. ZonedDateTime.parse(str,                   IsoFormatterZonedDateTime)

  def parseLocalDate      :  LocalDate     = java.time. LocalDate    .parse(str, IsoFormatterLocalDate)
  def parseLocalTime      :  LocalTime     = java.time. LocalTime    .parse(str, IsoFormatterLocalTime)

  // ===========================================================================
  def parseLocalDateTime(pattern: String): LocalDateTime = ofPattern(pattern).pipe(java.time.LocalDateTime.parse(str, _))
  def parseLocalDate    (pattern: String): LocalDate     = ofPattern(pattern).pipe(java.time.LocalDate    .parse(str, _))
  def parseLocalTime    (pattern: String): LocalTime     = ofPattern(pattern).pipe(java.time.LocalTime    .parse(str, _))

  // ---------------------------------------------------------------------------
  def parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime = java.time.LocalDateTime.parse(str, formatter)   // eg "2021-01-08T01:02:03".dateTime
  def parseLocalDate    (formatter: DateTimeFormatter): LocalDate     = java.time.LocalDate    .parse(str, formatter)   // eg "2021-01-08"         .date
  def parseLocalTime    (formatter: DateTimeFormatter): LocalTime     = java.time.LocalTime    .parse(str, formatter) } // eg            "01:02:03".time

// ===========================================================================