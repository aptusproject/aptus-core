package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynTargetTests extends TestSuite {
  import aptus.dyn._

  // ===========================================================================
  val sbl: Symbol = "sbl".symbol

  val keyString: Key = "str"
  val keySymbol: Key = "sbl"
  val keyEnum  : Key = "enum1"

  val path1 : Path = Path.leaf("str")
  val path2 : Path = Path.leaf("sbl")
  val path3 : Path = Path.leaf("enum1")
  val path4a: Path = "parent"  |> "leaf"
  val path4b: Path = "parent2" |> "leaf"

  val ren: Ren = "str" ~> "STR"

  val rpath6 : RPath = "parent" |> "leaf" ~> "LEAF"
  val rpath6b: RPath = "parent2" |> "leaf" ~> "LEAF"
  val rpath6c: RPath = "parent3" |> "leaf" ~> "LEAF"

  // ===========================================================================
  val tests = Tests {
    test(checkNoRenarget(keyString, noRenarget(           "str")))
    test(checkNoRenarget(keyString, noRenarget(_.explicit("str"))))

    test(checkNoRenarget(keySymbol, noRenarget( sbl)))
    test(checkNoRenarget(keyEnum  , noRenarget(EnumKey.enum1)))

    test(checkNoRenarget(path4a, noRenarget(           "parent" |> "leaf")))
    test(checkNoRenarget(path4a, noRenarget(_.explicit("parent" |> "leaf"))))

    // ===========================================================================
    test(checkTarget(keyString, target("str")))
    test(checkTarget(keySymbol, target(sbl)))
    test(checkTarget(keyEnum  , target(EnumKey.enum1)))

    test(checkTarget(ren, target(           "str" ~> "STR")))
    test(checkTarget(ren, target(_.explicit("str" ~> "STR"))))

    test(checkTarget( path4a, target(           "parent" |> "leaf")))
    test(checkTarget(rpath6,  target(           "parent" |> "leaf" ~> "LEAF")))
    test(checkTarget(rpath6,  target(_.explicit("parent" |> "leaf" ~> "LEAF")))) }

  // ===========================================================================
  def checkNoRenarget[T](a: T , b: NoRenarget) = { Predef.assert(a        == b.und , a -> b -> a      .getClass  -> b.und       .getClass)  }
  def checkTarget    [T](a: T , b: Target)     = { Predef.assert(a        == b.und , a -> b -> a      .getClass  -> b.und       .getClass)  }

  // ===========================================================================
  def noRenarget(value: NoRenarget)   : NoRenarget    = value
  def target    (value: Target) : Target  = value

  // ---------------------------------------------------------------------------
  import aptus.aptdata.meta
  def target    (value: meta.selectors.Target    .Builder): Target     = value(meta.selectors.Target)
  def noRenarget(value: meta.selectors.NoRenarget.Builder): NoRenarget = value(meta.selectors.NoRenarget) }

// ===========================================================================
sealed trait EnumKey extends enumeratum.EnumEntry

  // ---------------------------------------------------------------------------
  object EnumKey extends enumeratum.Enum[EnumKey] {
    val values = findValues

    case object enum1 extends EnumKey }

// ===========================================================================

