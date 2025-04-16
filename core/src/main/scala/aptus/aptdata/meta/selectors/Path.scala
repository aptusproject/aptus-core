package aptus
package aptdata
package meta
package selectors

// ===========================================================================
/** nesting */
case class Path(parent: Seq[Key], key: Key) // t241205112955 - do offer a Path+Ren, eg foo |> bar ~> BAR
    extends HasFormatDefault {

  // ---------------------------------------------------------------------------
  override final def toString: String = formatDefault

    override final def formatDefault: String =
      (parent :+ key)
        .map(_.formatDefault)
        .join(" |> ")

  // ---------------------------------------------------------------------------
  def  |>(s: Key): Path = (parent :+ key).pipe(Path.apply(_, s))
  def ||>(s: Key): Path = (parent :+ key).pipe(Path.apply(_, s)) /* if need to avoid conflict with Gallia's */

  // ---------------------------------------------------------------------------
  def tailPair: (Key, Option[Path]) =
    parent match {
      case Nil             => key     -> None
      case headKey +: tail => headKey -> Some(Path(parent = tail, key)) } }

  // ===========================================================================
  object Path {
    /* mostly for tests */
    def leaf(key: Key): Path = Path(parent = Nil, key.und) }

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
