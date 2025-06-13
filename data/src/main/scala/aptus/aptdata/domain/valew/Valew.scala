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
    def transformValew(valueF: Valew => NakedValue) = ValewOps.potentiallyUnwrap(valueF(this))

    // ===========================================================================
    def fold[T](f: Dyns => T)(g: Seq[_] => T)(h: Dyn => T)(i: NakedValue => T): T =
      naked match {
        // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
        case dyns: Dyns   => f(dyns)
        case seq : Seq[_] => g(seq)
        case dyn : Dyn    => h(dyn)
        case sgl          => i(sgl) }

  // ---------------------------------------------------------------------------
  def fold2(f: Dyn => Dyn)(g: Dyns => Dyns): NakedValue = _fold2(f)(g)(value = naked)

    // ---------------------------------------------------------------------------
    private def _fold2(f: Dyn => Dyn)(g: Dyns => Dyns)(value: NakedValue): NakedValue =
        value match { // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
          case dyn : Dyn    => f(dyn)
          case dyns: Dyns   => g(dyns)
          case seq : Seq[_] => seq.map(_fold2(f)(g))
          case other        => other }

    // ---------------------------------------------------------------------------
    def fold3[T](d: Dyn => T)(n: NakedValue => T)(s: Seq[T] => T): T =
      naked match {
        // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
        case dyns: Dyns   => dyns.exoMap(d).consumeAll().pipe(s)
        case seq : Seq[_] => s(seq.map {
          case dyn : Dyn    => d(dyn)
          case sgl          => n(sgl) })
        case dyn : Dyn    => d(dyn)
        case sgl          => n(sgl) }

    // ---------------------------------------------------------------------------
    def transformNesting(f: Dyn => Dyn): Valew =
      fold[Valew]
        { dyns => dyns.endoMap(f).pipe(Valew.build) }
        { seq =>
          seq
            .map {
              case dyn : Dyn    => f(dyn)
              case sgl          => Valew.notNestingError(sgl) }
            .dyns
            .pipe(Valew.build) }
        { dyn => f(dyn).pipe(Valew.build) }
        { sgl => Valew.notNestingError(sgl) }

    // ---------------------------------------------------------------------------
    def nesting(p: Dyn => Unit): Unit =
      fold[Unit]
        { dyns => dyns.valuesIterator.foreach(p) }
        { seq =>
          seq
            .map {
              case dyn : Dyn    => p(dyn)
              case _            => () } }
        { dyn => p(dyn) }
        { _   => () }

    // ===========================================================================
    override def toString: String = formatDebug

      // ---------------------------------------------------------------------------
      def formatDebug: String =
        s"Valew(${naked.getClass}:${naked.toString})"

      // ---------------------------------------------------------------------------
      def formatDefault: String =
        fold3[String](
          _.formatDebug)(
          aptdata.lowlevel.AnyValueFormatter.format)(
          _.mkString("[", ",", "]"))

    // ---------------------------------------------------------------------------
// TODO: t250528102620 - if seq...
@deprecated def format: String = aptdata.lowlevel.AnyValueFormatter.format(naked) }

  // ===========================================================================
  object Valew {
    private def notNestingError(value: NakedValue) = aptus.illegalArgument(
      s"E250529135752 - not nesting: ${value} (${value.getClass})")

    // ---------------------------------------------------------------------------
    /** must be prenormalized first (see 241122121139) */
    def build(value: NakedValue) = Valew.apply(value)

    @inline def potentiallyUnwrap(value: Any): Valew = ValewOps.potentiallyUnwrap(value) }

// ===========================================================================
