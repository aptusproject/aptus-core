package aptus
package experimental
package dyn
package ops
package mult

import common.HasDataEntityErrorFormatter

// ===========================================================================
trait MultipleTrait[Mult <: HasIteratorRelated[Mult]]
    extends HasEndoFlatMap[Mult]
       with HasExoMap     [Mult]
       with HasDataEntityErrorFormatter       [Mult]
       with MultipleOpsImpl      [Mult] /* eg union, forceOne, ... */
       with MultipleWrappedOps   [Mult] /* endoMaps (wrapping) */
       with MultipleGroupingTrait[Mult] /* eg abstract groupBy(x) */ {
    self: HasAllMultiple[Mult] =>
  override final protected[dyn] def deef: DataEntityErrorFormatter = valuesIterator.pipe(DataEntityErrorFormatter.fromIterator)

  override final def endoFlatMap   (f: Sngl => Iterable[Sngl]): Mult        = valuesIterator.flatMap(f).pipe(const)
  override final def     endoMap   (f: Sngl => Sngl)          : Mult        = valuesIterator.    map(f).pipe(const)
  override final def      exoMap[T](f: Sngl => T)             : IteratoR[T] = valuesIterator.    map(f)
  /*override */final def      exoFlatMap[T](f: Sngl => Iterable[T])             : IteratoR[T] = valuesIterator.    flatMap(f) }

// ===========================================================================
