package aptus
package experimental
package dyntest

// ===========================================================================
object DynVeryBasicTest {
  import dyn._
  import Dyn._

  // ---------------------------------------------------------------------------
  private val expected1 = dyn("value" -> "dummy", "value2" -> "dummy2")
  private val expected2 = dyn("value" -> "dummy", "value2" -> "dummy2", "value3" -> "dummy3")

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    {
      val in = dyn(foo -> bar, baz -> 1, qux -> true)

      in.ensurePresent(foo, qux).check(in)
      in.ensureMissing(FOO, BAR).check(in)

      Try(in.ensureMissing(foo, qux)).check(Error.EnsurePresenceError)
      Try(in.ensurePresent(FOO, BAR)).check(Error.EnsureAbsenceError) }

    // ===========================================================================
    // merging
    Try(Dummy.merge(Dummy)).check(Error.DuplicateKeys)
        Dummy.merge(dyn("value2" -> "dummy2")).check(expected1)

    // ===========================================================================
    // rename
    Dummy .rename("value").to("value2")                      .check(dyn("value2" -> "dummy"))
    Dummy2.rename("value1" ~> "VALUE1", "value2" ~> "VALUE2").check(dyn("VALUE1" -> "dummy1", "VALUE2" -> "dummy2"))

    // ===========================================================================
    // retain
    Dummy2.retain("value1").check(dyn("value1" -> "dummy1"))
    Dummy2.retain("value1", "value2").check(Dummy2)
    Dummy2.retain("VALUE").check(Empty)

    // ===========================================================================
    // remove
    Dummy2.remove("value2")          .check(dyn("value1" -> "dummy1"))
    Dummy2.remove("value1", "value2").check(Empty)
    Dummy2.remove("VALUE")           .check(Dummy2)

    // ===========================================================================
    // add
    Dummy.add(      "value2" ->       "dummy2").check(expected1)
    Dummy.add(key = "value2", value = "dummy2").check(expected1)

    Dummy.add(aptus.listMap("value2" -> "dummy2", "value3" -> "dummy3")).check(expected2)
    Dummy.add("value2" -> "dummy2", "value3" -> "dummy3").check(expected2)

    Try(Dummy.add("value"  -> "dummy2")).check(Error.DuplicateKeys)

    // ---------------------------------------------------------------------------
    // replace
    _Sngl1.replace          (foo).withValue("BAR").check(dyn(foo -> "BAR", baz -> 1))
    _Sngl1.replaceIfPresent (foo).withValue("BAR").check(dyn(foo -> "BAR", baz -> 1))
    _Sngl1.replaceGuaranteed(foo).withValue("BAR").check(dyn(foo -> "BAR", baz -> 1))
    _Sngl1.replaceIfPresent (FOO).withValue("BAR").check(_Sngl1)


    // ---------------------------------------------------------------------------
    // reorder keys

    {
      val xx = dyn(baz -> 1, foo -> bar)
      val yy = _Sngl3.add(qux -> true)

      _Sngl1.reorderKeys(_.reverse).check(xx)

      yy.reorderKeys           (_.reverse).check(dyn(qux -> true, p -> _Sngl1)) // worth keeping?
      yy.reorderKeysRecursively(_.reverse).check(dyn(qux -> true, p -> xx))

      yy                                          .equivalent(yy).checkTrue()
      yy.reorderKeysRecursively(_.reverse).pipe(yy.equivalent)   .checkTrue() }
  } }

// ===========================================================================