package aptus
package experimental
package dyn
package aliases

// ===========================================================================
private[dyn] trait DynScalaAliases {
type Seq2D[T] = Seq[Seq[T]]
type Array2D[T] = Array[Array[T]]

type RealMatrixCommons = org.apache.commons.math3.linear.RealMatrix

  private[dyn] type ListMap[K, V] = collection.immutable.ListMap[K, V]

  private[dyn] type BigDec     = BigDecimal
  private[dyn] type ByteBuffer = java.nio.ByteBuffer

  private[dyn] type LocalTime     = java.time.LocalTime
  private[dyn] type LocalDate     = java.time.LocalDate
  private[dyn] type LocalDateTime = java.time.LocalDateTime

  private[dyn] type DateTime = LocalDateTime
  private[dyn] type Date     = LocalDate

  private[dyn] type Instant        = java.time.Instant
  private[dyn] type  ZonedDateTime = java.time.ZonedDateTime
  private[dyn] type OffsetDateTime = java.time.OffsetDateTime }

// ===========================================================================