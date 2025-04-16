package aptus
package aptdata
package meta

// ===========================================================================
package object selectors extends SelectorsTrait

  // ---------------------------------------------------------------------------
  package selectors {

    trait SelectorsTrait {
      private[aptus] type Keys   = Seq[Key]
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