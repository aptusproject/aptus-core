package aptus

// ===========================================================================
trait AptusScalaVersionSpecific { // 3.x
  trait WTT[T] {}; object WTT { implicit def to[T]: WTT[T] = new WTT[T]{} } // TODO

  // ---------------------------------------------------------------------------
  // type Items[$Items <: Items[_, _], $Item] = aptitems.Items[$Items, $Item]
  //                      ^ Cyclic reference involving type Items (TODO: t241202151014)

  // ---------------------------------------------------------------------------
  def listMap[K, V](values: Seq[(K, V)])          : ListMap[K, V] = collection.immutable.ListMap.from(values)
  def listMap[K, V](value1: (K, V), more: (K, V)*): ListMap[K, V] = listMap[K, V](value1 +: more) }

// ===========================================================================
