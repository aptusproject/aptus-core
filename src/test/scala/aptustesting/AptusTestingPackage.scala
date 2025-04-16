// ===========================================================================
package object aptustesting {
  import scala.reflect.{classTag, ClassTag}
  import scala.util.{Try, Failure, Success}
  import scala.util.chaining._

  // ---------------------------------------------------------------------------
  type ListMap[K, V] = collection.immutable.ListMap[K, V]
  val  ListMap       = collection.immutable.ListMap

  // ===========================================================================
  def resourceContent(target: String):      String  = target.pipe(scala.io.Source.fromResource(_)).mkString
  def resourceLines  (target: String): List[String] = target.pipe(scala.io.Source.fromResource(_)).getLines().toList

  def resourceFilePath(target: String): String = com.google.common.io.Resources.getResource(target).getPath // eg /home/tony/[...]/core/bin/core/scala-2.13/test-classes/test.json

  // ===========================================================================
  def noop   [A](actual: A)             : Unit = compare(actual, actual)
  def compare[A](actual: A, expected: A): Unit = { Predef.assert(actual == expected, message = s"\n\texpected: |${expected}|\n\tactual:   |${actual}|") }
  
  def compare      [A](enabled: Boolean)(actual: => A, expected: A): Unit = { if (enabled) compare(actual, expected) else () }
  def compareIfUnix[A]                  (actual: => A, expected: A): Unit = compare(aptus.system.isUnix())(actual, expected)

  // ---------------------------------------------------------------------------
  def isTrue(actual: Boolean): Unit = compare[Boolean](actual, true)
  def isFlse(actual: Boolean): Unit = compare[Boolean](actual, false)

  // ---------------------------------------------------------------------------
  def isSome[T](actual: Option[T], someValue: T): Unit = compare[Option[T]](actual, Some(someValue))
  def isNone[T](actual: Option[T])              : Unit = compare[Option[T]](actual, Option.empty[T])

  // ---------------------------------------------------------------------------
  def fail[$Throwable <: Throwable : ClassTag](f: => Any)             : Unit = { _fail[$Throwable](f, msgOpt = None) }
  def fail[$Throwable <: Throwable : ClassTag](f: => Any, msg: String): Unit = { _fail[$Throwable](f, msgOpt = Some(msg)) }

    // ---------------------------------------------------------------------------
    def failAssertion(f: => Any)             : Unit = fail[java.lang.AssertionError]          (f)
    def failAssertion(f: => Any, msg: String): Unit = fail[java.lang.AssertionError]          (f, msg)
    def failArgument (f: => Any)             : Unit = fail[java.lang.IllegalArgumentException](f)
    def failArgument (f: => Any, msg: String): Unit = fail[java.lang.IllegalArgumentException](f, msg)
    def failState    (f: => Any)             : Unit = fail[java.lang.IllegalStateException]   (f)
    def failState    (f: => Any, msg: String): Unit = fail[java.lang.IllegalStateException]   (f, msg)

  // ===========================================================================
  private def _fail[$Throwable <: Throwable : ClassTag](f: => Any, msgOpt: Option[String]) = {
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
