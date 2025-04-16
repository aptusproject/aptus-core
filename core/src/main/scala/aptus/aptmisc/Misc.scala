package aptus
package aptmisc

import scala.util.chaining._

// ===========================================================================
object AptusSystem {
  def userName         (): String   = System.getProperty("user.name")
  def hostName         (): HostName = java.net.InetAddress.getLocalHost().getHostName() // TODO: https://stackoverflow.com/questions/7348711/recommended-way-to-get-hostname-in-java
  
  // ---------------------------------------------------------------------------
  /** eg "Linux", "Mac OS X", "Windows XXX", ... **/ // seems Android returns "Linux" too, TODO: t210602093755 - TBC
  def osName(): String = System.getProperty("os.name")
  
  // ---------------------------------------------------------------------------
  def isUnix(): Boolean = org.apache.commons.lang3.SystemUtils.IS_OS_UNIX

    def isLinux  (): Boolean = org.apache.commons.lang3.SystemUtils.IS_OS_LINUX
    def isMac    (): Boolean = org.apache.commons.lang3.SystemUtils.IS_OS_MAC
    def isWindows(): Boolean = org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS
    def isFreeBSD(): Boolean = org.apache.commons.lang3.SystemUtils.IS_OS_FREE_BSD
    def isSunOS  (): Boolean = org.apache.commons.lang3.SystemUtils.IS_OS_SUN_OS     
}

// ===========================================================================
object Fs { 
  def homeDirectoryPath(): DirPath  = System.getProperty("user.home") // does not include a trailing slash    
  def workingDir       (): DirPath  = System.getProperty("user.dir" ) // does not include a trailing slash; directory where java was run from, where you started the JVM
  def pwd              (): DirPath  = workingDir()                    // does not include a trailing slash

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
object Reflection {
  def       stackTrace(): List[java.lang.StackTraceElement] = new Throwable().getStackTrace.toList
  def formatStackTrace(): String = aptutils.ThrowableUtils.stackTraceString(new Throwable())
}

// ===========================================================================
object Time { import aptutils.TimeUtils.elapsed
  lazy val TimestampFormat = new java.text.SimpleDateFormat("yyMMddHHmmss")

  def stampEpochMs(): Long = new java.util.Date().getTime

  def stamp(): String = TimestampFormat.format(new java.util.Date())

  def milliseconds[A](block: => A): A = { val (result, time) = elapsed(block);  time                               .pipe(elapsed => println(s"done: ${elapsed} ms"     )); result }
  def seconds[A]     (block: => A): A = { val (result, time) = elapsed(block); (time /    1000.0).formatDecimals(2).pipe(elapsed => println(s"done: ${elapsed} seconds")); result }
  def minutes[A]     (block: => A): A = { val (result, time) = elapsed(block); (time /   60000.0).formatDecimals(2).pipe(elapsed => println(s"done: ${elapsed} minutes")); result }
  def hours  [A]     (block: => A): A = { val (result, time) = elapsed(block); (time / 3600000.0).formatDecimals(2).pipe(elapsed => println(s"done: ${elapsed} hours"  )); result } }

// ===========================================================================
