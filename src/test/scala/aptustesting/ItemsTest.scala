package aptustesting

import utest._
import util.chaining._

// ===========================================================================
object ItemsTest  extends TestSuite {

  val tests = Tests {
    test { compare(people.endoMap(_.upperCaseName), peopleUpperCase) }
    test { compare(people.exoMap (_.name),          Seq(Bob, Alice, Charlie)) }

    test { compare(people.filter(_.age <= 31), noCharlie) }

    test { compare(people.matchingIndex         (1) , alice) }
    test { compare(people.matchingIndices(Set(0, 1)), noCharlie) }

    // ===========================================================================
    // adding

    test { compare(
      people.append(bob.upperCaseName),
      peeps(bob, alice, charlie, bob.upperCaseName)) }

    //TODO: prepend, ...

    // ---------------------------------------------------------------------------
    // replacing

    test { compare(
        people.replaceExactlyOneItem(_.name == Bob)(_.incrementAge),
        peeps(bob.incrementAge, alice, charlie)) }

      test { failAssertion(
        people.replaceExactlyOneItem(_.age < 100)(_.incrementAge)) }

    // ---------------------------------------------------------------------------
    test { compare(
        people.replaceOneItemIfMatching(_.name == Bob)(_.incrementAge),
        peeps(bob.incrementAge, alice, charlie)) }

      test { failAssertion(
        people.replaceOneItemIfMatching(_.age < 100)(_.incrementAge)) }

      test { noop(people.replaceOneItemIfMatching(_.name == BOB)(_.incrementAge)) }

    // ---------------------------------------------------------------------------
    test { compare(
      people.replaceAllMatchingItems(_.age < 100)(_.upperCaseName),
      peopleUpperCase) }

    test { noop(people.replaceAllMatchingItems(_.name == BOB)(_.incrementAge)) }

    // ===========================================================================
    // TODO: keep porting test from local env
  }

  // ===========================================================================
  // meta

  val Bob     = "Bob"
  val Alice   = "Alice"
  val Charlie = "Charlie"

  val BOB = "BOB"

  // ---------------------------------------------------------------------------
  case class People(values: Seq[Person])
    extends aptus.aptitems.Items[People, Person] {
      val const = People.apply }

  // ---------------------------------------------------------------------------
  case class Person(name: String, age: Int) {
    def upperCaseName = copy(name = name.toUpperCase)
    def incrementAge  = copy(age  = age + 1) }

  // ===========================================================================
  // data

  val bob     = Person(Bob    , 30)
  val alice   = Person(Alice  , 31)
  val charlie = Person(Charlie, 32)

  // ---------------------------------------------------------------------------
  val people          = peeps(bob, alice, charlie)
  val peopleUpperCase = peeps(bob.upperCaseName, alice.upperCaseName, charlie.upperCaseName)
  val noCharlie       = peeps(bob, alice)

  // ---------------------------------------------------------------------------
  def peeps(value: Person, more: Person*) = (value +: more).toList.pipe(People.apply) }

// ===========================================================================