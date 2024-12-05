package aptus
package experimental
package dyntest

// ===========================================================================
object DynMoreTests {
  import dyn._
  import Dyn .dyn
  import Dyns.dyns

  // ===========================================================================
  private val f1 = "f1"
  private val f2 = "f2"
  private val F  = "F"
  private val g  = "g"

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {

    // convert
    dyn(foo -> "9", baz -> 1).convertToInt(foo)      .check(dyn(foo -> 9  , baz ->  1))
    dyn(foo -> "9", baz -> 1).convert     (foo).toInt.check(dyn(foo -> 9  , baz ->  1))
    dyn(foo -> bar, baz -> 1).convert     (baz).toStr.check(dyn(foo -> bar, baz -> "1"))

    _Mult1.convert(baz).toStr.check(dyns(dyn(foo -> bar1, baz -> "1"), dyn(foo -> bar2, baz -> "2")))

    // ===========================================================================
    {
      val in  = dyn("p" -> dyn( foo  -> "bar"))
      val res = dyn("p" -> dyn("FOO" -> "bar"))

      // dyn("p" -> dyn(foo -> "bar")).rename("p" |> foo -> "FOO")
      in.transform   ("p").using(_.dyn.rename(foo).to("FOO")).check(res)
      in.transformDyn("p")      (_    .rename(foo).to("FOO")).check(res)
    }
}}

// ===========================================================================
