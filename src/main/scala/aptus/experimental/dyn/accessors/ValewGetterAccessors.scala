package aptus
package experimental
package dyn
package accessors

// ===========================================================================
private[dyn] trait ValewGetterAccessors {
    self: ValewGetter => // TODO

  // ---------------------------------------------------------------------------
  private def error = illegalArgument("TODO:t241022114436")

  // ---------------------------------------------------------------------------
  private type One[T] =        T
  private type Opt[T] = Option[T]

  // ===========================================================================
// TODO: codegen
  def boolean(key: Key): One[Boolean] = getOrElse(key, error).boolean
  def int    (key: Key): One[Int    ] = getOrElse(key, error).int
  def double (key: Key): One[Double ] = getOrElse(key, error).double
  def string (key: Key): One[String ] = getOrElse(key, error).string

  def booleans(key: Key): Seq[Boolean] = getOrElse(key, error).booleans
  def ints    (key: Key): Seq[Int    ] = getOrElse(key, error).ints
  def doubles (key: Key): Seq[Double ] = getOrElse(key, error).doubles
  def strings (key: Key): Seq[String ] = getOrElse(key, error).strings

def boolean_(key: Key): Opt[Boolean] = get(key).map(_.boolean)
  def int_    (key: Key): Opt[Int    ] = get(key).map(_.int)
  def double_ (key: Key): Opt[Double ] = get(key).map(_.double)
  def string_ (key: Key): Opt[String ] = get(key).map(_.string)

  def objs (key: Key): Seq[Dyn] = ???
  def obj_ (key: Key): Opt[Dyn] = ???
  def obj  (key: Key): One[Dyn] = ???
  def objs_(key: Key): Pes[Dyn] = ???

  // ===========================================================================
  // 2D

  def booleanss(key: Key): Seq2D[Boolean] = getOrElse(key, error).booleanss
  def intss    (key: Key): Seq2D[Int    ] = getOrElse(key, error).intss
  def doubless (key: Key): Seq2D[Double ] = getOrElse(key, error).doubless
  def stringss (key: Key): Seq2D[String ] = getOrElse(key, error).stringss

  def realMatrixCommons(key: Key): RealMatrixCommons = getOrElse(key, error).realMatrixCommons

  // ===========================================================================
//TODO:
  def boolean(key: Path): One[Boolean] = ???// self.getOrElse(key, error).boolean
  def int    (key: Path): One[Int    ] = ???// self.getOrElse(key, error).int
  def double (key: Path): One[Double ] = ???// self.getOrElse(key, error).double
  def string (key: Path): One[String ] = ???// self.getOrElse(key, error).string

  def booleans(key: Path): Seq[Boolean] = ???// self.getOrElse(key, error).booleans
  def ints    (key: Path): Seq[Int    ] = ???// self.getOrElse(key, error).ints
  def doubles (key: Path): Seq[Double ] = ???// self.getOrElse(key, error).doubles
  def strings (key: Path): Seq[String ] = ???// self.getOrElse(key, error).strings

  def boolean_(key: Path): Opt[Boolean] = ???// self.getOrElse(key, error).boolean_
  def int_    (key: Path): Opt[Int    ] = ???// self.getOrElse(key, error).int_
  def double_ (key: Path): Opt[Double ] = ???// self.getOrElse(key, error).double_
  def string_ (key: Path): Opt[String ] = ???// self.getOrElse(key, error).string_

  // ---------------------------------------------------------------------------
  def objs (key: Path): Seq[Dyn] = ???
  def obj_ (key: Path): Opt[Dyn] = ???
  def obj  (key: Path): One[Dyn] = ???
  def objs_(key: Path): Pes[Dyn] = ???

  // ===========================================================================
  //def values$ [$Type](key: KeyW):        Seq[$Type]  = anys$ (key).asInstanceOf[    Seq[$Type] ]
  //def values$_[$Type](key: KeyW): Option[Nes[$Type]] = anys$_(key).asInstanceOf[Option[Nes[$Type]]]
  //def values$$[$Type](key: KeyW):        Seq[$Type]  = anys$$(key).asInstanceOf[Seq[$Type]]
  //
  //def obj   (path: KPath):     Obj    = ???
  //def objs$ (key: KeyW):     Seq[Obj] = ???//values$ [Obj](key)

  def objs$_(key: Key): Option[Nes[Dyn]] = ???//values$_[Obj](key)
  def objs$$(key: Key):        Seq[Dyn]  = ???//values$$[Obj](key)
  def objs$$2_(path: Path): Pes[Dyn] = ???
  def objs$$2 (path: Key) : Nes[Dyn]  = ??? // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

  //def texts$_(key: KeyW): Option[Nes[String]] = anys$_(key).map(_.map(_.str))
  def texts$$(key: Key):     Seq[String]  = ???//anys$$(key)      .map(_.str)
  def texts$$2(path: Path): Seq[String] = ??? // ObjUNesting.scala:def texts$$2(pnk: PotentiallyNestedKey) = dis._plumbing.nestingAccess$$2(pnk)(_.accessors.texts$$)

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
  def anys$$(key: Key): Seq[NakedValue]  =
    any_(key)
      .map {
        case seq: Seq[_] => seq
        case sgl         => Seq(sgl) }
      .getOrElse(Nil) }

// ===========================================================================
