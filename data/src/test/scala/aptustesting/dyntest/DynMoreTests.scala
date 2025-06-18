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

      test(  3  .pipe(info._valueExtractionWithMatching(nestingPredicate1)).check1(int))
      test(  3.3.pipe(info._valueExtractionWithMatching(nestingPredicate1)).check0())
      test("abc".pipe(info._valueExtractionWithMatching(nestingPredicate1)).check1(str))

      test(Dyn.dyn(foo -> bar).pipe(info._valueExtractionWithMatching(nestingPredicate1)).check1(nc))
      test(Dyn.dyn(foo -> bar).pipe(info._valueExtractionWithMatching(nestingPredicate2)).check1(nc))

      test(new java.io.File("myfile").pipe(info._valueExtractionWithMatching(nestingPredicate1)).check0()) }

    // ===========================================================================
    test {
      val in  = dyn("p" -> dyn( foo  -> "bar"))
      val res = dyn("p" -> dyn("FOO" -> "bar"))

      // dyn("p" -> dyn(foo -> "bar")).rename("p" |> foo -> "FOO")
      test(in.transform       (p).using(_.nesting.rename(foo).to("FOO")).check(res))
      test(in.transformNesting(p).using(_        .rename(foo).to("FOO")).check(res)) } } }

// ===========================================================================
