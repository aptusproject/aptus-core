package aptus
package experimental
package dyn
package domain
package valew

// ===========================================================================
/** Little bit like gallia.Whatever */
case class Valew private[Valew] (naked: NakedValue)
      extends accessors.ValewBasicAccessors   /* eg .string      */
         with accessors.ValewComplexAccessors /* eg .realLikeOpt */ {

    require(!ValewOps.isOrContainsValew(naked), naked) // a241119155118

    // ---------------------------------------------------------------------------
    def transform(valueF: Valew => NakedValue) = ValewOps.potentiallyUnwrap(valueF(this))

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        s"Valew(${naked.getClass}:${naked.toString})"

    // ---------------------------------------------------------------------------
    def format: String = _root_.gallia.data.DataFormatting.format(naked) }

  // ===========================================================================
  object Valew {
    /** must be prenormalized first (see 241122121139) */
    def build(value: NakedValue) = Valew.apply(value)

    @inline def potentiallyUnwrap(value: Any): Valew = ValewOps.potentiallyUnwrap(value) }

// ===========================================================================
