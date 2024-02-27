package aptustesting

import utest._

// ===========================================================================
object ItemsTest  extends TestSuite {

  val tests = Tests {
    test(compare(people.endoMap(_.toUpperCase), peopleUpperCase))

    test(compare(people.exoMap(_.name), Seq("Bob", "Alice", "Charlie")))

    test(compare(people.filter(_.age <= 31), noCharlie))

    test(compare(people.matchingIndex  (1), alice))
    test(compare(people.matchingIndices(Set(0, 1)), noCharlie))

    // TODO: keep porting test from local env
  }

  // ===========================================================================
  // meta

  case class People(values: Seq[Person])
    extends aptus.aptmisc.Items[People, Person] {
      val const = People.apply }

  // ---------------------------------------------------------------------------
  case class Person(name: String, age: Int) {
    def toUpperCase = copy(name = name.toUpperCase)
    def increment   = copy(age  = age + 1) }

  // ===========================================================================
  // data

  val bob     = Person("Bob"    , 30)
  val alice   = Person("Alice"  , 31)
  val charlie = Person("Charlie", 32)

  // ---------------------------------------------------------------------------
  val people          = People(Seq(bob, alice, charlie))
  val peopleUpperCase = People(Seq(bob.toUpperCase, alice.toUpperCase, charlie.toUpperCase))
  val noCharlie       = People(Seq(bob, alice))
}

// ===========================================================================