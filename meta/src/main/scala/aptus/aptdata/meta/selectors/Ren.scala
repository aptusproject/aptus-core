package aptus
package aptdata
package meta
package selectors

// ===========================================================================
/** renaming */
case class Ren(from: Key, to: Key)
      extends HasFormatDefault
         with Targetable
         with Renargetable {

    def rpath: RPath = RPath.leaf(this)

    // ---------------------------------------------------------------------------
    def pair = from -> to

    // ---------------------------------------------------------------------------
    override final def toString: String = formatDefault
      override final def formatDefault: String =
        s"${from}${RenSeparator}${to}" }

  // ===========================================================================
  object Ren {
    def idem(key: Key): Ren = Ren(key, key) }

// ===========================================================================
/** renamings */
case class Rens(values: Seq[Ren])
    extends aptitems.Items[Rens, Ren] {
  final override val const = Rens.apply

  // ---------------------------------------------------------------------------
  override def toString: String = formatDefault
    def formatDefault: String =
      formatWithSectionAndSize

  // ---------------------------------------------------------------------------
  values.map(_.from).requireDistinct()
  values.map(_.to)  .requireDistinct()
  // TODO: more...

  // ---------------------------------------------------------------------------
  def toMap: Map[Key, Key] = values.map(_.pair).force.map }

// ===========================================================================
