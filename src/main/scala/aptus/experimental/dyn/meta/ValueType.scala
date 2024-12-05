package aptus
package experimental
package dyn
package meta

// ===========================================================================
/*private[dyn]*/ trait ValueType {
  def forceBasicType: BasicType =
    either.swap.getOrElse(??? /* TODO */)

  def forceNestedClass: Cls =
    either.getOrElse(??? /* TODO */)

  // ---------------------------------------------------------------------------
  def either: Either[BasicType, Cls] = this match {
    case bt: BasicType => Left (bt)
    case nc: Cls       => Right(nc) } }

// ===========================================================================