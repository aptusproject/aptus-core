package aptustesting

import aptus._
import utest._

// ===========================================================================
object ForceTests extends TestSuite {

  val tests = Tests {
    test(".force.one") {
      test(faiL(Seq[Int]().force.one).error("E250618164000"))
      test(faiL(Seq[Int]().force.one).error("size: 0"))
      test(     Seq(1)    .force.one .check(1))
      test(faiL(Seq(1, 2) .force.one).error("E250618164000"))
      test(faiL(Seq(1, 2) .force.one).error("size: 2"))
      test(faiL(Seq(1, 2) .force.oneExpanded).error(
        """|size: 2
           |
           |values:
           |1
           |2
           |""".stripMargin)) }

    // ---------------------------------------------------------------------------
    test(".force.option") {
      test(     Seq[Int]().force.option .check0())
      test(     Seq[Int]().force.option .check0())
      test(     Seq(1)    .force.option .check1(1))
      test(faiL(Seq(1, 2) .force.option).error("E250618164001"))
      test(faiL(Seq(1, 2) .force.option).error("size: 2"))
      test(faiL(Seq(1, 2) .force.optionExpanded).error(
        """|size: 2
           |
           |values:
           |1
           |2
           |""".stripMargin)) }

    // ---------------------------------------------------------------------------
    test(".force.distinct") {
      test(     Seq[Int]()  .force.distinct .check0())
      test(     Seq(1)      .force.distinct .check1(1))
      test(     Seq(1, 2)   .force.distinct .checkN(1, 2))
      test(faiL(Seq(1, 2, 1).force.distinct).error("E250618164002"))
      test(faiL(Seq(1, 2, 1).force.distinct).error("size: 3 (1)" /* 1 duplicate */))
      test(faiL(Seq(1, 2, 1).force.distinctExpanded).error(
        """|size: 3
           |
           |values:
           |1
           |2
           |1
           |""".stripMargin)) }

    // ---------------------------------------------------------------------------
    test(".force.set") {
      test(     Seq[Int]()  .force.set .check(Set()))
      test(     Seq(1)      .force.set .check(Set(1)))
      test(     Seq(1, 2)   .force.set .check(Set(1, 2)))
      test(faiL(Seq(1, 2, 1).force.set).error("E250618164003"))
      test(faiL(Seq(1, 2, 1).force.set).error("size: 3 (1)" /* 1 duplicate */))
      test(faiL(Seq(1, 2, 1).force.setExpanded).error(
        """|size: 3
           |
           |values:
           |1
           |2
           |1
           |""".stripMargin)) }

    // ===========================================================================
    test(".force.tuple2") {
      test(faiL(Seq[Int]()  .force.tuple2).error("E250618164102"))
      test(faiL(Seq[Int]()  .force.tuple2).error("size: 0"))
      test(faiL(Seq(1)      .force.tuple2).error("E250618164102"))
      test(faiL(Seq(1)      .force.tuple2).error("size: 1"))
      test(     Seq(1, 2)   .force.tuple2 .check((1, 2)))
      test(faiL(Seq(1, 2, 3).force.tuple2).error("E250618164102"))
      test(faiL(Seq(1, 2, 3).force.tuple2).error("size: 3"))
      test(faiL(Seq(1, 2, 3).force.tuple2Expanded).error(
        """|size: 3
           |
           |values:
           |1
           |2
           |3
           |""".stripMargin)) }

    // ---------------------------------------------------------------------------
    // TODO: map, mapLeft, ...
  } }

// ===========================================================================
