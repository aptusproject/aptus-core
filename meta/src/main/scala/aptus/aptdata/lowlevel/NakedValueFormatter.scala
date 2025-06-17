package aptus
package aptdata
package lowlevel

// ===========================================================================
object AnyValueFormatter {
  import DataFormatting._
  private type NakedValue = Any // as seen in Valew

  // ---------------------------------------------------------------------------
  def formatSupport(value: Any): String = partialFormatBasic(value)

  // ---------------------------------------------------------------------------
  def formatLeaf(value: NakedValue): String =
      (partialFormatBasic
        .orElse(partialFormatFallback)).apply(value)

    // ---------------------------------------------------------------------------
    private val partialFormatBasic: PartialFunction[Any, String] = {
      case x: String     =>               x
      case x: Int        => formatInt    (x)
      case x: Double     => formatDouble (x)
      case x: Boolean    => formatBoolean(x)

      // ---------------------------------------------------------------------------
      case x: Byte       => formatByte (x)
      case x: Short      => formatShort(x)
      case x: Long       => formatLong (x)
      case x: Float      => formatFloat(x)

      // ---------------------------------------------------------------------------
      case x: BigInt     => formatBigInt(x)
      case x: BigDec     => formatBigDec(x)

      // ---------------------------------------------------------------------------
      case x: Date       => formatDate    (x)
      case x: DateTime   => formatDateTime(x)
      case x: Instant    => formatInstant (x)

      // ---------------------------------------------------------------------------
      case x: ByteBuffer => formatBinary(x) }

    // ===========================================================================
    private val partialFormatFallback: PartialFunction[Any, String] = {
      case null   => "[null!]" // typically unintentional
      case x: Any => s"${x.toString}(${x.getClass})" } }

// ===========================================================================
