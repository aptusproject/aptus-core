package aptus
package aptutils

import util.chaining._
import aptus.aptutils.MapUtils.groupByKeyWithListMap

// ===========================================================================
object PivotingUtils {

  /** no prior grouping of key/values */
  def pivot[K, V](input: Seq[(K, V)]): ListMap[V, Seq[K]] =
      input
        .view
        .map(_.swap)
        .iterator
        .pipe(groupByKeyWithListMap)

    // ---------------------------------------------------------------------------
    /** no prior grouping of key/values */
    def pivotAndFlatten[K, V](input: Seq[(K, V)]): Seq[(V, K)] =
      pivot[K, V](input)
        .view
        .flatMap { x => x._2.map { x._1 -> _ } }
        .toList

    // ===========================================================================
    def pivotPreGrouped[K, V](input: Seq[(K, Seq[V])]): ListMap[V, Seq[K]] =
        input
          .view
          .flatMap { case (k, vs) =>
            vs.map(_ -> k) }
          .iterator
          .pipe(groupByKeyWithListMap)

      // ---------------------------------------------------------------------------
      def pivotPreGrouped[K, V](input: ListMap[K, Seq[V]]): ListMap[V, Seq[K]] = // TODO: confirm can reuse Map's below?
        input
          .view
          .flatMap { case (k, vs) =>
            vs.map(_ -> k) }
          .iterator
          .pipe(groupByKeyWithListMap)

      // ---------------------------------------------------------------------------
      def pivotPreGrouped[K, V](input: Map[K, Seq[V]]): ListMap[V, Seq[K]] =
        input
          .view
          .flatMap { case (k, vs) =>
            vs.map(_ -> k) }
          .iterator
          .pipe(groupByKeyWithListMap) }

// ===========================================================================
