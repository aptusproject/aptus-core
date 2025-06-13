package aptus
package aptdata
package ops
package common

// ===========================================================================
trait CommonShorthands[Data] {
    self: CommonOpsTrait[Data] =>

  // ---------------------------------------------------------------------------
  // shorthand for the most common ones
  @nonovrd final def convertToStr    (target1: Target, more: Target*): Data = convert(target1 +~ more).toStr
  @nonovrd final def convertToInt    (target1: Target, more: Target*): Data = convert(target1 +~ more).toInt
  @nonovrd final def convertToDouble (target1: Target, more: Target*): Data = convert(target1 +~ more).toDouble
  @nonovrd final def convertToBoolean(target1: Target, more: Target*): Data = convert(target1 +~ more).toBoolean }

// ===========================================================================
