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

  // ===========================================================================
  @nonovrd final def convert(target1: Target, more: Target*): ConvertOps[Data] = convert(target1 +~ more)
  @abstrct       def convert(targets: Targets)              : ConvertOps[Data]

  // ===========================================================================
  // TODO: t250606100085 - offer multiple at least, maybe paths?
  @abstrct def nest(nestee: Key): NestOps[Data] }

// ===========================================================================
