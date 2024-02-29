package aptustesting

import utest._

// ===========================================================================
object PivotingTest extends TestSuite {
  import PivotingTestData._
  import aptus.{Seq_, Map_, ListMap_}

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(compare(flattened            .data.pivot                    , expected))
    test(compare(flattened            .data.pivotAndFlatten          , expectedFlattened))
    test(compare(preProupedWithSeq    .data.pivotPreGrouped          , expected))
    test(compare(preProupedWithListMap     .pivotPreGrouped          , expected))
    test(compare(preProupedWithMap         .pivotPreGrouped.toTreeMap, expected.toTreeMap)) }

  // ===========================================================================
  private object PivotingTestData {
    val flattened: Seq[(String, Int)] =
      Seq(
        "x" -> 0,
        "x" -> 1,
        "x" -> 2,
        "y" -> 3,
        "y" -> 0)

    // ---------------------------------------------------------------------------
    val preProupedWithSeq: Seq[(String, Seq[Int])] =
        Seq(
          "x" -> Seq(0, 1, 2),
          "y" -> Seq(3, 0) )

      // ---------------------------------------------------------------------------
      val preProupedWithListMap: ListMap[String, Seq[Int]] =
        aptus.listMap(
          "x" -> Seq(0, 1, 2),
          "y" -> Seq(3, 0) )

      // ---------------------------------------------------------------------------
      val preProupedWithMap: Map[String, Seq[Int]] =
        Map(
          "x" -> Seq(0, 1, 2),
          "y" -> Seq(3, 0) )

    // ===========================================================================
    val expected: ListMap[Int, Seq[String]] =
      aptus.listMap(
        0 -> Seq("x", "y"),
        1 -> Seq("x"),
        2 -> Seq("x"),
        3 -> Seq(     "y") )

    // ---------------------------------------------------------------------------
    val expectedFlattened: Seq[(Int, String)] =
      Seq(
        0 -> "x",
        0 -> "y",
        1 -> "x",
        2 -> "x",
        3 -> "y" ) } }

// ===========================================================================

