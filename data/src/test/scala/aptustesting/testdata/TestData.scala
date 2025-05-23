package aptustesting
package testdata

import aptus.dyn.{Dyn, Dyns}
import aptus.dyn.Dyn.dyn

// ===========================================================================
object TestData {
  import testmeta.TestMeta._

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