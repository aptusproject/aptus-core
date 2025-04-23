package aptustesting
package dyntest
package io

import aptus.CloseabledIterator
import aptustesting.testmeta.TestMeta.MyComplexData
import utest._

// ===========================================================================
object DynDataClassesTest extends TestSuite {
  import testmeta.TestMeta.{Person, MyComplexDataSchema}
  import testdata.TestData.{johnStatic, johnDynamic, johnDynamics}

  import aptus.experimental.dyn._ // for .toDynamic

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test("schema") {
      val myComplexDataCls = aptus.aptdata.meta.schema.cls[MyComplexData]
      test(myComplexDataCls.check(MyComplexDataSchema))
      test(aptustesting.resourceContent("ClsExample.json").pipe(aptus.aptdata.meta.converter.clsFromString).check(myComplexDataCls)) }

    // ---------------------------------------------------------------------------
    test("singles") {
      test( johnStatic.toDynamic       .check(johnDynamic))
      test(johnDynamic.toStatic[Person].check(johnStatic))

      test(util.Try(johnDynamic.toStatic[Nothing])               .checkException(classOf[java.lang.IllegalArgumentException], "E250415171031", "scala.Nothing"))
      // only a problem for scala 2.x:
      // test(util.Try(johnDynamic.toStatic)                        .checkException(classOf[java.lang.IllegalArgumentException], "E250415171031", "scala.Nothing"))

      test(util.Try(johnDynamic.toStatic[Option[Person]])        .checkException(classOf[java.lang.IllegalArgumentException], "E250415171031", "scala.Option"))
      test(util.Try(johnDynamic.toStatic[Either[Person, Person]]).checkException(classOf[java.lang.IllegalArgumentException], "E250415171031", "scala.util.Either"))
      test(util.Try(johnDynamic.toStatic[Try   [Person]])        .checkException(classOf[java.lang.IllegalArgumentException], "E250415171031", "scala.util.Try"))

      // only a problem for scala 2.x:
      // def myMethod[T <: Product /* forgetting to add ": WTT" */] = johnDynamic.toStatic[T]
      // test(util.Try(myMethod[Person]).checkException(classOf[java.lang.IllegalArgumentException])) // <none>.T

      test(fromDataClass(johnStatic).check(johnDynamic)) }

    // ---------------------------------------------------------------------------
    test("multiples") {
      test(List                         (johnStatic, johnStatic).toDynamic.check(johnDynamics))
      test(Iterator                     (johnStatic, johnStatic).toDynamic.check(johnDynamics))
      test(CloseabledIterator.fromValues(johnStatic, johnStatic).toDynamic.check(johnDynamics))

      test(johnDynamics       .toStatic[Person]             .check(List(johnStatic, johnStatic)))
      test(johnDynamics.asDynz.toStatic[Person].consumeAll().check(List(johnStatic, johnStatic))) }

    // ---------------------------------------------------------------------------
    test("round trips") {
      test(     johnStatic             .noop(_.toDynamic.toStatic[Person]))
      test(List(johnStatic, johnStatic).noop(_.toDynamic.toStatic[Person])) } } }

// ===========================================================================


