package aptus
package aptmisc

// ===========================================================================
class SelfClosingIterator[A](parent: Iterator[A], cls: Closeable*) extends Iterator[A] with Closeable {
    private var closed: Boolean = false

    // ===========================================================================
    override def hasNext: Boolean = !closed && parent.hasNext

    override def next(): A = {
      val next = parent.next()
      if (!parent.hasNext) { close() }
      next }

    // ---------------------------------------------------------------------------
    override def close() = { closed = true; cls.foreach(_.close()) } }

  // ===========================================================================
  object SelfClosingIterator {
    def fromPair[T](pair: (Iterator[T], Closeable)): SelfClosingIterator[T] =
      new SelfClosingIterator[T](pair._1, pair._2) }

// ===========================================================================