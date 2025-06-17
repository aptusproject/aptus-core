package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynMoreTests extends TestSuite {
  import aptus.dyn._

  // ===========================================================================
  val tests = Tests {

    test {
      import aptus.aptdata.meta.schema._

      val int  = SubInfo.sngl(_._Int)
      val str  = SubInfo.sngl(_._String)
      val nc   = SubInfo.sngl(Cls.cls(Fld.one(qux, _._String)))
      val info = Info.union(false, int, str, nc)

      val nestingPredicate1: PartialFunction[Any, Boolean] = _.isInstanceOf[Dyn]
      val nestingPredicate2: PartialFunction[Any, Boolean] = { case _: Dyn => true }

      test(  3  .pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(List(int)))
      test(  3.3.pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(Nil))
      test("abc".pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(List(str)))

      test(Dyn.dyn(foo -> bar).pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(List(nc)))
      test(Dyn.dyn(foo -> bar).pipe(info._valueExtractionWithMatching(nestingPredicate2)).check(List(nc)))

      test(new java.io.File("myfile").pipe(info._valueExtractionWithMatching(nestingPredicate1)).check(Nil))
    }

    // ===========================================================================
    test("convert") {
      test(dyn(foo -> "9", baz -> 1).convertToInt(foo)      .check(dyn(foo -> 9  , baz ->  1)))
      test(dyn(foo -> "9", baz -> 1).convert     (foo).toInt.check(dyn(foo -> 9  , baz ->  1)))
      test(dyn(foo -> bar, baz -> 1).convert     (baz).toStr.check(dyn(foo -> bar, baz -> "1")))

      test(_Mult1.convert(baz).toStr.check(dyns(dyn(foo -> bar1, baz -> "1"), dyn(foo -> bar2, baz -> "2")))) }

    // ===========================================================================
    test {
      val in  = dyn("p" -> dyn( foo  -> "bar"))
      val res = dyn("p" -> dyn("FOO" -> "bar"))

      // dyn("p" -> dyn(foo -> "bar")).rename("p" |> foo -> "FOO")
      test(in.transform       (p).using(_.nesting.rename(foo).to("FOO")).check(res))
      test(in.transformNesting(p).using(_        .rename(foo).to("FOO")).check(res)) } } }

// ===========================================================================
