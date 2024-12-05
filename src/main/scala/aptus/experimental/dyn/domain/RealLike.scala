package aptus
package experimental
package dyn
package domain

// ===========================================================================
trait RealLike[R] extends NumberLike[R] {
      protected val value: R

      // ---------------------------------------------------------------------------
      def either: Either[Double, Float]

      // ---------------------------------------------------------------------------
      final def toDouble: Double = either.fold(identity, _.toDouble)

      // ---------------------------------------------------------------------------
      // TODO: more real-like common ops?
      def + [N: Numeric]   (that: N)  : N   = Ops.plus (value, that)
      def - [N: Numeric]   (that: N)  : N   = Ops.minus(value, that)
      def * [N: Numeric]   (that: N)  : N   = Ops.times(value, that)
      def / [F: Fractional](that: F)  : F   = Ops.div  (value, that)

      // ===========================================================================
      private object Ops {
        def plus [T, N: Numeric]   (value: T, that: N): N = { val num  = implicitly[Numeric[N]]   ; num .plus (num .from(value), that) }
        def minus[T, N: Numeric]   (value: T, that: N): N = { val num  = implicitly[Numeric[N]]   ; num .minus(num .from(value), that) }
        def times[T, N: Numeric]   (value: T, that: N): N = { val num  = implicitly[Numeric[N]]   ; num .times(num .from(value), that) }
        def div  [T, F: Fractional](value: T, that: F): F = { val frac = implicitly[Fractional[F]]; frac.div  (frac.from(value), that) } } }

    // ===========================================================================
    object RealLike {
      private[dyn] class Double__(val value: Double) extends RealLike[Double] { val either = Left (value) }
      private[dyn] class Float__ (val value: Float)  extends RealLike[Float]  { val either = Right(value) }

      // ---------------------------------------------------------------------------
      // order matters (see a241125101924)
      private[dyn] implicit def _float (value: Float) : RealLike[_] = new Float__ (value)
      private[dyn] implicit def _double(value: Double): RealLike[_] = new Double__(value) }

  // ===========================================================================
  object RealLikeParsing { import RealLike._
    def opt(value: NakedValue): Option[RealLike[_]] =
      value match {
        case x: Double => Some(new Double__ (x))
        case x: Float  => Some(new Float__(x))
        case _         => None } }

// ===========================================================================
