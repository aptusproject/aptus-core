package aptustesting

import aptus._
import utest._

import java.time._
import java.time.format.DateTimeFormatter

// ===========================================================================
object TimeTests extends TestSuite {

  private val ms = "1,647,447,105,888"
  private val  s = "1,647,447,105" // max int is: 2,147,483,647
  private val  d = "19,067"

  // ---------------------------------------------------------------------------
  private val localDate2 = "2022-03-16"
  private val localTime2 =             "11:38:40"

  private val instant2       = "2022-03-16T15:38:40Z"
  private val  localDateTime2 = "2022-03-16T11:38:40"
  private val offsetDateTime2 = "2022-03-16T11:38:40-04:00"
  private val  zonedDateTime2 = "2022-03-16T11:38:40-04:00[America/Toronto]"

  // ===========================================================================
  private val InstantString        = "2022-03-11T19:05:20.537Z" // The Z on the end means UTC (that is, an offset-from-UTC of zero hours-minutes-seconds). The Z is pronounced “Zulu”
  private val LocalDateTimeString  = "2022-03-11T14:05:20.537"
  private val OffsetDateTimeString = "2022-03-11T14:05:20.537-05:00"
  private val ZonedDateTimeString  = "2022-03-11T14:05:20.537-05:00[America/Toronto]"
  private val LocalDateString      = "2022-03-11"
  private val LocalTimeString                 = "14:05:20.537"
  private val LocalTimeHoursMinutes           = "14:05"
  private val LocalTimeHoursMinutesSeconds    = "14:05:20"

  // ---------------------------------------------------------------------------
  private val instant        = Instant.parse(InstantString)

  private val  localDateTime:  LocalDateTime = instant.atLocal
  private val offsetDateTime: OffsetDateTime = instant.atOffsetDefault
  private val  zonedDateTime:  ZonedDateTime = instant.atZoneDefault

  private val localDate: LocalDate = localDateTime.toLocalDate
  private val localTime: LocalTime = localDateTime.toLocalTime
  
  // ===========================================================================
  private val i = instant2       .time.parseInstant
  private val l = localDateTime2 .time.parseLocalDateTime
  private val o = offsetDateTime2.time.parseOffsetDateTime
  private val z = zonedDateTime2 .time.parseZonedDateTime

  // ===========================================================================
  val tests = Tests {
    test(compare( d.remove(",").toLong.toLocalDate    .formatIso, localDate2))
  //test(compare( s.remove(",").toLong.toLocalDateTime.formatIso, "2022-03-16T12:11:45")) /* TODO: may cause issues with time change */
    test(compare(ms.remove(",").toLong.toInstant      .formatIso, "2022-03-16T16:11:45.888Z"))

    // ===========================================================================
    test("from instant") {
        test(compare(i.atLocal         ,  l))
        test(compare(i.atZoneDefault   ,  z))
      //test(compare(i.atOffsetDefault ,  o)) /* TODO: may cause issues with time change */
}
      // ---------------------------------------------------------------------------
      test("from LocalDateTime") {
        //test(compare(l.atInstantDefault,  i)) /* TODO: may cause issues with time change */
        test(compare(l.atZoneDefault   ,  z))
        //test(compare(l.atOffsetDefault ,  o)) /* TODO: may cause issues with time change */
      }

      // ---------------------------------------------------------------------------
      test("from OffsetDateTime") {
        test(compare(o.atInstant      ,  i))
        test(compare(o.atLocal        ,  l))
        test(compare(o.atZoneDefault  ,  z)) }

      // ---------------------------------------------------------------------------
      test("from ZonedDateTime") {
        test(compare(z.atInstant      ,  i))
        test(compare(z.atLocal        ,  l))
      //test(compare(z.atOffsetDefault,  o)) /* TODO: may cause issues with time change */
      }

    // ===========================================================================
    test(compare(localDate.atTime(localTime).toString, LocalDateTimeString))

    // ---------------------------------------------------------------------------
    test("time formatting") {
      test(compare(instant       .formatIso, InstantString))
      test(compare( localDateTime.formatIso,  LocalDateTimeString))
      test(compare(offsetDateTime.formatIso, OffsetDateTimeString)) /* TODO: may cause issues with time change */
      test(compare( zonedDateTime.formatIso,  ZonedDateTimeString))

      // ---------------------------------------------------------------------------
      test(compare(localDate.formatIso                , LocalDateString))
      test(compare(localTime.formatIso                , LocalTimeString))
      test(compare(localTime.formatHoursMinutes       , LocalTimeHoursMinutes))
      test(compare(localTime.formatHoursMinutesSeconds, LocalTimeHoursMinutesSeconds))

      // ===========================================================================
      test(compare(instant                        .toString, InstantString))
      test(compare( localDateTime.atInstantDefault.toString, InstantString)) /* TODO: may cause issues with time change */
      test(compare(offsetDateTime.toInstant       .toString, InstantString))
      test(compare( zonedDateTime.toInstant       .toString, InstantString))

      // ---------------------------------------------------------------------------
      test(compare( localDateTime                .toString, LocalDateTimeString))
      test(compare(offsetDateTime.toLocalDateTime.toString, LocalDateTimeString)) /* TODO: may cause issues with time change */
      test(compare( zonedDateTime.toLocalDateTime.toString, LocalDateTimeString))
      test(compare(instant       .atLocal        .toString, LocalDateTimeString))

      // ---------------------------------------------------------------------------
      test(compare(offsetDateTime                .toString, OffsetDateTimeString)) /* TODO: may cause issues with time change */
      test(compare( localDateTime.atOffsetDefault.toString, OffsetDateTimeString)) /* TODO: may cause issues with time change */
      test(compare(zonedDateTime .atOffsetDefault.toString, OffsetDateTimeString)) /* TODO: may cause issues with time change */
      test(compare(instant       .atOffsetDefault.toString, OffsetDateTimeString)) /* TODO: may cause issues with time change */

      // ---------------------------------------------------------------------------
      test(compare(zonedDateTime               .toString, ZonedDateTimeString))
      test(compare( localDateTime.atZoneDefault.toString, ZonedDateTimeString))
      test(compare(offsetDateTime.atZoneDefault.toString, ZonedDateTimeString)) /* TODO: may cause issues with time change */
      test(compare(instant       .atZoneDefault.toString, ZonedDateTimeString)) } } }

// ===========================================================================