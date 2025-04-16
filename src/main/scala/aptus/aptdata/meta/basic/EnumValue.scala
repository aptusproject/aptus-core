package aptus
package aptdata
package meta
package basic

// ===========================================================================
/** a simple wrapper for enum values */
case class EnumValue(stringValue: EnumStringValue) extends AnyVal {
    override def toString: String = stringValue /* used by convert(myEnum).toStr */ }

  // ---------------------------------------------------------------------------
  object EnumValue {
    def enumValueOrdering: Ordering[EnumValue] = Ordering.by(_.stringValue)

    def valid(values: Seq[EnumValue]): Boolean = !(values.isEmpty || values.distinct != values) }

// ===========================================================================