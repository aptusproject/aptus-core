package aptus
package misc

import java. io._
import java.nio.file.{Path => _, _}

// ===========================================================================
class AptusPath(path: String) {
  def isFile     (): Boolean = Files.isRegularFile (Paths.get(path))
  def isDirectory(): Boolean = Files.isDirectory   (Paths.get(path))
  def isSymlink  (): Boolean = Files.isSymbolicLink(Paths.get(path))

  // ---------------------------------------------------------------------------
  // TODO: touch file
  def removeFile(): Unit = { if (new java.io.File(path).exists()) { s"rm ${path}".systemCall(); () } } // FIXME: wait until removed JVM way  

  // ---------------------------------------------------------------------------
  def listContent: List[Path] = new java.io.File(path).list().toList
  
  // ---------------------------------------------------------------------------
  def ensureDir   (): DirPath = if (isDirectory()) path else createNewDir()
  def createNewDir(): DirPath = { new File(path).mkdir(); path }

  // ---------------------------------------------------------------------------
  def listFileNames(): List[String] = new java.io.File(path).list().toList // see org.apache.commons.io.FileUtils for more involved

  // ---------------------------------------------------------------------------
  //TODO: mkdirs, glob, ... (more needs to be ported from original aptus, see t210116165619)
}

// ===========================================================================