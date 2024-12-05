package aptus
package experimental
package dyntest

// ===========================================================================
trait DynTestAliases { import dyn._
            type ListMap[K, V] = collection.immutable.ListMap[K, V]
          
            type BigDec     = BigDecimal
            type ByteBuffer = java.nio.ByteBuffer
          
            type LocalTime     = java.time.LocalTime
            type LocalDate     = java.time.LocalDate
            type LocalDateTime = java.time.LocalDateTime
          
            type Instant        = java.time.Instant
            type  ZonedDateTime = java.time.ZonedDateTime
            type OffsetDateTime = java.time.OffsetDateTime  
  type Try[T] = util.Try[T]
  val  Try    = util.Try

  // ---------------------------------------------------------------------------
  type NakedValue = Any

  val  Error      = domain.errors.Error

  type HasErrorId = domain.errors.HasErrorId

  // ---------------------------------------------------------------------------
  type IntegerLike = domain.IntegerLike[_]
  val  IntegerLike = domain.IntegerLike

  type    RealLike = domain.   RealLike[_]
  val     RealLike = domain.   RealLike

  // ---------------------------------------------------------------------------
  val _Int = BasicType._Int

  // ---------------------------------------------------------------------------
  type SuperType = domain.SuperType }

// ===========================================================================