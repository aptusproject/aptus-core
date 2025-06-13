package aptus
package aptdata
package ops
package common

// ===========================================================================
/** only for abstract methods and methods that can be defined based on those */
trait CommonRenameTrait[Data] {
  @abstrct protected[ops] def _rename(target: TargetData): Data

  // ---------------------------------------------------------------------------
  @abstrct       def rename(mapping: Map[Key, Key]): Data

  // ---------------------------------------------------------------------------
  @abstrct       def rename(sel: Sel2)       : _Rename
  @abstrct       def rename          (from: NoRenarget): _Rename
  @abstrct       def renameGuaranteed(from: NoRenarget): _Rename

    // ---------------------------------------------------------------------------
    abstract class _Rename private[aptdata] { def to(to: Key): Data }

  // ---------------------------------------------------------------------------
  @nonovrd final def rename(renarget1: Renarget, more: Renarget*): Data = rename(renarget1 +~ more)
  @nonovrd final def rename(renargets: Renargets)                : Data = TargetData.parse(renargets).pipe(_rename)

  // ---------------------------------------------------------------------------
  // only really needed for remove & rename, because others like retain() can use: .retain("foo".guaranteePresence)
  @nonovrd final def renameGuaranteed(renarget1: Renarget, more: Renarget*): Data =  rename(renarget1 +! more)
  @nonovrd final def renameGuaranteed(renargets: Seq[Renarget])            : Data =
    _rename(Renargets(renargets, guaranteed = true).pipe(TargetData.parse)) }

// ===========================================================================

