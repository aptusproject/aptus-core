package aptus
package aptdata
package ops
package common

// ===========================================================================
private[aptdata] object CommonMoreTransformsUtils {

  class _TransformType[Data, T](data: CommonTransformTrait[Data], basicType: BasicType, targets: Targets) {
    @inline def apply(f: T => NakedValue): Data = using(f) // favor more explicit .using
            def using(f: T => NakedValue): Data =
      basicType match { // codegened (see 241121238314)

//FIXME: what about Seq[Int] - t241127142124

        case BasicType._String   => data.transform(targets).string  (x => f.asInstanceOf[String     => NakedValue](x))
        case BasicType._Boolean  => data.transform(targets).boolean (x => f.asInstanceOf[Boolean    => NakedValue](x))
        case BasicType._Int      => data.transform(targets).int     (x => f.asInstanceOf[Int        => NakedValue](x))
        case BasicType._Double   => data.transform(targets).double  (x => f.asInstanceOf[Double     => NakedValue](x))
        case BasicType._Byte     => data.transform(targets).byte    (x => f.asInstanceOf[Byte       => NakedValue](x))
        case BasicType._Short    => data.transform(targets).short   (x => f.asInstanceOf[Short      => NakedValue](x))
        case BasicType._Long     => data.transform(targets).long    (x => f.asInstanceOf[Long       => NakedValue](x))
        case BasicType._Float    => data.transform(targets).float   (x => f.asInstanceOf[Float      => NakedValue](x))
        case BasicType._BigInt   => data.transform(targets).bigInt  (x => f.asInstanceOf[BigInt     => NakedValue](x))
        case BasicType._BigDec   => data.transform(targets).bigDec  (x => f.asInstanceOf[BigDec     => NakedValue](x))
        case BasicType._Date     => data.transform(targets).date    (x => f.asInstanceOf[Date       => NakedValue](x))
        case BasicType._DateTime => data.transform(targets).dateTime(x => f.asInstanceOf[DateTime   => NakedValue](x))
        case BasicType._Instant  => data.transform(targets).instant (x => f.asInstanceOf[Instant    => NakedValue](x))
        case BasicType._Binary   => data.transform(targets).binary  (x => f.asInstanceOf[ByteBuffer => NakedValue](x))

        case _                   => ??? } }

  // ===========================================================================
  class _TransformIfType[Data, T](data: CommonTransformTrait[Data], basicType: BasicType, targets: Targets) {
    @inline def apply(f: T => NakedValue): Data = using(f) // favor more explicit .using
            def using(f: T => NakedValue): Data =
      basicType match { // codegened (see 241121238324)

//FIXME: what about Seq[Int] - t241127142124

        case BasicType._String   => data.transform(targets).ifString  (x => f.asInstanceOf[String     => NakedValue](x))
        case BasicType._Boolean  => data.transform(targets).ifBoolean (x => f.asInstanceOf[Boolean    => NakedValue](x))
        case BasicType._Int      => data.transform(targets).ifInt     (x => f.asInstanceOf[Int        => NakedValue](x))
        case BasicType._Double   => data.transform(targets).ifDouble  (x => f.asInstanceOf[Double     => NakedValue](x))
        case BasicType._Byte     => data.transform(targets).ifByte    (x => f.asInstanceOf[Byte       => NakedValue](x))
        case BasicType._Short    => data.transform(targets).ifShort   (x => f.asInstanceOf[Short      => NakedValue](x))
        case BasicType._Long     => data.transform(targets).ifLong    (x => f.asInstanceOf[Long       => NakedValue](x))
        case BasicType._Float    => data.transform(targets).ifFloat   (x => f.asInstanceOf[Float      => NakedValue](x))
        case BasicType._BigInt   => data.transform(targets).ifBigInt  (x => f.asInstanceOf[BigInt     => NakedValue](x))
        case BasicType._BigDec   => data.transform(targets).ifBigDec  (x => f.asInstanceOf[BigDec     => NakedValue](x))
        case BasicType._Date     => data.transform(targets).ifDate    (x => f.asInstanceOf[Date       => NakedValue](x))
        case BasicType._DateTime => data.transform(targets).ifDateTime(x => f.asInstanceOf[DateTime   => NakedValue](x))
        case BasicType._Instant  => data.transform(targets).ifInstant (x => f.asInstanceOf[Instant    => NakedValue](x))
        case BasicType._Binary   => data.transform(targets).ifBinary  (x => f.asInstanceOf[ByteBuffer => NakedValue](x))

        case _                 => ??? } } }

// ===========================================================================

