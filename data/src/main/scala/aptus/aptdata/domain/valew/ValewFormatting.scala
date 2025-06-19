package aptus
package aptdata
package domain
package valew

import lowlevel.AnyValueFormatter.formatLeaf

// ===========================================================================
trait ValewFormatting { self: Valew =>

  // ---------------------------------------------------------------------------
  def formatDebug: String =
    s"Valew(${naked.getClass}:${naked.toString})" // TODO: use TypeNode rather

  // ---------------------------------------------------------------------------
  def formatDefault: String =
    fold3[String](
      _.formatDebug)(
      formatLeaf)(
      _.mkString("[", ",", "]"))

  // ===========================================================================
  def formatLeafValew: String =
   naked match {
      case _: Dyn    => notNestingError()
      case _: Dyns   => notNestingError()
      case _: Seq[_] => notMultiplesError()
      case leaf      => formatLeaf(leaf) }

  // ===========================================================================
  def formatValew            : String = _formatValew(Trio.Default)(naked)
  def formatValew(trio: Trio): String = _formatValew(trio)(naked)

    // ---------------------------------------------------------------------------
    private def _formatValew(trio: Trio)(any: Any): String =
     any match {
        case dyn : Dyn    => dyn .formatCompactJson
        case dyns: Dyns   => dyns.formatCompactJson
        case seq : Seq[_] => seq.map(_formatValew(trio)).pipe(trio.format2)
        case leaf         => formatLeaf(leaf) } }

// ===========================================================================