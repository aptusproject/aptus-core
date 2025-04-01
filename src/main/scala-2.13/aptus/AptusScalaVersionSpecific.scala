package aptus

// ===========================================================================
trait AptusScalaVersionSpecific { // 2.13
  trait WTT[T] // TODO: where did this go? removed after 2.13.13? scala.reflect.runtime.universe.WeakTypeTag[T] - object runtime is not a member of package reflect
    object WTT { implicit def to[T]: WTT[T] = new WTT[T]{} }
  
  // ---------------------------------------------------------------------------
  def listMap[K, V](values: Seq[(K, V)])          : ListMap[K, V] = collection.immutable.ListMap.from(values)
  def listMap[K, V](value1: (K, V), more: (K, V)*): ListMap[K, V] = listMap[K, V](value1 +: more) }

// ===========================================================================
