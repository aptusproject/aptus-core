package aptustesting.dyntest

import utest._

// ===========================================================================
object DynTargetDataTest extends TestSuite {
  import aptus.dyn._
  import Dyn.Empty

  // ===========================================================================
  val Input =
    dyn(
      "a"  -> 1,
      "b"  -> 2,
      "p1" -> dyn("c" -> 3),
      "p2" -> dyn("d" -> 4, "e" -> 5),
      "p3" -> dyn("p" -> dyn("f" -> 6)),
      "extra" -> 0)

  // ---------------------------------------------------------------------------
  private val targets: Seq[Target] = Seq(
    "a",
    "b"  ~> "B",
    "p1" |> "c",
    "p2" |> "d",
    "p2" |> "e" ~> "E",
    "p3" |> "p" |> "f")

  // ===========================================================================
  val tests = Tests {
    import aptus.aptdata.ops.common.td._
    import TargetData.{build => td}

    // ---------------------------------------------------------------------------
    Targets
      .guaranteed(targets)
      .pipe(TargetData.parse)
      .check(td(T)(
        "a"  -> AsIs,
        "b"  -> WithRename("B"),
          "p1" -> td(T)("c" -> AsIs),
        "p2" -> td(T)("d" -> AsIs, "e" -> WithRename("E")),
        "p3" -> td(T)("p" -> td(T)("f" -> AsIs))))

    // ---------------------------------------------------------------------------
    Targets
      .mayBeMissing(targets)
      .pipe(TargetData.parse)
      .check(td(F)(
        "a"  -> AsIs,
        "b"  -> WithRename("B"),
          "p1" -> td(F)("c" -> AsIs),
        "p2" -> td(F)("d" -> AsIs, "e" -> WithRename("E")),
        "p3" -> td(F)("p" -> td(F)("f" -> AsIs)))) } }

// ===========================================================================

