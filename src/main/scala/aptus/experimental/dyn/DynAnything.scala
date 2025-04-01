package aptus
package experimental
package dyn

// ===========================================================================
trait DynAnything {
    private[dyn] implicit class DynAnything_[A](protected val value: A) extends DynAnything__[A] }

  // ---------------------------------------------------------------------------
  trait DynAnything__[A] { protected val value: A
    private[dyn] def pipe[B](f: A => B)   : B =   f(value)
    private[dyn] def tap    (f: A => Unit): A = { f(value); value } }

// ===========================================================================