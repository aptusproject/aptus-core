package aptustesting
package utils
package tl

import util.chaining._

// ===========================================================================
trait TestResources {
  def resourceContent(target: String):      String  = target.pipe(scala.io.Source.fromResource(_)).mkString
  def resourceLines  (target: String): List[String] = target.pipe(scala.io.Source.fromResource(_)).getLines().toList

  // ---------------------------------------------------------------------------
  // eg /home/tony/[...]/core/bin/core/scala-2.13/test-classes/test.json
  def resourceFilePath(target: String): String = com.google.common.io.Resources.getResource(target).getPath }

// ===========================================================================