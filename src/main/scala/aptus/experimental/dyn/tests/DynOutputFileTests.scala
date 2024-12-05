package aptus
package experimental
package dyntest

// ===========================================================================
object DynOutputFileTests {
  import dyn._

  // ---------------------------------------------------------------------------
  private val z3: Dyns = _Mult1.increment(baz)

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {
    "/tmp/dyn.jsono".tap { f =>
      _Sngl1.increment(baz).write(f)
      f.readFileContent().check(s"""{"$foo":"$bar","$baz":2}\n""")
      f.path.file.removeFile() }

    // ---------------------------------------------------------------------------
    "/tmp/dyns.jsona".tap { f =>
      z3.write(f)
      f.readFileContent().check(s"""[{"$foo":"$bar1","$baz":2},{"$foo":"$bar2","$baz":3}]\n""")
      f.path.file.removeFile() }

    // ---------------------------------------------------------------------------
    checkIo("/tmp/dyns.tsv")(z3)(
      """|foo	baz
         |bar1	2
         |bar2	3
         |"""
        .stripMargin)

    // ---------------------------------------------------------------------------
    // write back TSV
    checkIo("/tmp/dyns2.tsv")(TsvFilePath      .dyns)(TsvFilePath.readFileContent())
    checkIo("/tmp/dyns2.tsv")(TsvWithCRFilePath.dyns)(TsvFilePath.readFileContent()) }

  // ===========================================================================
  @deprecated def checkIo(f: String)(dyns: Dyns)(expected: String) = {
    dyns.write(f)

    f
      .readFileContent()
      .assert(
        _     == expected,
        actual =>
          "\n\tactual:   " + actual  .toCharArray.map(_.toInt).toList +
          "\n\texpected: " + expected.toCharArray.map(_.toInt).toList + "\n")

    f.path.file.removeFile() } }

// ===========================================================================