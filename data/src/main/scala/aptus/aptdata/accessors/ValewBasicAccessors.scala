package aptus
package aptdata
package accessors

// ===========================================================================
private[aptdata] trait ValewBasicAccessors { val naked: NakedValue
  import Error.{AccessAsSpecificType => e}

  // ---------------------------------------------------------------------------
  def booleanss: Seq2D[Boolean] = naked.asInstanceOf[Seq[Seq[Boolean]]]
  def intss    : Seq2D[Int]     = naked.asInstanceOf[Seq[Seq[Int]]]
  def doubless : Seq2D[Double]  = naked.asInstanceOf[Seq[Seq[Double]]]
  def stringss : Seq2D[String]  = naked.asInstanceOf[Seq[Seq[String]]]
  //TODO: codegen the rest

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

  def string    : One[String    ] = naked match { case x: String     => x; case _ => e.throwString(naked) }
  def boolean   : One[Boolean   ] = naked match { case x: Boolean    => x; case _ => e.throwString(naked) }
  def int       : One[Int       ] = naked match { case x: Int        => x; case _ => e.throwString(naked) }
  def double    : One[Double    ] = naked match { case x: Double     => x; case _ => e.throwString(naked) }

  def byte      : One[Byte      ] = naked match { case x: Byte       => x; case _ => e.throwString(naked) }
  def short     : One[Short     ] = naked match { case x: Short      => x; case _ => e.throwString(naked) }
  def long      : One[Long      ] = naked match { case x: Long       => x; case _ => e.throwString(naked) }
  def float     : One[Float     ] = naked match { case x: Float      => x; case _ => e.throwString(naked) }

  def bigInt    : One[BigInt    ] = naked match { case x: BigInt     => x; case _ => e.throwString(naked) }
  def bigDec    : One[BigDec    ] = naked match { case x: BigDec     => x; case _ => e.throwString(naked) }

  def date      : One[Date      ] = naked match { case x: Date       => x; case _ => e.throwString(naked) }
  def dateTime  : One[DateTime  ] = naked match { case x: DateTime   => x; case _ => e.throwString(naked) }
  def instant   : One[Instant   ] = naked match { case x: Instant    => x; case _ => e.throwString(naked) }

  def byteBuffer: One[ByteBuffer] = naked match { case x: ByteBuffer => x; case _ => e.throwString(naked) }

  // ---------------------------------------------------------------------------
  def strings    : Seq[String    ] = _multiple.map { case x: String     => x; case _ => e.throwString(naked) }
  def booleans   : Seq[Boolean   ] = _multiple.map { case x: Boolean    => x; case _ => e.throwString(naked) }
  def ints       : Seq[Int       ] = _multiple.map { case x: Int        => x; case _ => e.throwString(naked) }
  def doubles    : Seq[Double    ] = _multiple.map { case x: Double     => x; case _ => e.throwString(naked) }

  def bytes      : Seq[Byte      ] = _multiple.map { case x: Byte       => x; case _ => e.throwString(naked) }
  def shorts     : Seq[Short     ] = _multiple.map { case x: Short      => x; case _ => e.throwString(naked) }
  def longs      : Seq[Long      ] = _multiple.map { case x: Long       => x; case _ => e.throwString(naked) }
  def floats     : Seq[Float     ] = _multiple.map { case x: Float      => x; case _ => e.throwString(naked) }

  def bigInts    : Seq[BigInt    ] = _multiple.map { case x: BigInt     => x; case _ => e.throwString(naked) }
  def bigDecs    : Seq[BigDec    ] = _multiple.map { case x: BigDec     => x; case _ => e.throwString(naked) }

  def dates      : Seq[Date      ] = _multiple.map { case x: Date       => x; case _ => e.throwString(naked) }
  def dateTimes  : Seq[DateTime  ] = _multiple.map { case x: DateTime   => x; case _ => e.throwString(naked) }
  def instants   : Seq[Instant   ] = _multiple.map { case x: Instant    => x; case _ => e.throwString(naked) }

  def byteBuffers: Seq[ByteBuffer] = _multiple.map { case x: ByteBuffer => x; case _ => e.throwString(naked) }

  // ---------------------------------------------------------------------------
  private def _multiple: Seq[Any] = naked match { case x: Seq[_] => x; case _ => e.throwString(naked) }

  // ===========================================================================
  def matrix = naked.asInstanceOf[org.apache.commons.math3.linear.RealMatrix]
//def realMatrixCommons: RealMatrixCommons = naked.asInstanceOf[RealMatrixCommons]

  def text           : One[String] = naked.toString

}

// ===========================================================================
