package aptus
package experimental
package dyntest
package mult

// ===========================================================================
object DynMultipleTest {
  import dyn._
  import Dyn .dyn
  import Dyns.dyns

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    z9.headOption.check(d8.in.some)
    z9.head      .check(d8)

    z9.lastOption.check(d9.in.some)
    z9.last      .check(d9)

    // ===========================================================================
    _Mult1.testDynz(_.increment(baz))
      .check {
    _Mult1           .increment(baz) }

    // ===========================================================================
    // union/append/prepend

    {
      def _tmp[T <: ops.mult.MultipleOpsTrait[T]]
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
      _tmp(_Mult1, _Mult1)(identity)(identity)
      _tmp(_Mult1, _Mult1)(_.asDynz)(_.asDyns) }

    // ===========================================================================
    _Mult1.take(1).check(dyns(_Sngl1x1))
    _Mult1.drop(1).check(dyns(_Sngl1x2))

    _Mult1.take(Some(1)).check(dyns(_Sngl1x1))
    _Mult1.drop(Some(1)).check(dyns(_Sngl1x2))

    // ---------------------------------------------------------------------------
          _Mult1.take(1).forceOne  .check(_Sngl1x1)
          _Mult1.drop(1).forceOne  .check(_Sngl1x2)

    Try { _Mult1        .forceOne }.check(Error.MoreThanOneElement)
    Try { _Mult1.drop(2).forceOne }.check(Error.NoElements)

    // ===========================================================================
    _Mult1.append(dyn(baz -> 3)).filter(_.containsKey(foo)).check(_Mult1)
    _Mult1.append(dyn(baz -> 3)).filter(_.int(baz) < 3)    .check(_Mult1)

    _Mult1.append(dyn(baz -> 3)).filter(_.containsKey(foo)).check(_Mult1)
    _Mult1.append(dyn(baz -> 3)).filter(_.int(baz) < 3)    .check(_Mult1)

  }
}

// ===========================================================================
