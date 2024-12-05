package aptus
package aptutils

// ===========================================================================
object SeqUtils {

  def distinct[A](a: Seq[A], f: (Boolean, Any) => Unit): Seq[A] = {
    f(
      a.isDistinct,
      (a.size -> a.distinct.size, a.duplicates.#@@.sectionAllOff(2)))

    a }

  // ---------------------------------------------------------------------------
  def requireDistinctBy[A, B](coll: Seq[A])(f: A => B): Seq[A] = {
    val x = coll.map(f)

    require(
        x.isDistinct,
        x.duplicates.#@@.sectionAllOff(2))

    coll }
  
  // ===========================================================================
  // note Ordering is invariant in T

  // ---------------------------------------------------------------------------
  private[aptus] def iterableOrdering[T : Ordering]: Ordering[Iterable[T]] =
      (x: Iterable[T], y: Iterable[T]) => { // 210827123947
        val sizeComparison = x.size.compare(y.size)

        if (sizeComparison != 0) sizeComparison
        else {
          val ordering = implicitly[Ordering[T]]

          x .zip(y)
            .map { case (l, r) => ordering.compare(l, r) }
            .find(_ != 0)
            .getOrElse(0) } }

    // ---------------------------------------------------------------------------
    private[aptus] def iterableOrderingIgnoreSize[T : Ordering]: Ordering[Iterable[T]] = // TODO: by?
      (x: Iterable[T], y: Iterable[T]) => {
        val ord = implicitly[Ordering[Option[T]]]

        x.map(Some.apply)
          .zipAll(y.map(Some.apply), None, None)
          .map { case (l, r) =>
            ord.compare(l, r) }
          .find(_ != 0)
          .getOrElse(0) }

  // ---------------------------------------------------------------------------
  private[aptus] def optionOrdering[T : Ordering]: Ordering[Option[T]] =
      (x: Option[T], y: Option[T]) => iterableOrdering.compare(x, y) // 210827123947

  // ---------------------------------------------------------------------------
  private[aptus] def arrayOrdering[T : Ordering]: Ordering[Array[T]] = {
      (x: Array[T], y: Array[T]) => iterableOrdering.compare(x, y) }

    // ---------------------------------------------------------------------------
    private[aptus] def arrayOrderingIgnoreSize[T : Ordering]: Ordering[Array[T]] =
      (x: Array[T], y: Array[T]) => iterableOrderingIgnoreSize.compare(x, y)

  // ---------------------------------------------------------------------------
  private[aptus] def seqOrdering[T : Ordering]: Ordering[Seq[T]] =
      (x: Seq[T], y: Seq[T]) => iterableOrdering.compare(x, y)

    // ---------------------------------------------------------------------------
    private[aptus] def seqOrderingIgnoreSize[T : Ordering]: Ordering[Seq[T]] =
      (x: Seq[T], y: Seq[T]) => iterableOrderingIgnoreSize.compare(x, y)

  // ---------------------------------------------------------------------------
  private[aptus] def listOrdering[T : Ordering]: Ordering[List[T]] =
    (x: List[T], y: List[T]) => iterableOrdering.compare(x, y)

    // ---------------------------------------------------------------------------
    private[aptus] def listOrderingIgnoreSize[T : Ordering]: Ordering[List[T]] =
      (x: List[T], y: List[T]) => iterableOrderingIgnoreSize.compare(x, y)

  // ===========================================================================
  private[aptus] def listListOrdering[T : Ordering]: Ordering[List[List[T]]] =
    (x: List[List[T]], y: List[List[T]]) => {
      val sizeComparison = x.size.compare(y.size)

      if (sizeComparison != 0) sizeComparison
      else {
        val ordering = aptus.listOrdering

        x .zip(y)
          .map { case (l, r) => ordering.compare(l, r) }
          .find(_ != 0)
          .getOrElse(0) } } }

// ===========================================================================
