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
  }
}

// ===========================================================================