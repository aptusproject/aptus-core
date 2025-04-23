package aptustesting
package dyntest

// ===========================================================================
object DynTransformDerivedTest {
  import aptus.dyn._
  import Error._

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    _Sngl1d.lowerCase(foo)                              .check(_Sngl1)

    // ---------------------------------------------------------------------------
    _Sngl1.upperCase(foo)                              .check(_Sngl1d)
    _Sngl1.upperCase(_.keyPredicate(_.startsWith("f"))).check(_Sngl1d)
		_Sngl1.upperCase(_.selectOne(_.find(_.startsWith("f")).get)).check(_Sngl1d)

    _Sngl1.upperCaseGuaranteed(foo).check(_Sngl1d)
    _Sngl1.upperCaseIfPresent (foo).check(_Sngl1d)
    _Sngl1.upperCaseIfPresent (baz).check(_Sngl1) // present but not String
    _Sngl1.upperCaseIfPresent (qux).check(_Sngl1)

    // ===========================================================================
    _Sngl1.decrement(baz)                              .check(_Sngl1k)
    _Sngl1.increment(baz)                              .check(_Sngl1b)
    _Sngl1.increment(_.keyPredicate(_.startsWith("b"))).check(_Sngl1b)
		_Sngl1.increment(_.selectOne(_.find(_.startsWith("b")).get)).check(_Sngl1b)

    _Sngl1.incrementGuaranteed(baz).check(_Sngl1b)
    _Sngl1.incrementIfPresent (baz).check(_Sngl1b)
    Try {
    _Sngl1.incrementIfPresent(foo) }.check(TransformSpecificType, IntegerLike)
    _Sngl1.incrementIfPresent(qux)  .check(_Sngl1)

    // ---------------------------------------------------------------------------
    _Sngl1.convert(baz).toLong.increment(baz).check(_Sngl1b)
    _Sngl1.convert(baz).toLong.decrement(baz).check(_Sngl1k)

    Try { _Sngl1.convert(baz).toDouble.increment(baz).check(_Sngl1) }
      // far more likely to be unintended (eg from reading from JSON and meant to be an Int)
      .check(TransformSpecificType, IntegerLike)

    _Sngl1 .convert(baz).toLong.transformIntegerLike(selectors.TargetSelectorShorthands.explicitKey(baz))(_ + 1).check(_Sngl1b)

    Try {
_Sngl1 .convert(baz).toDouble.transformIntegerLike(selectors.TargetSelectorShorthands.explicitKey(baz))(_ + 1) }  .check(TransformSpecificType, IntegerLike)

    _Sngl1b.convert(baz).toLong.transformIntegerLike(selectors.TargetSelectorShorthands.explicitKey(baz))(_ * 2)  .check(dyn(foo -> bar, baz -> 4))
    _Sngl1b.convert(baz).toLong.transformIntegerLike(selectors.TargetSelectorShorthands.explicitKey(baz))(_ / 2)  .check(dyn(foo -> bar, baz -> 1))
    Try {
    _Sngl1b.convert(baz).toLong.transformIntegerLike(selectors.TargetSelectorShorthands.explicitKey(baz))(_ / 3) }.check(AccessAsSpecificType, _Int)
    _Sngl1b.convert(baz).toLong.transformIntegerLike(selectors.TargetSelectorShorthands.explicitKey(baz))(_ / 2.0).check(dyn(foo -> bar, baz -> 1.0))

    Try { _Sngl1.increment(foo).check(_Sngl1) }.check(TransformSpecificType, IntegerLike)

    // ===========================================================================
    _Sngl1rr.ceiling(baz).check(_Sngl1.replace(baz).withValue(2))

    // ---------------------------------------------------------------------------
          _Sngl1rr.floor(baz)   .check(_Sngl1)
    Try { _Sngl1  .floor(baz) } .check(TransformSpecificType, RealLike)
    Try { _Sngl1  .floor(foo) } .check(TransformSpecificType, RealLike)

    // ===========================================================================
    _Sngl1.remove(baz).transformSole(_.string.toUpperCase).check(dyn(foo -> BAR))
    //FIXME: t241204111738 - have to retain original multiple-ness -
if (_f) _Sngl1.remove(baz).transformSoleString (_.toUpperCase).check(dyn(foo -> BAR)) } }

// ===========================================================================
