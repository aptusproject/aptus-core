package aptus
package aptmisc

import java.io._
import java.nio.file.{CopyOption, Files => NioFiles, Paths => NioPaths}

// ===========================================================================
trait _AptusPath {
  protected val path: String

  protected lazy val  ioFile = new java.io.File(path)
  protected lazy val nioPath = NioPaths.get(path)

  // ---------------------------------------------------------------------------
  override def toString: String = path

  // ---------------------------------------------------------------------------
  def        exists(): Boolean =  NioFiles.exists(nioPath)
  def doesNotExists(): Boolean = !NioFiles.exists(nioPath)

  protected def _isFile   (): Boolean = NioFiles.isRegularFile (nioPath)
  protected def _isDir    (): Boolean = NioFiles.isDirectory   (nioPath)
  protected def _isSymlink(): Boolean = NioFiles.isSymbolicLink(nioPath)

  // ===========================================================================
  def touchFile()               : Unit = com.google.common.io.Files.touch(ioFile)
  def writeFile(content: String): Unit = { content.writeFileContent(path) /* see String_ */; () }

  def ensureDir   (): DirPath = if (_isDir()) path else createNewDir()
  def createNewDir(): DirPath = { assert(ioFile.mkdirs(), path); path }

  def ensureEmpty(): DirPath = { listNames().ensuring(_.isEmpty); path }

  // ===========================================================================
  // note: may be removed it immediately ("On some operating systems it may not be possible to remove a file when it is open and in use by this Java virtual machine or other programs")
  def removeFile (): Unit     = { require(_isFile(), path); NioFiles.delete(nioPath); () }
  def removeFile2(): FilePath = { require(_isFile(), path); NioFiles.delete(nioPath); path }

  def removeFileIfExists (): Unit = { if (_isFile()) { NioFiles.delete(nioPath) }; () }
  def removeFileIfExists2(): FilePath = { if (_isFile()) { NioFiles.delete(nioPath) }; path }

  def moveTo(path: OutputFilePath) = NioFiles.move(nioPath, NioPaths.get(path), Array[CopyOption]():_*)

  // ---------------------------------------------------------------------------
  // note: may be removed it immediately ("On some operating systems it may not be possible to remove a file when it is open and in use by this Java virtual machine or other programs")
  def removeEmptyDir(): Unit = {
    require(_isDir(), path)
    require(ioFile.list.isEmpty, listNames().take(3).join(", "))
    NioFiles.delete(nioPath)
    ()
  }

  // ---------------------------------------------------------------------------
  @deprecated def emptyDirNonRecursively() = { require(listDirNames().isEmpty); removeFiles() } // must not  contains sub-directories

  def removeFiles(): String = {
    require(_isDir(), path)

    listFileNames()
      .map { n => s"${path}/${n}".path }
      .filter (_.isFile    ())
      .foreach(_.removeFile())

    path }

  // ===========================================================================
  def listContent(): List[FsPath] = Option(ioFile.list()).map(_.toList).getOrElse(illegalArgument("220926133236 - not a dir"))

    // ---------------------------------------------------------------------------
    def listNames    (): List[Name]     = ioFile.assert(_.isDirectory, x => s"\n\t\tnot a dir:\n\t\t\t${x}").list().toList // see org.apache.commons.io.FileUtils for more involved
    def listFileNames(): List[FileName] = listNames().filter(x => new File(path / x).isFile())
    def listDirNames (): List[DirName]  = listNames().filter(x => new File(path / x).isDirectory())

    def listPaths    (): List[FsPath]   = listNames().map(path / _)
    def listFilePaths(): List[FilePath] = listPaths().filter(new File(_).isFile())
    def listDirPaths (): List[DirPath]  = listPaths().filter(new File(_).isDirectory())

    def listPathsRecursively    (): List[FsPath]   = listPaths().flatMap { p => (if (new File(p).isDirectory()) p.path.listFilePathsRecursively() else Nil) ++ Seq(p) }
    def listFilePathsRecursively(): List[FilePath] = listPathsRecursively().filter(new File(_).isFile())
    def listDirPathsRecursively (): List[DirPath]  = listPathsRecursively().filter(new File(_).isDirectory())

    def listFilePathsRecursively(suffix: String): List[FilePath] = listFilePathsRecursively().filter(_.endsWith(suffix)) // convenient for file extensions

  def name = ioFile.getName()

  // ===========================================================================
  //TODO: mkdirs, glob, ... (more needs to be ported from original aptus, see t210116165619)
}

// ===========================================================================
private[aptus] final class AptusDirPath(override val path: String) extends _AptusPath {
  def  isEmpty(): Boolean = listContent().isEmpty
  def nonEmpty(): Boolean = !isEmpty()

  def mkdir (): String = { s"mkdir    ${path}".systemCall(); path }
  def mkdirs(): String = { s"mkdir -p ${path}".systemCall(); path } }

// ===========================================================================
// make private again
/*private[aptus]*/ final class AptusFilePath(override val path: String) extends _AptusPath {
  def fileName: String = name
  def basename: String = name.splitBy(".").head

  // ---------------------------------------------------------------------------
  def readBytes: Array[Byte] = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)) // TODO: buffered version for streaming

  // ---------------------------------------------------------------------------
  def  isEmpty: Boolean = ioFile.isFile && java.nio.file.Files.size(nioPath) == 0L
  def nonEmpty: Boolean = ioFile.isFile && java.nio.file.Files.size(nioPath) >  0L

  // ---------------------------------------------------------------------------
  def renameTo     (to: FilePath): FilePath = aptutils.FsUtils.moveFile     (path, to)
  def moveFile     (to: FilePath): FilePath = aptutils.FsUtils.moveFile     (path, to)
  def moveFileToDir(to: DirPath) : FilePath = aptutils.FsUtils.moveFileToDir(path, to) }

// ===========================================================================
// make private again
/*private[aptus]*/ final class AptusPath(override val path: String) extends _AptusPath { // TODO: as AnyVal?
  def dir  = new aptmisc.AptusDirPath (path)
  def file = new aptmisc.AptusFilePath(path)

  def mkdir () = dir.mkdir ()
  def mkdirs() = dir.mkdirs()

  def isFile   (): Boolean = _isFile()
  def isDir    (): Boolean = _isDir()
  def isSymlink(): Boolean = _isSymlink() }

// ===========================================================================
