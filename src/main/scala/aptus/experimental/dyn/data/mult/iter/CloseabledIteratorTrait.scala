package aptus
package experimental
package dyn
package data
package mult
package iter

// ===========================================================================
private[dyn] trait CloseabledIteratorTrait { // to help with TODO: t241205113436 - switch to CloseabledIterator
  private[dyn] type IteratoR[T] = Iterator[T] // to help with TODO: t241205113436 - switch to CloseabledIterator
  private[dyn] val  IteratoR    = Iterator

  // ---------------------------------------------------------------------------
  private[dyn] implicit class Seq__2      [T](values: Seq[T])      { def iteratoR = values.iterator }
  private[dyn] implicit class Iterator___2[T](values: IteratoR[T]) { def toIteratoR = values; def consumeAll = values.toList }

  type Bug[T] = Iterator___2[T] // not sure why

  // ---------------------------------------------------------------------------
  //  type   IteratoR[T] = CloseabledIterator[T]
  //  object IteratoR { def apply[T](value: T, more: T*): IteratoR[T] = ??? }
  //type Bug[T] = IteratoR___2[T]
  //  // ---------------------------------------------------------------------------
  //  private[dyn] implicit class Seq____[T](values: Seq[T]) {
  //    def iteratoR: IteratoR[T] = CloseabledIterator.fromUncloseable(values.iterator) }
  //
  //  private[dyn] implicit class Iterator__2(values: Iterator[Dyn]) {
  //    private[dyn] def consumeAll = values.toList
  //    private[dyn] def toIteratoR: IteratoR[Dyn] = CloseabledIterator.fromUncloseable(values)}
  //
  //  private[dyn] implicit class IteratoR___3[K, V](values: IteratoR[(K, V)]) {
  //    def groupByKeyWithListMap: List[(K, Seq[V])] = ??? }
  //
  //  private[dyn] implicit class IteratoR___2[T](values: IteratoR[T]) {
  //private[dyn] def consumeAll = values.consumeAll
  ////    def writeGzipLines(f: String)(implicit ev: T <:< String): String = ???
  //    def ++ (that: IteratoR[T]): IteratoR[T] = ???
  //    @deprecated private[dyn] def toList = values.consumeAll }
  //
  //  private[dyn] implicit class IteratoR__(values: IteratoR[Dyn]) {
  //      private[dyn] def dyns: Dyns = data.mult.list.Dyns(values.consumeAll)
  //      private[dyn] def dynz: Dynz = data.mult.iter.Dynz(values) }
}

// ===========================================================================