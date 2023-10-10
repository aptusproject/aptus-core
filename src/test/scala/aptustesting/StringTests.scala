package aptustesting

import aptus._
import utest._

// ===========================================================================
object StringTests extends TestSuite {
  
  val tests = Tests {
    
    // ===========================================================================
    // split

    // ---------------------------------------------------------------------------
    test(assert("hello|world".split  ("|").toList.p == List("h", "e", "l", "l", "o", "|", "w", "o", "r", "l", "d"))) // unintuitive (java's)
    test(assert("hello|world".splitBy("|") == Seq("hello", "world")))

    // ---------------------------------------------------------------------------
    // noop-like
    test(compare( ""        .splitBy("|"), Seq("")))
    test(compare( " "       .splitBy("|"), Seq(" ")))
    test(compare( "foo"     .splitBy("|"), Seq(    "foo")))
    
    test(compare( "foo|bar" .splitBy("|"), Seq(    "foo", "bar")))
    test(compare("|foo|bar" .splitBy("|"), Seq("", "foo", "bar")))
    test(compare( "foo|bar|".splitBy("|"), Seq(    "foo", "bar", "")))
    test(compare("|foo|bar|".splitBy("|"), Seq("", "foo", "bar", "")))

    // ---------------------------------------------------------------------------
    test(assert("a"        .splitBy("\n").size == 1))
    test(assert("a\n"      .splitBy("\n").size == 2))
    test(assert("a\nb\nc"  .splitBy("\n").size == 3))
    test(assert("a\nb\nc\n".splitBy("\n").size == 4))

    // ===========================================================================
    // camelCaseToSnake <-> snakeToCamelCase

    test(noop("HelloWorld" .camelCaseToSnake.snakeToCamelCase))
    test(noop("hello_world".snakeToCamelCase.camelCaseToSnake))

    // ---------------------------------------------------------------------------
    test(compare("helloWorld" .camelCaseToSnake.snakeToCamelCase, "HelloWorld"))
    test(compare("hello_world".snakeToCamelCase                 , "HelloWorld"))
    test(compare("Hello_World".snakeToCamelCase                 , "HelloWorld"))
    test(compare("hELLO_wORLD".snakeToCamelCase                 , "HelloWorld"))

    // ---------------------------------------------------------------------------
    test(compare("Hello_World".snakeToCamelCase.camelCaseToSnake, "hello_world"))
    test(compare("HELLO_WORLD".snakeToCamelCase.camelCaseToSnake, "hello_world"))
    test(compare("HelloWorld" .camelCaseToSnake                 , "hello_world"))
    test(compare("helloWorld" .camelCaseToSnake                 , "hello_world"))
    
    // ---------------------------------------------------------------------------
    test(compare("foo".padRight(5, ' '), "foo  "))
    test(compare("foo".padLeft (5, ' '), "  foo"))

    test(compare(1.str.padRight(3, '0'), "100"))
    test(compare(1.str.padLeft (3, '0'), "001"))

    // ---------------------------------------------------------------------------
    test("splitUntil") {
      test(compare("hello\tworld\thow\tare\tyou".splitUntil('\t', 3), Seq("hello", "world", "how\tare\tyou")))

      test(fail[IllegalArgumentException]("f,o,o,b,a,r".splitUntil(',', 0)))

      test(compare                       ("f,o,o,b,a,r".splitUntil(',', 1), Seq("f,o,o,b,a,r")))
      test(compare                       ("f,o,o,b,a,r".splitUntil(',', 2), Seq("f", "o,o,b,a,r")))
      test(compare                       ("f,o,o,b,a,r".splitUntil(',', 3), Seq("f", "o", "o,b,a,r")))

      test(compare                       ("f,o,o,b,a,r".splitUntil(';', 1), Seq("f,o,o,b,a,r")))
      test(compare                       ("f,o,o,b,a,r".splitUntil(';', 2), Seq("f,o,o,b,a,r")))
      test(compare                       ("f,o,o,b,a,r".splitUntil(';', 3), Seq("f,o,o,b,a,r"))) }

    // ===========================================================================
    // system calls

    test(compareIfUnix(    "echo          hello"        .systemCall(),                   "hello\n"))
    test(compareIfUnix(Seq("echo", "-e", "hello\nworld").systemCall().splitBy("\n"), Seq("hello", "world")))
  }
}

// ===========================================================================
