package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynTransformsTest extends TestSuite {
  import aptus.dyn._
  import Error._

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1.transform      (foo).using { case Valew(s: String) => s.toUpperCase }.check(dyn(foo -> BAR, baz -> 1))) // should never actually have to do that
    test(_Sngl1.transform      (foo).using { _.string.toUpperCase }                  .check(dyn(foo -> BAR, baz -> 1)))
    test(_Sngl1.transformString(foo).using { _       .toUpperCase }                  .check(dyn(foo -> BAR, baz -> 1)))

    // ---------------------------------------------------------------------------
    val f: Valew => NakedValue = _.int + 1

    // ===========================================================================
    test(_Sngl1.transform(baz ~> BAZ) .using(f).check(dyn(foo -> bar, BAZ -> 2)))

    test(_Sngl3 .transform(      p |> baz).using(f).check(          dyn(p -> _Sngl1_2)))
    test(_Sngl33.transform(p2 |> p |> baz).using(f).check(dyn(p2 -> dyn(p -> _Sngl1_2))))

    // ===========================================================================
    test(dyns(dyn(p -> _Sngl1), dyn(p -> _Sngl1))
      .transform(p |> baz).using(f)
      .check {
        dyns(dyn(p -> _Sngl1_2), dyn(p -> _Sngl1_2)) })

    // ---------------------------------------------------------------------------
    test(dyns(dyn(p -> _Mult1), dyn(p -> _Mult1))
      .transform(p |> baz).using(f)
      .check {
        dyns(dyn(p -> _Mult1b), dyn(p -> _Mult1b)) })

    // ---------------------------------------------------------------------------
    test(Try(dyn(p -> _Mult1.asDynz)).check(Error.NoNestedIterators))

    // ===========================================================================
    test(_Sngl1.transform(foo).using(_.string.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transform(foo).using(_.string.toUpperCase).check(_Sngl1_BAR))

    // ===========================================================================
    test(_Sngl1.transformIfString(foo).using   (_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transform        (foo).ifString(_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transformString  (foo).using   (_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transform        (foo).string  (_.toUpperCase).check(_Sngl1_BAR))

    test(    _Sngl1.transformIfString(baz).using(_.toUpperCase) .check(_Sngl1))
    test(Try(_Sngl1.transformString  (baz).using(_.toUpperCase)).check(TransformSpecificType))

    test(_Sngl1.transform(foo).using { case Valew(s: String) => s.toUpperCase; case Valew(_: Int) => ??? }.check(_Sngl1_BAR))
    test(_Sngl1.transform(foo).using(_.string                    .toUpperCase)                            .check(_Sngl1_BAR))

    test(_Sngl1.transform(foo).using { x => x.basicTypeOpt match {
        case Some(BasicType._String) => x.string.toUpperCase
        case Some(BasicType._Int)    => x } }
      .check(_Sngl1_BAR))

    test(_Sngl1.transformIfString(foo).using(_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transformIfInt   (baz).using(_ + 1)        .check(_Sngl1_2))

    test(_Sngl1.transformString  (foo).using(_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transformInt     (baz).using(_ + 1)        .check(_Sngl1_2))

    test(Try { _Sngl1.transformString(baz).using(_.toUpperCase).check(_Sngl1_BAR) }.check(TransformSpecificType))
    test(Try { _Sngl1.transformInt   (foo).using(_ + 1)        .check(_Sngl1)  }.check(TransformSpecificType))

    import shorthands.$$
    test(_Sngl1.transformIfType($$       ._String)(foo).using(_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transformIfType(BasicType._String)(foo).using(_.toUpperCase).check(_Sngl1_BAR))

    test(_Sngl1.transformType($$       ._String)(foo).using(_.toUpperCase).check(_Sngl1_BAR))
    test(_Sngl1.transformType(BasicType._String)(foo).using(_.toUpperCase).check(_Sngl1_BAR))

    test(      _Sngl1.transformIfType(BasicType._String)(baz).using(_.toUpperCase)  .check(_Sngl1))
    test(Try { _Sngl1.transformType  (BasicType._String)(baz).using(_.toUpperCase) }.check(Error.TransformSpecificType))

    test(      _Sngl1.transformIfType(BasicType._String)(FOO /* not present */).using(_.toUpperCase)  .check(_Sngl1))
// TODO: t250605154441 - decide behavior
    //test(Try { _Sngl1.transformType  (BasicType._String)(FOO /* not present */).using(_.toUpperCase) }.check(Error.TransformSpecificType))

    test(_Sngl3 .transformNesting (p).using(_.upperCase(foo)).check(_Sngl3d))
    test(_Mult11.transformNestings(p).using(_.upperCase(foo)).check(_Mult11d))

		test(_Sngl1.transform(foo ~> FOO).using(_.string.toUpperCase).check(dyn(FOO -> BAR, baz -> 1)))

    // ---------------------------------------------------------------------------
    test(_Sngl1.transformString(foo).using(_.toUpperCase))

    // ===========================================================================
    test("multiple targets") {
      val expected = _Sngl1_BAR.add(p -> _Sngl1_RAB)

      test(_Sngl1.add(p -> _Sngl1_rab).transformGuaranteed(foo, p |> foo).using(_.string.toUpperCase).check(expected))
      test(Try {
           _Sngl1.add(p -> _Sngl1_rab).transformGuaranteed(foo, p |> FOO).using(_.string.toUpperCase) }.checkIllegalArgument(TransformGuaranteeFailure.id)) }

    // ===========================================================================
    // ===========================================================================
    // ===========================================================================
    // custom transforms

    test(_Sngl1.transformEntries   { _.mapUnderlyingValues(_.reverse) }.check(dyn(baz -> 1, foo -> bar)))
    test(_Sngl1.transformData      { _.reverse }                       .check(dyn(baz -> 1, foo -> bar))) } }

// ===========================================================================
