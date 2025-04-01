package aptus
package aptdata
package meta
package selectors

// ===========================================================================
sealed trait TargetSelector {
    val guaranteed: Boolean

              def setPresenceGuarantee(value: Boolean): TargetSelector
    protected def formatNonGuaranteed: String

    // ---------------------------------------------------------------------------
    override final def toString     : String = formatDefault
    @nonovrd final def formatDefault: String = if (guaranteed) formatNonGuaranteed.append("[guaranteed]]") else formatNonGuaranteed

    // ---------------------------------------------------------------------------
    @nonovrd final def guaranteePresence: TargetSelector = setPresenceGuarantee(value = true)
    @nonovrd final def mayBeMissing     : TargetSelector = setPresenceGuarantee(value = false) }

  // ===========================================================================
  object TargetSelector { // a241127165900 - can't have it all at once (eg dynamic + path)

    // ===========================================================================
    // explicit ones - TODO

    case class Explicit(value: Key, guaranteed: Boolean = false)
        extends TargetSelector {
      override protected def formatNonGuaranteed: String = value.name
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) } // TODO: boilerplate...

    // ---------------------------------------------------------------------------
    case class Renaming(value: Ren, guaranteed: Boolean = false)
        extends TargetSelector {
      override protected def formatNonGuaranteed: String = value.formatDefault
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ---------------------------------------------------------------------------
    case class Nesting(value: Path, guaranteed: Boolean = false)
        extends TargetSelector {
      override protected def formatNonGuaranteed: String = ???
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ===========================================================================
    // dynamic ones

    /** for eg: key.startsWith("...") */
    case class Predicate(p: SKey => Boolean, guaranteed: Boolean = false)
        extends TargetSelector {
      override protected def formatNonGuaranteed: String = ???
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ---------------------------------------------------------------------------
    /** for eg: keys.head */
    case class SelectOne(f: SKeys => SKey, guaranteed: Boolean = false)
        extends TargetSelector {
      override protected def formatNonGuaranteed: String = ???
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ---------------------------------------------------------------------------
    /** for eg: keys.take(3) */
    case class SelectMultiple(f: SKeysSelection, guaranteed: Boolean = false)
        extends TargetSelector {
      override protected def formatNonGuaranteed: String = ???
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) } }

// ===========================================================================
