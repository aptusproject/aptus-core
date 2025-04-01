package aptus

// ===========================================================================
trait AptusScalaVersionSpecific { // 3.x
  trait WTT[T] {}; object WTT { implicit def to[T]: WTT[T] = new WTT[T]{} } // TODO

  // ---------------------------------------------------------------------------
  def listMap[K, V](values: Seq[(K, V)])          : ListMap[K, V] = collection.immutable.ListMap.from(values)
  def listMap[K, V](value1: (K, V), more: (K, V)*): ListMap[K, V] = listMap[K, V](value1 +: more) }

// ===========================================================================
