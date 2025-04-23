package aptustesting
package dyntest

// ===========================================================================
object DynTransformsTest {
  import aptus.dyn._
  import Error._

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    _Sngl1.transform      (foo).using { case Valew(s: String) => s.toUpperCase }.check(dyn(foo -> BAR, baz -> 1)) // should never actually have to do that
    _Sngl1.transform      (foo).using { _.string.toUpperCase }                  .check(dyn(foo -> BAR, baz -> 1))
    _Sngl1.transformString(foo).using { _       .toUpperCase }                  .check(dyn(foo -> BAR, baz -> 1))

    // ---------------------------------------------------------------------------
    val f: Valew => NakedValue = _.int + 1

    // ===========================================================================
    _Sngl1.transform(_.renaming(baz,   BAZ)).using(f).check(dyn(foo -> bar, BAZ -> 2))
    _Sngl1.transform(           baz ~> BAZ) .using(f).check(dyn(foo -> bar, BAZ -> 2))

    _Sngl3 .transform(      p |> baz).using(f).check(          dyn(p -> _Sngl1b))
    _Sngl33.transform(p2 |> p |> baz).using(f).check(dyn(p2 -> dyn(p -> _Sngl1b)))

    // ===========================================================================
    dyns(dyn(p -> _Sngl1), dyn(p -> _Sngl1))
      .transform(p |> baz).using(f)
      .check {
    dyns(dyn(p -> _Sngl1b), dyn(p -> _Sngl1b)) }

    // ---------------------------------------------------------------------------
    dyns(dyn(p -> _Mult1), dyn(p -> _Mult1))
      .transform(p |> baz).using(f)
      .check {
    dyns(dyn(p -> _Mult1b), dyn(p -> _Mult1b)) }

    // ---------------------------------------------------------------------------
    Try(dyn(p -> _Mult1.asDynz)).check(Error.NoNestedIterators)

    // ===========================================================================
    // ===========================================================================
    // ===========================================================================
    // custom transforms

    _Sngl1.transformEntries   { _.mapUnderlyingValues(_.reverse) }.check(dyn(baz -> 1, foo -> bar))
    _Sngl1.transformData      { _.reverse }                       .check(dyn(baz -> 1, foo -> bar))

    // ===========================================================================
    _Sngl1.transformIfString(foo).using   (_.toUpperCase).check(_Sngl1d)
    _Sngl1.transform        (foo).ifString(_.toUpperCase).check(_Sngl1d)
    _Sngl1.transformString  (foo).using   (_.toUpperCase).check(_Sngl1d)
    _Sngl1.transform        (foo).string  (_.toUpperCase).check(_Sngl1d)

        _Sngl1.transformIfString(baz).using(_.toUpperCase) .check(_Sngl1)
    Try(_Sngl1.transformString  (baz).using(_.toUpperCase)).check(TransformSpecificType)

    _Sngl1.transform(foo).using { case Valew(s: String) => s.toUpperCase; case Valew(_: Int) => ??? }.check(_Sngl1d)
    _Sngl1.transform(foo).using(_.string                    .toUpperCase)                            .check(_Sngl1d)

    _Sngl1.transform(foo).using { x => x.basicTypeOpt match {
        case Some(BasicType._String) => x.string.toUpperCase
        case Some(BasicType._Int)    => x } }
      .check(_Sngl1d)

    _Sngl1.transformIfString(foo).using(_.toUpperCase).check(_Sngl1d)
    _Sngl1.transformIfInt   (baz).using(_ + 1)        .check(_Sngl1b)

    _Sngl1.transformString  (foo).using(_.toUpperCase).check(_Sngl1d)
    _Sngl1.transformInt     (baz).using(_ + 1)        .check(_Sngl1b)

    Try { _Sngl1.transformString(baz).using(_.toUpperCase).check(_Sngl1d) }.check(TransformSpecificType)
    Try { _Sngl1.transformInt   (foo).using(_ + 1)        .check(_Sngl1)  }.check(TransformSpecificType)

    import shorthands.$$
    _Sngl1.transformIfType($$       ._String)(foo)(_.toUpperCase).check(_Sngl1d)
    _Sngl1.transformIfType(BasicType._String)(foo)(_.toUpperCase).check(_Sngl1d)

    _Sngl3    .transformDyn (p)(_.upperCase(foo)).check(_Sngl3d)
    _Mult11.transformDyns(p)(_.upperCase(foo)).check(_Mult11d)

		_Sngl1.transform(foo ~> FOO).using(_.string.toUpperCase).check(dyn(FOO -> BAR, baz -> 1))

    // ---------------------------------------------------------------------------
    _Sngl1.transformString(foo).using(_.toUpperCase) } }

// ===========================================================================
