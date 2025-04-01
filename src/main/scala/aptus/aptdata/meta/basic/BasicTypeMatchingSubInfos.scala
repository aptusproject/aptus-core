package aptus
package aptdata
package meta
package basic

// ===========================================================================
private[meta] object BasicTypeMatchingSubInfos { // for use by schema "value extraction"

  @TypeMatching
  def apply(nestingPredicate: PartialFunction[AnyValue, Boolean])(info: Info)(multiple: Multiple)(value: AnyValue): Seq[SubInfo] = {

    def filter(basicType: BasicType.Selector): Seq[SubInfo] =
      info.union.filter(_ == SubInfo(multiple, basicType(BasicType)))

    // ---------------------------------------------------------------------------
    val basic: PartialFunction[AnyValue, Seq[SubInfo]] = {

      // ---------------------------------------------------------------------------
      case _: Boolean => filter(_._Boolean)
      case _: String  => filter(_._String)
      case _: Int     => filter(_._Int)
      case _: Double  => filter(_._Double)

      // ---------------------------------------------------------------------------
      case _: Byte    => filter(_._Byte)
      case _: Short   => filter(_._Short)
      case _: Long    => filter(_._Long)
      case _: Float   => filter(_._Float)

      // ---------------------------------------------------------------------------
      case _: BigInt  => filter(_._BigInt)
      case _: BigDec  => filter(_._BigDec)

      // ---------------------------------------------------------------------------
      case _: Date     => filter(_._Date)
      case _: DateTime => filter(_._DateTime)
      case _: Instant  => filter(_._Instant)

      // ---------------------------------------------------------------------------
      case _: ByteBuffer => filter(_._BigDec)

      // ---------------------------------------------------------------------------
      case _: meta.basic.EnumValue => info.union.filter(_.isEnmMatching(multiple)) }

    // ---------------------------------------------------------------------------
    val nesting: PartialFunction[AnyValue, Seq[SubInfo]] =
      nestingPredicate.andThen { matches =>
        if (matches) info.union.filter(_.valueType.isNesting) else Nil }

    // ---------------------------------------------------------------------------
    basic
      .orElse(nesting)
      .apply(value) } }

// ===========================================================================
