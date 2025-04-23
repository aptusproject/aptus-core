package aptustesting

import aptus._
import utest._

// ===========================================================================
object AnythingTests extends TestSuite {
  
  val tests = Tests {
    
    // ---------------------------------------------------------------------------
    test("piping") {
      test(noop   ("bonjour".pipeIf(_.startsWith("h"))(_.toUpperCase)))
      test(compare("hello"  .pipeIf(_.startsWith("h"))(_.toUpperCase), "HELLO"))

      test(noop   (3.pipeIf(_ % 2 == 0)(_ + 1)))
      test(compare(4.pipeIf(_ % 2 == 0)(_ + 1), 5))

      val suffixOpt: Option[String] = Some("?")
      val none     : Option[String] = None
      test(compare("hello".pipeOpt(suffixOpt)(suffix => _ + suffix), "hello?"))
      test(noop   ("hello".pipeOpt(none)     (suffix => _ + suffix))) }

    // ---------------------------------------------------------------------------
    test("asserts/requires") {
      test(noop         ("hello".assert (_.nonEmpty, identity)))
      test(failAssertion("hello".assert (_. isEmpty, identity)))
      test(failAssertion("hello".assert (_. isEmpty, x => s"input: ${x}"), msg = "assertion failed: input: hello"))

      test(noop         ("hello".assertEquals("hello")))
      test(noop         ("hello".assertEquals(_.size, 5)))
      test(failAssertion("hello".assertEquals("HELLO")  , msg = "assertion failed: \n\texpected: |HELLO|\n\tactual:   |hello|"))
      test(failAssertion("hello".assertEquals(_.size, 6), msg = "assertion failed: \n\texpected: |6|\n\tactual:   |5|"))

      test(noop         ("hello".require(_.nonEmpty, identity)))
      test(failArgument ("hello".require(_. isEmpty, identity)))
      test(failArgument ("hello".require(_. isEmpty, x => s"input: ${x}"))) }

    // ---------------------------------------------------------------------------
    test("in.{none,some}If") {
      test(isSome("foo".in.someIf(_.size < 5), "foo"))
      test(isNone("foo".in.noneIf(_.size < 5))) }

    // ---------------------------------------------------------------------------
    test("contained in") {
      test(isTrue(2.containedIn(    1, 2, 3) ))
      test(isTrue(2.containedIn(Seq(1, 2, 3))))
      test(isTrue(2.containedIn(Set(1, 2, 3))))

      test(isFlse(5.containedIn(    1, 2, 3) ))
      test(isFlse(5.containedIn(Seq(1, 2, 3))))
      test(isFlse(5.containedIn(Set(1, 2, 3))))

      // ---------------------------------------------------------------------------
      test(isFlse(2.notContainedIn(    1, 2, 3) ))
      test(isFlse(2.notContainedIn(Seq(1, 2, 3))))
      test(isFlse(2.notContainedIn(Set(1, 2, 3))))

      test(isTrue(5.notContainedIn(    1, 2, 3) ))
      test(isTrue(5.notContainedIn(Seq(1, 2, 3))))
      test(isTrue(5.notContainedIn(Set(1, 2, 3)))) }

    // ---------------------------------------------------------------------------
    test("associate left/right") {
      test(compare(3.associateLeft (_ + 1), (4, 3)))
      test(compare(3.associateRight(_ + 1),    (3, 4))) }

    // ---------------------------------------------------------------------------
    test("pad left ints") {
      test(compare(5.padLeftInt(3, ' '), "  5"))
      test(compare(5.padLeftInt(3)     , "  5")) } } }

// ===========================================================================
