package aptus
package experimental
package dyntest

// ===========================================================================
object DynOutputStringTests {
  import dyn._
  import Dyn.dyn

  // ===========================================================================
  private val expected1 =
    s"""|{
       |  "$foo": "$bar",
       |  "$baz": 2
       |}""".stripMargin

  // ---------------------------------------------------------------------------
  private val expected2 =
    s"""|[
       |  {
       |    "$foo": "$bar1",
       |    "$baz": 2
       |  },
       |  {
       |    "$foo": "$bar2",
       |    "$baz": 3
       |  }
       |]""".stripMargin

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    _Sngl1
      .increment(baz)
      .formatPrettyJson
      .check(expected1)

    // ---------------------------------------------------------------------------
    _Mult1
      .increment(baz)
      .formatPrettyJson
      .check(expected2) }

    // ===========================================================================
    // custom formatter

    dyn(foo -> bar, qux -> new java.io.File("/my/path")).formatCompactJson
      .check("""{"foo":"bar","qux":"/my/path"}""")

    // ---------------------------------------------------------------------------
    // TODO: t241205093939 - simplify
    _root_.gallia.gson.customJsonFormatters +
        (classOf[java.nio.file.Path] ->
          new _root_.gallia.gson.CustomJsonFormatter {
            def format(value: Any): String =
              value.asInstanceOf[java.nio.file.Path].toFile.getAbsolutePath })

      dyn(foo -> bar, qux -> java.nio.file.Paths.get("/my/path")).formatCompactJson
        .check("""{"foo":"bar","qux":"/my/path"}""") }

// ===========================================================================