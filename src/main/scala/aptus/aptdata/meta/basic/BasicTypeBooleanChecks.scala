package gallia
package basic

// ===========================================================================
trait BasicTypeBooleanChecks { val value: BasicType

  // codegened (see 241121238316)
  def isString  : Boolean = value == BasicType._String
  def isBoolean : Boolean = value == BasicType._Boolean
  def isInt     : Boolean = value == BasicType._Int
  def isDouble  : Boolean = value == BasicType._Double
  def isByte    : Boolean = value == BasicType._Byte
  def isShort   : Boolean = value == BasicType._Short
  def isLong    : Boolean = value == BasicType._Long
  def isFloat   : Boolean = value == BasicType._Float
  def isBigInt  : Boolean = value == BasicType._BigInt
  def isBigDec  : Boolean = value == BasicType._BigDec
  def isDate    : Boolean = value == BasicType._Date
  def isDateTime: Boolean = value == BasicType._DateTime
  def isInstant : Boolean = value == BasicType._Instant
  def isBinary  : Boolean = value == BasicType._Binary }

// ===========================================================================
