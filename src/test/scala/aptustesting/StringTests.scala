package aptustesting

import util.chaining._
import aptus._
import utest._

// ===========================================================================
object StringTests extends TestSuite {
  
  val tests = Tests {
    test("symbol")(compare("foo".symbol, Symbol("foo")))

    // ---------------------------------------------------------------------------
    test("encoding") {
      test(compare("foo".toBase64   , "Zm9v"))
      test(compare("foo".toHexString, "666F6F"))

    //test(compare("666F6F".unHex   .toList, "foo".getBytes.toList)) - TODO
      test(compare("Zm9v"  .unBase64.toList, "foo".getBytes.toList)) }

    // ===========================================================================
    test("split") {
      test(compare("hello|world".split  ("|").toList, List("h", "e", "l", "l", "o", "|", "w", "o", "r", "l", "d"))) // unintuitive (java's)
      test(compare("hello|world".splitBy("|")       , Seq("hello", "world")))

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
      test(compare("a"        .splitBy("\n").size, 1))
      test(compare("a\n"      .splitBy("\n").size, 2))
      test(compare("a\nb\nc"  .splitBy("\n").size, 3))
      test(compare("a\nb\nc\n".splitBy("\n").size, 4))

      // ===========================================================================
      test("splitXsv") {
        test(compare("a\tb\tc"       .splitTabs.associateLeft(_.size), 3 -> Seq("a", "b", "c")))
        test(compare("a\t\"b\"\tc"   .splitTabs.associateLeft(_.size), 3 -> Seq("a", "b", "c")))    // handles quotes
        test(compare("a\t\"b\tB\"\tc".splitTabs.associateLeft(_.size), 3 -> Seq("a", "b\tB", "c"))) // ignores separator if quoted

        test(compare("a,b,c"      .splitCommas.associateLeft(_.size), 3 -> Seq("a", "b", "c")))
        test(compare("a,\"b\",c"  .splitCommas.associateLeft(_.size), 3 -> Seq("a", "b", "c")))     // handles quotes
        test(compare("a,\"b,B\",c".splitCommas.associateLeft(_.size), 3 -> Seq("a", "b,B", "c"))) } // ignores separator if quoted

      // ===========================================================================
      test("splitUntil") {
        test(compare("hello\tworld\thow\tare\tyou".splitUntil('\t', 3), Seq("hello", "world", "how\tare\tyou")))

        test(failArgument("f,o,o,b,a,r".splitUntil(',', 0)))

        test(compare     ("f,o,o,b,a,r".splitUntil(',', 1), Seq("f,o,o,b,a,r")))
        test(compare     ("f,o,o,b,a,r".splitUntil(',', 2), Seq("f", "o,o,b,a,r")))
        test(compare     ("f,o,o,b,a,r".splitUntil(',', 3), Seq("f", "o", "o,b,a,r")))

        test(compare     ("f,o,o,b,a,r".splitUntil(';', 1), Seq("f,o,o,b,a,r")))
        test(compare     ("f,o,o,b,a,r".splitUntil(';', 2), Seq("f,o,o,b,a,r")))
        test(compare     ("f,o,o,b,a,r".splitUntil(';', 3), Seq("f,o,o,b,a,r"))) } }

    // ===========================================================================
    test("case (camel/snake)") {
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
      test(compare("helloWorld" .camelCaseToSnake                 , "hello_world")) }

    // ===========================================================================
    test("system calls") {
      test(compareIfUnix(    "echo          hello"        .systemCall(),                   "hello\n"))
      test(compareIfUnix(Seq("echo", "-e", "hello\nworld").systemCall().splitBy("\n"), Seq("hello", "world"))) }

    // ===========================================================================
    test("append/prepend/surround") {
      test(compare("foo".prepend("bar"), "barfoo"))
      test(compare("foo". append("bar"),    "foobar"))

      test(compare("foo".prependTab,     "\tfoo"))
      test(compare("foo".prependNewline, "\nfoo"))
      test(compare("foo".prependTab,     "\tfoo"))
      test(compare("foo".prependNewline, "\nfoo"))

      test(compare("foo".swp                   , "|foo|")) // typicall for debugging
      test(compare("foo".surroundWith("|")     , "|foo|"))
      test(compare("foo".surroundWith("[", "]"), "[foo]"))

      test(compare(
        "foo".newline.tab.slash.dot.colon.semicolon.comma.dash.underscore.pound.at.space.equalSign,
        "foo\n\t/.:;,-_#@ ="))

      test(compare(
        "foo".newline("|").tab("|").slash("|").dot("|").colon("|").semicolon("|").comma("|").dash("|").underscore("|").pound("|").at("|").space("|").equalSign("|"),
        "foo\n|\t|/|.|:|;|,|-|_|#|@| |=|"))

      test(compare("foo" / "bar", "foo/bar")) }

    // ===========================================================================
    test("padding") {
      test(compare("foo".padLeft (5, ' '), "  foo"))
      test(compare("foo".padRight(5, ' '),   "foo  "))

      test(compare("5".padLeftSpaces (3), "  5"))
      test(compare("5".padRightSpaces(3),   "5  "))
      test(compare("5".padLeftZeros  (3), "005")) }

    // ---------------------------------------------------------------------------
    test("trim lines")(compare(" \n hello\nworld \n! ".trimLines, "hello\nworld\n!"))

    // ===========================================================================
    test(compare("foo".indent    (1)     ,   " foo\n")) // java's (for comparison)
    test(compare("foo".indent    (3)     , "   foo\n")) // java's (for comparison)
    test(compare("foo".indentLine        , "\tfoo"))
    test(compare("foo".indentLine(3)     , "\t\t\tfoo"))
    test(compare("foo".indentLine(3, " "), "   foo"))

    // ---------------------------------------------------------------------------
    test(compare("foo\nbar".indentAll        , "\tfoo\n\tbar"))
    test(compare("foo\nbar".indentAll(3)     , "\t\t\tfoo\n\t\t\tbar"))
    test(compare("foo\nbar".indentAll(3, " "), "   foo\n   bar"))

    // ---------------------------------------------------------------------------
    test(compare("foo\nbar\nbaz".sectionAllOff         , "\n\tfoo\n\tbar\n\tbaz"))
    test(compare("foo\nbar\nbaz".sectionAllOff(3)      , "\n\t\t\tfoo\n\t\t\tbar\n\t\t\tbaz"))
    test(compare("foo\nbar\nbaz".sectionAllOff("data:"), "data:\n\tfoo\n\tbar\n\tbaz"))

    // ===========================================================================
    test("tests (booleans-returning)") {
      test(compare( "foo" .isTrimmed, true))
      test(compare(" foo" .isTrimmed, false))
      test(compare( "foo ".isTrimmed, false))
      test(compare(" foo ".isTrimmed, false))

      // ---------------------------------------------------------------------------
      test(compare("\"foo\"".isQuoted, true))
      test(compare( "'foo'" .isQuoted, false))
      test(compare(  "foo"  .isQuoted, false))

      // ---------------------------------------------------------------------------
      test(compare("\"foo\"".isSingleQuoted, false))
      test(compare( "'foo'" .isSingleQuoted, true))
      test(compare(  "foo"  .isSingleQuoted, false))

      // ---------------------------------------------------------------------------
      test(compare("123".isDigits, true))
      test(compare("abc".isDigits, false))
      test(compare("a2c".isDigits, false))

      // ---------------------------------------------------------------------------
      test(compare("123"  .isValidInt, true))
      test(compare("123.4".isValidInt, false))
      test(compare("abc"  .isValidInt, false))
      test(compare("a2c"  .isValidInt, false))

      // ---------------------------------------------------------------------------
      test(compare("foo".notContains("a"), true))
      test(compare("foo".notContains("o"), false)) }

    // ===========================================================================
    test("regexes") {
      test(compare("foobar".extractGroup("f(.+)r".pattern), Some("ooba")))
      test(compare("foobar".extractGroup("f(.+)R".pattern), None))

      test(compare("foobar,bazqux".extractGroups("f(.+?)r,b(.+?)x".pattern), Some(Seq("ooba", "azqu"))))
      test(compare("foobar,bazqux".extractGroups("f(.+?)R,b(.+?)x".pattern), None))

      test(compare("foobar".replaceGroup( "foo(.+)" .regex, "BAR"), "fooBAR"))
      test(compare("foobar".replaceGroup("^foo(.+)$".regex, "BAR"), "fooBAR"))

      test(compare("foo bar baz".replaceGroup(".+( ).+".regex, "_"), "foo_bar baz")) // replaces first only

        test(compare("foo bar baz"               .replaceAll  (" ", "_") , "foo_bar_baz")) /* for comparison */
        test(compare("foo bar baz".pipe(" ".regex.replaceAllIn(_  , "_")), "foo_bar_baz")) /* for comparison */ }

    // ===========================================================================
    // time: see TimeTest instead

    // ===========================================================================
  }

}

// ===========================================================================
