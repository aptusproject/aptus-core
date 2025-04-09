package aptustesting
package dyntest

// ===========================================================================
trait DynTestAliases { import aptus.experimental.dyn._
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
  val _Int = BasicType._Int

  // ---------------------------------------------------------------------------
  type SuperType = domain.SuperType }

// ===========================================================================
