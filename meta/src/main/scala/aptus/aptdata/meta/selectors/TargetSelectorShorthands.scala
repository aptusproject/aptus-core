package aptus
package aptdata
package meta
package selectors

// ===========================================================================
object TargetSelectorShorthands
    extends MultipleKeySelectorShorthands
       with SelectOneShorthands

  // ---------------------------------------------------------------------------
  object MultipleKeySelectorShorthands extends MultipleKeySelectorShorthands
  object SelectOneShorthands           extends SelectOneShorthands

  // ===========================================================================
  trait MultipleKeySelectorShorthands { import TargetSelector._
    def keyPredicate(p: SKeyPred)   : TargetSelector      = Predicate(p)
    def selection(f: SKeysSelection): TargetSelector = SelectMultiple(f) // TODO: t250401120253 - rename

    // ===========================================================================
    def allKeys: TargetSelector = selection(x => x)

    // ---------------------------------------------------------------------------
    def allKeysBut(key1: Key, more: Key*): TargetSelector = allKeysBut(Keyz(key1 +: more))
    def allKeysBut(keys: Keyz)           : TargetSelector = keyPredicate(x => keys.notContainsKey(Key.from(x))) }

  // ===========================================================================
  trait SelectOneShorthands {
    def selectOne(f: SKeys => SKey): TargetSelector = TargetSelector.SelectOne(f)

    // ---------------------------------------------------------------------------
    def  soleKey: TargetSelector = selectOne(_.force.one) // TODO: custom error msg
    def firstKey: TargetSelector = selectOne(_.head)
    def  lastKey: TargetSelector = selectOne(_.last)

    // ---------------------------------------------------------------------------
    def keyAt(index: Index): TargetSelector =
      if (index >= 0) selectOne {      _.apply(         index) }
      else            selectOne { x => x.apply(x.size + index) } }

// ===========================================================================
