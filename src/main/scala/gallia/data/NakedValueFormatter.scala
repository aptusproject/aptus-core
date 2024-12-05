package gallia
package data

import adaptor.GalliaScalaAliases._

// ===========================================================================
private object NakedValueFormatter {
  import DataFormatting._

  // ---------------------------------------------------------------------------
  def format(value: Any/*NakedValue*/): String = value match {
    case x: String     => x
    case x: Int        => formatInt    (x)
    case x: Double     => formatDouble (x)
    case x: Boolean    => formatBoolean(x)

    // ---------------------------------------------------------------------------
    case x: Byte       => formatByte (x)
    case x: Short      => formatShort(x)
    case x: Long       => formatLong (x)
    case x: Float      => formatFloat(x)

    // ---------------------------------------------------------------------------
    case x: BigInt     => formatBigInt(x) // can't trust JSON with bignums
    case x: BigDec     => formatBigDec(x) // can't trust JSON with bignums

    // ---------------------------------------------------------------------------
    case x: LocalDate      => formatLocalDate(x)
    case x: LocalDateTime  => formatLocalDateTime (x)
    case x: Instant        => formatInstant(x)

    // ---------------------------------------------------------------------------
    case x: ByteBuffer => formatBinary(x) } }

// ===========================================================================
