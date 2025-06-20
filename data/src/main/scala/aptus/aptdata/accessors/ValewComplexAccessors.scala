package aptus
package aptdata
package accessors

import domain._

// ===========================================================================
private[aptdata] trait ValewComplexAccessors { self: Valew =>
  def nesting : Dyn  = naked.asInstanceOf[Dyn]
  def nestings: Dyns = naked.asInstanceOf[Dyns]

  // TODO: confirm can't have Iterable[Dyn] at this point?
  def nestingsOpt: Option[Dyns] = naked match { case x: Dyns => Some(x); case _ => None }
  def  nestingOpt: Option[Dyn]  = naked match { case x: Dyn  => Some(x); case _ => None }

  // ===========================================================================
  def  numberLikeOpt: Option[ NumberLike[_]] = integerLikeOpt.orElse(realLikeOpt)

  def integerLikeOpt: Option[IntegerLike[_]] = IntegerLikeParsing.opt(naked)
  def    realLikeOpt: Option[   RealLike[_]] =    RealLikeParsing.opt(naked)

  // ===========================================================================
  def basicType: BasicType = basicTypeOpt.getOrElse(illegalArgument("TODO:241126143216"))

  // ===========================================================================
  // codegened (see 241202212207)

  @nonovrd final def   stringOpt: Option[String       ] = naked match { case x: String        => Some(x); case _ => None }
  @nonovrd final def  booleanOpt: Option[Boolean      ] = naked match { case x: Boolean       => Some(x); case _ => None }
  @nonovrd final def      intOpt: Option[Int          ] = naked match { case x: Int           => Some(x); case _ => None }
  @nonovrd final def   doubleOpt: Option[Double       ] = naked match { case x: Double        => Some(x); case _ => None }
  @nonovrd final def     byteOpt: Option[Byte         ] = naked match { case x: Byte          => Some(x); case _ => None }
  @nonovrd final def    shortOpt: Option[Short        ] = naked match { case x: Short         => Some(x); case _ => None }
  @nonovrd final def     longOpt: Option[Long         ] = naked match { case x: Long          => Some(x); case _ => None }
  @nonovrd final def    floatOpt: Option[Float        ] = naked match { case x: Float         => Some(x); case _ => None }
  @nonovrd final def   bigIntOpt: Option[BigInt       ] = naked match { case x: BigInt        => Some(x); case _ => None }
  @nonovrd final def   bigDecOpt: Option[BigDec       ] = naked match { case x: BigDec        => Some(x); case _ => None }
  @nonovrd final def     dateOpt: Option[Date         ] = naked match { case x: Date          => Some(x); case _ => None }
  @nonovrd final def dateTimeOpt: Option[DateTime     ] = naked match { case x: DateTime      => Some(x); case _ => None }
  @nonovrd final def  instantOpt: Option[Instant      ] = naked match { case x: Instant       => Some(x); case _ => None }
  @nonovrd final def   binaryOpt: Option[ByteBuffer   ] = naked match { case x: ByteBuffer    => Some(x); case _ => None }

  // ===========================================================================
  // codegened (see 241202211928)

  @nonovrd final def isString  : Boolean = basicTypeOpt.exists(_.isString)
  @nonovrd final def isBoolean : Boolean = basicTypeOpt.exists(_.isBoolean)
  @nonovrd final def isInt     : Boolean = basicTypeOpt.exists(_.isInt)
  @nonovrd final def isDouble  : Boolean = basicTypeOpt.exists(_.isDouble)
  @nonovrd final def isByte    : Boolean = basicTypeOpt.exists(_.isByte)
  @nonovrd final def isShort   : Boolean = basicTypeOpt.exists(_.isShort)
  @nonovrd final def isLong    : Boolean = basicTypeOpt.exists(_.isLong)
  @nonovrd final def isFloat   : Boolean = basicTypeOpt.exists(_.isFloat)
  @nonovrd final def isBigInt  : Boolean = basicTypeOpt.exists(_.isBigInt)
  @nonovrd final def isBigDec  : Boolean = basicTypeOpt.exists(_.isBigDec)
  @nonovrd final def isDate    : Boolean = basicTypeOpt.exists(_.isDate)
  @nonovrd final def isDateTime: Boolean = basicTypeOpt.exists(_.isDateTime)
  @nonovrd final def isInstant : Boolean = basicTypeOpt.exists(_.isInstant)
  @nonovrd final def isBinary  : Boolean = basicTypeOpt.exists(_.isBinary)

  // ---------------------------------------------------------------------------
  // codegened (see 241202211927)
  def basicTypeOpt: Option[BasicType] =
    naked match {
      case _: String        => Some(BasicType._String)
      case _: Boolean       => Some(BasicType._Boolean)
      case _: Int           => Some(BasicType._Int)
      case _: Double        => Some(BasicType._Double)

      case _: Byte          => Some(BasicType._Byte)
      case _: Short         => Some(BasicType._Short)
      case _: Long          => Some(BasicType._Long)
      case _: Float         => Some(BasicType._Float)

      case _: BigInt        => Some(BasicType._BigInt)
      case _: BigDec        => Some(BasicType._BigDec)

      case _: Date          => Some(BasicType._Date)
      case _: DateTime      => Some(BasicType._DateTime)
      case _: Instant       => Some(BasicType._Instant)

      case _: ByteBuffer    => Some(BasicType._Binary)

      case _                 => None } }

// ===========================================================================
