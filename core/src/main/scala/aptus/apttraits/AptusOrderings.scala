package aptus
package apttraits

// ===========================================================================
// TODO: move elsewhere
trait AptusOrderings { import aptus.aptutils._
  def iterableOrdering[T : Ordering]: Ordering[Iterable[T]] = SeqUtils.iterableOrdering[T] // note: Ordering is invariant
  def   optionOrdering[T : Ordering]: Ordering[Option  [T]] = SeqUtils.  optionOrdering[T]

  //def tuple2Ordering[T1 : Ordering, T2: Ordering]: Ordering[Tuple2  [T1, T2]] = ???

  def   seqOrdering[T : Ordering]: Ordering[Seq  [T]] = SeqUtils.  seqOrdering[T]
  def  listOrdering[T : Ordering]: Ordering[List [T]] = SeqUtils. listOrdering[T]
  def arrayOrdering[T : Ordering]: Ordering[Array[T]] = SeqUtils.arrayOrdering[T]

  def   seqOrderingIgnoreSize[T : Ordering]: Ordering[Seq  [T]] = SeqUtils.  seqOrderingIgnoreSize[T]
  def  listOrderingIgnoreSize[T : Ordering]: Ordering[List [T]] = SeqUtils. listOrderingIgnoreSize[T]
  def arrayOrderingIgnoreSize[T : Ordering]: Ordering[Array[T]] = SeqUtils.arrayOrderingIgnoreSize[T]

  def listListOrdering[T : Ordering]: Ordering[List[List[T]]] = SeqUtils.listListOrdering[T]

  // ===========================================================================
  def localDateOrdering     : Ordering[LocalDate]       = Ordering.by((x: LocalDate) => x.toEpochDay)
  def localTimeOrdering     : Ordering[LocalTime]       = Ordering.by((x: LocalTime) => x.toNanoOfDay)

  // ---------------------------------------------------------------------------
  def  localDateTimeOrdering: Ordering[ LocalDateTime] = Ordering.by((x:  LocalDateTime) => x.toInstant(UTC))
  def offsetDateTimeOrdering: Ordering[OffsetDateTime] = Ordering.by((x: OffsetDateTime) => x.toInstant)
  def  zonedDateTimeOrdering: Ordering[ ZonedDateTime] = Ordering.by((x:  ZonedDateTime) => x.toInstant)

  // ---------------------------------------------------------------------------
  def byteBufferOrdering    : Ordering[ByteBuffer]     = Ordering.by((x: ByteBuffer) => x.array)(arrayOrdering[Byte]) }

// ===========================================================================