package aptus
package aptdata
package domain
package valew

// ===========================================================================
trait ValewTransforming { self: Valew =>

  def fold[T]
        (ds: Dyns => T)
        (s: Seq[_] => T)
        (d: Dyn => T)
        (n: NakedValue => T)
      : T =
    naked match {
      // note: no nested Dynz/Iterator (see a241119155444)
      case dyns: Dyns   => ds(dyns)
      case seq : Seq[_] => s(seq)
      case dyn : Dyn    => d(dyn)
      case sgl          => n(sgl) }

  // ---------------------------------------------------------------------------
  def fold2(f: Dyn => Dyn)(g: Dyns => Dyns): NakedValue = _fold2(f)(g)(naked)

    // ---------------------------------------------------------------------------
    private def _fold2(f: Dyn => Dyn)(g: Dyns => Dyns)(vle: Any): NakedValue =
        vle match { // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
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
  def fold3b[T](d: Dyn => Option[T])(n: NakedValue => Option[T])(s: Seq[T] => T): Option[T] =
    naked match {
      // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
      case dyns: Dyns   => dyns.exoFlatMap(d(_)).consumeAll().in.noneIf(_.isEmpty).map(s)
      case seq : Seq[_] => seq.flatMap {
          case dyn : Dyn    => d(dyn)
          case sgl          => n(sgl) }
        .in.noneIf(_.isEmpty)
        .map(s)
      case dyn : Dyn    => d(dyn)
      case sgl          => n(sgl) }

  // ---------------------------------------------------------------------------
  def fold3d[T](d: Dyn => T)(d2: Dyns => Seq[T])(n: NakedValue => T): Seq[T] =
    naked match {
      // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
      case dyns: Dyns   => d2(dyns)
      case seq : Seq[_] => seq.map {
// intentially don't recurse
        case dyn : Dyn    => d(dyn)
        case sgl          => n(sgl) }
      case dyn : Dyn    => d(dyn).in.seq
      case sgl          => n(sgl).in.seq }

  // ---------------------------------------------------------------------------
  def fold3e[T](f2: Dyn => Seq[T])(f1: Dyns => Seq[T]): Seq[T] = _fold3e[T](f2)(f1)(naked)

    protected def _fold3e[T](f2: Dyn => Seq[T])(f1: Dyns => Seq[T])(vle: Any): Seq[T] =
      vle match {
          case dyns: Dyns   => f1(dyns)
          case seq : Seq[_] => seq.flatMap(_fold3e[T](f2)(f1))
          case dyn : Dyn    => f2(dyn)
          case _            => Nil }

    // ---------------------------------------------------------------------------
    def transformNesting(f: Dyn => Dyn): Valew =
      fold[Valew]
        { dyns => dyns.endoMap(f).pipe(Valew.build) }
        { seq =>
          seq
            .map {
              case dyn : Dyn => f(dyn)
              case sgl       => Valew.build(sgl).notNestingError() }
            .dyns
            .pipe(Valew.build) }
        { dyn => f(dyn).pipe(Valew.build) }
        { _   => notNestingError() }

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
        { _   => () } }

// ===========================================================================