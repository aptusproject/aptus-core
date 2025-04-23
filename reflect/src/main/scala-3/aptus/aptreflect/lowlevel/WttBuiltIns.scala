package aptus
package aptreflect
package lowlevel

import scala.reflect.ClassTag
import nodes.TypeNodeBuiltIns

// ===========================================================================
object WttBuiltIns extends ReflectionTypesAbstraction {
  val _Any: WTT[scala.Any] = WTT[scala.Any](TypeNodeBuiltIns.ScalaAny, ClassTag(classOf[scala.Any]), None)

  // ---------------------------------------------------------------------------
  val _String  : WTT[String]  = WTT[String] (TypeNodeBuiltIns.String, ClassTag(classOf[String]), None)

  // ---------------------------------------------------------------------------
  val _Boolean : WTT[Boolean] = WTT[Boolean](TypeNodeBuiltIns.ScalaBoolean, ClassTag(classOf[String]), None)
  val _Int     : WTT[Int]     = WTT[Int]    (TypeNodeBuiltIns.ScalaInt    , ClassTag(classOf[String]), None)
  val _Double  : WTT[Double]  = WTT[Double] (TypeNodeBuiltIns.ScalaDouble , ClassTag(classOf[String]), None)

  // ---------------------------------------------------------------------------
  val _Byte    : WTT[Byte]  = WTT[Byte] (TypeNodeBuiltIns.ScalaByte , ClassTag(classOf[String]), None)
  val _Short   : WTT[Short] = WTT[Short](TypeNodeBuiltIns.ScalaShort, ClassTag(classOf[String]), None)
  val _Long    : WTT[Long]  = WTT[Long] (TypeNodeBuiltIns.ScalaLong , ClassTag(classOf[String]), None)

  val _Float   : WTT[Float] = WTT[Float](TypeNodeBuiltIns.ScalaFloat, ClassTag(classOf[String]), None)

  // ---------------------------------------------------------------------------
  val _BigInt    : WTT[BigInt]     = WTT[BigInt]    (TypeNodeBuiltIns.ScalaMathBigInt    , ClassTag(classOf[String]), None)
  val _BigDecimal: WTT[BigDecimal] = WTT[BigDecimal](TypeNodeBuiltIns.ScalaMathBigDecimal, ClassTag(classOf[String]), None)

  // ---------------------------------------------------------------------------
  val _Date     : WTT[java.time.LocalDate]      = WTT[java.time.LocalDate]     (TypeNodeBuiltIns.JavaTimeLocalDate     , ClassTag(classOf[String]), None)
  val _DateTime : WTT[java.time.LocalDateTime]  = WTT[java.time.LocalDateTime] (TypeNodeBuiltIns.JavaTimeLocalDateTime , ClassTag(classOf[String]), None)
  val _Instant  : WTT[java.time.Instant]        = WTT[java.time.Instant]       (TypeNodeBuiltIns.JavaTimeInstant       , ClassTag(classOf[String]), None)

  // ---------------------------------------------------------------------------
  val _ByteBuffer: WTT[java.nio.ByteBuffer] = WTT[java.nio.ByteBuffer](TypeNodeBuiltIns.JavaNioByteBuffer, ClassTag(classOf[String]), None) }

// ===========================================================================