package aptus
package experimental
package dyn
package domain
package valew

// ===========================================================================
private object ValewOps {

  def isOrContainsValew(value: Any): Boolean =
    value match {
      // TODO: Maps too?
      case x: Iterable[_] => x.exists(isOrContainsValew)
      case x: Option  [_] => x.exists(isOrContainsValew)
      case x              => x.isInstanceOf[Valew] }

  // ---------------------------------------------------------------------------
  /* typically when transforming eg one key among many others */
  def potentiallyUnwrap(value: Any): Valew = // 241126120322
    Valew(value match {
      case Valew(x) => x
      case       x  => x }) }

// ===========================================================================