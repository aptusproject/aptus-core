package aptus
package experimental
package dyn
package ops
package mult

import aptus.aptdata.static.DynDynamicToStatic
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

  // ---------------------------------------------------------------------------
  final protected def _toStatic[DC <: Product: aptreflect.lowlevel.ReflectionTypesAbstraction.WTT]: CloseabledIterator[DC] = {
    val (schema, dynamicToStatic) = DynDynamicToStatic.toStatic[DC]
    exoMap { dynamicToStatic.instantiateStaticRecursively(schema)(_).asInstanceOf[DC] } }

  // ---------------------------------------------------------------------------
  override final def endoFlatMap       (f: Sngl => Iterable[Sngl]): Mult                  = valuesIterator.flatMap(f).pipe(const)
  override final def     endoMap       (f: Sngl => Sngl)          : Mult                  = valuesIterator.    map(f).pipe(const)
  override final def      exoMap    [T](f: Sngl => T)             : CloseabledIterator[T] = valuesIterator.    map(f)
           final def      exoFlatMap[T](f: Sngl => Iterable[T])   : CloseabledIterator[T] = valuesIterator.flatMap(f) }

// ===========================================================================
