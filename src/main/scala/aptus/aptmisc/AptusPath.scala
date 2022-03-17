package aptus
package aptmisc

import java. io._
import java.nio.file.{Path => NioPath, Paths => NioPaths, Files => NioFiles}

// ===========================================================================
private[aptus] final class AptusPath(path: String) { // TODO: as AnyVal?
  private lazy val  ioFile = new java.io.File(path)
  private lazy val nioPath = NioPaths.get(path)

  // ---------------------------------------------------------------------------
  override def toString: String = path

  // ---------------------------------------------------------------------------
  def isFile   (): Boolean = NioFiles.isRegularFile (nioPath)
  def isDir    (): Boolean = NioFiles.isDirectory   (nioPath)
  def isSymlink(): Boolean = NioFiles.isSymbolicLink(nioPath)

  // ===========================================================================
  def touchFile()               : Unit = com.google.common.io.Files.touch(ioFile)
  def writeFile(content: String): Unit = content.writeFileContent(path) /* see String_ */

  def ensureDir   (): DirPath = if (isDir()) path else createNewDir()
  def createNewDir(): DirPath = { ioFile.mkdir(); path }

  // ===========================================================================
  // note: may be removed it immediately ("On some operating systems it may not be possible to remove a file when it is open and in use by this Java virtual machine or other programs")
  def removeFile(): Unit = {
    require(isFile(), path)
    NioFiles.delete(nioPath)

    ()
  }

  // ---------------------------------------------------------------------------
  // note: may be removed it immediately ("On some operating systems it may not be possible to remove a file when it is open and in use by this Java virtual machine or other programs")
  def removeEmptyDir(): Unit = {
    require(isDir(), path)
    require(ioFile.list.isEmpty, listNames().take(3).join(", "))
    NioFiles.delete(nioPath)
    ()
  }

  // ---------------------------------------------------------------------------
  @deprecated def emptyDirNonRecursively() = { require(listDirNames().isEmpty); removeFiles() } // must not  contains sub-directories

  def removeFiles() = {
    require(isDir(), path)

    listFileNames()
      .map { n => s"${path}/${n}".path }
      .filter (_.isFile    ())
      .foreach(_.removeFile())
  }

  // ===========================================================================
  def listContent: List[Path] = ioFile.list().toList

    // ---------------------------------------------------------------------------
    def listNames    (): List[Name]     = ioFile.assert(_.isDirectory, x => s"\n\t\tnot a dir:\n\t\t\t${x}").list().toList // see org.apache.commons.io.FileUtils for more involved
    def listFileNames(): List[FileName] = listNames().filter(x => new File(path / x).isFile())
    def listDirNames (): List[DirName]  = listNames().filter(x => new File(path / x).isDirectory())

    def listPaths    (): List[Path]     = listNames().map(path / _)
    def listFilePaths(): List[FilePath] = listPaths().filter(new File(_).isFile())
    def listDirPaths (): List[DirPath]  = listPaths().filter(new File(_).isDirectory())

    def listPathsRecursively    (): List[Path]     = listPaths().flatMap { p => (if (new File(p).isDirectory()) p.path.listFilePathsRecursively() else Nil) ++ Seq(p) }
    def listFilePathsRecursively(): List[FilePath] = listPathsRecursively().filter(new File(_).isFile())
    def listDirPathsRecursively (): List[DirPath]  = listPathsRecursively().filter(new File(_).isDirectory())

  // ===========================================================================
  //TODO: mkdirs, glob, ... (more needs to be ported from original aptus, see t210116165619)
}

// ===========================================================================
