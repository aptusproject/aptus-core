package aptus
package aptdata
package meta
package selectors

// ===========================================================================
/** nesting */
case class RPath(parent: Seq[Key], ren: Ren) extends HasFormatDefault with Targetable with Renargetable {
  def rpaths: RPaths = RPaths.from(this)

  // ---------------------------------------------------------------------------
  def forcePair: (Key, RPath) = parent.splitAtHead.mapSecond(RPath.apply(_, ren))

  // ---------------------------------------------------------------------------
  def parentPathOpt: Option[Path] =
    parent match {
      case Nil          => None
      case init :+ last => Some(Path(init, last)) }

  // ---------------------------------------------------------------------------
  override final def toString: String = formatDefault

    override final def formatDefault: String =
      parentPathOpt
        .map(_.formatDefault)
        .map(_.append(PathSeparator))
        .getOrElse("")
        .append(ren.formatDefault) }

  // ===========================================================================
  object RPath {
    private type Self = RPath

    // ---------------------------------------------------------------------------
    def leaf(ren: Ren): Self = RPath(parent = Nil, ren) }

// ===========================================================================
/** nestings */
case class RPaths(values: Seq[RPath])
      extends aptitems.Items[RPaths, RPath] {
    // TODO: requires

    // ---------------------------------------------------------------------------
    final override val const = RPaths.apply

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        formatWithSectionAndSize  }

  // ===========================================================================
  object RPaths {
    def from(value1: RPath, more: RPath*): RPaths = RPaths((value1 +: more).toList) }

// ===========================================================================

