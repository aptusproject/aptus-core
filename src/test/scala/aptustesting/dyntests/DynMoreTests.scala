package aptustesting
package dyntest

// ===========================================================================
object DynMoreTests {
  import aptus.experimental.dyn._
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

    {
      import aptus.aptdata.meta.schema._

      val int  = SubInfo.sngl(_._Int)
      val str  = SubInfo.sngl(_._String)
      val nc   = SubInfo.sngl(Cls.cls(Fld.one(qux, _._String)))
      val info = Info.union(false, int, str, nc)

      val nestingPredicate1: PartialFunction[AnyValue, Boolean] = _.isInstanceOf[Dyn]
      val nestingPredicate2: PartialFunction[AnyValue, Boolean] = { case _: Dyn => true }

        3  .pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(List(int))
        3.3.pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(Nil)
      "abc".pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(List(str))

      Dyn.dyn(foo -> bar).pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(List(nc))
      Dyn.dyn(foo -> bar).pipe(info._valueExtractionWithMatching(nestingPredicate2)).check(List(nc))

      new java.io.File("myfile").pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(Nil)
    }

    // ===========================================================================
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
