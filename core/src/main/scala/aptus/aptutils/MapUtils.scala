package aptus
package aptutils

import collection.{mutable, immutable}

// ===========================================================================
object MapUtils {

  def toMutableMap[A, T, U](coll: Seq[A])(implicit ev: A <:< (T, U)): mutable.HashMap[T, U] = {
    val b = mutable.HashMap.newBuilder[T, U]; for (x <- coll) b += x; b.result() }

  def toHashMap[A, T, U](coll: Seq[A])(implicit ev: A <:< (T, U)): immutable.HashMap[T, U] = {
    val b = immutable.HashMap.newBuilder[T, U]; for (x <- coll) b += x; b.result() }

  def toListMap[A, T, U](coll: Seq[A])(implicit ev: A <:< (T, U)): immutable.ListMap[T, U] = {
    val b = immutable.ListMap.newBuilder[T, U]; for (x <- coll) b += x; b.result() }

  def toTreeMap[A, T, U](coll: Seq[A])(implicit ev: A <:< (T, U), ord: Ordering[T]): immutable.TreeMap[T, U] = {
    val b = immutable.TreeMap.newBuilder[T, U]; for (x <- coll) b += x; b.result() }

  // ===========================================================================
  def toMutableMap[K, V](coll: Map[K, V]): mutable.HashMap[K, V] = {
    val b = mutable.HashMap.newBuilder[K, V]; for (x <- coll) b += x; b.result() }

  def toHashMap[K, V](coll: Map[K, V]): immutable.HashMap[K, V] = {
    val b = immutable.HashMap.newBuilder[K, V]; for (x <- coll) b += x; b.result() }

  def toListMap[K, V](coll: Map[K, V]): immutable.ListMap[K, V] = {
    val b = immutable.ListMap.newBuilder[K, V]; for (x <- coll) b += x; b.result() }

  def toTreeMap[K, V](coll: Map[K, V])(implicit ord: Ordering[K]): immutable.TreeMap[K, V] = {
    val b = immutable.TreeMap.newBuilder[K, V]; for (x <- coll) b += x; b.result() }

  // ===========================================================================
  // all these below are inspired by TraversableLike's (2.12)

  // ---------------------------------------------------------------------------
  def groupByWithListMap[T, K, V](f: T => K, g: T => V)(values: Iterator[T]): immutable.ListMap[K, Seq[V]] = {
      var m = immutable.ListMap.empty[K, mutable.ArrayDeque[V]]

      for (elem <- values) {
        val key = f(elem)
        val bldr =
          m.get(key) match {
            case Some(x) => x
            case None =>
              val x = mutable.ArrayDeque[V]()
              m = m + (key -> x) // -------------> seems like '+=' doesn't append? TODO: t210115142355 - investigate
              x }
        bldr += g(elem) }

      val b = immutable.ListMap.newBuilder[K, Seq[V]]
      for ((k, v) <- m)
        b += ((k, v.toList))

      b.result }

  // ---------------------------------------------------------------------------
  def groupByKey[K, V](entries: Iterator[(K, V)]): immutable.Map[K, Seq[V]] = {
      val m = mutable.Map.empty[K, mutable.ArrayDeque[V]]

      for (elem <- entries) {
        val key = elem._1
        val bldr = m.getOrElseUpdate(key, mutable.ArrayDeque[V]())
        bldr += elem._2 }

      val b = immutable.Map.newBuilder[K, Seq[V]]
      for ((k, v) <- m)
        b += ((k, v.toList))

      b.result }

    // ---------------------------------------------------------------------------
    def groupByKeyWithListMap[K, V](entries: IterableOnce[(K, V)]): ListMap[K, Seq[V]] = {
      var m = immutable.ListMap.empty[K, mutable.ArrayDeque[V]]

      entries
        .iterator
        .foreach { elem =>
          val key = elem._1
          val bldr =
            m.get(key) match { //m.getOrElseUpdate(key, mutable.ArrayDeque[V]())
              case Some(x) => x
              case None =>
                val x = mutable.ArrayDeque[V]()
                m = m + (key -> x) // -------------> seems like '+=' doesn't append? TODO: t210115142355 - investigate
                x }
          bldr += elem._2 }

      val b = immutable.ListMap.newBuilder[K, Seq[V]]
      for ((k, v) <- m)
        b += ((k, v.toList))

      b.result }

    // ---------------------------------------------------------------------------
    def groupByKeyWithListMap[K, V](entries: CloseabledIterator[(K, V)]): ListMap[K, Seq[V]] = {
        var m = immutable.ListMap.empty[K, mutable.ArrayDeque[V]]

        entries
          .foreach { elem =>
            val key = elem._1
            val bldr =
              m.get(key) match { //m.getOrElseUpdate(key, mutable.ArrayDeque[V]())
                case Some(x) => x
                case None =>
                  val x = mutable.ArrayDeque[V]()
                  m = m + (key -> x) // -------------> seems like '+=' doesn't append? TODO: t210115142355 - investigate
                  x }
            bldr += elem._2 }

        val b = immutable.ListMap.newBuilder[K, Seq[V]]
        for ((k, v) <- m)
          b += ((k, v.toList))

        b.result }

    // ---------------------------------------------------------------------------
    def groupByKeyWithTreeMap[K, V](entries: Iterator[(K, V)])(implicit ord: Ordering[K]): immutable.TreeMap[K, Seq[V]] = {
      val m = mutable.TreeMap.empty[K, mutable.ArrayDeque[V]]

      for (elem <- entries) {
        val key = elem._1
        val bldr = m.getOrElseUpdate(key, mutable.ArrayDeque[V]())
        bldr += elem._2 }

      val b = immutable.TreeMap.newBuilder[K, Seq[V]]
      for ((k, v) <- m)
        b += ((k, v.toList))

      b.result }

    // ===========================================================================
    def groupByAdjacency[A, B](coll: Seq[A])(f: A => B): Seq[(B, Seq[A])] = {
      var previous: B = null.asInstanceOf[B]

      val mut1 = mutable.ArrayDeque[A]()
      val mut2 = mutable.ArrayDeque[(B, Seq[A])]()

      coll
        .map { value =>
          if (previous != null &&
              previous != f(value)) {
            val pair = (previous,  mut1.toList)
            mut2 += pair
            mut1.clear() }

          mut1 += value
          previous = f(value) }

      val pair = (previous, mut1.toList)
      mut2 += pair
      mut1.clear()

      val res = mut2.toList
      mut2.clear()

      res } }

// ===========================================================================
