package aptus
package aptdata
package ops
package common

// ===========================================================================
/* only for abstract methods and methods that can be defined based on those */
trait CommonOpsTrait[Data]
    extends CommonPresenceAbsenceTrait[Data]
    with    CommonRenameTrait         [Data]
    with    CommonRemoveRetainTrait   [Data]
    with    CommonAddReplacePutTrait  [Data] {

  @abstrct       def reorderKeys           (f: SKeysFunction): Data
  @abstrct       def reorderKeysRecursively(f: SKeysFunction): Data
  @nonovrd final def sorted                                  : Data = reorderKeysRecursively(_.sorted)

  // ===========================================================================
  @nonovrd final def convert(target1: Target, more: Target*): ConvertOps[Data] = convert(target1 +~ more)
  @abstrct       def convert(targets: Targets)              : ConvertOps[Data]

  // ===========================================================================
  @nonovrd final def nest(nestee1: Key, more: Key*): NestOps[Data] = nest(nestee1 +: more)
  @abstrct       def nest(nestees: Keyz)           : NestOps[Data] }

// ===========================================================================
