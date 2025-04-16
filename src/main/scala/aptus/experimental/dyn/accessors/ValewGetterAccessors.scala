package aptus
package experimental
package dyn
package accessors

// ===========================================================================
private[dyn] trait ValewGetterAccessors {
    self: ValewGetter => // TODO

  // ---------------------------------------------------------------------------
  private def error = illegalArgument("TODO:t241022114436")

  // ===========================================================================
  // codegened (see 241121238315)

  def string    (key: Key): One[String    ] = getOrElse(key, error).string
  def boolean   (key: Key): One[Boolean   ] = getOrElse(key, error).boolean
  def int       (key: Key): One[Int       ] = getOrElse(key, error).int
  def double    (key: Key): One[Double    ] = getOrElse(key, error).double
  def byte      (key: Key): One[Byte      ] = getOrElse(key, error).byte
  def short     (key: Key): One[Short     ] = getOrElse(key, error).short
  def long      (key: Key): One[Long      ] = getOrElse(key, error).long
  def float     (key: Key): One[Float     ] = getOrElse(key, error).float
  def bigInt    (key: Key): One[BigInt    ] = getOrElse(key, error).bigInt
  def bigDec    (key: Key): One[BigDec    ] = getOrElse(key, error).bigDec
  def date      (key: Key): One[Date      ] = getOrElse(key, error).date
  def dateTime  (key: Key): One[DateTime  ] = getOrElse(key, error).dateTime
  def instant   (key: Key): One[Instant   ] = getOrElse(key, error).instant
  def byteBuffer(key: Key): One[ByteBuffer] = getOrElse(key, error).byteBuffer

  // ---------------------------------------------------------------------------
  def strings    (key: Key): Nes[String    ] = getOrElse(key, error).strings
  def booleans   (key: Key): Nes[Boolean   ] = getOrElse(key, error).booleans
  def ints       (key: Key): Nes[Int       ] = getOrElse(key, error).ints
  def doubles    (key: Key): Nes[Double    ] = getOrElse(key, error).doubles
  def bytes      (key: Key): Nes[Byte      ] = getOrElse(key, error).bytes
  def shorts     (key: Key): Nes[Short     ] = getOrElse(key, error).shorts
  def longs      (key: Key): Nes[Long      ] = getOrElse(key, error).longs
  def floats     (key: Key): Nes[Float     ] = getOrElse(key, error).floats
  def bigInts    (key: Key): Nes[BigInt    ] = getOrElse(key, error).bigInts
  def bigDecs    (key: Key): Nes[BigDec    ] = getOrElse(key, error).bigDecs
  def dates      (key: Key): Nes[Date      ] = getOrElse(key, error).dates
  def dateTimes  (key: Key): Nes[DateTime  ] = getOrElse(key, error).dateTimes
  def instants   (key: Key): Nes[Instant   ] = getOrElse(key, error).instants
  def byteBuffers(key: Key): Nes[ByteBuffer] = getOrElse(key, error).byteBuffers

  // ---------------------------------------------------------------------------
  def string_    (key: Key): Opt[String    ] = get(key).map(_.string)
  def boolean_   (key: Key): Opt[Boolean   ] = get(key).map(_.boolean)
  def int_       (key: Key): Opt[Int       ] = get(key).map(_.int)
  def double_    (key: Key): Opt[Double    ] = get(key).map(_.double)
  def byte_      (key: Key): Opt[Byte      ] = get(key).map(_.byte)
  def short_     (key: Key): Opt[Short     ] = get(key).map(_.short)
  def long_      (key: Key): Opt[Long      ] = get(key).map(_.long)
  def float_     (key: Key): Opt[Float     ] = get(key).map(_.float)
  def bigInt_    (key: Key): Opt[BigInt    ] = get(key).map(_.bigInt)
  def bigDec_    (key: Key): Opt[BigDec    ] = get(key).map(_.bigDec)
  def date_      (key: Key): Opt[Date      ] = get(key).map(_.date)
  def dateTime_  (key: Key): Opt[DateTime  ] = get(key).map(_.dateTime)
  def instant_   (key: Key): Opt[Instant   ] = get(key).map(_.instant)
  def byteBuffer_(key: Key): Opt[ByteBuffer] = get(key).map(_.byteBuffer)

  // ---------------------------------------------------------------------------
  def strings_    (key: Key): Ons[String    ] = get(key).map(_.strings)
  def booleans_   (key: Key): Ons[Boolean   ] = get(key).map(_.booleans)
  def ints_       (key: Key): Ons[Int       ] = get(key).map(_.ints)
  def doubles_    (key: Key): Ons[Double    ] = get(key).map(_.doubles)
  def bytes_      (key: Key): Ons[Byte      ] = get(key).map(_.bytes)
  def shorts_     (key: Key): Ons[Short     ] = get(key).map(_.shorts)
  def longs_      (key: Key): Ons[Long      ] = get(key).map(_.longs)
  def floats_     (key: Key): Ons[Float     ] = get(key).map(_.floats)
  def bigInts_    (key: Key): Ons[BigInt    ] = get(key).map(_.bigInts)
  def bigDecs_    (key: Key): Ons[BigDec    ] = get(key).map(_.bigDecs)
  def dates_      (key: Key): Ons[Date      ] = get(key).map(_.dates)
  def dateTimes_  (key: Key): Ons[DateTime  ] = get(key).map(_.dateTimes)
  def instants_   (key: Key): Ons[Instant   ] = get(key).map(_.instants)
  def byteBuffers_(key: Key): Ons[ByteBuffer] = get(key).map(_.byteBuffers)

  // ===========================================================================
  def obj  (key: Key): One[Dyn] = getOrElse(key, error).dyn
  def obj_ (key: Key): Opt[Dyn] = get      (key).map (_.dyn)
  def objs (key: Key): Nes[Dyn] = getOrElse(key, error).dyns.values // t250416161852 -
  def objs_(key: Key): Ons[Dyn] = get      (key).map (_.dyns.values)

  // ===========================================================================
  // 2D

  def booleanss(key: Key): Seq2D[Boolean] = getOrElse(key, error).booleanss
  def intss    (key: Key): Seq2D[Int    ] = getOrElse(key, error).intss
  def doubless (key: Key): Seq2D[Double ] = getOrElse(key, error).doubless
  def stringss (key: Key): Seq2D[String ] = getOrElse(key, error).stringss

  def realMatrixCommons(key: Key): RealMatrixCommons = getOrElse(key, error).matrix

  // ===========================================================================
//TODO:
  def boolean(key: Path): One[Boolean] = ???// self.getOrElse(key, error).boolean
  def int    (key: Path): One[Int    ] = ???// self.getOrElse(key, error).int
  def double (key: Path): One[Double ] = ???// self.getOrElse(key, error).double
  def string (key: Path): One[String ] = ???// self.getOrElse(key, error).string

  def booleans(key: Path): Nes[Boolean] = ???// self.getOrElse(key, error).booleans
  def ints    (key: Path): Nes[Int    ] = ???// self.getOrElse(key, error).ints
  def doubles (key: Path): Nes[Double ] = ???// self.getOrElse(key, error).doubles
  def strings (key: Path): Nes[String ] = ???// self.getOrElse(key, error).strings

  def boolean_(key: Path): Opt[Boolean] = ???// self.getOrElse(key, error).boolean_
  def int_    (key: Path): Opt[Int    ] = ???// self.getOrElse(key, error).int_
  def double_ (key: Path): Opt[Double ] = ???// self.getOrElse(key, error).double_
  def string_ (key: Path): Opt[String ] = ???// self.getOrElse(key, error).string_

  // ---------------------------------------------------------------------------
  def objs (key: Path): Nes[Dyn] = ???
  def obj_ (key: Path): Opt[Dyn] = ???
  def obj  (key: Path): One[Dyn] = ???
  def objs_(key: Path): Ons[Dyn] = ???

  // ===========================================================================
  //def values$ [$Type](key: KeyW):        Seq[$Type]  = anys$ (key).asInstanceOf[    Seq[$Type] ]
  //def values$_[$Type](key: KeyW): Option[Nes[$Type]] = anys$_(key).asInstanceOf[Option[Nes[$Type]]]
  //def values$$[$Type](key: KeyW):        Seq[$Type]  = anys$$(key).asInstanceOf[Seq[$Type]]
  //
  //def obj   (path: KPath):     Obj    = ???
  //def objs$ (key: KeyW):     Seq[Obj] = ???//values$ [Obj](key)

  def objs$_(key: Key): Option[Nes[Dyn]] = ???//values$_[Obj](key)
  def objs$$(key: Key):        Seq[Dyn]  = ???//values$$[Obj](key)
  def objs$$2_(path: Path): Ons[Dyn] = ???
  def objs$$2 (path: Key) : Nes[Dyn]  = ??? // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

  //def texts$_(key: KeyW): Option[Nes[String]] = anys$_(key).map(_.map(_.str))
  def texts$$(key: Key):     Seq[String]  = ???//anys$$(key)      .map(_.str)
  def texts$$2(path: Path): Nes[String] = ??? // ObjUNesting.scala:def texts$$2(pnk: PotentiallyNestedKey) = dis._plumbing.nestingAccess$$2(pnk)(_.accessors.texts$$)

  def objs$$2 (path: Path):        Nes[Dyn]  = ??? // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

  def text   (key: Path):        StringValue     = ???// text_(key).force
  def text_  (key: Path): Option[StringValue]    = ???// self.get(key).map(_.format)

  def any_  (key: Path): Option[NakedValue]    = ???// self.get(key)
  def any   (key: Path): NakedValue    = ???// self.getOrElse(key, error)

  // ---------------------------------------------------------------------------
  def basicValue(key: Key): NakedValue = ???
  def arraySize (key: Key): aptus.Size = ???

  // ---------------------------------------------------------------------------
  def text   (key: Key):        StringValue     = text_(key).force
  def text_  (key: Key): Option[StringValue]    = get(key).map(_.format)

  def any_  (key: Key): Opt[NakedValue] = get      (key) .map(_.naked)
  def any   (key: Key): One[NakedValue] = getOrElse(key, error).naked

  // ---------------------------------------------------------------------------
  /** returns a seq no matter what */
  def anys$$(key: Key): Nes[NakedValue]  =
    any_(key)
      .map {
        case seq: Seq[_] => seq
        case sgl         => Seq(sgl) }
      .getOrElse(Nil) }

// ===========================================================================
