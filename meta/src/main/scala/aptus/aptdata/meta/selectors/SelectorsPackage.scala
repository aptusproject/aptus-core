package aptus
package aptdata
package meta

// ===========================================================================
package object selectors extends SelectorsTrait {

    trait PresenceGuarantee[T] {
      def setPresenceGuarantee(value: Boolean): T

      // ---------------------------------------------------------------------------
      @nonovrd final def guaranteePresence: T = setPresenceGuarantee(value = true)
      @nonovrd final def mayBeMissing     : T = setPresenceGuarantee(value = false) }

    // ===========================================================================
    /** Explicit target: Key, Ren, Path, RPath - most common case */
    @pseudosealed trait Targetable extends Any { def formatDefault: String }

      // ---------------------------------------------------------------------------
      /** No-renaming target: Key or Path - mostly just for .remove() */
      @pseudosealed trait NoRenargetable extends Any with Targetable { def formatDefault: String }

      // ---------------------------------------------------------------------------
      /** Renaming Target: Ren or RPath - mostly just for .rename() */
      @pseudosealed trait Renargetable extends Any with Targetable { def formatDefault: String }

    // ===========================================================================
    type Enm  = enumeratum.EnumEntry

    // ---------------------------------------------------------------------------
    val PathSeparator = " |> "
    val  RenSeparator = " ~> " }

  // ===========================================================================
  package selectors {

    trait SelectorsTrait {
      private[aptus] type KeySet = Set[Key]

      private[aptus] type BKey    = Symbol
      private[aptus] type SKey    = String

      private[aptus] type SKeys   = Seq[SKey]
      private[aptus] type SKeySet = Set[SKey]

      // ---------------------------------------------------------------------------
      private[aptus] type SKeyPred       = SKey  => Boolean
      private[aptus] type SKeysSelection = SKeys => SKeys
      private[aptus] type SKeysFunction  = SKeys => SKeys } }

// ===========================================================================