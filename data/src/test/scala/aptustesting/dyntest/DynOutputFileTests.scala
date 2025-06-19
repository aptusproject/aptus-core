package aptustesting
package dyntest

import utest._
import aptus.{FileName, FilePath}

// ===========================================================================
object DynOutputFileTests extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  private lazy val z3: Dyns = _Mult1.increment(baz)

  // ---------------------------------------------------------------------------
  private val TsvHeaderFileContent = "foo\tbaz"

  // ---------------------------------------------------------------------------
  private val TsvDataFileContent =
    """|bar1	2
       |bar2	3
       |"""

  // ---------------------------------------------------------------------------
  private val TsvFileContent = s"${TsvHeaderFileContent}\n${TsvDataFileContent}"

  // ===========================================================================
  val tests = Tests {
    test(writeNRead("dyn.jsono") { f => _Sngl1.increment(baz).write(f); f.readFileContent().check(s"""{"$foo":"$bar","$baz":2}\n""") })

    // ---------------------------------------------------------------------------
    test(writeNRead("dyns.jsona") { f => z3.write(f); f.readFileContent().check(s"""[{"$foo":"$bar1","$baz":2},{"$foo":"$bar2","$baz":3}]\n""") })

    // ---------------------------------------------------------------------------
    test(writeNRead("dyns1.tsv") { f => z3       .write(f); f.readFileContent().check(TsvFileContent.stripMargin) })
    test(writeNRead("dynz1.tsv") { f => z3.asDynz.write(f); f.readFileContent().check(TsvDataFileContent.stripMargin) })

    // ---------------------------------------------------------------------------
    // write back TSV
    test(writeNRead("dyns2.tsv"){ f => TsvFilePath      .dyns.write(f); f.readFileContent().checkCharArrays(TsvFilePath.readFileContent()) })
    test(writeNRead("dyns3.tsv"){ f => TsvWithCRFilePath.dyns.write(f); f.readFileContent().checkCharArrays(TsvFilePath.readFileContent()) }) }

  // ===========================================================================
  private def writeNRead(fileName: FileName)(f: FilePath => Unit) = {
    val parentPath = aptus.fs.tempDirPath.path.mkdirs()
    val filePath = parentPath / fileName

    f(filePath)

      filePath.path.file.removeFile()
    parentPath.path.removeEmptyDir() } }

// ===========================================================================
