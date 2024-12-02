package aptustesting

import scala.util.chaining._
import utest._

// ===========================================================================
object CloseabledTests extends TestSuite {
  import aptus.{CloseabledIterator, Iterator_}

  // ---------------------------------------------------------------------------
  val tests = Tests {

    test { compare(
        // or eg "/tmp/myfile".streamFileLines3().pipe(transform)
        CloseabledIterator.fromValues("hello", "world").pipe(transform), 
        Seq("HE", "LLO", "WO", "RLD")) }

    test {
      val cls = List(1, 2, 3).iterator.toCloseabledIterator.toCloseabled

      compare(
        cls.consume(_.toList).tap { _ => cls.close() },
        List(1, 2, 3)) } }

  // ===========================================================================
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
