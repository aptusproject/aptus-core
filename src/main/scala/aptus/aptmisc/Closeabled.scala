package aptus
package aptmisc

import scala.util.chaining._
import scala.collection.GenTraversableOnce

// ===========================================================================
private[aptus] final class Closeabled[T](val underlying: T, val cls: Closeable) extends Closeable {
      override def close() = { cls.close() }

      // ---------------------------------------------------------------------------
      def consumeToList       [U](implicit ev: T =:= Iterator[U]): List[U]               = consume(_.toList) // fairly common
      def toCloseabledIterator[U](implicit ev: T =:= Iterator[U]): CloseabledIterator[U] = new CloseabledIterator[U](underlying, cls)

      // ---------------------------------------------------------------------------
      def consume[U](f: T =>            U ):            U  = { val result = f(underlying); close(); result }
      def     map[U](f: T =>            U ): Closeabled[U] = Closeabled.fromPair(f(underlying), cls)
      def flatMap[U](f: T => Closeabled[U]): Closeabled[U] = f(underlying).pipe { x => Closeabled.from(x.underlying, Seq(x.cls, this.cls)) }
    }
  
    // ===========================================================================
    private[aptus] object Closeabled {
  
      def from    [T <: Closeable](underlying: T)                        : Closeabled[T] = new Closeabled(underlying, underlying)
      def from    [T]             (underlying: T, values: Seq[Closeable]): Closeabled[T] = new Closeabled(underlying, closeable(values))
      def fromPair[T]             (pair: (T, Closeable))                 : Closeabled[T] = new Closeabled(pair._1, pair._2)
  
      // ---------------------------------------------------------------------------
      @ordermatters def closeable[A](values: Seq[Closeable]           ): Closeable = new java.io.Closeable { def close() = { values.foreach(_.close()) } }
      @ordermatters def closeable[A](cls1: Closeable, more: Closeable*): Closeable = closeable(cls1 +: more)      
    }
  
  // ===========================================================================
  private[aptus] final class CloseabledIterator[T](val underlying: Iterator[T], val cls: Closeable) extends Closeable { // TODO: look into geny for Iterator version?
      override def close() = { cls.close() }

      // ---------------------------------------------------------------------------
      def toCloseabled: Closeabled[Iterator[T]] = new Closeabled(underlying, cls)
      
      // ---------------------------------------------------------------------------
      def consumeToList: List[T] = { val result = underlying.toList; close(); result }
  
      // ---------------------------------------------------------------------------
      def     map[U](f: T =>                    U ): CloseabledIterator[U] = new CloseabledIterator(underlying    .map(f), cls)
      def flatMap[U](f: T => GenTraversableOnce[U]): CloseabledIterator[U] = new CloseabledIterator(underlying.flatMap(f), cls)
    }
  
    // ===========================================================================
    object CloseabledIterator {
      def fromPair[T](pair: (Iterator[T], Closeable)): CloseabledIterator[T] = new CloseabledIterator[T](pair._1, pair._2)
      
      def fromSeq   [T](values: Seq[T]): CloseabledIterator[T] = new CloseabledIterator[T](values.iterator, Closeabled.closeable(Nil)) // convenient for tests
      def fromValues[T](values: T*)    : CloseabledIterator[T] = fromSeq(values)
    }

// ===========================================================================
