package aptustesting

import utest._

// ===========================================================================
object ListMapTest extends TestSuite {
  private val one = "one"
  private val two = "two"

  // ---------------------------------------------------------------------------
  private val in  =       List   (2 -> two, 1 -> one, 2 -> two)
  private val out = aptus.listMap(2 -> List(two, two), 1 -> List(one))

  // ===========================================================================
  val tests = Tests {
    import aptus.Seq_
    import aptus.Iterator_

    // ---------------------------------------------------------------------------
    test { compare(
      in.groupByKeyWithListMap,
      out) }

    // ---------------------------------------------------------------------------

    test { compare(
      in.iterator.toCloseabledIterator.groupByKeyWithListMap,
      out) }
  } }

// ===========================================================================
