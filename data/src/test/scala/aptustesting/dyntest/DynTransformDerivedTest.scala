package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynTransformDerivedTest extends TestSuite {
  import aptus.dyn._
  import Error._

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1_BAR.lowerCase(foo)                              .check(_Sngl1))

    // ---------------------------------------------------------------------------
    test(_Sngl1.upperCase(foo)                              .check(_Sngl1_BAR))
    test(_Sngl1.upperCase(_.keyPredicate(_.startsWith("f"))).check(_Sngl1_BAR))
		test(_Sngl1.upperCase(_.selectOne(_.find(_.startsWith("f")).get)).check(_Sngl1_BAR))

    test(_Sngl1.upperCaseGuaranteed(foo).check(_Sngl1_BAR))
    test(_Sngl1.upperCaseIfPresent (foo).check(_Sngl1_BAR))
    test(_Sngl1.upperCaseIfPresent (baz).check(_Sngl1)) // present but not String
    test(_Sngl1.upperCaseIfPresent (qux).check(_Sngl1))

    // ===========================================================================
    test(_Sngl1.decrement(baz)                              .check(_Sngl1_0))
    test(_Sngl1.increment(baz)                              .check(_Sngl1_2))
    test(_Sngl1.increment(_.keyPredicate(_.startsWith("b"))).check(_Sngl1_2))
		test(_Sngl1.increment(_.selectOne(_.find(_.startsWith("b")).get)).check(_Sngl1_2))

    test(_Sngl1.incrementGuaranteed(baz).check(_Sngl1_2))
    test(_Sngl1.incrementIfPresent (baz).check(_Sngl1_2))
    test(Try {
    _Sngl1.incrementIfPresent(foo) }.check(TransformSpecificType, IntegerLike))
    test(_Sngl1.incrementIfPresent(qux)  .check(_Sngl1))

    // ---------------------------------------------------------------------------
    test(_Sngl1.convert(baz).toLong.increment(baz).check(_Sngl1_2))
    test(_Sngl1.convert(baz).toLong.decrement(baz).check(_Sngl1_0))

    test(Try { _Sngl1.convert(baz).toDouble.increment(baz).check(_Sngl1) }
      // far more likely to be unintended (eg from reading from JSON and meant to be an Int)
      .check(TransformSpecificType, IntegerLike))

    test(Try { _Sngl1.increment(foo).check(_Sngl1) }.check(TransformSpecificType, IntegerLike))

    // ===========================================================================
    test(_Sngl1_1_1.ceiling(baz).check(_Sngl1.replace(baz).withValue(2)))

    // ---------------------------------------------------------------------------
    test(      _Sngl1_1_1.floor(baz)   .check(_Sngl1))
    test(Try { _Sngl1  .floor(baz) } .check(TransformSpecificType, RealLike))
    test(Try { _Sngl1  .floor(foo) } .check(TransformSpecificType, RealLike))

    // ===========================================================================
    test(_Sngl1.remove(baz).transformSole(_.string.toUpperCase).check(dyn(foo -> BAR)))
    //FIXME: t241204111738 - have to retain original multiple-ness -
if (_f) _Sngl1.remove(baz).transformSoleString (_.toUpperCase).check(dyn(foo -> BAR)) } }

// ===========================================================================
