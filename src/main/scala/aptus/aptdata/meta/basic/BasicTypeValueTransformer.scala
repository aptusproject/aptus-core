package aptus
package aptdata
package meta
package basic

// ===========================================================================
object BasicTypeValueTransformer {

  def apply(basicType: BasicType)(value: AnyValue): AnyValue =
    basicType match {
      case BasicType._String  => value.asInstanceOf[String]
      case BasicType._Boolean => value.asInstanceOf[Boolean]
      case BasicType._Double  => value.asInstanceOf[Double]
      case BasicType._Int     => BasicType._Int.parseAnyToDouble(value)

      // ---------------------------------------------------------------------------
      case _: BasicType._Enm  => BasicType._Enm.parseAny(value)

      // ---------------------------------------------------------------------------
      case BasicType._Long    => BasicType._Long .parseAnyToDouble(value)
      case BasicType._Float   => BasicType._Float.parseAnyToDouble(value)

      case BasicType._Byte    => BasicType._Byte .parseAnyToDouble(value)
      case BasicType._Short   => BasicType._Short.parseAnyToDouble(value)

      // ---------------------------------------------------------------------------
      case BasicType._BigInt  => stringOrLong  (BigInt    .apply, BigInt    .apply)(value)
      case BasicType._BigDec  => stringOrDouble(BigDecimal.apply, BigDecimal.apply)(value)

      // ---------------------------------------------------------------------------
      case BasicType._Date     => stringOrLong(BasicType._Date    .pair)(value)
      case BasicType._DateTime => stringOrLong(BasicType._DateTime.pair)(value)
      case BasicType._Instant  => stringOrLong(BasicType._Instant      .pair)(value)

      // ---------------------------------------------------------------------------
      case BasicType._Binary   => BasicType._Binary.parseAny(value) }

  // ===========================================================================
  private def stringOrLong[T](pair:    (String => T,         Long => T)): Any => T = stringOrLong(pair._1, pair._2)
  private def stringOrLong[T](ifString: String => T, ifLong: Long => T) : Any => T =
    _ match {
      case s: String => ifString(s)
      case n: Number => ifLong  (numberToLong(n)) }

  // ---------------------------------------------------------------------------
  private def stringOrDouble[T](ifString: String => T, ifDouble: Double => T): Any => T =
    _ match {
      case s: String => ifString(s)
      case n: Number => ifDouble(n.doubleValue) }

  // ===========================================================================
  private def numberToLong(n: Number): Long =
    n .doubleValue
      .assert(aptus.aptdata.lowlevel.DataValueTransforming.doubleFitsLong)
      .pype(d => d.toLong.ensuring(_.toDouble == d)) }

// ===========================================================================
