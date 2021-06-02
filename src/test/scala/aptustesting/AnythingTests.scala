package aptustesting

import aptus._
import utest._

// ===========================================================================
object AnythingTests extends TestSuite {
  
  val tests = Tests {
    test(noop   ("bonjour".pipeIf(_.startsWith("h"))(_.toUpperCase)))
    test(compare("hello"  .pipeIf(_.startsWith("h"))(_.toUpperCase), "HELLO"))

    test(noop   (3.pipeIf(_ % 2 == 0)(_ + 1)))
    test(compare(4.pipeIf(_ % 2 == 0)(_ + 1), 5))

    val suffixOpt = Some("?")
    test(compare("hello".pipeOpt(suffixOpt)(suffix => _ + suffix), "hello?"))
    test(noop   ("hello".pipeOpt(None)     (suffix => _ + suffix)))
  }
}

// ===========================================================================
