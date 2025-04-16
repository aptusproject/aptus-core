package aptustesting
package dyntest

import utest._
import aptus.{FileName, FilePath}

// ===========================================================================
object DynOutputFileTests extends TestSuite {
  import aptus.experimental.dyn._

  // ---------------------------------------------------------------------------
  private lazy val z3: Dyns = _Mult1.increment(baz)

  // ---------------------------------------------------------------------------
  private val TsvFileContent =
    """|foo	baz
       |bar1	2
       |bar2	3
       |"""

  // ===========================================================================
  val tests = Tests {
    test(writeNRead("dyn.jsono") { f => _Sngl1.increment(baz).write(f); f.readFileContent().check(s"""{"$foo":"$bar","$baz":2}\n""") })

    // ---------------------------------------------------------------------------
    test(writeNRead("dyns.jsona") { f => z3.write(f); f.readFileContent().check(s"""[{"$foo":"$bar1","$baz":2},{"$foo":"$bar2","$baz":3}]\n""") })

    // ---------------------------------------------------------------------------
    test(writeNRead("dyns1.tsv") { f => z3.write(f); f.readFileContent().check(TsvFileContent.stripMargin) })

    // ---------------------------------------------------------------------------
    // write back TSV
    test(writeNRead("dyns2.tsv"){ f => TsvFilePath      .dyns.write(f); f.readFileContent.checkCharArrays(TsvFilePath.readFileContent()) })
    test(writeNRead("dyns3.tsv"){ f => TsvWithCRFilePath.dyns.write(f); f.readFileContent.checkCharArrays(TsvFilePath.readFileContent()) }) }

  // ===========================================================================
  private def writeNRead(fileName: FileName)(f: FilePath => Unit) = {
    val parentPath = aptus.fs.tempDirPath
    val filePath = parentPath / fileName
    parentPath.path.mkdirs()

    f(filePath)

      filePath.path.file.removeFile()
    parentPath.path.removeEmptyDir() } }

// ===========================================================================
