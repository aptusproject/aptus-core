package aptus
package aptdata
package ops
package mult

// ===========================================================================
private object MultipleUtils {

  // ---------------------------------------------------------------------------
  def lastOption(valuesIterator: Sngls): Option[Sngl] = {
    var last: Option[Sngl] = None

    while (valuesIterator.hasNext) {
      last = Some(valuesIterator.next()) }
    last }

  // ---------------------------------------------------------------------------
  def forceOne(valuesIterator: Sngls): Sngl =
    valuesIterator
      .in.someIf(_.hasNext)
      .map { iter =>
        iter
          .next()
          .in.noneIf { _ => iter.hasNext }
          .getOrElse(Error.MoreThanOneElement(()).thro) }
      .getOrElse(    Error.NoElements(())        .thro) }

// ===========================================================================
