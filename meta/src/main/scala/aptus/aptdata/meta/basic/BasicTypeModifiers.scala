package aptus
package aptdata
package meta
package basic

// ===========================================================================
trait BasicTypeModifiers { self: BasicType =>
  def enmOpt  : Option[BasicType._Enm] = if (self.isEnm) Some(forceEnm) else None
  def forceEnm:        BasicType._Enm  = self.asInstanceOf[BasicType._Enm]

  // ---------------------------------------------------------------------------
  def forceNumericalType  : NumericalType   = self.asInstanceOf[NumericalType]
  def forceUnboundedNumber: UnboundedNumber = self.asInstanceOf[UnboundedNumber]
  def forceBoundedNumber  : BoundedNumber   = self.asInstanceOf[BoundedNumber]
  def forceIntegerLikeType: IntegerLikeType = self.asInstanceOf[IntegerLikeType]
  def forceRealLikeType   : RealLikeType    = self.asInstanceOf[RealLikeType]

  def asNumericalTypeOpt  : Option[NumericalType]   = if (self.isInstanceOf[NumericalType])   Some(self.asInstanceOf[NumericalType])   else None
  def asUnboundedNumberOpt: Option[UnboundedNumber] = if (self.isInstanceOf[UnboundedNumber]) Some(self.asInstanceOf[UnboundedNumber]) else None
  def asBoundedNumberOpt  : Option[BoundedNumber]   = if (self.isInstanceOf[BoundedNumber])   Some(self.asInstanceOf[BoundedNumber])   else None
  def asIntegerLikeTypeOpt: Option[IntegerLikeType] = if (self.isInstanceOf[IntegerLikeType]) Some(self.asInstanceOf[IntegerLikeType]) else None
  def asRealLikeTypeOpt   : Option[RealLikeType]    = if (self.isInstanceOf[RealLikeType])    Some(self.asInstanceOf[RealLikeType])    else None }

// ===========================================================================