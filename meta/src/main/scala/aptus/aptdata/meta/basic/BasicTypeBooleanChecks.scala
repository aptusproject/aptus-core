package aptus
package aptdata
package meta
package basic

// ===========================================================================
trait BasicTypeBooleanChecks { self: BasicType =>
  def isNumericalType  : Boolean = self.isInstanceOf[NumericalType  ]
  def isUnboundedNumber: Boolean = self.isInstanceOf[UnboundedNumber]
  def isBoundedNumber  : Boolean = self.isInstanceOf[BoundedNumber  ] // used by inferring
  def isIntegerLikeType: Boolean = self.isInstanceOf[IntegerLikeType]
  def isRealLikeType   : Boolean = self.isInstanceOf[RealLikeType   ]

  // ---------------------------------------------------------------------------
  // codegened (see 241121238317)

  def isString  : Boolean = self == BasicType._String
  def isBoolean : Boolean = self == BasicType._Boolean
  def isInt     : Boolean = self == BasicType._Int
  def isDouble  : Boolean = self == BasicType._Double

  def isByte    : Boolean = self == BasicType._Byte
  def isShort   : Boolean = self == BasicType._Short
  def isLong    : Boolean = self == BasicType._Long
  def isFloat   : Boolean = self == BasicType._Float

  def isBigInt  : Boolean = self == BasicType._BigInt
  def isBigDec  : Boolean = self == BasicType._BigDec

  def isDate    : Boolean = self == BasicType._Date
  def isDateTime: Boolean = self == BasicType._DateTime
  def isInstant : Boolean = self == BasicType._Instant

  def isBinary  : Boolean = self == BasicType._Binary

  def isEnm     : Boolean = self.isInstanceOf[BasicType._Enm] }

// ===========================================================================
