package aptustesting
package utils
package tl

import scala.reflect.{classTag, ClassTag}
import scala.util.{Try, Failure, Success}

// ===========================================================================
private object TestFailureUtils {

  def apply[$Throwable <: Throwable : ClassTag](f: => Any, msgOpt: Option[String]): Unit = {
    Try(f) match {

      // ---------------------------------------------------------------------------
      case Failure(f) =>
        val expectedThrowableName = classTag[$Throwable].runtimeClass.getName
        val actualThrowableName   = f.getClass.getName

        // ---------------------------------------------------------------------------
        Predef.assert(
                      expectedThrowableName == actualThrowableName,
            message = expectedThrowableName -> actualThrowableName)

        // ---------------------------------------------------------------------------
        Predef.assert(
            msgOpt.forall(_ == f.getMessage),
            message = msgOpt.get -> f.getMessage)

      // ---------------------------------------------------------------------------
      case Success(_) => assert(false) } } }

// ===========================================================================