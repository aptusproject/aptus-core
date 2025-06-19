package aptustesting
package dyntest

import aptus.aptdata.domain

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
  val _Int = aptus.aptdata.meta.basic.BasicType._Int

  // ---------------------------------------------------------------------------
  type NakedValue = Any

  type Valew = aptus.aptdata.domain.valew.Valew
  val  Valew = aptus.aptdata.domain.valew.Valew

  type Seq2D[T] = aptus.dyn.Seq2D[T]
  type Seq3D[T] = aptus.dyn.Seq3D[T]

  // ---------------------------------------------------------------------------
  type RealMatrixCommons = org.apache.commons.math3.linear.RealMatrix }

// ===========================================================================
