package aptustesting.dyntest

import utest._

// ===========================================================================
object DynPresenceAbsenceTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test("predicates") {
      test(_Sngl1.isPresent(     foo).checkTrue ())
      test(_Sngl1.isPresent(     FOO).checkFalse())

      test(_Sngl3.isPresent(p |> foo).checkTrue ())
      test(_Sngl3.isPresent(p |> FOO).checkFalse())
      test(_Sngl3.isPresent(P |> FOO).checkFalse())

      test(_Mult11.isPresent(p |> foo).checkTrue ())
      test(_Mult11.isPresent(p |> FOO).checkFalse())
      test(_Mult11.isPresent(P |> FOO).checkFalse())

      test(_Mult11e.isPresent(p |> FOO).checkFalse())
      test(_Mult11e.isPresent(P |> FOO).checkFalse())

      test(_Mult11e.isPresent      (p |> foo).checkTrue ())
      test(_Mult11e.isAlwaysPresent(p |> foo).checkFalse())
      test(_Mult11 .isAlwaysPresent(p |> foo).checkTrue ())
      test(_Mult11 .isAlwaysPresent(P |> foo).checkFalse()) }

    // ===========================================================================
    test("ensuring") {
      test(_Sngl1_T.noop(_.ensurePresent(foo, qux)))
      test(_Sngl1_T.noop(_.ensureAbsent (FOO, BAR)))

      // ---------------------------------------------------------------------------
      test(_Sngl3.noop(_.ensurePresent(p |> foo)))
      test(_Sngl3.noop(_.ensureAbsent (p |> FOO)))

      // ---------------------------------------------------------------------------
      test(Try(_Sngl1_T.ensureAbsent (foo, qux)).check(Error.EnsurePresenceError))
      test(Try(_Sngl1_T.ensurePresent(FOO, BAR)).check(Error.EnsureAbsenceError))

      // ===========================================================================
      test(    _Sngl3.add(qux -> T).noop(_.ensurePresent(qux, p |> foo)))
      test(    _Sngl3.add(qux -> T).noop(_.ensureAbsent (QUX, p |> FOO)))
      test(Try(_Sngl3.add(qux -> T)       .ensureAbsent (qux, p |> FOO)).check(Error.EnsureAbsenceError))
      test(Try(_Sngl3.add(qux -> T)       .ensureAbsent (QUX, p |> foo)).check(Error.EnsureAbsenceError))

      // ---------------------------------------------------------------------------
      test(    _Sngl3.add(qux -> T).noop(_.ensureAbsent (QUX, p |> FOO)))
      test(    _Sngl3.add(qux -> T).noop(_.ensurePresent(qux, p |> foo)))
      test(Try(_Sngl3.add(qux -> T)       .ensurePresent(QUX, p |> foo)).check(Error.EnsurePresenceError))
      test(Try(_Sngl3.add(qux -> T)       .ensurePresent(qux, p |> FOO)).check(Error.EnsurePresenceError)) } } }

// ===========================================================================

