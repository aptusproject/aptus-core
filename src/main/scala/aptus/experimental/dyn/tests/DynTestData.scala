package aptus
package experimental
package dyntest

//TODO: better imports for dyn in dyntest
import dyn._
import Dyn .dyn
import Dyns.dyns

// ===========================================================================
trait DynTestData {
  val T = true
  val F = false

  // ---------------------------------------------------------------------------
  val foo = "foo"
  val FOO = "FOO"

  // ---------------------------------------------------------------------------
  val bar  = "bar"
  val bar1 = "bar1"
  val bar2 = "bar2"
  val bar3 = "bar3"

  val BAR  = "BAR"
  val BAR1 = "BAR1"
  val BAR2 = "BAR2"

  val qux = "qux"

  // ---------------------------------------------------------------------------
  val p  = "p"
  val p1 = "p1"
  val p2 = "p2"

  val baz = "baz"
  val BAZ = "BAZ"

  // ===========================================================================
  val _Sngl1  = dyn(foo -> bar, baz -> 1)
  val _Sngl1a = dyn(foo -> bar, baz -> 1)
  val _Sngl1b = dyn(foo -> bar, baz -> 2)
  val _Sngl1k = dyn(foo -> bar, baz -> 0)
  val _Sngl1c = dyn(FOO -> bar, baz -> 1)
  val _Sngl1d = dyn(foo -> BAR, baz -> 1)
  def _Sngl1e = dyn(foo -> "rab", baz -> 1)
  val _Sngl1rr = dyn(foo -> bar, baz -> 1.1)
  val _Sngl1s = dyn(foo -> bar, baz -> "1")
  val _Sngl1r  = dyn(foo -> bar, baz -> 1.0)

  val _Sngl1x1  = dyn(foo -> bar1, baz ->  1)
  val _Sngl1x1s = dyn(foo -> bar1, baz -> "1")
  val _Sngl1x2  = dyn(foo -> bar2, baz ->  2)
  val _Sngl1x2s = dyn(foo -> bar2, baz -> "2")

  val _Sngl1_T = _Sngl1.add(qux -> T)

  // ---------------------------------------------------------------------------
  val _Sngl3  =           dyn(p -> _Sngl1)
  val _Sngl3d =           dyn(p -> _Sngl1d)
  val _Sngl33 = dyn(p2 -> _Sngl3)

  // ---------------------------------------------------------------------------
  def _Mult1  = dyns(_Sngl1x1, _Sngl1x2)

  def _Mult1a = _Mult1
  def _Mult1b = dyns(dyn(foo -> bar1, baz -> 2), dyn(foo -> bar2, baz -> 3))
  def _Mult1c = dyns(dyn(FOO -> bar1, baz -> 1), dyn(FOO -> bar2, baz -> 2))
  def _Mult1d = dyns(dyn(foo -> BAR1, baz -> 1), dyn(foo -> BAR2, baz -> 2))
  def _Mult1e = dyns(dyn(foo -> "1rab", baz -> 1), dyn(foo -> "2rab", baz -> 2))
  def _Mult1s = dyns(_Sngl1x1s, _Sngl1x2s)
  def _Mult1r = _Mult1.convert(baz).toDouble.ensuring(_.doubles(baz) == List(1.0, 2.0))

  // ---------------------------------------------------------------------------
  val _Mult11  = dyn(p -> _Mult1)
  val _Mult11b = dyn(p -> _Mult1b)
  val _Mult11d = dyn(p -> _Mult1d)

  // ===========================================================================
  val d8: Dyn  = dyn(foo -> bar1, baz -> 1)
  val d9: Dyn  = dyn(foo -> bar2, baz -> 2)
  val z9: Dyns = dyns(d8, d9)

  // ===========================================================================
  val JsonObjectFilePath = "/data/test/test.json"  //  {"foo": "bar", "baz": 1}\n
  val JsonArrayFilePath  = "/data/test/test.jsona" // [{"foo": "bar1", "baz": 1},{"foo": "bar2", "baz": 2}]\n
  val JsonLinesFilePath  = "/data/test/test.jsonl" //  {"foo": "bar1", "baz": 1}\n{"foo": "bar2", "baz": 2}\n

  val TsvFilePath       = "/data/test/test1.tsv" // foo\tbaz\nbar1\t1\nbar2\t2\n
  val TsvWithCRFilePath = "/data/test/test_with_cr.tsv" // has \r...

  val RowFilePath       = "/data/test/row.tsv" // header + one row of data
}

// ===========================================================================
