package aptus
package aptmisc

import util.chaining._

import java.time._
import java.time.format.DateTimeFormatter

import aptutils.TimeUtils._

// ===========================================================================
final class TimeParsing private[aptus] (str: String) {
  def parseInstant        : Instant        = Instant.from(                               IsoFormatterInstant.parse(str))
  def parseLocalDateTime  :  LocalDateTime =  LocalDateTime.parse(str.replace(" ", "T"), IsoFormatterLocalDateTime) // https://stackoverflow.com/questions/9531524/in-an-iso-8601-date-is-the-t-character-mandatory
  def parseOffsetDateTime : OffsetDateTime = OffsetDateTime.parse(str,                   IsoFormatterOffsetDateTime)
  def parseZonedDateTime  :  ZonedDateTime =  ZonedDateTime.parse(str,                   IsoFormatterZonedDateTime)

  def parseLocalDate      :  LocalDate     =  LocalDate    .parse(str, IsoFormatterLocalDate)
  def parseLocalTime      :  LocalTime     =  LocalTime    .parse(str, IsoFormatterLocalTime)

  // ===========================================================================
  def parseLocalDateTime(pattern: String): LocalDateTime = DateTimeFormatter.ofPattern(pattern).pipe(LocalDateTime.parse(str, _))
  def parseLocalDate    (pattern: String): LocalDate     = DateTimeFormatter.ofPattern(pattern).pipe(LocalDate    .parse(str, _))
  def parseLocalTime    (pattern: String): LocalTime     = DateTimeFormatter.ofPattern(pattern).pipe(LocalTime    .parse(str, _))

  // ---------------------------------------------------------------------------
  def parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime = LocalDateTime.parse(str, formatter)   // eg "2021-01-08T01:02:03".dateTime
  def parseLocalDate    (formatter: DateTimeFormatter): LocalDate     = LocalDate    .parse(str, formatter)   // eg "2021-01-08"         .date
  def parseLocalTime    (formatter: DateTimeFormatter): LocalTime     = LocalTime    .parse(str, formatter) } // eg            "01:02:03".time

// ===========================================================================