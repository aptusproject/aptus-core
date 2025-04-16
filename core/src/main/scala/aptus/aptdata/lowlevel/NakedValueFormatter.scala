package aptus
package aptdata
package lowlevel

// ===========================================================================
object AnyValueFormatter {
  import DataFormatting._

  // ---------------------------------------------------------------------------
  def format(value: Any): String = value match {
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
    case x: Date       => formatDate    (x)
    case x: DateTime   => formatDateTime(x)
    case x: Instant    => formatInstant (x)

    // ---------------------------------------------------------------------------
    case x: ByteBuffer => formatBinary(x) } }

// ===========================================================================
