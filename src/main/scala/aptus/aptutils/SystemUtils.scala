package aptus
package aptutils

import scala.util.chaining._
import scala.collection.JavaConverters._
import scala.concurrent.{Future, ExecutionContext}
import java.io.{OutputStream, InputStream}
import org.apache.commons.io.IOUtils

// ===========================================================================
object SystemUtils {

  def runCommand                    (cmd: String): String = tokenize(cmd).pipe(run(redirectError = false)).right.get
  def runCommandWithErrorRedirection(cmd: String): String = tokenize(cmd).pipe(run(redirectError = true )).right.get

  // ---------------------------------------------------------------------------        
  def run                    (cmdItems: String*): String = run(redirectError = false)(cmdItems).right.get
  def runWithErrorRedirection(cmdItems: String*): String = run(redirectError = true )(cmdItems).right.get

    // ===========================================================================
    def run(redirectError: Boolean)(cmdItems: Seq[String]): Either[(String, String), String] = {
        val builder = new ProcessBuilder(cmdItems:_*)
        
        if (redirectError) {
          builder.redirectErrorStream(true) }
    
        val process = builder.start()     
    
        process.waitFor()
        
        val err = _err(process)
        val in  = _in (process)
    
        if (err.nonEmpty) Left (err -> in)
        else              Right(in)    
      }
    
      // ===========================================================================
      private def _in (process: Process): String = process.getInputStream().pipe(IOUtils.toString(_, "UTF-8"))  
      private def _err(process: Process): String = process.getErrorStream().pipe(IOUtils.toString(_, "UTF-8"))
      
      // ===========================================================================
      private def tokenize(cmd: String): Seq[String] = // as used in Runtime.exec
        new java.util.StringTokenizer(cmd)
          .asScala
          .map(_.asInstanceOf[String])
          .toList  

  // ===========================================================================
  // TODO: t210308150015 - look into https://github.com/com-lihaoyi/os-lib 
  def streamSystemCall(ec: ExecutionContext)(args: String*): (OutputStream /* to write input into */, InputStream /* to read output from */) = {
    val builder = new java.lang.ProcessBuilder(args:_*)
      builder.redirectErrorStream(true)

    val process = builder.start()

    val os = process.getOutputStream() // to write input into
    val is = process.getInputStream () // to obtain output from

    (os, is)    
  }

}

// ===========================================================================
