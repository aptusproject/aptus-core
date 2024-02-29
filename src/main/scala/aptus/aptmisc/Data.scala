package aptus
package aptmisc

import util.chaining._
import scala.collection.immutable.{ListMap, TreeMap}
import aptutils.MapUtils
import aptutils.joining.FluencyDomain._

// ===========================================================================
final class Data[A] private[aptus] (coll: Seq[A]) {

  def groupByWithListMap[K, V](f: A => K)(implicit ev: A =:= V): ListMap[K, Seq[V]] = groupByWithListMap(f, v => v)
  def groupByWithListMap[K, V](f: A => K, g: A => V)           : ListMap[K, Seq[V]] = aptutils.MapUtils.groupByWithListMap(f, g)(coll.iterator)

  // ---------------------------------------------------------------------------
  def groupByKey           [K, V](implicit ev: A <:< (K, V)                  ):     Map[K, Seq[V]] = MapUtils.groupByKey           (coll.iterator.asInstanceOf[Iterator[(K, V)]])
  def groupByKeyWithListMap[K, V](implicit ev: A <:< (K, V)                  ): ListMap[K, Seq[V]] = MapUtils.groupByKeyWithListMap(coll.iterator.asInstanceOf[Iterator[(K, V)]])
  def groupByKeyWithTreeMap[K, V](implicit ev: A <:< (K, V), ord: Ordering[K]): TreeMap[K, Seq[V]] = MapUtils.groupByKeyWithTreeMap(coll.iterator.asInstanceOf[Iterator[(K, V)]])

  // ---------------------------------------------------------------------------
  def groupByAdjacency[B](f: A => B): Seq[(B, Seq[A])] = MapUtils.groupByAdjacency(coll)(f)

  // ===========================================================================
  def countBySelf: List[(A, Int)] = coll.groupBy(identity).view.map { x => x._1 -> x._2.size }.toList.sortBy(-_._2) // TODO: t211004120452 - more efficient version

  /** maintains order (if presorted for instance) */
  def countBySelfWithOrder: List[(A, Int)] = coll.map(x => x -> x).iterator.pipe(MapUtils.groupByKeyWithListMap).toList.map { x => x._1 -> x._2.size }.sortBy(-_._2) // TODO: t211004120452 - more efficient version

  // TODO: t220929165238 - more efficient version (see groupByKey)
  def countByKey[K, V](implicit ev: A <:< (K, V)): Seq[(Count, K)] = groupByKey.map { case (k, v) => v.size -> k }.toList.sortBy(-_._1)

  // ===========================================================================
  def innerJoin[B](that: Seq[B]): InnerJoin[A, B] = new InnerJoin[A, B](coll, that)
  def  leftJoin[B](that: Seq[B]):  LeftJoin[A, B] = new  LeftJoin[A, B](coll, that)
  def rightJoin[B](that: Seq[B]): RightJoin[A, B] = new RightJoin[A, B](coll, that)
  def outerJoin[B](that: Seq[B]): OuterJoin[A, B] = new OuterJoin[A, B](coll, that)

  // ---------------------------------------------------------------------------
  def innerCoGroup[B](that: Seq[B]): InnerCoGroup[A, B] = new InnerCoGroup[A, B](coll, that)
  def  leftCoGroup[B](that: Seq[B]):  LeftCoGroup[A, B] = new  LeftCoGroup[A, B](coll, that)
  def rightCoGroup[B](that: Seq[B]): RightCoGroup[A, B] = new RightCoGroup[A, B](coll, that)
  def outerCoGroup[B](that: Seq[B]): OuterCoGroup[A, B] = new OuterCoGroup[A, B](coll, that)

  // ===========================================================================
  /** no prior grouping of key/values */ def pivot          [K, V](implicit ev: A <:< (K,     V )): ListMap[V, Seq[K]] = aptutils.PivotingUtils.pivot          (coll.asInstanceOf[Seq[(K, V)]])
  /** no prior grouping of key/values */ def pivotAndFlatten[K, V](implicit ev: A <:< (K,     V )):    Seq[(V,     K)] = aptutils.PivotingUtils.pivotAndFlatten(coll.asInstanceOf[Seq[(K, V)]])
  /** pre-grouped by keys */             def pivotPreGrouped[K, V](implicit ev: A <:< (K, Seq[V])): ListMap[V, Seq[K]] = aptutils.PivotingUtils.pivotPreGrouped(coll.asInstanceOf[Seq[(K, Seq[V])]]) }

// ===========================================================================