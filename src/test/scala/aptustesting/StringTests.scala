package aptustesting

import aptus._
import utest._

// ===========================================================================
object StringTests extends TestSuite {
  
  val tests = Tests {
    
    // ===========================================================================
    // splitBy
    
    // noop-like
    test(compare( ""        .splitBy("|"), Seq("")))
    test(compare( " "       .splitBy("|"), Seq(" ")))
    test(compare( "foo"     .splitBy("|"), Seq(    "foo")))
    
    test(compare( "foo|bar" .splitBy("|"), Seq(    "foo", "bar")))
    test(compare("|foo|bar" .splitBy("|"), Seq("", "foo", "bar")))
    test(compare( "foo|bar|".splitBy("|"), Seq(    "foo", "bar", "")))
    test(compare("|foo|bar|".splitBy("|"), Seq("", "foo", "bar", "")))
    
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
    
    // ===========================================================================
    // system calls

    test(compareIfUnix(    "echo          hello"        .systemCall(),                   "hello\n"))
    test(compareIfUnix(Seq("echo", "-e", "hello\nworld").systemCall().splitBy("\n"), Seq("hello", "world")))
  }
}

// ===========================================================================
