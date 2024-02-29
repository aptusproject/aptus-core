package aptus
package aptutils
package joining

import aptus.Seq_

// ===========================================================================
object JoiningUtils {

  def innerJoin[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (L, R))] = {
    val _left: ListMap[K, Seq[L]] = leftData.groupByWithListMap(leftKey)
    val _rite: ListMap[K, Seq[R]] = riteData.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    intersection(_left, _rite) }

  // ===========================================================================
  def outerJoin[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (Option[L], Option[R]))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    val intersect: Seq[(K, (Option[L], Option[R]))] =
      intersection(leftMap, riteMap)
        .map { case (k, (l, r)) => (k, (Some(l), Some(r))) }

    // ---------------------------------------------------------------------------
    val leftOnly: Seq[(K, (Option[L], Option[R]))] =
      sideOnly[K, L](leftMap)(riteMap.keySet)
        .flatMap { case (k, ls) =>
          ls.map { l => k -> (Some(l), None) } }
        .toList

    // ---------------------------------------------------------------------------
    val riteOnly: Seq[(K, (Option[L], Option[R]))] =
      sideOnly[K, R](riteMap)(leftMap.keySet)
        .flatMap { case (k, rs) =>
          rs.map { r => k -> (None, Some(r)) } }
        .toList

    // ---------------------------------------------------------------------------
    leftOnly ++ intersect ++ riteOnly }

  // ===========================================================================
  def leftJoin[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (L, Option[R]))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    val leftOnly: Seq[(K, (L, Option[R]))] =
      sideOnly[K, L](leftMap)(riteMap.keySet)
        .flatMap { case (k, ls) =>
          ls.map { l => k -> (l, None) } }
        .toList

    // ---------------------------------------------------------------------------
    val intersect: Seq[(K, (L, Option[R]))] =
      intersection(leftMap, riteMap)
        .map { case (k, (l, r)) => (k, (l, Some(r))) }

    // ---------------------------------------------------------------------------
    leftOnly ++ intersect }

  // ===========================================================================
  def rightJoin[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (Option[L], R))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    val intersect: Seq[(K, (Option[L], R))] =
      intersection(leftMap, riteMap)
        .map { case (k, (l, r)) => (k, (Some(l), r)) }
    
    // ---------------------------------------------------------------------------
    val riteOnly: Seq[(K, (Option[L], R))] =
      sideOnly[K, R](riteMap)(leftMap.keySet)
        .flatMap { case (k, rs) =>
          rs.map { r => k -> (None, r) } }
        .toList

    // ---------------------------------------------------------------------------
    intersect ++ riteOnly } }

// ===========================================================================
