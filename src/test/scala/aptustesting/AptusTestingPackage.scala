// ===========================================================================
package object aptustesting {
  import aptus._
  import scala.reflect.{classTag, ClassTag}
  import scala.util.{Try, Failure, Success}
  
  // ===========================================================================
  def noop   [A](actual: A)             : Unit = compare(actual, actual)
  def compare[A](actual: A, expected: A): Unit = { assert(actual == expected, message = actual -> expected) }
  
  def compare      [A](enabled: Boolean)(actual: => A, expected: A): Unit = { if (enabled) compare(actual, expected) else () }
  def compareIfUnix[A]                  (actual: => A, expected: A): Unit = compare(aptus.system.isUnix())(actual, expected)

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