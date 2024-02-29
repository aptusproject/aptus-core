package aptus

import aptus.aptutils._

// ===========================================================================
trait AptusTopLevel {
  /* for extends AnyVals, see https://stackoverflow.com/questions/14929422/should-implicit-classes-always-extend-anyval */

  def illegalState        (x: Any*): Nothing = { throw new IllegalStateException        (x.mkString(", ")) }
  def illegalArgument     (x: Any*): Nothing = { throw new IllegalArgumentException     (x.mkString(", ")) }
  def unsupportedOperation(x: Any*): Nothing = { throw new UnsupportedOperationException(x.mkString(", ")) }

  // ---------------------------------------------------------------------------
  def iterableOrdering[T : Ordering]: Ordering[Iterable[T]] = SeqUtils.iterableOrdering[T] // note: Ordering is invariant
  def   optionOrdering[T : Ordering]: Ordering[Option  [T]] = SeqUtils.  optionOrdering[T]

  //def tuple2Ordering[T1 : Ordering, T2: Ordering]: Ordering[Tuple2  [T1, T2]] = ???

  def   seqOrdering[T : Ordering]: Ordering[Seq  [T]] = SeqUtils.  seqOrdering[T]
  def  listOrdering[T : Ordering]: Ordering[List [T]] = SeqUtils. listOrdering[T]
  def arrayOrdering[T : Ordering]: Ordering[Array[T]] = SeqUtils.arrayOrdering[T]

  def listListOrdering[T : Ordering]: Ordering[List[List[T]]] = SeqUtils.listListOrdering[T]

  // ---------------------------------------------------------------------------
  def zip[T1, T2, T3]        (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3])                                  : Iterable[(T1, T2, T3)]         = a.zip(b).zip(c)              .map { case   ((a, b), c)         => (a, b, c) }
  def zip[T1, T2, T3, T4]    (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4])                 : Iterable[(T1, T2, T3, T4)]     = a.zip(b).zip(c).zip(d)       .map { case  (((a, b), c), d)     => (a, b, c, d) }
  def zip[T1, T2, T3, T4, T5](a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4], e: Iterable[T5]): Iterable[(T1, T2, T3, T4, T5)] = a.zip(b).zip(c).zip(d).zip(e).map { case ((((a, b), c), d), e) => (a, b, c, d, e) }
  def zip[T1, T2](a: Iterable[T1], b: Iterable[T2]): Iterable[(T1, T2)] = a.zip(b) /* for good measure, should favor: a.zip(b) */ }

// ===========================================================================
