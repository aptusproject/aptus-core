// ===========================================================================
package object aptustesting {
  import scala.reflect._
  import scala.util._
  
  // ===========================================================================
  def noop   [A](actual: A)              = compare(actual, actual)
  def compare[A](actual: A, expected: A) = { assert(actual == expected, message = actual -> expected) }

  // ---------------------------------------------------------------------------
  def fail[$Throwable <: Throwable : ClassTag](f: => Any)              = { _fail[$Throwable](f, msgOpt = None) }
  def fail[$Throwable <: Throwable : ClassTag](f: => Any, msg: String) = { _fail[$Throwable](f, msgOpt = Some(msg)) }

  // ===========================================================================
  private def _fail[$Throwable <: Throwable : ClassTag](f: => Any, msgOpt: Option[String]) = {
    Try(f) match {
      
      // ---------------------------------------------------------------------------      
      case Failure(f) =>
        val expectedThrowableName = classTag[$Throwable].runtimeClass.getName
        val actualThrowableName   = f.getClass.getName
        
        // ---------------------------------------------------------------------------
        assert(
                      expectedThrowableName == actualThrowableName,
            message = expectedThrowableName -> actualThrowableName)
        
        // ---------------------------------------------------------------------------
        assert(
            msgOpt.forall(_ == f.getMessage),
            message = msgOpt.get -> f.getMessage)

      // ---------------------------------------------------------------------------
      case Success(s) => assert(false)
    }
  }

}

// ===========================================================================