package aptustesting

import aptus._
import utest._

// ===========================================================================
object AnythingTests extends TestSuite {
  
  val tests = Tests {
    
    // ---------------------------------------------------------------------------
    // piping
    test(noop   ("bonjour".pipeIf(_.startsWith("h"))(_.toUpperCase)))
    test(compare("hello"  .pipeIf(_.startsWith("h"))(_.toUpperCase), "HELLO"))

    test(noop   (3.pipeIf(_ % 2 == 0)(_ + 1)))
    test(compare(4.pipeIf(_ % 2 == 0)(_ + 1), 5))

    val suffixOpt = Some("?")
    test(compare("hello".pipeOpt(suffixOpt)(suffix => _ + suffix), "hello?"))
    test(noop   ("hello".pipeOpt(None)     (suffix => _ + suffix)))
    
    // ---------------------------------------------------------------------------
    // asserts/requires

    test(noop                                    ("hello".assert (_.nonEmpty)))
    test(fail[java.lang.AssertionError]          ("hello".assert (_. isEmpty)))
    test(fail[java.lang.AssertionError]          ("hello".assert (_. isEmpty, x => s"input: ${x}"), msg = "assertion failed: input: hello"))

    test(noop                                    ("hello".require(_.nonEmpty)))
    test(fail[java.lang.IllegalArgumentException]("hello".require(_. isEmpty)))
    test(fail[java.lang.IllegalArgumentException]("hello".require(_. isEmpty, x => s"input: ${x}"), msg = "requirement failed: input: hello"))
    
    // ---------------------------------------------------------------------------
    // in.{none,some}If

    test("foo".in.someIf(_.size < 3), Some("foo"))
    test("foo".in.noneIf(_.size < 3), None)    
  }
}

// ===========================================================================
