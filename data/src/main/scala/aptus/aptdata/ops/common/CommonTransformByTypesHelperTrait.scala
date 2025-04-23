package aptus
package aptdata
package ops
package common

import domain.{RealLike, IntegerLike}
import domain.errors.Error.{TransformSpecificType => e}

// ===========================================================================
private[common]
 trait CommonTransformByTypesHelperTrait[Data] {
    self: HasDataEntityErrorFormatter[Data] with
          HasTargetSelector =>

  @abstrct def using(f: ValueF): Data

  final def usingz(f: Dyn => NakedValue)       : Data = ???
  final def usingObjectx(f: Data => NakedValue): Data = ???

  // ===========================================================================
  final def integerLike(f: IntegerLike[_] => NakedValew): Data = using { v => v.integerLikeOpt.map(f).getOrElse(e.throwIntegerLike(deef, target, v)) }
  final def    realLike(f:    RealLike[_] => NakedValew): Data = using { v => v.   realLikeOpt.map(f).getOrElse(e.throwRealLike   (deef, target, v)) }

  final def ifIntegerLike(f: IntegerLike[_] => NakedValew): Data = using { v => v.integerLikeOpt.map(f).getOrElse(v) }
  final def    ifRealLike(f:    RealLike[_] => NakedValew): Data = using { v => v.   realLikeOpt.map(f).getOrElse(v) }

// ---------------------------------------------------------------------------
// TODO: t250402145024
//  private[aptdata] final def basicType[T](basicType: BasicType)(f: T        => NakedValue): Data = using { v => v.basicTypeOpt.map {
////Exception in thread "main" java.lang.ClassCastException: class BasicType$_String$ cannot be cast to class java.lang.String (BasicType$_String$ is in
//    x => f(x.asInstanceOf[basicType.T])
//    }.getOrElse(e.throwBasicType(deef, target, v)(_._String)) }

  // ---------------------------------------------------------------------------
  // codegened (see 241202192553)

  final def string  (f: String     => NakedValue): Data = using { v => v.stringOpt  .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._String    )) }
  final def boolean (f: Boolean    => NakedValue): Data = using { v => v.booleanOpt .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Boolean   )) }
  final def int     (f: Int        => NakedValue): Data = using { v => v.intOpt     .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Int       )) }
  final def double  (f: Double     => NakedValue): Data = using { v => v.doubleOpt  .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Double    )) }
  final def byte    (f: Byte       => NakedValue): Data = using { v => v.byteOpt    .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Byte      )) }
  final def short   (f: Short      => NakedValue): Data = using { v => v.shortOpt   .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Short     )) }
  final def long    (f: Long       => NakedValue): Data = using { v => v.longOpt    .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Long      )) }
  final def float   (f: Float      => NakedValue): Data = using { v => v.floatOpt   .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Float     )) }
  final def bigInt  (f: BigInt     => NakedValue): Data = using { v => v.bigIntOpt  .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._BigInt    )) }
  final def bigDec  (f: BigDec     => NakedValue): Data = using { v => v.bigDecOpt  .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._BigDec    )) }
  final def date    (f: Date       => NakedValue): Data = using { v => v.dateOpt    .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Date      )) }
  final def dateTime(f: DateTime   => NakedValue): Data = using { v => v.dateTimeOpt.map(f).getOrElse(e.throwBasicType(deef, target, v)(_._DateTime  )) }
  final def instant (f: Instant    => NakedValue): Data = using { v => v.instantOpt .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Instant   )) }
  final def binary  (f: ByteBuffer => NakedValue): Data = using { v => v.binaryOpt  .map(f).getOrElse(e.throwBasicType(deef, target, v)(_._Binary    )) }

  // ---------------------------------------------------------------------------
  // codegened (see 241202192554)

  final def ifString  (f: String     => NakedValue): Data = using { v => v.stringOpt  .map(f).getOrElse(v) }
  final def ifBoolean (f: Boolean    => NakedValue): Data = using { v => v.booleanOpt .map(f).getOrElse(v) }
  final def ifInt     (f: Int        => NakedValue): Data = using { v => v.intOpt     .map(f).getOrElse(v) }
  final def ifDouble  (f: Double     => NakedValue): Data = using { v => v.doubleOpt  .map(f).getOrElse(v) }
  final def ifByte    (f: Byte       => NakedValue): Data = using { v => v.byteOpt    .map(f).getOrElse(v) }
  final def ifShort   (f: Short      => NakedValue): Data = using { v => v.shortOpt   .map(f).getOrElse(v) }
  final def ifLong    (f: Long       => NakedValue): Data = using { v => v.longOpt    .map(f).getOrElse(v) }
  final def ifFloat   (f: Float      => NakedValue): Data = using { v => v.floatOpt   .map(f).getOrElse(v) }
  final def ifBigInt  (f: BigInt     => NakedValue): Data = using { v => v.bigIntOpt  .map(f).getOrElse(v) }
  final def ifBigDec  (f: BigDec     => NakedValue): Data = using { v => v.bigDecOpt  .map(f).getOrElse(v) }
  final def ifDate    (f: Date       => NakedValue): Data = using { v => v.dateOpt    .map(f).getOrElse(v) }
  final def ifDateTime(f: DateTime   => NakedValue): Data = using { v => v.dateTimeOpt.map(f).getOrElse(v) }
  final def ifInstant (f: Instant    => NakedValue): Data = using { v => v.instantOpt .map(f).getOrElse(v) }
  final def ifBinary  (f: ByteBuffer => NakedValue): Data = using { v => v.binaryOpt  .map(f).getOrElse(v) } }

// ===========================================================================
