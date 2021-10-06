package aptus
package aptmisc

import scala.util.chaining._

// ===========================================================================
private[aptus] final class Closeabled[T](val underlying: T, val cls: Closeable) extends Closeable { // TODO: look into geny for Iterator version?
    override def close() = { cls.close() }

    // ---------------------------------------------------------------------------
    def consume[U](f: T =>            U ):            U  = { val result = f(underlying); close(); result }
    def     map[U](f: T =>            U ): Closeabled[U] = Closeabled.fromPair(f(underlying), cls)
    def flatMap[U](f: T => Closeabled[U]): Closeabled[U] = f(underlying).pipe { x => Closeabled.from(x.underlying, Seq(x.cls, this.cls)) }
  }

  // ===========================================================================
  private[aptus] object Closeabled {

    def from    [T <: Closeable](underlying: T)                        : Closeabled[T] = new Closeabled(underlying, underlying)
    def from    [T]             (underlying: T, values: Seq[Closeable]): Closeabled[T] = new Closeabled(underlying, closeable(values))
    def fromPair[T]             (pair: (T, Closeable))                 : Closeabled[T] = new Closeabled(pair._1, closeable(pair._2))

    // ---------------------------------------------------------------------------
    @ordermatters private def closeable[A](values: Seq[Closeable]           ): Closeable = new java.io.Closeable { def close() = { values.foreach(_.close()) } }
    @ordermatters private def closeable[A](cls1: Closeable, more: Closeable*): Closeable = closeable(cls1 +: more)
  }

// ===========================================================================
