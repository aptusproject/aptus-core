package aptustesting

import utest._

// ===========================================================================
object JoiningTest extends TestSuite {
  import JoiningTestData._
  import aptus.Seq_

  // ---------------------------------------------------------------------------
  val tests = Tests {

    test("join") {

      test(compare(
          LeftSeqTestData.innerJoin(RightSeqTestData).on(_.head, _.head),
          List(
            k1 -> (k1ab, k1zy),
            k1 -> (k1cd, k1zy))))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.innerJoin(RightCcTestData).on(_.fooK, _.barK),
          List(
            k1 -> (k1_9, k1_t),
            k1 -> (k1_8, k1_t))))

      // ===========================================================================
      test(compare(
          LeftSeqTestData.leftJoin(RightSeqTestData).on(_.head, _.head),
          List(
            k2 -> (k2ef, None),
            k1 -> (k1ab, Some(k1zy)),
            k1 -> (k1cd, Some(k1zy)) ) ))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.leftJoin(RightCcTestData).on(_.fooK, _.barK),
          List(
            k2 -> (k2_7, None),
            k1 -> (k1_9, Some(k1_t)),
            k1 -> (k1_8, Some(k1_t)) ) ))

      // ===========================================================================
      test(compare(
          LeftSeqTestData.rightJoin(RightSeqTestData).on(_.head, _.head),
          List(
            k1 -> (Some(k1ab), k1zy),
            k1 -> (Some(k1cd), k1zy),
            k3 -> (None      , k3xw) )))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.rightJoin(RightCcTestData).on(_.fooK, _.barK),
          List(
            k1 -> (Some(k1_9), k1_t),
            k1 -> (Some(k1_8), k1_t),
            k3 -> (None      , k3_f) )))

      // ===========================================================================
      test(compare(
          LeftSeqTestData.outerJoin(RightSeqTestData).on(_.head, _.head),
          List(
            k2 -> (Some(k2ef), None),
            k1 -> (Some(k1ab), Some(k1zy)),
            k1 -> (Some(k1cd), Some(k1zy)),
            k3 -> (None      , Some(k3xw)) )))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.outerJoin(RightCcTestData).on(_.fooK, _.barK),
          List(
            k2 -> (Some(k2_7), None),
            k1 -> (Some(k1_9), Some(k1_t)),
            k1 -> (Some(k1_8), Some(k1_t)),
            k3 -> (None      , Some(k3_f)) ))) }

    // ===========================================================================
    test("coGroup") {

      test(compare(
          LeftSeqTestData.innerCoGroup(RightSeqTestData).on(_.head, _.head),
          List(k1 -> (Seq(k1ab, k1cd), Seq(k1zy)) )))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.innerCoGroup(RightCcTestData).on(_.fooK, _.barK),
          List(k1 -> (Seq(k1_9, k1_8), Seq(k1_t)) )))

      // ===========================================================================
      test(compare(
          LeftSeqTestData.leftCoGroup(RightSeqTestData).on(_.head, _.head),
          List(
            k2 -> (Seq(k2ef)      , None),
            k1 -> (Seq(k1ab, k1cd), Some(Seq(k1zy))) )))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.leftCoGroup(RightCcTestData).on(_.fooK, _.barK),
          List(
            k2 -> (Seq(k2_7)      , None),
            k1 -> (Seq(k1_9, k1_8), Some(Seq(k1_t))) )))

      // ===========================================================================
      test(compare(
          LeftSeqTestData.rightCoGroup(RightSeqTestData).on(_.head, _.head),
          List(
            k1 -> (some(k1ab, k1cd), Seq(k1zy)),
            k3 -> (None            , Seq(k3xw)) )))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.rightCoGroup(RightCcTestData).on(_.fooK, _.barK),
          List(
            k1 -> (some(k1_9, k1_8), Seq(k1_t)),
            k3 -> (None            , Seq(k3_f)) )))

      // ===========================================================================
      test(compare(
          LeftSeqTestData.outerCoGroup(RightSeqTestData).on(_.head, _.head),
          List(
            k2 -> (some(k2ef)      , None),
            k1 -> (some(k1ab, k1cd), some(k1zy)),
            k3 -> (None            , some(k3xw)) )))

        // ---------------------------------------------------------------------------
        test(compare(
          leftCcTestData.outerCoGroup(RightCcTestData).on(_.fooK, _.barK),
          List(
            k2 -> (some(k2_7)      , None),
            k1 -> (some(k1_9, k1_8), some(k1_t)),
            k3 -> (None            , some(k3_f)) ))) }
  }

  // ===========================================================================
  private object JoiningTestData {
    val k1 = "k1"
    val k2 = "k2"
    val k3 = "k3"

    // ===========================================================================
    val k1ab = Seq(k1, "a", "b")
    val k1cd = Seq(k1, "c", "d")
    val k2ef = Seq(k2, "e", "f")

    // ---------------------------------------------------------------------------
    val k1zy = Seq(k1, 0, 1)
    val k3xw = Seq(k3, 2, 3)

    // ---------------------------------------------------------------------------
    val  LeftSeqTestData = Seq(k1ab, k1cd, k2ef)
    val RightSeqTestData = Seq(k1zy, k3xw)

    // ===========================================================================
    case class Left(fooK: String, fooV: Int)
    case class Rite(barK: String, barV: Boolean)

    // ---------------------------------------------------------------------------
    val k1_9 = Left(k1, 9)
    val k1_8 = Left(k1, 8)
    val k2_7 = Left(k2, 7)

    // ---------------------------------------------------------------------------
    val k1_t = Rite(k1, true)
    val k3_f = Rite(k3, false)

    // ---------------------------------------------------------------------------
    val  leftCcTestData = Seq(k1_9, k1_8, k2_7)
    val RightCcTestData = Seq(k1_t, k3_f)

    // ===========================================================================
    def some   (value1: Left  , more: Left*)  : Option[Seq[Left]]   = Some((value1 +: more).toList)
    def some   (value1: Rite  , more: Rite*)  : Option[Seq[Rite]]   = Some((value1 +: more).toList)
    def some[T](value1: Seq[T], more: Seq[T]*): Option[Seq[Seq[T]]] = Some((value1 +: more).toList) } }

// ===========================================================================
