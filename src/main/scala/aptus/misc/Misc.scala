package aptus
package misc

import scala.util.chaining._

// ===========================================================================
object Environment {
  def userName         (): String   = System.getProperty("user.name")
  def hostName         (): HostName = java.net.InetAddress.getLocalHost().getHostName() // TODO: https://stackoverflow.com/questions/7348711/recommended-way-to-get-hostname-in-java
  
  // ---------------------------------------------------------------------------
  /** eg "Linux", "Mac OS X", "Windows XXX", ... **/ // seems Android returns "Linux" too, TODO: t210602093755 - TBC
  def osName(): String = System.getProperty("os.name")
  
  // ---------------------------------------------------------------------------
  def isLinux  (): Boolean = osName().toLowerCase.startsWith("linux")
  def isMac    (): Boolean = osName().toLowerCase.startsWith("mac")
  def isWindows(): Boolean = osName().toLowerCase.startsWith("windows")
  // TODO: FreeBSD and SunOS?
}

// ===========================================================================
object Fs { 
  def homeDirectoryPath(): DirPath  = System.getProperty("user.home")    
  def workingDir       (): DirPath  = System.getProperty("user.dir" ) // directory where java was run from, where you started the JVM
  def pwd              (): DirPath  = workingDir()
  
  // ---------------------------------------------------------------------------
  /** eg /tmp/1428129663834606041 */    
  def tempFilePath: FilePath = javaTempFile().pipe { f => f.getParent.slash(f.getName.pipe(extractRandomPart)) }
  def tempDirPath : DirPath  = javaTempFile().pipe { f => f.getParent.slash(f.getName.pipe(extractRandomPart)) }       

  // ===========================================================================
  private def javaTempFile(): java.io.File =
      java.io.File
        /* createTempFile(prefix, suffix, dir) somehow expects the parent to already exist... */
        .createTempFile(/* prefix */ "aptus@", /* suffix = */ ".tmp") // misnomer because doesn't "create" per se (TBC)
  
    // ---------------------------------------------------------------------------
    private def extractRandomPart(tempFileName: String): String = tempFileName.dropWhile(_ != '@').drop(1).takeWhile(_ != '.')      
}

// ===========================================================================
object Hardware {
  lazy val Runtime = java.lang.Runtime.getRuntime()

  // ---------------------------------------------------------------------------
  def availableProcessors() = Runtime.availableProcessors()
  def totalMemory        () = Runtime.totalMemory() // in bytes
  def freeMemory         () = Runtime. freeMemory() // in bytes
  def maxMemory          () = Runtime.  maxMemory() // in bytes       
}

// ===========================================================================
object Random {    
  def uuidString(): String = java.util.UUID.randomUUID().toString
}

// ===========================================================================
object Reflect {
  def       stackTrace(): List[java.lang.StackTraceElement] = new Throwable().getStackTrace.toList
  def formatStackTrace(): String = utils.ThrowableUtils.stackTraceString(new Throwable())
}

// ===========================================================================
object Time {
  import aptus.Double_
  import utils.TimeUtils.elapsed
  
  def seconds[A](block: => A): A = { val (result, time) = elapsed(block); (time / 1000.0).formatDecimals(2).pipe(elapsed => println(s"done: ${elapsed} seconds")); result }
}
// ===========================================================================
