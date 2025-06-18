package aptustesting
package dyntest
package misc

import utest._
import aptus.aptreflect.nodes.TypeNodeBuiltIns
import aptus.aptreflect.nodes.TypeNodeBuiltIns._

// ===========================================================================
object DynValewTest extends TestSuite with DynTestData {
  private implicit class DynValewTestAnything_[T](diss: T) { def v = Valew.build(diss) }

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test("runtimeTypeNode") {
      test(bar .v.runtimeTypeNode.check(TypeNodeBuiltIns.String))

      // runtime gives you java.lang's, not scala's
      test(1   .v.runtimeTypeNode.check(JavaLangInteger))
      test(1.1 .v.runtimeTypeNode.check(JavaLangDouble))
      test(true.v.runtimeTypeNode.check(JavaLangBoolean))

      test( Some(bar)            .v.runtimeTypeNode.check(scalaOption(TypeNodeBuiltIns.String)))
      test( None                 .v.runtimeTypeNode.check(scalaNoneOfUnknownType))
      test((None: Option[String]).v.runtimeTypeNode.check(scalaNoneOfUnknownType)) // erased: can't determine just from runtime value

      test(Seq(bar1, bar2).v.runtimeTypeNode.check(scalaSeq(TypeNodeBuiltIns.String)))
      test(Seq(1,  2 )    .v.runtimeTypeNode.check(scalaSeq(JavaLangInteger)))
      test(Seq(1.1,  2.2) .v.runtimeTypeNode.check(scalaSeq(JavaLangDouble)))
      test(Seq(1, "2")    .v.runtimeTypeNode.check(scalaSeq(ScalaAny)))

      test( Nil              .v.runtimeTypeNode.check(scalaNilOfUnknownType))
      test((Nil: Seq[String]).v.runtimeTypeNode.check(scalaNilOfUnknownType)) // erased

      test(Set(bar1, bar2).v.runtimeTypeNode.check(scalaSet(TypeNodeBuiltIns.String)))
      test(Set()          .v.runtimeTypeNode.check(scalaSet(ScalaAny)))
      test(Set[String]()  .v.runtimeTypeNode.check(scalaSet(ScalaAny))) // erased

      test(Array(bar1, bar2).v.runtimeTypeNode.check(scalaArray(TypeNodeBuiltIns.String)))
      test(Array()          .v.runtimeTypeNode.check(scalaArray(ScalaAny)))
      test(Array[String]()  .v.runtimeTypeNode.check(scalaArray(ScalaAny))) // erased

      test(Map(bar1 -> 1, bar2 -> 2).v.runtimeTypeNode.check(scalaMap(TypeNodeBuiltIns.String, JavaLangInteger)))
      test(Map()                    .v.runtimeTypeNode.check(scalaMap(ScalaAny, ScalaAny)))
      test(Map[String, Int]()       .v.runtimeTypeNode.check(scalaMap(ScalaAny, ScalaAny))) // erased

      // ---------------------------------------------------------------------------
      test(_Sngl1       .v.runtimeTypeNode.check(AptusDyn ))
      test(_Mult1       .v.runtimeTypeNode.check(AptusDyns))
      test(_Mult1.asDynz.v.runtimeTypeNode.check(AptusDynz)) }

    // ===========================================================================
    test("texts") {
      test(bar.v.texts.check1(bar))
      test(Seq(bar1, bar2).v.texts.checkN(bar1, bar2))
      test(_Sngl1.v.texts.check1("""{"foo":"bar","baz":1}"""))
      test(_Mult1.v.texts.checkN("""{"foo":"bar1","baz":1}""", """{"foo":"bar2","baz":2}""")) } } }

// ===========================================================================

