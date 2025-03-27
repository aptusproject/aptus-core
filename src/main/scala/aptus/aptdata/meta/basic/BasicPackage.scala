package gallia

// ===========================================================================
package object basic extends aptus.min.AptusMinimal {
  private[basic] type CT[T]      = scala.reflect.ClassTag[T]
  private[basic] def ctag[T: CT] = scala.reflect.classTag[T]

  // ---------------------------------------------------------------------------
  implicit class BasicType__(val value: BasicType)
    extends BasicTypeBooleanChecks

  // ---------------------------------------------------------------------------
  private[basic] type FullNameString = String
  private[basic] type AnyValue       = String

  // ---------------------------------------------------------------------------
  private[basic] object CustomOrdering {
    val localDate     = aptus.localDateOrdering
    val localDateTime = aptus.localDateTimeOrdering
    val byteBuffer    = aptus.byteBufferOrdering }

  // ===========================================================================
  private[basic] trait EnumEntry {
      val entryName: String =
        getClass.getSimpleName.stripSuffixGuaranteed("$") }

    // ---------------------------------------------------------------------------
    trait Enum[E <: EnumEntry] { import basic.BasicType._
      def findValues: Seq[E] = // excludes those in anticipation of gallia: _LocalTime, _OffsetDateTime, _ZonedDateTime
        Seq(
            _String, _Boolean, _Int, _Double,
            _Byte, _Short, _Long, _Float,
            _BigInt, _BigDec,
            _Date, _DateTime, _Instant,
            _Binary )
          .map(_.asInstanceOf[E]) } }

// ===========================================================================
