package aptustesting

import aptus._
import utest._

// ===========================================================================
object StringTests extends TestSuite {
  def compare[A](actual: A, expected: A) = { assert(actual == expected/*, actual -> expected - TODO: t210601112229 - not valid in scala 3 anymore?*/) }

  // ---------------------------------------------------------------------------
  val tests = Tests {
    // no-ops
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
