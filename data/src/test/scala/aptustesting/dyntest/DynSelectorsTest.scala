package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynSelectorsTests extends TestSuite {
  import aptus.dyn._
  import Error.TransformGuaranteeFailure

  // ---------------------------------------------------------------------------
  private val f1: Valew => NakedValue = _.int + 1
  private val f2: Valew => NakedValue = _.string.toUpperCase

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1.upperCase(foo)        .check(_Sngl1_BAR))
    test(_Sngl1.upperCase(_.firstKey) .check(_Sngl1_BAR))
    test(_Sngl1.upperCase(_.keyAt( 0)).check(_Sngl1_BAR))
    test(_Sngl1.upperCase(_.keyAt(-2)).check(_Sngl1_BAR))

    // ===========================================================================
    test(_Sngl1.transform          (baz).using(f1).check(_Sngl1_2))
    test(_Sngl1.transform    (_.lastKey).using(f1).check(_Sngl1_2))
    test(_Sngl1.transformGuaranteed(baz).using(f1).check(_Sngl1_2))

    test(_Sngl1.transform    (_.lastKey.guaranteePresence).using(f1).check(_Sngl1_2))
test(_Sngl1.transform(Targets(Seq(baz), guaranteed = true)).using(f1).check(_Sngl1_2)) // TODO: t250606122238

    test(_Sngl1.transform(_.selection(_.reverse.take(1))).using(f1).check(_Sngl1_2))

    test(_Sngl1.transform(_.keyPredicate(         _ == baz))     .using(f1).check(_Sngl1_2))

    test(_Sngl1.transform(_.selectOne   (_.find  (_ == baz).get)).using(f1).check(_Sngl1_2))
    test(_Sngl1.transform(_.selection   (_.filter(_ == baz)))    .using(f1).check(_Sngl1_2))

    // ===========================================================================
    test(_Sngl1.transform          (baz).using(f1).check(_Sngl1_2))
    test(_Sngl1.transformGuaranteed(baz).using(f1).check(_Sngl1_2))
    test(_Sngl1.transform          (BAZ).using(f1).check(_Sngl1))
    test(util.Try {
      _Sngl1.transformGuaranteed(BAZ).using(f1) }.check(TransformGuaranteeFailure))

    // ---------------------------------------------------------------------------
    test(_Sngl1.transform          (_.selection(_.reverse.take(1))).using(f1).check(_Sngl1_2))
//    test(_Sngl1.transformGuaranteed(_.reverse.take(1)).using(f).check(_Sngl1b))
//    test(_Sngl1.transformIfPresent (_.reverse.take(1)).using(f).check(_Sngl1b))

    //test(_Sngl1.transformIfPresent (_.reverse.take(1)).using(f).check(_Sngl1))
//    test(util.Try {
//    _Sngl1.transformGuaranteed(_.reverse.take(1)).using(f) }.check(_.isFailure))

    // ===========================================================================
    test(_Sngl1_1str.retain(foo).transform(_.soleKey)        .using(f2).check(dyn(foo -> BAR)))
    test(_Sngl1_1str            .transform(_.allKeys)        .using(f2).check(dyn(foo -> BAR, baz -> "1")))
    test(_Sngl1_1str            .transform(_.allKeysBut(baz)).using(f2).check(dyn(foo -> BAR, baz -> "1")))
    test(Try {
    _Sngl1             .transform(_.allKeys)        .using(f2).check(dyn(foo -> BAR, baz -> "1")) }
      .check(Error.AccessAsSpecificType, BasicType._String)) } }

// ===========================================================================
