package aptus
package experimental
package dyntest

// ===========================================================================
object DynSelectorsTests {
  import dyn._
  import Dyn .dyn
  import Error.TransformGuaranteeFailure

  // ---------------------------------------------------------------------------
  private val f1: Valew => NakedValue = _.int + 1
  private val f2: Valew => NakedValue = _.string.toUpperCase

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {

    _Sngl1.transform(baz).using(f1).check(_Sngl1b)

    _Sngl1.transform(_.selection(_.reverse.take(1))).using(f1).check(_Sngl1b)

    _Sngl1.transform(_.explicitKey (              baz))     .using(f1).check(_Sngl1b)
    _Sngl1.transform(_.keyPredicate(         _ == baz))     .using(f1).check(_Sngl1b)

    _Sngl1.transform(_.selectOne   (_.find  (_ == baz).get)).using(f1).check(_Sngl1b)
    _Sngl1.transform(_.selection   (_.filter(_ == baz)))    .using(f1).check(_Sngl1b)
    _Sngl1.transform(_.nesting     (() |> baz))             .using(f1).check(_Sngl1b)

    // ---------------------------------------------------------------------------
    import shorthands._
    _Sngl1.transform($p(         _ == baz))     .using(f1).check(_Sngl1b)
    _Sngl1.transform($o(_.find  (_ == baz).get)).using(f1).check(_Sngl1b)
    _Sngl1.transform($m(_.filter(_ == baz)))    .using(f1).check(_Sngl1b)

    // ---------------------------------------------------------------------------
    _Sngl1.transform(selectors.TargetSelector.Explicit      (              baz))     .using(f1).check(_Sngl1b)
    _Sngl1.transform(selectors.TargetSelector.Predicate     (         _ == baz))     .using(f1).check(_Sngl1b)
    _Sngl1.transform(selectors.TargetSelector.SelectOne     (_.find  (_ == baz).get)).using(f1).check(_Sngl1b)
    _Sngl1.transform(selectors.TargetSelector.SelectMultiple(_.filter(_ == baz)))    .using(f1).check(_Sngl1b)

    // ===========================================================================
    _Sngl1.transform          (baz).using(f1).check(_Sngl1b)
    _Sngl1.transformGuaranteed(baz).using(f1).check(_Sngl1b)
    _Sngl1.transformIfPresent (baz).using(f1).check(_Sngl1b)

    _Sngl1.transformIfPresent (BAZ).using(f1).check(_Sngl1)
    util.Try {
    _Sngl1.transformGuaranteed(BAZ).using(f1) }.check(TransformGuaranteeFailure)

    // ---------------------------------------------------------------------------
    _Sngl1.transform          (_.selection(_.reverse.take(1))).using(f1).check(_Sngl1b)
//    _Sngl1.transformGuaranteed(_.reverse.take(1)).using(f).check(_Sngl1b)
//    _Sngl1.transformIfPresent (_.reverse.take(1)).using(f).check(_Sngl1b)

    //_Sngl1.transformIfPresent (_.reverse.take(1)).using(f).check(_Sngl1)
//    util.Try {
//    _Sngl1.transformGuaranteed(_.reverse.take(1)).using(f) }.check(_.isFailure)
_Mult1.take(2)
    // ===========================================================================
    _Sngl1s.retain(foo).transform(_.soleKey)        .using(f2).check(dyn(foo -> BAR))
    _Sngl1s            .transform(_.allKeys)        .using(f2).check(dyn(foo -> BAR, baz -> "1"))
    _Sngl1s            .transform(_.allKeysBut(baz)).using(f2).check(dyn(foo -> BAR, baz -> "1"))
    Try {
    _Sngl1             .transform(_.allKeys)        .using(f2).check(dyn(foo -> BAR, baz -> "1")) }
      .check(Error.AccessAsSpecificType, BasicType._String) }}

// ===========================================================================
