package aptus

// ===========================================================================
private[aptus] trait AptusCommonAliases
    extends AptusCommonsTimeAliases {
  private[aptus] type ListMap[K, V] = scala.collection.immutable.ListMap[K, V]

  // ---------------------------------------------------------------------------
  private[aptus] type Charset  = java.nio.charset.Charset
  private[aptus] val  UTF_8    = java.nio.charset.StandardCharsets.UTF_8
  private[aptus] val  US_ASCII = java.nio.charset.StandardCharsets.US_ASCII

  // ---------------------------------------------------------------------------
  private[aptus] type URI = java.net.URI
  private[aptus] type URL = java.net.URL

  // ---------------------------------------------------------------------------
  private[aptus] type Closeable   = java.io.Closeable
  private[aptus] type ByteBuffer  = java.nio.ByteBuffer

  // ---------------------------------------------------------------------------
  private[aptus] type Regex       = scala.util.matching.Regex }

// ===========================================================================
trait AptusCommonsTimeAliases {
  private[aptus] val UTC = java.time.ZoneOffset.UTC

  // ---------------------------------------------------------------------------
  private[aptus] type LocalDate = java.time.LocalDate
  private[aptus] type LocalTime = java.time.LocalTime

  private[aptus] type  LocalDateTime = java.time. LocalDateTime
  private[aptus] type OffsetDateTime = java.time.OffsetDateTime
  private[aptus] type  ZonedDateTime = java.time. ZonedDateTime

  private[aptus] type  Instant = java.time.Instant

  private[aptus] type DateTimeFormatter = java.time.format.DateTimeFormatter }

// ===========================================================================