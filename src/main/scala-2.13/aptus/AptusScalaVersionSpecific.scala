package aptus

// ===========================================================================
trait AptusScalaVersionSpecific {
  type Items[$Items <: Items[_, _], $Item] = aptmisc.Items[$Items, $Item]

  // ---------------------------------------------------------------------------
  import collection.immutable.ListMap
  def listMap[K, V](values: Seq[(K, V)])          : ListMap[K, V] = ListMap.from(values)
  def listMap[K, V](value1: (K, V), more: (K, V)*): ListMap[K, V] = listMap[K, V](value1 +: more) }

// ===========================================================================
