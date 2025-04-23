package aptus

// ===========================================================================
package aptdata {

  trait AptdataAnything {
      private[aptdata] implicit class AptdataAnything_[A](protected val value: A) extends AptdataAnything__[A] }

    // ---------------------------------------------------------------------------
    trait AptdataAnything__[A] { protected val value: A
      private[aptdata] def pipe[B](f: A => B)   : B =   f(value)
      private[aptdata] def tap    (f: A => Unit): A = { f(value); value } } }

// ===========================================================================

