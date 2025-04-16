package aptus
package experimental
package dyn
package domain

// ===========================================================================
trait IntegerLike[I] extends NumberLike[I] { // TODO: rename to Integral? use stdlib's Integral more?
      protected val value: I

      // TODO: more int-like common ops?
      def + [N: Numeric]   (that: N)  : N   = Ops.plus     (value, that)
      def - [N: Numeric]   (that: N)  : N   = Ops.minus    (value, that)
      def * [N: Numeric]   (that: N)  : N   = Ops.times    (value, that)
      def / [F: Fractional](that: F)  : F   = Ops.div(value, that)
      def /                (that: Int): Int = Ops.div(value, that: Double)
        .tap { v => if (!v.isValidInt) Error.AccessAsSpecificType(BasicType._Int, v).thro }.toInt

      // ===========================================================================
      private object Ops {
        def plus [T, N: Numeric]   (value: T, that: N): N = { val num  = implicitly[Numeric[N]]   ; num .plus (num .from(value), that) }
        def minus[T, N: Numeric]   (value: T, that: N): N = { val num  = implicitly[Numeric[N]]   ; num .minus(num .from(value), that) }
        def times[T, N: Numeric]   (value: T, that: N): N = { val num  = implicitly[Numeric[N]]   ; num .times(num .from(value), that) }
        def div  [T, F: Fractional](value: T, that: F): F = { val frac = implicitly[Fractional[F]]; frac.div  (frac.from(value), that) } } }

    // ===========================================================================
    object IntegerLike {
      private[dyn] class Int__  (val value: Int)   extends IntegerLike[Int]
      private[dyn] class Long__ (val value: Long)  extends IntegerLike[Long]
      private[dyn] class Short__(val value: Short) extends IntegerLike[Short]
      private[dyn] class Byte__ (val value: Byte)  extends IntegerLike[Byte]

      // ---------------------------------------------------------------------------
      // order matters (see a241125101924)
      private[dyn] implicit def _byte (value: Byte) : IntegerLike[_] = new Byte__ (value)
      private[dyn] implicit def _short(value: Short): IntegerLike[_] = new Short__(value)
      private[dyn] implicit def _int  (value: Int)  : IntegerLike[_] = new Int__  (value)
      private[dyn] implicit def _long (value: Long) : IntegerLike[_] = new Long__ (value) }

  // ===========================================================================
  object IntegerLikeParsing { import IntegerLike._

    def opt(value: NakedValue): Option[IntegerLike[_]] =
      value match {
        case x: Int  => Some(new Int__ (x))
        case x: Long => Some(new Long__(x))
        case _       => None } }

// ===========================================================================
