package aptus
package aptutils
package joining

import aptus.Seq_

// ===========================================================================
object CoGroupingUtils {

  def innerCoGroup[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (Seq[L], Seq[R]))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.data.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.data.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    leftMap
      .flatMap { case (key, leftValues) =>
        riteMap
          .get(key)
          .map { riteValues =>
            key -> (leftValues, riteValues) } }
      .toList }

  // ===========================================================================
  def outerCoGroup[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (Option[Nes[L]], Option[Nes[R]]))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.data.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.data.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    val intersection: Seq[(K, (Option[Nes[L]], Option[Nes[R]]))] =
      leftMap
        .flatMap { case (key, leftValues) =>
          riteMap
            .get(key)
            .map { riteValues =>
              key -> (Some(leftValues), Some(riteValues)) } }
        .toList

    // ---------------------------------------------------------------------------
    val leftOnly: Seq[(K, (Option[Nes[L]], Option[Nes[R]]))] =
      sideOnly[K, L](leftMap)(riteMap.keySet)
        .map { case (k, ls) => k -> (Some(ls), None) }
        .toList

    // ---------------------------------------------------------------------------
    val riteOnly: Seq[(K, (Option[Nes[L]], Option[Nes[R]]))] =
      sideOnly[K, R](riteMap)(leftMap.keySet)
        .map { case (k, rs) => k -> (None, Some(rs)) }
        .toList

    // ---------------------------------------------------------------------------
    leftOnly ++ intersection ++ riteOnly }

  // ===========================================================================
  def leftCoGroup[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (Seq[L], Option[Nes[R]]))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.data.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.data.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    val intersection: Seq[(K, (Seq[L], Option[Nes[R]]))] =
      leftMap
        .flatMap { case (key, leftValues) =>
          riteMap
            .get(key)
            .map { riteValues =>
              key -> (leftValues, Some(riteValues)) } }
        .toList

    // ---------------------------------------------------------------------------
    val leftOnly: Seq[(K, (Seq[L], Option[Nes[R]]))] =
      sideOnly[K, L](leftMap)(riteMap.keySet)
        .map { case (k, ls) => k -> (ls, None) }
        .toList

    // ---------------------------------------------------------------------------
    leftOnly ++ intersection }

  // ===========================================================================
  def rightCoGroup[L, R, K]
        (leftData: Seq[L], riteData: Seq[R])
        (leftKey : L => K, riteKey : R => K)
      : Seq[(K, (Option[Nes[L]], Seq[R]))] = {
    val leftMap: ListMap[K, Seq[L]] = leftData.data.groupByWithListMap(leftKey)
    val riteMap: ListMap[K, Seq[R]] = riteData.data.groupByWithListMap(riteKey)

    // ---------------------------------------------------------------------------
    val intersection: Seq[(K, (Option[Nes[L]], Seq[R]))] =
      leftMap
        .flatMap { case (key, leftValues) =>
          riteMap
            .get(key)
            .map { riteValues =>
              key -> (Some(leftValues), riteValues) } }
        .toList

    // ---------------------------------------------------------------------------
    val riteOnly: Seq[(K, (Option[Nes[L]], Seq[R]))] =
      sideOnly[K, R](riteMap)(leftMap.keySet)
        .map { case (k, rs) => k -> (None, rs) }
        .toList

    // ---------------------------------------------------------------------------
    intersection ++ riteOnly } }

// ===========================================================================
