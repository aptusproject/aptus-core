package aptus
package aptdata
package accessors

// ===========================================================================
private[aptdata] trait ValewGetterComplexAccessors {
    self: ValewGetter with ValewGetterBasicAccessors => // TODO

  private def error = illegalArgument("TODO:t241022114436")

  // ===========================================================================
  /** if confident one and exactly one (else favor seq-based + eg force.option) */
  def nakedValue(target: NoRenarget): One[NakedValue] = nakedValues(target).force.one

  // ---------------------------------------------------------------------------
  /** never fails, just returns all values */
  def nakedValues(target: NoRenarget): Seq[NakedValue] = target.und match {
    case key: Key => self.get(key ).toSeq.flatMap { _.naked match { case seq: Seq[_] => seq; case sgl => Seq(sgl) } }
    case Path(Nil,          leaf) => nakedValues(leaf)
    case Path(head +: rest, leaf) => ValewGetterAccessorsUtils.nakedValues(self)(head, Path(rest, leaf)) }

  // ---------------------------------------------------------------------------
  // to be cleanedup/migrated
            def any__  : Option[NakedValue] = ???//value__[AnyValue]
            def anys$  : Seq   [NakedValue] = ???//values$[AnyValue]
  //@inline def anys$$ : Seq   [NakedValue] = ???//Bar.anys$$(u)

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