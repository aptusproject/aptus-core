package aptus.aptmisc

// ===========================================================================
/** convenient for quick hacks */
final class ThreadLocalWrapper[T] private (threadLocal: ThreadLocal[Option[T]]) {
  def forceValue ():        T  = threadLocal.get().get
  def getValueOpt(): Option[T] = threadLocal.get()
  def isSet()      : Boolean   = threadLocal.get().nonEmpty

  // ---------------------------------------------------------------------------
  def map[U](f: T => U): Option[U] = threadLocal.get().map(f)

  // ---------------------------------------------------------------------------
  def setValue(value: T): Unit = threadLocal.set(Some(value)) // favor "using" when possible
  def clear   ()        : Unit = threadLocal.set(None)        // favor "using" when possible

  // ---------------------------------------------------------------------------
  def usingValue[U](value: T)(p: => U): U = {
    val originOpt = threadLocal.get()
    threadLocal.set(Some(value))
    val result = p
    threadLocal.set(originOpt)
    result
  }

  // ---------------------------------------------------------------------------
  def test()(implicit ev: T <:< Boolean): Boolean = threadLocal.get().asInstanceOf[Option[Boolean]].getOrElse(false)
    def setToTrue ()(implicit ev: T <:< Boolean): Unit = setValue(true .asInstanceOf[T])
    def setToFalse()(implicit ev: T <:< Boolean): Unit = setValue(false.asInstanceOf[T])

  // ---------------------------------------------------------------------------
  def isZero()(implicit ev: T <:< Int): Boolean = threadLocal.get().asInstanceOf[Option[Int]].getOrElse(0) == 0

  def setToZero()(implicit ev: T <:< Int): Unit = setValue(0.asInstanceOf[T])
}

// ===========================================================================
object ThreadLocalWrapper {
  def empty[T]: ThreadLocalWrapper[T] = new ThreadLocalWrapper[T](ThreadLocal.withInitial(() => None))

  def init[T](value: T): ThreadLocalWrapper[T] = new ThreadLocalWrapper[T](ThreadLocal.withInitial(() => Some(value)))
}

// ===========================================================================