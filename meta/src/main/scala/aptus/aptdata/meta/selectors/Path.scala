package aptus
package aptdata
package meta
package selectors

// ===========================================================================
/** nesting */
case class Path(parent: Seq[Key], key: Key)
    extends HasFormatDefault
       with Targetable
       with NoRenargetable {

  // ---------------------------------------------------------------------------
  override final def toString: String = formatDefault

    override final def formatDefault: String =
      (parent :+ key)
        .map(_.formatDefault)
        .join(PathSeparator)

  // ---------------------------------------------------------------------------
  def  |>(s: Key): Path = (parent :+ key).pipe(Path.apply(_, s))
  def ||>(s: Key): Path = (parent :+ key).pipe(Path.apply(_, s)) /* if need to avoid conflict with Gallia's */

  def  ~>(s: Key): RPath = RPath(parent, Ren(key, s))
  def ~~>(s: Key): RPath = ~>(s)

  // ---------------------------------------------------------------------------
  def fold[T](f: Key => T)(g: (Key, Path) => T): T =
      parent match {
        case Nil             => f(key)
        case headKey +: tail => g(headKey, Path(parent = tail, key)) }

    // ---------------------------------------------------------------------------
    def forcePair: (Key, Path) = parent.splitAtHead.mapSecond(Path.apply(_, key))

  // ---------------------------------------------------------------------------
  def initPathOpt: Option[Path] = parent.in.noneIf(_.isEmpty).map { x => Path(x.init, x.last) }

    // ---------------------------------------------------------------------------
    def tailPair: (Key, Option[Path]) =
      parent match {
        case Nil             => key     -> None
        case headKey +: tail => headKey -> Some(Path(parent = tail, key)) } }

  // ===========================================================================
  object Path {
    def leaf(key: Key): Path = Path(parent = Nil, key) }

// ===========================================================================
/** nestings */
case class Paths(values: Seq[Path])
      extends aptitems.Items[Paths, Path] {
    // TODO: requires

    // ---------------------------------------------------------------------------
    final override val const = Paths.apply

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        formatWithSectionAndSize  }

  // ===========================================================================
  object Paths {
    def from(value1: Path, more: Path*): Paths = Paths((value1 +: more).toList) }

// ===========================================================================
