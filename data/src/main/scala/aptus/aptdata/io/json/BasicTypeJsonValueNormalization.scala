package aptus
package aptdata
package io
package json

import enumeratum.EnumEntry
import meta.basic.EnumValue
import aptdata.lowlevel.DataFormatting

// ===========================================================================
object BasicTypeJsonValueNormalization { // can't move to 'basic' package because of reliance on enumeratum

  val partial: PartialFunction[Any, Any] = {
    case x: String     => x
    case x: Int        => x
    case x: Double     => x
    case x: Boolean    => x

    // ---------------------------------------------------------------------------
    case x: EnumValue   => x.stringValue
    case x: EnumEntry   => x.entryName

    // ---------------------------------------------------------------------------
    case x: Byte       => x
    case x: Short      => x
    case x: Long       => x
    case x: Float      => x

    // ---------------------------------------------------------------------------
    case x: BigInt     => DataFormatting.formatBigInt(x) // can't trust JSON with bignums
    case x: BigDec     => DataFormatting.formatBigDec(x) // can't trust JSON with bignums

    // ---------------------------------------------------------------------------
    case x: Date     => DataFormatting.formatDate(x)
    case x: DateTime => DataFormatting.formatDateTime (x)
    case x: Instant  => DataFormatting.formatInstant(x)

    // ---------------------------------------------------------------------------
    case x: ByteBuffer => DataFormatting.formatBinary(x) } }

// ===========================================================================