package aptus

// ===========================================================================
trait AptusScalaVersionSpecific { // 3.x
  // FIXME: can't get this to work with scala 3: type Items[$Items <: Items[_, _], $Item] = aptmisc.Items[$Items, $Item]

  // ---------------------------------------------------------------------------
  def listMap[K, V](values: Seq[(K, V)])          : ListMap[K, V] = collection.immutable.ListMap.from(values)
  def listMap[K, V](value1: (K, V), more: (K, V)*): ListMap[K, V] = listMap[K, V](value1 +: more) }

// ===========================================================================
