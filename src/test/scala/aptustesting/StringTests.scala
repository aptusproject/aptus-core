package aptustesting

import aptus._
import utest._

// ===========================================================================
object StringTests extends TestSuite {
  
  val tests = Tests {
    // noop-like
    test(compare( ""        .splitBy("|"), Seq("")))
    test(compare( " "       .splitBy("|"), Seq(" ")))
    test(compare( "foo"     .splitBy("|"), Seq(    "foo")))
    
    test(compare( "foo|bar" .splitBy("|"), Seq(    "foo", "bar")))
    test(compare("|foo|bar" .splitBy("|"), Seq("", "foo", "bar")))
    test(compare( "foo|bar|".splitBy("|"), Seq(    "foo", "bar", "")))
    test(compare("|foo|bar|".splitBy("|"), Seq("", "foo", "bar", "")))
  }
}

// ===========================================================================
