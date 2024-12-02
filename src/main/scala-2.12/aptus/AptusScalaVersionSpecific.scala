package aptus

// ===========================================================================
trait AptusScalaVersionSpecific { // 2.12
  type Items[$Items <: Items[_, _], $Item] = aptmisc.Items[$Items, $Item]

  // ---------------------------------------------------------------------------
  // only for 2.12, since 2.13 already has ListMap.from
  def listMap[K, V](values: Seq[(K, V)])          : ListMap[K, V] = { val b = collection.immutable.ListMap.newBuilder[K, V]; values.foreach(b += _); b.result }
  def listMap[K, V](value1: (K, V), more: (K, V)*): ListMap[K, V] = listMap[K, V](value1 +: more) }

// ===========================================================================
