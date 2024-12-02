package aptus

// ===========================================================================
package object apttraits

// ---------------------------------------------------------------------------
package apttraits { // not sure why not letting me put those in the package object

  /** by and large the most useful ones */
  trait AptusMinExtensions { // TODO: t241202114104 - we lost the AnyVal advantage here :-/
    implicit def anything_[T](value:     T) : Anything_[T] = new Anything_(value)
    implicit def seq_     [T](value: Seq[T]): Seq_     [T] = new Seq_     (value)
    implicit def string_     (value: String): String_      = new String_  (value) }

  // ---------------------------------------------------------------------------
  trait AptusChaining {
    implicit class Chaining_[A](value: A) { // so as to not import chaining._ everywhere
      def pipe[B](f: A => B)   : B =   f(value)
      def tap    (f: A => Unit): A = { f(value); value } } }

  // ===========================================================================
  /** convenient for quick prototyping */
  trait AptusBooleanShorthands {
    val _t = true
    val _f = false }

  // ---------------------------------------------------------------------------
  trait AptusDummyImplicitShorthand {
    type DI = DummyImplicit }

  // ---------------------------------------------------------------------------
  trait AptusNullShorthands {
    val NullInt     = null.asInstanceOf[Int]
    val NullDouble  = null.asInstanceOf[Double]
    val NullBoolean = null.asInstanceOf[Boolean]
    val NullString  = null.asInstanceOf[String] }

  // ---------------------------------------------------------------------------
  trait AptusExceptionUtils {
    def illegalState        (x: Any*): Nothing = { throw new IllegalStateException        (x.mkString(", ")) }
    def illegalArgument     (x: Any*): Nothing = { throw new IllegalArgumentException     (x.mkString(", ")) }
    def unsupportedOperation(x: Any*): Nothing = { throw new UnsupportedOperationException(x.mkString(", ")) } }

  // ---------------------------------------------------------------------------
  trait AptusZippers {
    def zip[T1, T2, T3]        (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3])                                  : Iterable[(T1, T2, T3)]         = a.zip(b).zip(c)              .map { case   ((a, b), c)         => (a, b, c) }
    def zip[T1, T2, T3, T4]    (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4])                 : Iterable[(T1, T2, T3, T4)]     = a.zip(b).zip(c).zip(d)       .map { case  (((a, b), c), d)     => (a, b, c, d) }
    def zip[T1, T2, T3, T4, T5](a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4], e: Iterable[T5]): Iterable[(T1, T2, T3, T4, T5)] = a.zip(b).zip(c).zip(d).zip(e).map { case ((((a, b), c), d), e) => (a, b, c, d, e) }
    def zip[T1, T2](a: Iterable[T1], b: Iterable[T2]): Iterable[(T1, T2)] = a.zip(b) /* for good measure, should favor: a.zip(b) */ }

  // ---------------------------------------------------------------------------
  trait AptusBinaryUtils {
    def byteBuffer(byte1: Byte, more: Byte*): ByteBuffer = java.nio.ByteBuffer.wrap((byte1 +: more).toArray)
    def byteBuffer(value: String)           : ByteBuffer = java.nio.ByteBuffer.wrap(value.getBytes)
    def byteBuffer(bytes: Array[Byte])      : ByteBuffer = java.nio.ByteBuffer.wrap(bytes) } }

// ===========================================================================