package aptus
package aptdata
package meta
package selectors

// ===========================================================================
object TargetSelectorShorthands {
  def explicitKey(value: Key): TargetSelector = TargetSelector.Explicit(value)

  def keyPredicate(p: SKeyPred): TargetSelector = TargetSelector.Predicate(p)

  def selectOne(f: SKeys => SKey): TargetSelector = TargetSelector.SelectOne(f)
def selection(f: SKeysSelection): TargetSelector = TargetSelector.SelectMultiple(f) // TODO: t250401120253 - rename

  def renaming(from: Key, to: Key): TargetSelector = TargetSelector.Renaming(Ren(from, to))
  def renaming(ren: Ren)          : TargetSelector = TargetSelector.Renaming(ren)

  def nesting(path: Path): TargetSelector = TargetSelector.Nesting(path)

  // ---------------------------------------------------------------------------
  def soleKey: TargetSelector = selectOne(_.force.one) // TODO: custom error msg
  def allKeys: TargetSelector = selection(x => x)

  // ---------------------------------------------------------------------------
  def allKeysBut(key1: Key, more: Key*): TargetSelector = allKeysBut(key1 +: more)
  def allKeysBut(keys: Keys)           : TargetSelector = keyPredicate(!keys.contains(_)) }

// ===========================================================================
