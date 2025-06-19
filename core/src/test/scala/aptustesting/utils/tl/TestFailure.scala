package aptustesting
package utils
package tl

// ===========================================================================
trait TestFailure {
  def faiL[T](p: => T) = new {
    def error(error: String) = util.Try(p).checkIllegalArgument(error) }

  // ---------------------------------------------------------------------------
  def fail[$Throwable <: Throwable : reflect.ClassTag](f: => Any)             : Unit = { TestFailureUtils[$Throwable](f, msgOpt = None) }
  def fail[$Throwable <: Throwable : reflect.ClassTag](f: => Any, msg: String): Unit = { TestFailureUtils[$Throwable](f, msgOpt = Some(msg)) }

    // ---------------------------------------------------------------------------
    def failAssertion(f: => Any)             : Unit = fail[java.lang.AssertionError]          (f)
    def failAssertion(f: => Any, msg: String): Unit = fail[java.lang.AssertionError]          (f, msg)
    def failArgument (f: => Any)             : Unit = fail[java.lang.IllegalArgumentException](f)
    def failArgument (f: => Any, msg: String): Unit = fail[java.lang.IllegalArgumentException](f, msg)
    def failState    (f: => Any)             : Unit = fail[java.lang.IllegalStateException]   (f)
    def failState    (f: => Any, msg: String): Unit = fail[java.lang.IllegalStateException]   (f, msg) }

// ===========================================================================