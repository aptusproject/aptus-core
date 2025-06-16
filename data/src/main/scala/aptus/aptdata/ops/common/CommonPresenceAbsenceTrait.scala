package aptus
package aptdata
package ops
package common

// ===========================================================================
/** only for abstract methods and methods that can be defined based on those */
trait CommonPresenceAbsenceTrait[Data] {
  @abstrct protected[ops] def _ensurePresent(target: TargetEither): Data

      // selection: most don't make sense, consider eg: .ensurePresent(_.firstKey)

      @nonovrd final def ensurePresent(target1: NoRenarget, more: NoRenarget*): Data =  ensurePresent((target1 +: more).toList)
      @nonovrd final def ensurePresent(targets: Seq[NoRenarget])              : Data = _ensurePresent(NoRenargets.mayBeMissing(targets).pipe(TargetData.parse).in.right)

    // ---------------------------------------------------------------------------
    @abstrct protected[ops] def _ensureAbsent(target: TargetEither): Data

      // selection: most don't make sense, consider eg: .ensureAbsent(_.firstKey)

      @nonovrd final def ensureAbsent(target1: NoRenarget, more: NoRenarget*): Data =  ensureAbsent((target1 +: more).toList)
      @nonovrd final def ensureAbsent(targets: Seq[NoRenarget])              : Data = _ensureAbsent(NoRenargets.mayBeMissing(targets).pipe(TargetData.parse).in.right) }

// ===========================================================================

