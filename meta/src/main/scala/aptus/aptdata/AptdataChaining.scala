package aptus

// ===========================================================================
package aptdata {

  trait AptdataChaining {
      private[aptdata] implicit class AptdataChaining_[A](protected val value: A) extends AptdataChaining__[A] }

    // ---------------------------------------------------------------------------
    trait AptdataChaining__[A] { protected val value: A
      private[aptdata] def pipe[B](f: A => B)   : B =   f(value)
      private[aptdata] def tap    (f: A => Unit): A = { f(value); value } } }

// ===========================================================================

