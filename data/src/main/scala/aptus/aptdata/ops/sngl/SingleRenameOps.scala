package aptus
package aptdata
package ops
package sngl

// ===========================================================================
trait SingleRenameOps { sngl: Dyn =>

  override final protected[ops] def _rename(target: TargetData): Dyn = target.rename(sngl)

  // ---------------------------------------------------------------------------
  override final def rename(mapping: Map[Key, Key]): Dyn = mapEntries(_.rename(mapping))

  override final def rename(sel: Sel2): _Rename = new _Rename { def to(to: Key): Dyn =
    sel(meta.selectors.SelectOneShorthands).targetData(sngl.skeys, to).rename(sngl) }

  override final def rename          (from: NoRenarget)  : _Rename = new _Rename { def to(to: Key): Dyn = rename(from ~> to) }
  override final def renameGuaranteed(from: NoRenarget)  : _Rename = new _Rename { def to(to: Key): Dyn = rename(
    ((from ~> to): Renarget).guaranteePresence) } }

// ===========================================================================