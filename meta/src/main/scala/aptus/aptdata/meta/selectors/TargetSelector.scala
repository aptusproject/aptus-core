package aptus
package aptdata
package meta
package selectors

// ===========================================================================
/** dynamic target selector */
sealed trait TargetSelector extends PresenceGuarantee[TargetSelector] {
    protected[aptdata] val guaranteed: Boolean
    protected[aptdata] val selection : SKeysSelection

    // ---------------------------------------------------------------------------
              def setPresenceGuarantee(value: Boolean): TargetSelector
    protected def formatNonGuaranteed: String

    // ---------------------------------------------------------------------------
    override final def toString     : String = formatDefault
    @nonovrd final def formatDefault: String = if (guaranteed) formatNonGuaranteed.append("[guaranteed]]") else formatNonGuaranteed

    // ---------------------------------------------------------------------------
    def targetData(skeys: SKeys)         : td.TargetData = selection.apply(skeys)             .pipe(td.TargetData.parseSKeys(guaranteed = false))
    def targetData(skeys: SKeys, to: Key): td.TargetData = selection.apply(skeys).map(_ ~> to).pipe(td.TargetData.parseRens (guaranteed = false)) }

  // ===========================================================================
  object TargetSelector { // a241127165900 - can't have it all at once (eg dynamic + path)

    /** for eg: key.startsWith("...") */
    case class Predicate(
          protected[aptdata] val p: SKey => Boolean,
          protected[aptdata] val guaranteed: Boolean = false)
        extends TargetSelector {

      // ---------------------------------------------------------------------------
      override val selection: SKeysSelection = _.filter(p)

      override protected def formatNonGuaranteed: String = "Predicate"
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ===========================================================================
    /** for eg: keys.head */
    case class SelectOne(
          protected[aptdata] val f: SKeys => SKey,
          protected[aptdata] val guaranteed: Boolean = false)
        extends TargetSelector {
      override val selection: SKeysSelection = f(_).in.seq

      // ---------------------------------------------------------------------------
      override protected def formatNonGuaranteed: String = "SelectOne"
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ===========================================================================
    /** for eg: keys.take(3) */
    case class SelectMultiple(
          protected[aptdata] val f: SKeysSelection,
          protected[aptdata] val guaranteed: Boolean = false)
        extends TargetSelector {
      override val selection: SKeysSelection = f

      // ---------------------------------------------------------------------------
      override protected def formatNonGuaranteed: String = "SelectMultiple"
      @nonovrd           def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) } }

// ===========================================================================
