package aptustesting
package dyntest

import aptus.experimental.dyn.domain

// ===========================================================================
trait DynTestAliases {
  type Try[T] = util.Try[T]
  val  Try    = util.Try

  // ---------------------------------------------------------------------------
  val  Error      = domain.errors.Error

  type HasErrorId = domain.errors.HasErrorId

  // ---------------------------------------------------------------------------
  type IntegerLike = domain.IntegerLike[_]
  val  IntegerLike = domain.IntegerLike

  type    RealLike = domain.   RealLike[_]
  val     RealLike = domain.   RealLike

  // ---------------------------------------------------------------------------
  type SuperType = domain.SuperType

  // ---------------------------------------------------------------------------
  val _Int = aptus.aptdata.meta.basic.BasicType._Int }

// ===========================================================================
