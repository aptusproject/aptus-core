package aptus
package aptutils

// ===========================================================================
object SeqUtils {

  def distinct[A](a: Seq[A], f: (Boolean, Any) => Unit): Seq[A] = {
    f(
      a.isDistinct,
      (a.size, a.duplicates.#@@.sectionAllOff(2)))

    a
  }

  // ---------------------------------------------------------------------------
  def requireDistinctBy[A, B](coll: Seq[A])(f: A => B): Seq[A] = {
    val x = coll.map(f)

    require(
        x.isDistinct,
        x.duplicates.#@@.sectionAllOff(2))

    coll
  }
  
  // ===========================================================================
  // note Ordering is invariant in T
  private[aptus] def iterableOrdering[T : Ordering]: Ordering[Iterable[T]] = new Ordering[Iterable[T]] { // 210827123947
      override def compare(x: Iterable[T], y: Iterable[T]): Int = {            
        val sizeComparison = x.size.compare(y.size)
  
        if (sizeComparison != 0) sizeComparison
        else {
          val ordering = implicitly[Ordering[T]]
  
          x .zip(y)
            .map { case (l, r) => ordering.compare(l, r) }
            .find(_ != 0)
            .getOrElse(0)
        }
      }
    }

    // ---------------------------------------------------------------------------
    private[aptus] def seqOrdering[T : Ordering]: Ordering[Seq[T]] =
      new Ordering[Seq[T]] {
        override def compare(x: Seq[T], y: Seq[T]): Int = 
          iterableOrdering.compare(x, y) }
    
    // ---------------------------------------------------------------------------
    private[aptus] def listOrdering[T : Ordering]: Ordering[List[T]] = new Ordering[List[T]] {
      override def compare(x: List[T], y: List[T]): Int = 
          iterableOrdering.compare(x, y) }

    // ---------------------------------------------------------------------------
    private[aptus] def arrayOrdering[T : Ordering]: Ordering[Array[T]] = new Ordering[Array[T]] {
      override def compare(x: Array[T], y: Array[T]): Int =  
          iterableOrdering.compare(x, y) }
         
}

// ===========================================================================
