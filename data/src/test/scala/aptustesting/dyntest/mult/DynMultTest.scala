package aptustesting
package dyntest
package mult

import utest._

// ===========================================================================
object DynMultTest extends TestSuite {
  import aptus.dyn._

  // ===========================================================================
  val tests = Tests {
    test(z9.headOption.check(d8.in.some))
    test(z9.head      .check(d8))

    test(z9.lastOption.check(d9.in.some))
    test(z9.last      .check(d9))

    // ===========================================================================
    test(_Mult1.testDynz(_.increment(baz))
      .check {
    _Mult1           .increment(baz) })

    // ===========================================================================
    // union/append/prepend

    test {
      def _tmp[T <: aptus.aptdata.ops.mult.MultipleOpsTrait[T]]
          (x: Dyns, y: Dyns)
          (f: Dyns => T)
          (g: T => Dyns) = {
        x.pipe(f).union(y.pipe(f)).pipe(g).check(dyns(
            dyn(foo -> bar1, baz -> 1), dyn(foo -> bar2, baz -> 2),
            dyn(foo -> bar1, baz -> 1), dyn(foo -> bar2, baz -> 2)))

        x.pipe(f).append(_Sngl1).pipe(g).check(dyns(
            dyn(foo -> bar1, baz -> 1), dyn(foo -> bar2, baz -> 2),
            dyn(foo -> bar , baz -> 1)))

        x.pipe(f).prepend(_Sngl1).pipe(g).check(dyns(
            dyn(foo -> bar , baz -> 1),
            dyn(foo -> bar1, baz -> 1), dyn(foo -> bar2, baz -> 2))) }

      // ---------------------------------------------------------------------------
      test(_tmp(_Mult1, _Mult1)(identity)(identity))
      test(_tmp(_Mult1, _Mult1)(_.asDynz)(_.asDyns)) }

    // ===========================================================================
    test(_Mult1.take(1).check(dyns(_Sngl1x1)))
    test(_Mult1.drop(1).check(dyns(_Sngl1x2)))

    test(_Mult1.take(Some(1)).check(dyns(_Sngl1x1)))
    test(_Mult1.drop(Some(1)).check(dyns(_Sngl1x2)))

    // ---------------------------------------------------------------------------
          test(_Mult1.take(1).forceOne  .check(_Sngl1x1))
          test(_Mult1.drop(1).forceOne  .check(_Sngl1x2))

    test(Try { _Mult1        .forceOne }.check(Error.MoreThanOneElement))
    test(Try { _Mult1.drop(2).forceOne }.check(Error.NoElements))

    // ===========================================================================
    test(_Mult1.append(dyn(baz -> 3)).filter(_.containsKey(foo)).check(_Mult1))
    test(_Mult1.append(dyn(baz -> 3)).filter(_.int(baz) < 3)    .check(_Mult1))

    test(_Mult1.append(dyn(baz -> 3)).filter(_.containsKey(foo)).check(_Mult1))
    test(_Mult1.append(dyn(baz -> 3)).filter(_.int(baz) < 3)    .check(_Mult1)) } }

// ===========================================================================
