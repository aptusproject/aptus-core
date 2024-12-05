package aptus
package experimental
package dyn
package domain
package selectors

// ===========================================================================
/** renaming */
case class Ren(from: Key, to: Key)
    extends HasFormatDefault {

  def pair = from -> to

  // ---------------------------------------------------------------------------
  override final def toString: String = formatDefault
    override final def formatDefault: String =
      s"${from} ~> ${to}" }

// ===========================================================================
/** renamings */
case class Rens(values: Seq[Ren]) extends Items[Rens, Ren] {
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
