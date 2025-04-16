package aptustesting
package dyntest
package misc

import utest._

import aptus.aptdata.meta.basic.EnumValue
import aptus.aptreflect.nodes.{TypeNode, TypeField, TypeNodeBuiltIns}
import aptus.aptreflect.lowlevel.ReflectionTypesAbstraction.{WTT, WeakTypeTag_}
import aptus.aptreflect.dynamic.DynamicTypeNode_ // for .formatPrettyJson (TypeNode)

// ===========================================================================
object AptusTypeNodeTest extends TestSuite {

  // ---------------------------------------------------------------------------
  private def typeNode[T: WTT]: TypeNode = implicitly[WTT[T]].typeNode

  // ---------------------------------------------------------------------------
  val MyCaseClassTypeNode: TypeNode =
    TypeNode
      .trivial(s"aptustesting.testmeta.${aptus.ScalaVersion.companionName("TestMeta") /* append dollar sign or not */}.MyCaseClass")
      .dataClass(true)
      .fields(
        TypeField.string("myString"),
        TypeField.int   ("myInt"))

  // ===========================================================================
  val tests = Tests {

    // ---------------------------------------------------------------------------
    test("built-ins") {
        test(compare(typeNode[String]             , TypeNodeBuiltIns.JavaString))
        test(compare(typeNode[scala.Predef.String], TypeNodeBuiltIns.JavaString))
        test(compare(typeNode[java.lang.String]   , TypeNodeBuiltIns.JavaString))

        // ---------------------------------------------------------------------------
        test(compare(typeNode[Boolean], TypeNodeBuiltIns.ScalaBoolean))
        test(compare(typeNode[Int]    , TypeNodeBuiltIns.ScalaInt))
        test(compare(typeNode[Double] , TypeNodeBuiltIns.ScalaDouble))

        // ---------------------------------------------------------------------------
        test(compare(typeNode[Byte] , TypeNodeBuiltIns.ScalaByte))
        test(compare(typeNode[Short], TypeNodeBuiltIns.ScalaShort))
        test(compare(typeNode[Long] , TypeNodeBuiltIns.ScalaLong))
        test(compare(typeNode[Float], TypeNodeBuiltIns.ScalaFloat))

        // ---------------------------------------------------------------------------
        test(compare(typeNode[BigInt]    , TypeNodeBuiltIns.ScalaMathBigInt))
        test(compare(typeNode[BigDecimal], TypeNodeBuiltIns.ScalaMathBigDecimal))

        // ---------------------------------------------------------------------------
        test(compare(typeNode[java.time.LocalDate]     , TypeNodeBuiltIns.JavaTimeLocalDate))
        test(compare(typeNode[java.time.LocalDateTime] , TypeNodeBuiltIns.JavaTimeLocalDateTime))
        test(compare(typeNode[java.time.Instant]       , TypeNodeBuiltIns.JavaTimeInstant))

        // ---------------------------------------------------------------------------
        test(compare(typeNode[java.nio.ByteBuffer]     , TypeNodeBuiltIns.JavaNioByteBuffer))

        // ---------------------------------------------------------------------------
        test(compare(typeNode[Seq   [Boolean]]     , TypeNodeBuiltIns.scalaSeq   (TypeNodeBuiltIns.ScalaBoolean)))
        test(compare(typeNode[Option[Boolean]]     , TypeNodeBuiltIns.scalaOption(TypeNodeBuiltIns.ScalaBoolean))) }

    // ---------------------------------------------------------------------------
    test("java") {
        test(compare(typeNode[java.lang.String]      , TypeNode.trivial("java.lang.String")))
        test(compare(typeNode[java.lang.Integer]     , TypeNode.trivial("java.lang.Integer")))
        test(compare(typeNode[java.lang.Boolean]     , TypeNode.trivial("java.lang.Boolean")))
        test(compare(typeNode[java.io.File]          , TypeNode.trivial("java.io.File"))) }

    // ---------------------------------------------------------------------------
    test("special ones") {
      test(compare(typeNode[Object], TypeNode.trivial("java.lang.Object")))

      test(compare(typeNode[Any]   , TypeNode.trivial("scala.Any")))
      test(compare(typeNode[AnyVal], TypeNode.trivial("scala.AnyVal")))

      test(compare(typeNode[Numeric[Int]],    TypeNode.trivial("scala.math.Numeric").typeArg(TypeNodeBuiltIns.ScalaInt)))
      test(compare(typeNode[Numeric[Double]], TypeNode.trivial("scala.math.Numeric").typeArg(TypeNodeBuiltIns.ScalaDouble))) }

    // ---------------------------------------------------------------------------
    test("case classes") {
        import testmeta.TestMeta._

        test(compare(typeNode[MyCaseClassAlias], typeNode[MyCaseClass]))
        test(compare(typeNode[MyCaseClassAlias], MyCaseClassTypeNode))
        test(compare(typeNode[MyCaseClass]     , MyCaseClassTypeNode))

        test(assert(!typeNode[MyAnyVal1].leaf.dataClass))
        test(assert(!typeNode[MyAnyVal2].leaf.dataClass)) }

    // ---------------------------------------------------------------------------
    test("aptus ones") {
      test(compare(typeNode[EnumValue], TypeNodeBuiltIns.AptusEnumValue))
      test(compare(typeNode[EnumValue], TypeNode.trivial("aptus.aptdata.meta.basic.EnumValue").enumValue(true))) }

    // ---------------------------------------------------------------------------
    test("complex TypeNode") {
      val actual  : TypeNode         = typeNode[testmeta.TestMeta.MyComplexData]
      val Expected: aptus.JsonPretty = aptustesting.resourceContent("TypeNodeExample.json").prettyJson

      val actualFormatted = actual.formatPrettyJson

      if (aptus.ScalaVersion.isScala2)
        Predef.assert(
              actualFormatted.replace("scala.collection.Seq", "scala.collection.immutable.Seq" /* only for 2.12 */) ==
              Expected       .replace("TestMeta$", "TestMeta" /* for both 2.12 and 2.13 */),
          Seq(
              actualFormatted.replace("scala.collection.Seq", "scala.collection.immutable.Seq" /* only for 2.12 */),
              Expected       .replace("TestMeta$", "TestMeta" /* for both 2.12 and 2.13 */))
            .section)
      else
        Predef.assert(actualFormatted == Expected) } }

  // ===========================================================================
  def compare(actual: TypeNode, expected: TypeNode) =
    Predef.assert(
      actual == expected,
      Seq(actual, expected).map(_.formatDebug).section2) }

// ===========================================================================