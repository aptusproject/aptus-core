package aptus
package aptdata
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
    def to[T](f: Dyns => T)(g: Seq[_] => T)(h: Dyn => T)(i: NakedValue => T): T =
      naked match {
        // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
        case dyns: Dyns   => f(dyns)
        case seq : Seq[_] => g(seq)
        case dyn : Dyn    => h(dyn)
        case sgl          => i(sgl) }

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        s"Valew(${naked.getClass}:${naked.toString})"

    // ---------------------------------------------------------------------------
    // TODO: t250528102620 - if seq...
    def format: String = aptdata.lowlevel.AnyValueFormatter.format(naked) }

  // ===========================================================================
  object Valew {
    /** must be prenormalized first (see 241122121139) */
    def build(value: NakedValue) = Valew.apply(value)

    @inline def potentiallyUnwrap(value: Any): Valew = ValewOps.potentiallyUnwrap(value) }

// ===========================================================================
