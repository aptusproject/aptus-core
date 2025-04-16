package aptus
package aptutils
package joining

// ===========================================================================
object FluencyDomain {

  final class InnerJoin[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
      def on[K](l: T => K, r: U => K): Seq[(K, (T, U))] =
        JoiningUtils.innerJoin[T, U, K](coll, that)(l, r) }

    // ---------------------------------------------------------------------------
    final class OuterJoin[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
      def on[K](l: T => K, r: U => K): Seq[(K, (Option[T], Option[U]))] =
        JoiningUtils.outerJoin[T, U, K](coll, that)(l, r) }

    // ---------------------------------------------------------------------------
    final class LeftJoin[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
      def on[K](l: T => K, r: U => K): Seq[(K, (T, Option[U]))] =
        JoiningUtils.leftJoin[T, U, K](coll, that)(l, r) }

    // ---------------------------------------------------------------------------
    final class RightJoin[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
      def on[K](l: T => K, r: U => K): Seq[(K, (Option[T], U))] =
        JoiningUtils.rightJoin[T, U, K](coll, that)(l, r) }

  // ===========================================================================
  final class InnerCoGroup[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
      def on[K](l: T => K, r: U => K): Seq[(K, (Seq[T], Seq[U]))] =
        CoGroupingUtils.innerCoGroup[T, U, K](coll, that)(l, r) }

    // ---------------------------------------------------------------------------
    final class OuterCoGroup[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
        def on[K](l: T => K, r: U => K): Seq[(K, (Option[Nes[T]], Option[Nes[U]]))] =
          CoGroupingUtils.outerCoGroup[T, U, K](coll, that)(l, r) }

    // ---------------------------------------------------------------------------
    final class LeftCoGroup[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
        def on[K](l: T => K, r: U => K): Seq[(K, (Seq[T], Option[Nes[U]]))] =
          CoGroupingUtils.leftCoGroup[T, U, K](coll, that)(l, r) }

    // ---------------------------------------------------------------------------
    final class RightCoGroup[T, U] private[aptus] (coll: Seq[T], that: Seq[U]) {
        def on[K](l: T => K, r: U => K): Seq[(K, (Option[Nes[T]], Seq[U]))] =
          CoGroupingUtils.rightCoGroup[T, U, K](coll, that)(l, r) } }

// ===========================================================================
