package aptus
package aptmisc

import util.chaining._

// ===========================================================================
/*
  not extending Seq[$Item] because for instance, then filter(p) returns Seq[$Item] instead of $Items
      and can't change return type on override (and don't want to call it something else
      overridign the few important ones isn't that big a deal
*/
trait Items[$Items <: Items[_, _], $Item] // TODO: t241202115349 - splitup + own package
    extends HasConst[Seq[$Item], $Items] {
  type Self = $Items
  type Item = $Item // nicer, if used by client

  // ---------------------------------------------------------------------------
  def values: Seq[$Item]

  // ---------------------------------------------------------------------------
  def size: Int = values.size

  def  isEmpty: Boolean = size == 0
  def nonEmpty: Boolean = size >  0

  def contains(value: $Item): Boolean = values.contains(value)

  def force: Force[$Item] = new Force[$Item](values)

  // ---------------------------------------------------------------------------
  @inline def map    [T](f: $Item =>          T ): Seq[T] = exoMap(f)
  @inline def flatMap[T](f: $Item => Iterable[T]): Seq[T] = exoFlatMap(f)

  def zipWithIndex: Seq[($Item, Index)] = values.zipWithIndex

  def sliding (size: Int): Iterator[Seq[$Item]] = values.sliding(size, 1)
  def grouping(size: Int): Iterator[Seq[$Item]] = values.grouped(size)

  // ---------------------------------------------------------------------------
  def foreach[U](f: $Item => U) = values.foreach(f)

  // ---------------------------------------------------------------------------
  def exoMap    [T](f: $Item =>          T ): Seq[T] = values.    map(f)
  def exoFlatMap[T](f: $Item => Iterable[T]): Seq[T] = values.flatMap(f)

  def endoMap    (f: $Item =>          $Item ): $Items = values.    map(f).pipe(const)
  def endoFlatMap(f: $Item => Iterable[$Item]): $Items = values.flatMap(f).pipe(const)

  // ---------------------------------------------------------------------------
  def mapUnderlyingValues(f: Seq[$Item] => Seq[$Item]): $Items = const(f(values))

  // ===========================================================================
  @deprecated("favor new name 'findValue'")
  def find      (p: $Item => Boolean): Option[$Item] = values.find(p)
  def findValue (p: $Item => Boolean): Option[$Item] = values.find(p)
  def forceValue(p: $Item => Boolean):        $Item  = values.find(p).get

  // ---------------------------------------------------------------------------
  def filter   (p: $Item => Boolean): $Items = values.filter   (p).pipe(const)
  def filterNot(p: $Item => Boolean): $Items = values.filterNot(p).pipe(const)

    // ---------------------------------------------------------------------------
    def filterBy   [B](f: $Item => B)(p: B => Boolean): $Items = filter { x =>  p(f(x)) }
    def filterByNot[B](f: $Item => B)(p: B => Boolean): $Items = filter { x => !p(f(x)) }

    def filterContainsAnyOf(set: Set[$Item]): $Items = filter { x => set.contains(x) }
    def filterContainsAnyOf(seq: Seq[$Item]): $Items = filter { x => seq.contains(x) }

    def filterNotContainsAnyOf(set: Set[$Item]): $Items = filter { x => !set.contains(x) }
    def filterNotContainsAnyOf(seq: Seq[$Item]): $Items = filter { x => !seq.contains(x) }

  // ===========================================================================
  def tail: $Items = values.tail.pipe(const)
  def init: $Items = values.init.pipe(const)

  def take(n: Int): $Items = values.take(n).pipe(const)
  def drop(n: Int): $Items = values.drop(n).pipe(const)

  def takeRight(n: Int): $Items = values.takeRight(n).pipe(const)
  def dropRight(n: Int): $Items = values.dropRight(n).pipe(const)

  def takeWhile(p: $Item => Boolean): $Items = values.takeWhile(p).pipe(const)
  def dropWhile(p: $Item => Boolean): $Items = values.dropWhile(p).pipe(const)

  def span     (p: $Item => Boolean): ($Items, $Items) = { val (x, y) = values.span     (p); const(x) -> const(y) } // (takeWhile(p), dropWhile(p))
  def partition(p: $Item => Boolean): ($Items, $Items) = { val (x, y) = values.partition(p); const(x) -> const(y) }

  def takeUntil(p: $Item => Boolean): $Items = values.span(p).pipe { x => x._1 :+ x._2.head }.pipe(const)

  def diff     (that: Self): Self = this.values.diff     (that.values).pipe(const)
  def intersect(that: Self): Self = this.values.intersect(that.values).pipe(const)

  def sorted(implicit ord: Ordering[$Item]): $Items = values.sorted   .pipe(const)
  def sortBy[T : Ordering](f: $Item => T)  : $Items = values.sortBy(f).pipe(const)
  def distinct                             : $Items = values.distinct .pipe(const)

  // ---------------------------------------------------------------------------
  // TODO: t241129122610 - rename + deprecate to mergeItems, appendItem, etc to avoid name conflicts with client code

  def merge(that: $Items): $Items = const(  (this.values ++ that.values).asInstanceOf[Seq[$Item]] )
      def prepend(datum: $Item): $Items = const(          datum +: values)
      def  append(datum: $Item): $Items = const(values :+ datum)

      def prepend(data: Seq[$Item]): $Items = const(          data ++ values)
      def  append(data: Seq[$Item]): $Items = const(values ++ data)

    // ---------------------------------------------------------------------------
    def merge(that: $Items, f: Seq[$Item] => Seq[$Item]): $Items = const(f((this.values ++ that.values).asInstanceOf[Seq[$Item]]))

      def prepend(datum: $Item, f: Seq[$Item] => Seq[$Item]): $Items = const(f(          datum +: values))
      def  append(datum: $Item, f: Seq[$Item] => Seq[$Item]): $Items = const(f(values :+ datum))

      def prepend(data: Seq[$Item], f: Seq[$Item] => Seq[$Item]): $Items = const(f(          data ++ values))
      def  append(data: Seq[$Item], f: Seq[$Item] => Seq[$Item]): $Items = const(f(values ++ data))

  // ---------------------------------------------------------------------------
  def startsWith(that: $Items): Boolean = values.startsWith(that.values)
  def   endsWith(that: $Items): Boolean = values.  endsWith(that.values)

  def forall(p: $Item => Boolean): Boolean = values.forall(p)
  def exists(p: $Item => Boolean): Boolean = values.exists(p)

  // ---------------------------------------------------------------------------
  def head: $Item = values.head
  def last: $Item = values.last

  def headOption: Option[$Item] = values.headOption
  def lastOption: Option[$Item] = values.lastOption

  def tailOption: Option[$Items] = values.tailOption.map(const) // from aptus
  def initOption: Option[$Items] = values.initOption.map(const) // from aptus

  // ===========================================================================
  /** MUST replace exactly one        */ def replaceExactlyOneItem   (p: $Item => Boolean)(f: $Item => $Item): Self = _replace(_ == 1)(p: $Item => Boolean)(f: $Item => $Item)
  /** MAY  replace none or one        */ def replaceOneItemIfMatching(p: $Item => Boolean)(f: $Item => $Item): Self = _replace(_ <= 1)(p: $Item => Boolean)(f: $Item => $Item)
  /** MAY  replace none, one, or more */ def replaceAllMatchingItems (p: $Item => Boolean)(f: $Item => $Item): Self = endoMap { x => if (p(x)) f(x) else x }

    // ---------------------------------------------------------------------------
    private def _replace(n: Int => Boolean)(p: $Item => Boolean)(f: $Item => $Item): Self = {
      var changes = 0
      endoMap { x => if (p(x)) { changes += 1; f(x) };  else x }
        .assert(_ => n(changes), x => (changes, this, x)) }

  // ===========================================================================
  def ifEmptyThenError (msg: String): $Items = if (isEmpty) aptus.illegalState(msg) else const(values)

  // ---------------------------------------------------------------------------
  def matchingIndex  (target: Index)      : $Item = values.apply(target) // just for naming consistency
  def matchingIndices(targets: Set[Index]): $Items = // TODO: move to Seq_ and delegate
    values
      .zipWithIndex
      .filter { x => targets.contains(x._2) }
      .map(_._1)
      .pipe(const)

  // ---------------------------------------------------------------------------
  // formatting
  def formatDefaults(implicit ev: Item <:< HasFormatDefault): Seq[String] = values.map(_.formatDefault)

  def formatCompact             (implicit ev: Item <:< HasFormatDefault): String = formatDefaults.#@@
  def formatCompact(sep: String)(implicit ev: Item <:< HasFormatDefault): String = formatDefaults.mkString("[", sep, "]")
  def formatLines               (implicit ev: Item <:< HasFormatDefault): String = formatDefaults.joinln
  def formatWithSectionAndSize  (implicit ev: Item <:< HasFormatDefault): String = formatDefaults.section(values.size.str.colon) }

// ===========================================================================
trait HasConst[Data, Me] { protected def const: Data => Me } // TODO: move and rename

// ===========================================================================