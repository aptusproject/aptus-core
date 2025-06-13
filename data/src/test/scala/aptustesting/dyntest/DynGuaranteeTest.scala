package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynGuaranteeTest extends TestSuite {
  import aptus.dyn._
  import Dyn.Empty

  // ---------------------------------------------------------------------------
  val tests = Tests {
    test(      _Sngl1.remove          (foo)        .check(_Sngl1_NoFoo))
    test(      _Sngl1.removeGuaranteed(foo)        .check(_Sngl1_NoFoo))
    test(      _Sngl1.remove          (FOO)  .check(_Sngl1))
    test(Try { _Sngl1.removeGuaranteed(FOO) }.checkGuaranteeError())

    // ---------------------------------------------------------------------------
    test(      _Sngl1.retain          (foo)        .check(_Sngl1_NoBaz))
    test(      _Sngl1.retainGuaranteed(foo)        .check(_Sngl1_NoBaz))
    test(      _Sngl1.retain(foo.guaranteePresence).check(_Sngl1_NoBaz))

    test(      _Sngl1.retain          (FOO)          .check(Empty))
    test(Try { _Sngl1.retainGuaranteed(FOO) }        .checkGuaranteeError())
    test(Try { _Sngl1.retain(FOO.guaranteePresence) }.checkGuaranteeError())

    // ---------------------------------------------------------------------------
    test(_Sngl1.rename(foo ~>  FOO)          .check(_Sngl1_FOO))
    test(_Sngl1.rename(foo).to(FOO)          .check(_Sngl1_FOO))
    test(_Sngl1.renameGuaranteed(foo).to(FOO).check(_Sngl1_FOO))

    test(Try { _Sngl1.renameGuaranteed(FOO ~>  foo) }.checkGuaranteeError())
    test(Try { _Sngl1.renameGuaranteed(FOO).to(foo) }.checkGuaranteeError())
    test(Try { _Sngl1.renameGuaranteed(FOO).to(foo) }.checkGuaranteeError())

    // ---------------------------------------------------------------------------
    test(_Sngl1.upperCase(foo)                  .check(_Sngl1_BAR))
    test(_Sngl1.upperCase(foo.guaranteePresence).check(_Sngl1_BAR))

    test(_Sngl1.remove(_.firstKey.guaranteePresence).check(_Sngl1_NoFoo))
    test(_Sngl1.remove(_. lastKey.guaranteePresence).check(_Sngl1_NoBaz))

    test(_Sngl1.retain(_.firstKey.guaranteePresence).check(_Sngl1_NoBaz))

    test(_Sngl1.rename(_.firstKey.guaranteePresence).to(FOO).check(_Sngl1_FOO)) } }

// ===========================================================================

