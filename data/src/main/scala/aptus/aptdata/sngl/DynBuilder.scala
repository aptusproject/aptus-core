package aptus
package aptdata
package sngl

// ===========================================================================
private[aptdata] object DynBuilder { // 241120103136

  /** checks for:
   * - a241119123810 - keys uniqueness
   * - a241119123811 - no None/empty collection (filtered out)
   * - a241119123645 - no nested Iterator/Dynz */
  def build(values: Iterable[Entry], byPassUniquenessCheck: Boolean = false): Dyn = {
    if (values.isEmpty) Dyn.Empty
    else {
      if (!byPassUniquenessCheck) { // optimization
        val      allKeys: Seq[Key] = values.map(_.key).toList
        val distinctKeys: Seq[Key] = allKeys.distinct

        if (allKeys.size != distinctKeys.size) {
          Error.DuplicateKeys(allKeys, distinctKeys).thro } }

      // ---------------------------------------------------------------------------
      values
        .flatMap { entry =>
          entry
            .valew
            .pipe(normalizeValue)
            .map(Valew.build)
            .map(Entry.buildw(entry.key, _)) }
        .toList
        .pipe(Dyn._build) } }

  // ===========================================================================
  // not meant to be 100% idiot proof
  private def normalizeValue(value: NakedValue): Option[NakedValue] = value match { // 241122121139
case y: domain.NumberLike[_] => ??? // TODO: t241126131228

    // typically for the key that may have just been modified (and not for the others)
    case Valew(u) => normalizeValue(u)

    // ---------------------------------------------------------------------------
    case iter: Iterable[_] =>
      if (iter.isEmpty) None
      else              Some(normalizeIterable(iter))

    // ---------------------------------------------------------------------------
    // Option is IterableOnce but not Iterable (see a241119153446)
    case opt: Option[_] =>
      opt match {
        // a241120103837 - automatically flattens optional values
        case None           => None
        case Some(Valew(_)) => ???//Some(x) - happens?
        case Some(      x ) => normalizeValue(x) /* TODO: happens? -> add to test */ }

    // ===========================================================================
    case Dyn .Empty => None
    case Dyns.Empty => None

    // ---------------------------------------------------------------------------
    case x: Dynz        => Error.NoNestedIterators(()).thro
    case x: Iterator[_] => Error.NoNestedIterators(()).thro

    // TODO: t241203213714 - turn maps to Dyn automatically?

    // ===========================================================================
    case x => Some(x) }

  // ===========================================================================
  private def normalizeIterable(iter: Iterable[_]): NakedValue =  {
    var onlyDyns = true

    iter
      .map {
        case Valew(x: Dyn) => ??? // TODO: confirm doesn't happen + rational
        case       x: Dyn  => x
        case Valew(x )     => onlyDyns = false; x
        case       x       => onlyDyns = false; x }
      .pipe { (values: Iterable[_]) =>
        // a241120102934 - automatically converts Iterable[Dyn] to Dyns
        if (!onlyDyns) values
        else           values.asInstanceOf[Iterable[Dyn]].pipe(mult.list.Dyns.build) } } }

// ===========================================================================
