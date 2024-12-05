package aptus
package experimental
package dyntest
package io

import util.chaining._

// ===========================================================================
object DynDataClassesTest {
  import aptus.experimental.dyn

  // ---------------------------------------------------------------------------
  case class Datum(foo: String, baz: Int)

  val datum = Datum(foo = bar, baz = 1)

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    if (_f) { //TODO: t241204140907{a,b}
      dyn.fromDataClass[Datum](datum)               .add(qux -> T)     .check(_Sngl1_T)
      dyn.fromDataClass[Datum](datum)(WTT.to[Datum]).add(qux -> T)     .check(_Sngl1_T)

      /* round trip */
      datum.pipe(dyn.fromDataClass[Datum]).toDataClass[Datum].check(datum) } }
}

// ===========================================================================


