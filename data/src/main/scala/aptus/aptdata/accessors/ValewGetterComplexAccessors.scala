package aptus
package aptdata
package accessors

// ===========================================================================
private[aptdata] trait ValewGetterComplexAccessors {
    self: ValewGetter with ValewGetterAccessors => // TODO

  import lowlevel.AnyValueFormatter.format

  // ===========================================================================
  private def error = illegalArgument("TODO:t241022114436")

  // ---------------------------------------------------------------------------
  def multipleValuesError(target: NoRenarget) = illegalArgument(s"E250502125928 - multiple values - ${target.format}")

  // ===========================================================================
  def nesting  (target: NoRenarget): One[Dyn] = nesting_ (target).forceOrError(target)
  def nestings (target: NoRenarget): Nes[Dyn] = nestings_(target).forceOrError(target)

  // ---------------------------------------------------------------------------
  def nesting_ (target: NoRenarget): Opt[Dyn] = typed_[    Dyn ](target)(_.nesting)        (_ nesting_  _)
  def nestings_(target: NoRenarget): Ons[Dyn] = typed_[Seq[Dyn]](target)(_.nestings.values)(_ nestings_ _)

  // ===========================================================================
  // to be cleanedup/migrated
  def objs$_(key: Key): Option[Nes[Dyn]] = ???//values$_[Obj](key)
  def objs$$(key: Key):        Seq[Dyn]  = ???//values$$[Obj](key)
  def objs$$2_(path: Path): Ons[Dyn]     = ???
  def objs$$2 (path: Key) : Nes[Dyn]     = ??? // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

  def objs$$2 (path: Path): Nes[Dyn]  = ??? // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

  // ===========================================================================
  def text (target: NoRenarget): One[StringValue] = text_ (target).forceOrError(target)
  def texts(target: NoRenarget): Seq[StringValue] = texts_(target).forceOrError(target)

    // ---------------------------------------------------------------------------
    def text_(target: NoRenarget): Opt[StringValue] = target.und match {
      case key: Key => self.get(key).map { _.naked match { case _: Seq[_] => multipleValuesError(target); case sgl => format(sgl) } }
      case Path(Nil,          leaf) => text_(leaf)
      case Path(head +: rest, leaf) => ValewGetterAccessorsUtils.text_(self)(head, Path(rest, leaf)) }

    // ---------------------------------------------------------------------------
    def texts_(target: NoRenarget): Ons[StringValue] = target.und match {
      case key: Key                 => self.get(key ).map(toStringValues /* permissive */)
      case Path(Nil,          leaf) => self.get(leaf).map(toStringValues /* permissive */)
      case Path(head +: rest, leaf) => ValewGetterAccessorsUtils.texts_(self)(head, Path(rest, leaf)) }

    // ---------------------------------------------------------------------------
    private def toStringValues(valew: Valew): Seq[StringValue] =
      valew.fold[Seq[StringValue]]
        { dyns => dyns.formatCompactJson.in.seq }
        { seq  => seq.map(lowlevel.AnyValueFormatter.format) }
        { dyn  => dyn.formatCompactJson.in.seq }
        { sgl  => lowlevel.AnyValueFormatter.format(sgl).in.seq }

  // ---------------------------------------------------------------------------
  // to be cleanedup/migrated
  //def texts$_(key: KeyW): Option[Nes[String]] = anys$_(key).map(_.map(_.str))
  def texts$$(key: Key):     Seq[String]  = ???//anys$$(key)      .map(_.str)
  def texts$$2(path: Path): Nes[String] = ??? // ObjUNesting.scala:def texts$$2(pnk: PotentiallyNestedKey) = dis._plumbing.nestingAccess$$2(pnk)(_.accessors.texts$$)

  // ===========================================================================
  // to be cleanedup/migrated
            def any__  : Option[NakedValue] = ???//value__[AnyValue]
            def anys$  : Seq   [NakedValue] = ???//values$[AnyValue]
  //@inline def anys$$ : Seq   [NakedValue] = ???//Bar.anys$$(u)

  // ===========================================================================
  /** if confident one and exactly one (else favor seq-based + eg force.option) */
  def nakedValue(target: NoRenarget): One[NakedValue] = nakedValues(target).force.one

  // ---------------------------------------------------------------------------
  /** never fails, just returns all values */
  def nakedValues(target: NoRenarget): Seq[NakedValue] = target.und match {
    case key: Key => self.get(key ).toSeq.flatMap { _.naked match { case seq: Seq[_] => seq; case sgl => Seq(sgl) } }
    case Path(Nil,          leaf) => nakedValues(leaf)
    case Path(head +: rest, leaf) => ValewGetterAccessorsUtils.nakedValues(self)(head, Path(rest, leaf)) }

  // ===========================================================================
  // 2D

  def booleanss(key: Key): Seq2D[Boolean] = getOrElse(key, error).booleanss
  def intss    (key: Key): Seq2D[Int    ] = getOrElse(key, error).intss
  def doubless (key: Key): Seq2D[Double ] = getOrElse(key, error).doubless
  def stringss (key: Key): Seq2D[String ] = getOrElse(key, error).stringss

  def matrix           (key: Key): RealMatrixCommons = getOrElse(key, error).matrix
  def realMatrixCommons(key: Key): RealMatrixCommons = getOrElse(key, error).matrix

  // ===========================================================================
  def any_  (key: Path): Option[NakedValue] = ???// self.get(key)
  def any   (key: Path): NakedValue         = ???// self.getOrElse(key, error)

  // ---------------------------------------------------------------------------
  def basicValue(key: Key): NakedValue = ???
  def arraySize (key: Key): aptus.Size = ???

  // ---------------------------------------------------------------------------
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