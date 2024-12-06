package aptus
package experimental
package dyn
package ops
package common

import domain.{IntegerLike, RealLike}

// ===========================================================================
trait CommonMoreTransforms[Data] {
    self: CommonTransformTrait[Data] /* eg transform(x).string(...) */ with
       HasDataEntityErrorFormatter[Data] =>
  import Error.TransformSpecificType

  // ===========================================================================
  @nonovrd final def transformSole(f: Valew => NakedValue): Data = transform(_.soleKey).using(f)

    @nonovrd final def transformSoleBoolean(f: Boolean => NakedValue): Data = transformSole(_.booleanx.map(f))
    @nonovrd final def transformSoleInt    (f: Int     => NakedValue): Data = transformSole(_.intx    .map(f))
    @nonovrd final def transformSoleDouble (f: Double  => NakedValue): Data = transformSole(_.doublex .map(f))
    @nonovrd final def transformSoleString (f: String  => NakedValue): Data = transformSole(_.stringx .map(f))
    // only these 4

  // ===========================================================================
  // not nested Dynz (see a241119155444)

  @nonovrd final def transformDyn (key: Key)(f: Dyn  => NakedValue): Data = transform(key).using { _. dynOpt.map(f).getOrElse(TransformSpecificType(deef, TargetSelector.Explicit(key), data.sngl.Dyn).thro) }
  @nonovrd final def transformDyns(key: Key)(f: Dyns => NakedValue): Data = transform(key).using { _.dynsOpt.map(f).getOrElse(TransformSpecificType(deef, TargetSelector.Explicit(key), data.mult.list.Dyns).thro) }

@nonovrd final def transformDyn (ren: Ren)(f: Dyn  => NakedValue): Data = transform(ren).using { _. dynOpt.map(f).getOrElse(TransformSpecificType(deef, TargetSelector.Renaming(ren), data.sngl.Dyn).thro) }

  // ===========================================================================
  //TODO: t242041122561 - add the .using for consistency (need intermediate)
  @nonovrd final def transformString (key: Key) = new { def using(f: String  => NakedValue): Data = transform(key).string (f) }
  @nonovrd final def transformInt    (key: Key) = new { def using(f: Int     => NakedValue): Data = transform(key).int    (f) }
  @nonovrd final def transformBoolean(key: Key) = new { def using(f: Boolean => NakedValue): Data = transform(key).boolean(f) }
  @nonovrd final def transformDouble (key: Key) = new { def using(f: Double  => NakedValue): Data = transform(key).double (f) }
  // only these 4

  // ---------------------------------------------------------------------------
  //TODO: t242041122561 - add the .using for consistency (need intermediate)
  @nonovrd final def transformIfString (key: Key) = new { def using(f: String  => NakedValue): Data = transform(TargetSelector.Explicit(key)).ifString (f) }
  @nonovrd final def transformIfInt    (key: Key) = new { def using(f: Int     => NakedValue): Data = transform(TargetSelector.Explicit(key)).ifInt    (f) }
  @nonovrd final def transformIfBoolean(key: Key) = new { def using(f: Boolean => NakedValue): Data = transform(TargetSelector.Explicit(key)).ifBoolean(f) }
  @nonovrd final def transformIfDouble (key: Key) = new { def using(f: Double  => NakedValue): Data = transform(TargetSelector.Explicit(key)).ifDouble (f) }
  // only these 4

// TODO: if IntLike...?

  // ===========================================================================
  @nonovrd final def transformIntegerLike[T](target: TargetSelector)(f: IntegerLike[_] => T): Data = _transformIntegerLike[T](target: TargetSelector)(f)
  @nonovrd final def transformRealLike   [T](target: TargetSelector)(f:    RealLike[_] => T): Data = _transformRealLike   [T](target: TargetSelector)(f)

    // ---------------------------------------------------------------------------
    @inline private def _transformIntegerLike[T](target: TargetSelector)(f: IntegerLike[_] => T): Data =
      transform(target).using { valew => valew.integerLikeOpt.map(f).getOrElse {
        TransformSpecificType(deef, target, IntegerLike).thro /* including Double/Float - see a241125115501 */ } }

    @inline private def _transformRealLike[T](target: TargetSelector)(f: RealLike[_] => T): Data =
      transform(target).using { valew => valew.realLikeOpt.map(f).getOrElse {
        TransformSpecificType(deef, target, RealLike).thro } }

  // ===========================================================================
//def transformIfType2(basicType: BasicType.type => BasicType)(key: Key)(f: basicType.T => NakedValue): Data = ???//-- can't becuase of basicType.T
  @nonovrd def transformIfType[T <: BasicType]      (key: Key)(f:           T#T => NakedValue): Data = ???
  @nonovrd def transformIfType(basicType: BasicType)(key: Key)(f: basicType.T   => NakedValue): Data =
//FIXME: what about Seq[Int] - t241127142124
    basicType match {
//TODO: don't use if version
case BasicType._String   => transform(key).ifString  (x => f.asInstanceOf[String         => NakedValue](x))
case BasicType._Boolean  => transform(key).ifBoolean (x => f.asInstanceOf[Boolean        => NakedValue](x))
case BasicType._Int      => transform(key).ifInt     (x => f.asInstanceOf[Int            => NakedValue](x))
case BasicType._Double   => transform(key).ifDouble  (x => f.asInstanceOf[Double         => NakedValue](x))
case BasicType._Byte     => transform(key).ifByte    (x => f.asInstanceOf[Byte           => NakedValue](x))
case BasicType._Short    => transform(key).ifShort   (x => f.asInstanceOf[Short          => NakedValue](x))
case BasicType._Long     => transform(key).ifLong    (x => f.asInstanceOf[Long           => NakedValue](x))
case BasicType._Float    => transform(key).ifFloat   (x => f.asInstanceOf[Float          => NakedValue](x))
case BasicType._BigInt   => transform(key).ifBigInt  (x => f.asInstanceOf[BigInt         => NakedValue](x))
case BasicType._BigDec   => transform(key).ifBigDec  (x => f.asInstanceOf[BigDecimal     => NakedValue](x))
case BasicType._Date     => transform(key).ifDate    (x => f.asInstanceOf[LocalDate      => NakedValue](x))
case BasicType._DateTime => transform(key).ifDateTime(x => f.asInstanceOf[LocalDateTime  => NakedValue](x))
case BasicType._Instant  => transform(key).ifInstant (x => f.asInstanceOf[Instant        => NakedValue](x))
case BasicType._Binary   => transform(key).ifBinary  (x => f.asInstanceOf[ByteBuffer     => NakedValue](x))      
//      case BasicType._String => transform(key).ifString(x => f.asInstanceOf[String => NakedValue](x))
//      case BasicType._Int    => transformIfInt   (key)(x => f.asInstanceOf[Int    => NakedValue](x))
      case _                 => ??? } }

// ===========================================================================
