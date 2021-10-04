package aptus
package misc

import scala.util.chaining._
import scala.collection.JavaConverters._
import java.util.zip._

// ===========================================================================
object Zip {
  type RelativePath = String
  //TODO: charset

  // ---------------------------------------------------------------------------
  /** will contain '/' if nesting */
  def listContent(in: FilePath): Seq[RelativePath] =
    new ZipFile(in)
      .pipe { zipFile =>
        val names =
          zipFile
            .entries()
            .asScala
            .toList
            .map(_.getName)

        zipFile.close()

        names
      }

  // ---------------------------------------------------------------------------
  def streamLines(in: FilePath)                         : (Iterator[Line], java.io.Closeable) = streamLines(in, _ => true)
  def streamLines(in: FilePath, pred: String => Boolean): (Iterator[Line], java.io.Closeable) =
    new ZipFile(in)
      .pipe { zipFile =>
        val zipEntry = entry(zipFile, pred)

        val pair =
          utils.InputStreamUtils.lines (
            zipFile
              .getInputStream(zipEntry)
              .pipe(aptus.Closeabled.fromPair(_, zipFile)),
            StandardCharsets.UTF_8)

        pair.u -> pair }

  // ---------------------------------------------------------------------------
  def content(in: FilePath)                         : Content = content(in, _ => true)
  def content(in: FilePath, pred: String => Boolean): Content =
    new ZipFile(in)
      .pipe { zipFile =>
        val zipEntry = entry(zipFile, pred)

        utils.InputStreamUtils.content(
          zipFile
            .getInputStream(zipEntry)
            .pipe(aptus.Closeabled.fromPair(_, zipFile)),
          StandardCharsets.UTF_8) }

  // ===========================================================================
  private def entry(zipFile: ZipFile, pred: String => Boolean): ZipEntry =
    zipFile
      .entries()
      .asScala
      .filter(x => pred(x.getName))
      .toList
      .force.one
}

// ===========================================================================