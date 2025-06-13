package aptus
package aptdata
package ops
package common

// ===========================================================================
/** only for abstract methods and methods that can be defined based on those */
trait CommonRemoveRetainTrait[Data] {
  @abstrct protected[ops] def _retain(target: TargetEither): Data

  @abstrct       def retainMultiple(values: Set[Key])      : Data
  @abstrct       def retainKey     (key:         Key)      : Data

  @nonovrd final def retain(sel: Sel)                      : Data = _retain(Sel(sel).in.left)
  @nonovrd final def retain(target1: Target, more: Target*): Data =  retain(target1 +~ more)
  @abstrct       def retain(targets: Targets)              : Data = TargetData.parse(targets).in.right.pipe(_retain)

    // ---------------------------------------------------------------------------
    // just for convenience/consistency (since can use .guaranteePresence with Target, unlike Renarget/NoRenarget)
    @nonovrd final def retainGuaranteed(target1: Target, more: Target*): Data =  retain(target1 +! more)
    @nonovrd final def retainGuaranteed(targets: Seq[Target])          : Data =  retain(Targets(targets, guaranteed = true))
    @nonovrd final def retainGuaranteed(sel: Sel)                      : Data = _retain(Sel(sel).guaranteePresence.in.left)

  // ===========================================================================
  @abstrct protected[ops] def _remove(target: TargetEither): Data

  @abstrct       def removeMultiple(values: Set[Key]): Data
  @abstrct       def removeKey     (value : Key)     : Data

  @nonovrd final def remove(sel: Sel)                              : Data = _remove(Sel(sel).in.left)
  @nonovrd final def remove(target1: NoRenarget, more: NoRenarget*): Data =  remove(target1 +~ more)
  @nonovrd final def remove(targets: NoRenargets)                  : Data = _remove(TargetData.parse(targets).in.right)

    // ---------------------------------------------------------------------------
    // only really needed for remove & rename, because others like retain() can use: .retain("foo".guaranteePresence)
    @nonovrd final def removeGuaranteed(target1: NoRenarget, more: NoRenarget*): Data =  remove(target1 +! more)
    @nonovrd final def removeGuaranteed(targets: Seq[NoRenarget])              : Data =  remove(NoRenargets(targets, guaranteed = true))
    @nonovrd final def removeGuaranteed(sel: Sel)                              : Data = _remove(Sel(sel).guaranteePresence.in.left) }

// ===========================================================================

