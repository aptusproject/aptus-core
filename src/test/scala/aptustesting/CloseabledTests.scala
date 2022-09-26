package aptustesting

import scala.util.chaining._
import aptus._
import utest._

// ===========================================================================
object CloseabledTests extends TestSuite {
  
  val tests = Tests {       
    test(compare(
        // or eg "/tmp/myfile".streamFileLines3().pipe(transform)
        CloseabledIterator.fromValues("hello", "world").pipe(transform), 
        Seq("HE", "LLO", "WO", "RLD")))
  }

  // ---------------------------------------------------------------------------
  private def transform(citr: CloseabledIterator[String]) =
    citr
      .map(_.toUpperCase)
      .flatMap { s => 
        Seq(
          s.substring(0, 2),
          s.substring(2)) }
      .consumeAll

}

// ===========================================================================
