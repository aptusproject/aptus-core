package aptus
package utils

import java.io.{OutputStream, InputStream}
import scala.concurrent.{Future, ExecutionContext}

// ===========================================================================
object SystemUtils {
 
  // TODO: t210308150015 - look into https://github.com/com-lihaoyi/os-lib 
  def streamSystemCall(ec: ExecutionContext)(args: String*): (OutputStream /* to write input into */, InputStream /* to read output from */) = {
    val builder = new java.lang.ProcessBuilder(args:_*)
      builder.redirectErrorStream(true)

    val process = builder.start()

    val os = process.getOutputStream() // to write input into
    val is = process.getInputStream () // to obtain output from

    Future.apply { process.waitFor() } (ec)

    (os, is)    
  }

}

// ===========================================================================
