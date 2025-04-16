package aptus
package experimental
package dyn
package domain

// ===========================================================================
package object errors {
  trait HasErrorId    { val id        : ErrorId }
  trait HasStateError { val stateError: Boolean }

  // ---------------------------------------------------------------------------
  trait ErrorData extends Error{
          val companion: HasErrorId with HasStateError
    final def id        : ErrorId = companion.id
    final def stateError: Boolean = companion.stateError }

  // ---------------------------------------------------------------------------
  abstract class ErrorCompanion(
      val id        : ErrorId,
      val stateError: Boolean = false)
    extends HasErrorId with HasStateError }

// ===========================================================================