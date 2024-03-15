package aptustesting

import aptus._
import utest._

// ===========================================================================
object SeqTests extends TestSuite {

  private val a = "a"
  private val b = "b"
  private val c = "c"

  private val x = "x"
  private val y = "y"
  private val z = "z"

  // ---------------------------------------------------------------------------
  val tests = Tests {

    test(compare(
      Seq(
          Seq("d", "e", "f"),
          Seq("g", "h", "i"),
          Seq( a ,  b ,  c ))
        .sorted(aptus.seqOrdering[String]),
      Seq(
          Seq( a ,  b ,  c ),
          Seq("d", "e", "f"),
          Seq("g", "h", "i") )))

    // ---------------------------------------------------------------------------
    test(compare(
      Seq(a, b, a, c).countBySelf,
      // note: result is also sorted by descending count
      Seq((a, 2), (b, 1), (c, 1))))

    // ===========================================================================
    test(compare(
        Seq(a -> x, b -> y, a -> z, c -> x).groupByKey /* result order not guaranteed */.toList.sortBy(_.toString),
        Seq(a -> List(x, z), b -> List(y), c -> List(x))))

      // ---------------------------------------------------------------------------
      test(compare(
        Seq(a -> x, b -> y, a -> z, c -> x).groupByKeyWithListMap, // so we maintain order
        aptus.listMap(a -> List(x, z), b -> List(y), c -> List(x)) ))

    // ===========================================================================
    test("if empty/if one") {
      test(failState(Seq.empty[Int].ifEmptyThenError("is empty"), "is empty"))
      test(noop     (Seq(3)        .ifEmptyThenError("is empty")))

      // ---------------------------------------------------------------------------
      test(compare  (Seq.empty[Int].ifOneElement(_ + 1, _.size), 0))
      test(compare  (Seq(3)        .ifOneElement(_ + 1, _.size), 4))
      test(compare  (Seq(3, 4)     .ifOneElement(_ + 1, _.size), 2))

      // ---------------------------------------------------------------------------
      test(isNone  (Seq.empty[Int].ifOneElementOpt))
      test(isSome  (Seq(3)        .ifOneElementOpt, 3))
      test(isNone  (Seq(3, 4)     .ifOneElementOpt))

      // ---------------------------------------------------------------------------
      test(failState(Seq.empty[Int].ifOneElementOrElse(x => s"not one element: ${x.@@}"), "0, not one element: []"))
      test(noop     (Seq(3)        .ifOneElementOrElse(x => s"not one element: ${x.@@}")))
      test(failState(Seq(3, 4)     .ifOneElementOrElse(x => s"not one element: ${x.@@}"), "2, not one element: [3, 4]")) } } }

// ===========================================================================
