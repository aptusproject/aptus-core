package aptus
package aptdata
package domain
package valew
import valew.{Valew => Self}

import aptus.aptreflect.nodes._

// ===========================================================================
/** Little bit like gallia.Whatever */
case class Valew private[Valew] (naked: NakedValue)
      extends ValewFormatting
         with ValewTransforming
         with accessors.ValewBasicAccessors   /* eg .string      */
         with accessors.ValewComplexAccessors /* eg .realLikeOpt */ {
    require(!ValewOps.isOrContainsValew(naked), naked) // a241119155118

    // ---------------------------------------------------------------------------
    /** only use for debugging/tests */
    def runtimeTypeNode: TypeNode = Self.DynTypeNodeRuntimeParser(naked)

    // ---------------------------------------------------------------------------
    override def toString: String = formatDebug

    // ---------------------------------------------------------------------------
    def transformValew(valueF: Valew => NakedValue) = ValewOps.potentiallyUnwrap(valueF(this))

    // ===========================================================================
    private[valew] def notNestingError  () = error("E250529135752", "no nesting")
    private[valew] def notMultiplesError() = error("E250529135753", "no multiples")

      // ---------------------------------------------------------------------------
      private def error(id: String, msg: String) = aptus.illegalArgument(s"${id} - ${msg}: ${naked} (${runtimeTypeNode.formatPrettyJson})")

    // ===========================================================================
    def texts: Seq[StringValue] =
      fold3d[StringValue]
        { /* dyn  */ _         .formatCompactJson }               /* not really expecting nesting here, but making do */
        { /* dyns */ _.exoMap(_.formatCompactJson).consumeAll() } /* not really expecting nesting here, but making do */
        { lowlevel.AnyValueFormatter.formatLeaf(_) } }

  // ===========================================================================
  object Valew {
    /** must be prenormalized first (see 241122121139) */
    def build(value: NakedValue) = Valew.apply(value)

    @inline def potentiallyUnwrap(value: Any): Valew = ValewOps.potentiallyUnwrap(value)

    // ===========================================================================
    private lazy val DynTypeNodeRuntimeParser =
      new TypeNodeRuntimeParser({
        case _: Dyn       => TypeNodeBuiltIns.AptusDyn
        case _: Dyns      => TypeNodeBuiltIns.AptusDyns
        case _: Dynz      => TypeNodeBuiltIns.AptusDynz }) }

// ===========================================================================
