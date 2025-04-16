package aptus
package experimental
package dyn
package ops
package common

// ===========================================================================
trait CommonShorthands[Data] {
    self: CommonOpsTrait[Data] =>

  // ---------------------------------------------------------------------------
  // shorthand for the most common ones
  @nonovrd final def convertToStr    (key: Key): Data = convert(key).toStr
  @nonovrd final def convertToInt    (key: Key): Data = convert(key).toInt
  @nonovrd final def convertToDouble (key: Key): Data = convert(key).toDouble
  @nonovrd final def convertToBoolean(key: Key): Data = convert(key).toBoolean }

// ===========================================================================
