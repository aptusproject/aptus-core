package aptustesting
package testdata

import aptus.experimental.dyn.{Dyn, Dyns}
import aptus.experimental.dyn.Dyn.dyn

// ===========================================================================
object TestData {
  import testmeta.TestMeta._

  // ---------------------------------------------------------------------------
  val johnStatic: Person =
    Person(
        name      = "John Smith",
        age       = 32,
        phones    = Seq("123-456-7890", "098-765-4321"),
        addresses = Seq(
          Address("3 Orion Street", "Toronto", primary = true),
          Address("2 Belle Blvd"  , "Lyon",    primary = false)))

  // ---------------------------------------------------------------------------
  val johnDynamics: Dyns = Dyns.build(johnDynamic, johnDynamic)

  // ---------------------------------------------------------------------------
  val johnDynamic: Dyn =
    dyn(
      "name"      -> "John Smith",
      "age"       -> 32,
      "phones"    -> Seq("123-456-7890", "098-765-4321"),
      "addresses" -> Seq(
          dyn("street" -> "3 Orion Street", "city" -> "Toronto", "primary" -> true),
          dyn("street" -> "2 Belle Blvd"  , "city" -> "Lyon",    "primary" -> false))) }

// ===========================================================================