package aptus
package aptdata
package ops
package mult

// ===========================================================================
trait MultipleOpsImpl[Mult <: HasValuesIterator[Mult]]
        extends MultipleOpsTrait[Mult]
        with    common.CommonFormatDebug {
    self: HasIteratorConstructor[Mult]
       with HasValuesIterator   [Mult]
       with common.HasIdent[Mult] =>

  // ---------------------------------------------------------------------------
  override final def formatDebug: DebugString =
    valuesIterator.map(_.formatDebug).consumeAll().pipe { x => x.mkString("[", ",", "]").prepend(x.size.str.colon) }

  // ---------------------------------------------------------------------------
  override final def union  (that : Mult): Mult = (this.valuesIterator                  union that.valuesIterator).pipe(const)
  override final def prepend(value: Sngl): Mult = (CloseabledIterator.fromValues(value) union this.valuesIterator).pipe(const)

  // ---------------------------------------------------------------------------
//TODO: ensure n is valid in the context here
  override final def take(n: Int): Mult = valuesIterator.take(n).pipe(const) /* TODO: t241128141346 - close if closeabled iterator... */
  override final def drop(n: Int): Mult = valuesIterator.drop(n).pipe(const) /* TODO: t241128141346 - close if closeabled iterator... */
//TODO: t241203162005 - more: partition, scan, splitAt, ...

  // ===========================================================================
  override final def filter   (p: Sngl => Boolean): Mult = valuesIterator.filter(p).pipe(const)

  // ---------------------------------------------------------------------------
  override final def headOption: Option[Sngl] = valuesIterator.in.someIf(_.hasNext).map(_.next())
  override final def lastOption: Option[Sngl] = valuesIterator.pipe(MultipleUtils.lastOption)

  // ===========================================================================
  override final def forceOne: Sngl = valuesIterator.pipe(MultipleUtils.forceOne) // TODO: t241203161832 - split as .force.{one, distinct, ...}

  // ---------------------------------------------------------------------------
  override final def writeKey(key: Key, to: FilePath): FilePath =
    valuesIterator.map(_.text_(key).getOrElse("")).writeGzipLines(to)

  // ===========================================================================
  // borrowed from gallia:

                          override final def forceDoubleLookup[A, B](f1: Sngl => A, f2: Sngl => B): CoLookup[A, B] = {
                              CoLookup.forceDoubleLookup(valuesIterator)(f1, f2)
                                .inspect(_.mapAll(_.size, _.size).str.indentLine)
                                .pipe(x => new CoLookup[A, B](x._1, x._2)) }

                            // ---------------------------------------------------------------------------
                            override final def forceLookup[A, B](f1: Sngl => A, f2: Sngl => B): Map[A, B] = {
                              CoLookup
                                .forceLookup1(valuesIterator)(f1, f2)
                                .inspect(_.size.str.indentLine) }

                            // ---------------------------------------------------------------------------
                            override final def forceLookup2[A, B](f1: Sngl => A, f2: Sngl => B): Map[A, Seq[B]] = {
                              CoLookup
                                .forceLookup2(valuesIterator)(f1, f2)
                                .inspect(_.size.str.indentLine) }
}

// ===========================================================================
