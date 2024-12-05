package aptus
package experimental
package dyn
package accessors

import util.Try

// ===========================================================================
private[dyn] trait ValewBasicAccessors { val naked: NakedValue
  import Error.{AccessAsSpecificType => e}

  // ---------------------------------------------------------------------------
  def booleanss: Seq2D[Boolean] = naked.asInstanceOf[Seq[Seq[Boolean]]]
  def intss    : Seq2D[Int]     = naked.asInstanceOf[Seq[Seq[Int]]]
  def doubless : Seq2D[Double]  = naked.asInstanceOf[Seq[Seq[Double]]]
  def stringss : Seq2D[String]  = naked.asInstanceOf[Seq[Seq[String]]]
  //TODO: codegen the rest

  def realMatrixCommons: RealMatrixCommons = naked.asInstanceOf[RealMatrixCommons]

  // ===========================================================================
  // x = independent of multiplicity (0, 1, 2, .. dimensions)

  // ---------------------------------------------------------------------------
  def stringx           : Seq[String]  = _stringx(naked)(naked)
  def booleanx          : Seq[Boolean] = ???
  def intx              : Seq[Int]     = ???
  def doublex           : Seq[Double]  = ???
  //TODO: codegen the rest (t241204111302)

    // ---------------------------------------------------------------------------
    // FIXME: t241204111738
    private def _stringx(origin: NakedValue)(value: NakedValue): Seq[String] = _typedx[String](origin)(_.stringx)(value)

    // ---------------------------------------------------------------------------
    private def _typedx[T](origin: NakedValue)(f: Valew => Seq[T])(value: NakedValue): Seq[T] =
      value match {
        case s: T           => s.in.seq
        case x: Iterable[_] => x.flatMap(_typedx(origin)(f)).toSeq
        case _              => e.throwString /* TODO */ (origin) }

  // ===========================================================================
  // codegened (see 241121238316)


  def string           : One[String] = naked match { case x: String => x; case _ => e.throwString(naked) }
  def boolean           = naked.asInstanceOf[One[Boolean       ]]
  def int               = naked.asInstanceOf[One[Int           ]]
  def double            = naked.asInstanceOf[One[Double        ]]

  def byte              = naked.asInstanceOf[One[Byte          ]]
  def short             = naked.asInstanceOf[One[Short         ]]
  def long              = naked.asInstanceOf[One[Long          ]]
  def float             = naked.asInstanceOf[One[Float         ]]

  def bigInt            = naked.asInstanceOf[One[BigInt        ]]
  def bigDecimal        = naked.asInstanceOf[One[BigDecimal    ]]

  def localDate         = naked.asInstanceOf[One[LocalDate     ]]
  def localTime         = naked.asInstanceOf[One[LocalTime     ]]
  def localDateTime     = naked.asInstanceOf[One[LocalDateTime ]]
  def offsetDateTime    = naked.asInstanceOf[One[OffsetDateTime]]
  def zonedDateTime     = naked.asInstanceOf[One[ZonedDateTime ]]
  def instant           = naked.asInstanceOf[One[Instant       ]]

  def byteBuffer        = naked.asInstanceOf[One[ByteBuffer    ]]

  // ---------------------------------------------------------------------------
  //def strings           : Nes[String] = naked match { case x: Seq[String] => x; case _ => E.throwString(naked) }


// FIXME in codegen
def strings           = Try { naked.asInstanceOf[Seq[String        ]] }.getOrElse(e.throwString(naked))
  def booleans          = naked.asInstanceOf[Seq[Boolean       ]]
  def ints              = naked.asInstanceOf[Seq[Int           ]]
  def doubles           = naked.asInstanceOf[Seq[Double        ]]

  def bytes             = naked.asInstanceOf[Seq[Byte          ]]
  def shorts            = naked.asInstanceOf[Seq[Short         ]]
  def longs             = naked.asInstanceOf[Seq[Long          ]]
  def floats            = naked.asInstanceOf[Seq[Float         ]]

  def bigInts           = naked.asInstanceOf[Seq[BigInt        ]]
  def bigDecimals       = naked.asInstanceOf[Seq[BigDecimal    ]]

  def localDates        = naked.asInstanceOf[Seq[LocalDate     ]]
  def localTimes        = naked.asInstanceOf[Seq[LocalTime     ]]
  def localDateTimes    = naked.asInstanceOf[Seq[LocalDateTime ]]
  def offsetDateTimes   = naked.asInstanceOf[Seq[OffsetDateTime]]
  def zonedDateTimes    = naked.asInstanceOf[Seq[ZonedDateTime ]]
  def instants          = naked.asInstanceOf[Seq[Instant       ]]

  def byteBuffers       = naked.asInstanceOf[Seq[ByteBuffer    ]]

  // ---------------------------------------------------------------------------
  def string_           = naked.asInstanceOf[Opt[String        ]]
  def boolean_          = naked.asInstanceOf[Opt[Boolean       ]]
  def int_              = naked.asInstanceOf[Opt[Int           ]]
  def double_           = naked.asInstanceOf[Opt[Double        ]]

  def byte_             = naked.asInstanceOf[Opt[Byte          ]]
  def short_            = naked.asInstanceOf[Opt[Short         ]]
  def long_             = naked.asInstanceOf[Opt[Long          ]]
  def float_            = naked.asInstanceOf[Opt[Float         ]]

  def bigInt_           = naked.asInstanceOf[Opt[BigInt        ]]
  def bigDecimal_       = naked.asInstanceOf[Opt[BigDecimal    ]]

  def localDate_        = naked.asInstanceOf[Opt[LocalDate     ]]
  def localTime_        = naked.asInstanceOf[Opt[LocalTime     ]]
  def localDateTime_    = naked.asInstanceOf[Opt[LocalDateTime ]]
  def offsetDateTime_   = naked.asInstanceOf[Opt[OffsetDateTime]]
  def zonedDateTime_    = naked.asInstanceOf[Opt[ZonedDateTime ]]
  def instant_          = naked.asInstanceOf[Opt[Instant       ]]

  def byteBuffer_       = naked.asInstanceOf[Opt[ByteBuffer    ]]

  // ---------------------------------------------------------------------------
  def strings_          = naked.asInstanceOf[Pes[String        ]]
  def booleans_         = naked.asInstanceOf[Pes[Boolean       ]]
  def ints_             = naked.asInstanceOf[Pes[Int           ]]
  def doubles_          = naked.asInstanceOf[Pes[Double        ]]

  def bytes_            = naked.asInstanceOf[Pes[Byte          ]]
  def shorts_           = naked.asInstanceOf[Pes[Short         ]]
  def longs_            = naked.asInstanceOf[Pes[Long          ]]
  def floats_           = naked.asInstanceOf[Pes[Float         ]]

  def bigInts_          = naked.asInstanceOf[Pes[BigInt        ]]
  def bigDecimals_      = naked.asInstanceOf[Pes[BigDecimal    ]]

  def localDates_       = naked.asInstanceOf[Pes[LocalDate     ]]
  def localTimes_       = naked.asInstanceOf[Pes[LocalTime     ]]
  def localDateTimes_   = naked.asInstanceOf[Pes[LocalDateTime ]]
  def offsetDateTimes_  = naked.asInstanceOf[Pes[OffsetDateTime]]
  def zonedDateTimes_   = naked.asInstanceOf[Pes[ZonedDateTime ]]
  def instants_         = naked.asInstanceOf[Pes[Instant       ]]

  def byteBuffers_      = naked.asInstanceOf[Pes[ByteBuffer    ]] }

// ===========================================================================