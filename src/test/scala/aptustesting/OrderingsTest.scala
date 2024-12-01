package aptustesting

import utest._

// ===========================================================================
object OrderingsTest extends TestSuite {

  val list123 = List(1, 2, 3)
  val list456 = List(4, 5, 6)

  val list312 = List(3, 1, 2)
  val list564 = List(5, 6, 4)

  val listList: List[List[Int]] = List(list564, list312)

  // ---------------------------------------------------------------------------
  val listMap12 = aptus.listMap(1 -> "one", 2 -> "two")
  val listMap21 = aptus.listMap(2 -> "two", 1 -> "one")

  // ===========================================================================
  val tests = Tests {
    test { compare( list312 .sorted, list123) }

    // ---------------------------------------------------------------------------
    test {
      compare(
        listList.sorted(aptus.listOrdering[Int]),
        List(list312, list564)) }

    // ---------------------------------------------------------------------------
    test("ListMap orderings") {
      import aptus.ListMap_

      test {
        compare(
          // TODO: t241201154152 - comparing list maps directly seem to match even when it actually doesn't (see below)
          listMap21.sortListMap.toList,
          listMap12            .toList) }
      test {
        compare(
          // TODO: t241201154152 - should succeed intuitively...
          listMap21,
          listMap12) } } } }

// ===========================================================================
