package aptus
package aptdata
package meta
package basic

// ===========================================================================
/** a simple wrapper for enum values */
case class EnumValue(stringValue: EnumValue.EnumStringValue) extends AnyVal {
    override def toString: String = stringValue /* used by convert(myEnum).toStr */ }

  // ---------------------------------------------------------------------------
  object EnumValue {
    type EnumStringValue = String /* eg "Red" for color enum */

    val Dummy = EnumValue(stringValue = "_")

    def enumValueOrdering: Ordering[EnumValue] = Ordering.by(_.stringValue)

    def valid(values: Seq[EnumValue]): Boolean = !(values.isEmpty || values.distinct != values) }

// ===========================================================================
