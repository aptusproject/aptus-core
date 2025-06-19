package aptustesting
package utils

import util.chaining._
import aptus.String_

// ===========================================================================
object TestUtils {

  def checkResult[T](value: T, expected: T)(format: T => String): Unit = {
    assert(
      value == expected,
      "\n" +
      value   .pipe(format).swp.sectionAllOff("actual:"  , 2).newline +
      expected.pipe(format).swp.sectionAllOff("expected:", 2).newline) }

  // ---------------------------------------------------------------------------
  def checkResultCharArray[T](value: T, expected: T)(format: T => String): Unit = {
    assert(
      value == expected,
      "\n" +
      value   .pipe(format).toCharArray.map(_.toByte).toList +
      expected.pipe(format).toCharArray.map(_.toByte).toList) } }

// ===========================================================================