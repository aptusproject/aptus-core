package aptus
package aptutils

import util.chaining._

// ===========================================================================
package object joining {
  type ListMap[K, V] = collection.immutable.ListMap[K, V]

  /** non-empty sequence (intent only, not enforced) */
  type Nes[T] = aptus.Nes[T]

  // ---------------------------------------------------------------------------
  private[joining] def intersection[L, R, K]
        (leftMap: ListMap[K, Seq[L]],
         riteMap: ListMap[K, Seq[R]])
      : Seq[(K, (L, R))] =
    leftMap
      .view // else, as ListMap, will discard duplicate keys
      .flatMap { case (key, leftValues) =>
        val riteValues = riteMap.getOrElse(key, Nil)
        for (
            leftValue <- leftValues;
            riteValue <- riteValues)
          yield { key -> (leftValue, riteValue) } }
      .toList

  // ===========================================================================
  private[joining] def sideOnly[K, V](side: ListMap[K, Seq[V]])(otherKeySet: Set[K]) /* : collection.MapView[K, Seq[V]] - causes issues with 2.12 */ =
    side.keySet
      .diff { otherKeySet }
      .pipe { leftOnlyKeys =>
        side
          .filter { leftEntry =>
            leftOnlyKeys.contains(leftEntry._1) }
          .view } }

// ===========================================================================
