package aptustesting
package testdata

import aptus.dyn.{Dyn, Dyns}
import aptus.dyn.Dyn.dyn

// ===========================================================================
object TestData {
  import testmeta.TestMeta._

  // ---------------------------------------------------------------------------
  val myComplexData = MyComplexData(
        b            = true,

        myString     = "str",
        myOptInt     = Some(0),
        myDoubles    = Seq (1.1, 2.2, 3.3),
        myOptBooleans= Some(Seq(true, false, true)),

        myObj        =          myComplexSubData,
        myOptObj     = Some(    myComplexSubData2),
        myObjs       =      Seq(myComplexSubData, myComplexSubData2),
        myOptObjs    = Some(Seq(myComplexSubData, myComplexSubData2)),

        myClosing    = false)

    // ---------------------------------------------------------------------------
    lazy val myComplexSubData = MyComplexSubData(
        mySubString = "substr",
        mySubInt    =  1)

      // ---------------------------------------------------------------------------
      lazy val myComplexSubData2 = MyComplexSubData(
        mySubString = "substr2",
        mySubInt    =  2)

  // ---------------------------------------------------------------------------
  lazy val johnStatic: Person =
    Person(
        name      = "John Smith",
        age       = 32,
        phones    = Seq("123-456-7890", "098-765-4321"),
        addresses = Seq(
          Address("3 Orion Street", "Toronto", primary = true),
          Address("2 Belle Blvd"  , "Lyon",    primary = false)))

  // ---------------------------------------------------------------------------
  lazy val johnDynamics: Dyns = Dyns.dyns(johnDynamic, johnDynamic)

  // ---------------------------------------------------------------------------
  lazy val johnDynamic: Dyn =
    dyn(
      "name"      -> "John Smith",
      "age"       -> 32,
      "phones"    -> Seq("123-456-7890", "098-765-4321"),
      "addresses" -> Seq(
          dyn("street" -> "3 Orion Street", "city" -> "Toronto", "primary" -> true),
          dyn("street" -> "2 Belle Blvd"  , "city" -> "Lyon",    "primary" -> false))) }

// ===========================================================================