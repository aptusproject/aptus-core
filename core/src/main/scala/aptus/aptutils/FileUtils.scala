package aptus
package aptutils

import java.io.{Closeable => _, _}
import java.util.zip.GZIPOutputStream

// ===========================================================================
object FileUtils {

  def readFileContent(path: FilePath): Content      = { val src = io.Source.fromInputStream(inputStream(quickNormalize(path))); val content = src.mkString       ; src.close; content }
  def readFileLines  (path: FilePath): List[String] = { val src = io.Source.fromInputStream(inputStream(quickNormalize(path))); val lines   = src.getLines.toList; src.close; lines   }

  def streamFileLines  (path: FilePath): (Iterator[String], Closeable) = {
    val src = io.Source.fromInputStream(inputStream(quickNormalize(path)))

sys.addShutdownHook { // TODO: only if in auto mode
  println(s"shutting down: $path")
  //src.close()
  src.close()
}

  src.getLines -> src
  }

    // ===========================================================================
    private def inputStream(path: FilePath): InputStream =
     /* TODO: t241202085542 - investigate:
          Exception in thread "main" java.lang.NoClassDefFoundError:
            org/apache/commons/compress/compressors/CompressorInputStream
            You use commons-compress-1.9.0 .... InputStreamStatistics is since 1.17 this maybe fix this issue. */
      InputStreamUtils.smartInputStream(
        new BufferedInputStream(
          new FileInputStream(path)))

  // ===========================================================================
  def writeContent     (path: FilePath, content: Content): FilePath = if (path.endsWith(".gz")) writeGzipContent(path, content) else writePlainContent(path, content)
  def writePlainContent(path: FilePath, content: Content): FilePath = { val fw = plainFileWriter(path, append = false); fw.write(content); fw.close(); path }
  def writeGzipContent (path: FilePath, content: Content): FilePath = { val fw =  gzipFileWriter(path, append = false); fw.write(content); fw.close(); path }

  // ---------------------------------------------------------------------------
  def writeLines     (path: FilePath, values: Seq[Line]): FilePath = if (path.endsWith(".gz")) writeGzipLines(path, values) else writePlainLines(path, values)
  def writePlainLines(path: FilePath, values: Seq[Line]): FilePath = { val fw = plainFileWriter(path, append = false); values.map(x => s"${x}\n").foreach(fw.write) /* TODO: flush often? */; fw.close(); path }
  def writeGzipLines (path: FilePath, values: Seq[Line]): FilePath = { val fw =  gzipFileWriter(path, append = false); values.map(x => s"${x}\n").foreach(fw.write) /* TODO: flush often? */; fw.close(); path }

  // ---------------------------------------------------------------------------
  def writeGzipLines(
        out         : FilePath,
        flushModulo : Long,
        logModulo   : Option[Long => Unit])
        (iter: Iterator[Line])
      : OutputFilePath = {
    val gfw = gzipFileWriter(out, append = false)

    var counter: Long = 0

    iter
      .map(_.newline)
      .foreach { line =>
        counter += 1

        if ((counter % flushModulo) == 0) {
          gfw.flush()

          logModulo.foreach(_.apply(counter))
        }

        gfw.write(line)
      }

    gfw.close()

    out
  }

  // ===========================================================================
  def appendToPlainFile(out: FilePath, value: String): OutputFilePath = {
    val fw = plainFileWriter(out, append = true)

      fw.append(value)

      fw.flush()
      fw.close()

    out } // TODO: return content rather?

  // ---------------------------------------------------------------------------
  def appendToGzipFile(out: FilePath, value: String): OutputFilePath = {
    val fw = gzipFileWriter(out, append = true)

      fw.append(value)

      fw.flush()
      fw.close()

    out } // TODO: return content rather?

  // ===========================================================================
  // writers

  private def plainFileWriter(path: FilePath, append: Boolean): Writer =
    new FileWriter(quickNormalize(path), append)

  // ---------------------------------------------------------------------------
  private def gzipFileWriter(path: FilePath, append: Boolean): Writer =
    new OutputStreamWriter(syncFlushGzipOutputStream(path, append))

  // ===========================================================================
  // streams

  private def fileOutputStream(path: FilePath, append: Boolean): OutputStream =
    new FileOutputStream(quickNormalize(path), append)

  // ---------------------------------------------------------------------------
  //https://stackoverflow.com/questions/3640080/force-flush-on-a-gzipoutputstream-in-java
  private def syncFlushGzipOutputStream(path: FilePath, append: Boolean): OutputStream =
      new GZIPOutputStream(fileOutputStream(
          path   = quickNormalize(path),
          append = append),
        /*syncFlush = */true)

  // ===========================================================================
  private def quickNormalize(path: FilePath): FilePath =
    if (!path.startsWith("~/")) path
    else                        s"${aptus.fs.homeDirectoryPath()}/${path.drop(2)}" // eg ~/foo to /home/tony/foo
}

// ===========================================================================
