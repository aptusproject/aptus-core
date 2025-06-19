package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynBuilderTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(dyn(foo -> bar, baz -> (None: Option[Int])).check(dyn(foo -> bar)))
    test(dyn(foo -> bar, baz -> Some(1))            .check(dyn(foo -> bar, baz -> 1)))

    test(dyn(foo -> bar, baz -> 1).transform(baz).using(_.int.in.noneIf(_ <= 1)).check(dyn(foo -> bar)))
    test(dyn(foo -> bar, baz -> 1).transform(baz).using(_.int.in.noneIf(_ >  1)).check(dyn(foo -> bar, baz -> 1))) /* does nothing */ } }

// ===========================================================================
