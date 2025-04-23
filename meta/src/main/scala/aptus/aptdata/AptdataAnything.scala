package aptus

// ===========================================================================
//package object aptdata
//    extends AptdataAnything
//       with AptusGalliaMetaAdaptor {
//
//  // ---------------------------------------------------------------------------
//  implicit class AptusDataChaining_[A](value: A) {
//    @inline def pype[B](f: A => B): B = f(value) } // TODO: t250122123254 - why
//
//  // ---------------------------------------------------------------------------
//  private[aptdata] type AnyValue = Any }

// ===========================================================================
package aptdata {

  trait AptdataAnything {
      private[aptdata] implicit class AptdataAnything_[A](protected val value: A) extends AptdataAnything__[A] }

    // ---------------------------------------------------------------------------
    trait AptdataAnything__[A] { protected val value: A
      private[aptdata] def pipe[B](f: A => B)   : B =   f(value)
      private[aptdata] def tap    (f: A => Unit): A = { f(value); value } } }

// ===========================================================================

