package aptus
package aptmisc

import scala.util.chaining._
import scala.collection.GenTraversableOnce

// ===========================================================================
private[aptus] final class Closeabled[T](val underlying: T, val cls: Closeable) extends Closeable {
      override def close() = { cls.close() }

      // ---------------------------------------------------------------------------
      def pair: (T, Closeable) = underlying -> cls

      // ---------------------------------------------------------------------------
      def consumeToList       [U](implicit ev: T =:= Iterator[U]): List[U]               = consume(_.toList) // fairly common
      def toCloseabledIterator[U](implicit ev: T =:= Iterator[U]): CloseabledIterator[U] = new CloseabledIterator[U](underlying, cls)
def toCloseabledIterator0[U](implicit ev: T <:< Iterator[U]): CloseabledIterator[U] = new CloseabledIterator[U](underlying, cls) // version with =:= seems to cause problems for 2.12
      // ---------------------------------------------------------------------------
      def consume[U](f: T =>            U ):            U  = { val result = f(underlying); close(); result }
      def     map[U](f: T =>            U ): Closeabled[U] = Closeabled.fromPair(f(underlying), cls)
      def flatMap[U](f: T => Closeabled[U]): Closeabled[U] = f(underlying).pipe { x => Closeabled.from(x.underlying, Seq(x.cls, this.cls)) }
      def foreach[U](f: T =>            U ): Unit          = { f(underlying); () } }

    // ===========================================================================
    private[aptus] object Closeabled {
  
      def from    [T <: Closeable](underlying: T)                        : Closeabled[T] = new Closeabled(underlying, underlying)
      def from    [T]             (underlying: T, values: Seq[Closeable]): Closeabled[T] = new Closeabled(underlying, closeable(values))
      def fromPair[T]             (pair: (T, Closeable))                 : Closeabled[T] = new Closeabled(pair._1, pair._2)
      def fromUncloseable[T]      (underlying: T)                        : Closeabled[T] = new Closeabled(underlying, () => {})
  
      // ---------------------------------------------------------------------------
      @ordermatters def closeable(values: Seq[Closeable]           ): Closeable = () => values.foreach(_.close())
      @ordermatters def closeable(cls1: Closeable, more: Closeable*): Closeable = closeable(cls1 +: more)
    }
  
  // ===========================================================================
  // TODO: t210204105730 - look into https://github.com/com-lihaoyi/geny?
  private[aptus] final class CloseabledIterator[T](val underlying: Iterator[T], val cls: Closeable) extends Closeable {
      override def close() = { cls.close() }

      // ---------------------------------------------------------------------------
      def hasNext: Boolean = underlying.hasNext
      def next() : T       = underlying.next()

      // ===========================================================================
      def filter(p: T => Boolean): CloseabledIterator[T] = alter(_.filter(p))
      def find  (p: T => Boolean): Option            [T] = consume(_.find(p))

      def reduce(op: (T, T) => T):                    T  = consume(_.reduce(op))

      // ---------------------------------------------------------------------------
      def drop     (n: Int)         : CloseabledIterator[T] = alter(_.drop(n))
      def take     (n: Int)         : CloseabledIterator[T] = alter(_.take(n))
      def dropWhile(p: T => Boolean): CloseabledIterator[T] = alter(_.dropWhile(p))
      def takeWhile(p: T => Boolean): CloseabledIterator[T] = alter(_.takeWhile(p))

      // ---------------------------------------------------------------------------
      def isEmpty: Boolean = !underlying.hasNext
      def size   : Int     = consume(_.size) // TODO: t220629101212 - offer a Long alternative as well (account for knownSize)

      // ===========================================================================
      def toCloseabled : Closeabled[Iterator[T]] = new Closeabled(underlying, cls)
      def toSelfClosing:            Iterator[T]  = new aptmisc.SelfClosingIterator(underlying, cls)

      // ---------------------------------------------------------------------------
      def consume[U](f: Iterator[T] => U):      U  = { val result = f(underlying); close(); result }
      def consumeAll                     : List[T] = consume(_.toList)
      def consumeNext()                  :      T  = consume(_.next())

      // ---------------------------------------------------------------------------
      def alter[U](f: Iterator[T] => Iterator[U]): CloseabledIterator[U] = new CloseabledIterator(f(underlying), cls)

      // ---------------------------------------------------------------------------
      def     map[U](f: T =>                    U ): CloseabledIterator[U] = new CloseabledIterator(underlying    .map(f), cls)
      def flatMap[U](f: T => GenTraversableOnce[U]): CloseabledIterator[U] = new CloseabledIterator(underlying.flatMap(f), cls)

      // ===========================================================================
      def union[V >: T](that: CloseabledIterator[V]): CloseabledIterator[V] = new CloseabledIterator(
        this.underlying ++ that.underlying, Closeabled.closeable(this.cls, that.cls) ) // TODO: bother closing first one ealier?

      // ---------------------------------------------------------------------------
      def zip[U](that: CloseabledIterator[U]): CloseabledIterator[(T, U)] = new CloseabledIterator(
        this.underlying.zipSameSize(that.underlying), Closeabled.closeable(this.cls, that.cls) )

      // ===========================================================================
      def groupByPreSortedKey[K, V](implicit ev: T <:< (K, V)) = alter {
        _.asInstanceOf[Iterator[(K, V)]].pipe(aptutils.IteratorUtils.groupByPreSortedKey) }

      // ---------------------------------------------------------------------------
      def zipSameSize[U](that: CloseabledIterator[U]): CloseabledIterator[(T, U)] =
        new CloseabledIterator(
                               this.underlying.zipSameSize(that.underlying),
          Closeabled.closeable(this.cls,                   that.cls))

      // ===========================================================================
      def writeLines(path: String)(implicit ev: T <:< String) = writeGzipLines(path, 100, None)

      // ---------------------------------------------------------------------------
      def writeGzipLines(
            out         : FilePath,
            flushModulo : Long,
            logModulo   : Option[Long => Unit])
            (implicit ev: T <:< String)
          : OutputFilePath =
        map(_.asInstanceOf[String])
          .consume(aptutils.FileUtils.writeGzipLines(out, flushModulo, logModulo))
    }
  
    // ===========================================================================
    object CloseabledIterator {
      def fromPair       [T](pair: (Iterator[T], Closeable)): CloseabledIterator[T] = new CloseabledIterator[T](pair._1, pair._2)
      def fromUncloseable[T](underlying: Iterator[T])       : CloseabledIterator[T] = new CloseabledIterator[T](underlying, () => {})

      def fromSeq   [T](values: Seq[T]): CloseabledIterator[T] = new CloseabledIterator[T](values.iterator, () => ()) // convenient for tests
      def fromValues[T](values: T*)    : CloseabledIterator[T] = fromSeq(values)
      def fromSole  [T](value : T )    : CloseabledIterator[T] = fromSeq(Seq(value))
    }

// ===========================================================================
