package aptustesting
package dyntest

// ===========================================================================
object DynScalaTest { // TODO: move those to aptus test
  import aptus.experimental.dyn._
  import aptus.listMap

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------

  private def _apply(): Unit = {

    // ---------------------------------------------------------------------------
    // a241125140134 - big int is numeric
    {
      def foo[N: Numeric](value: N) = value

      foo(3)
      foo(BigInt(3))
    //foo("")
    }

    // ---------------------------------------------------------------------------
    { // a241125101924
      val byte : Byte  = 0
      val short: Short = 0
      val int  : Int   = 0

    //val long2Short: Short = long
    //val  int2Short: Short = int
      val byte2Short: Short = byte

    //val  long2Int: Int = long
      val short2Int: Int = short
      val  byte2Int: Int = byte
val int2Double: Double = int
      val   int2Long: Long = int
      val short2Long: Long = short
      val  byte2Long: Long = byte }


val xx: Int = 4 / 2
val xy: Int = 4 / 3 // really should error rather...
assert(2 == xx)
assert(1 == xy)

    // ---------------------------------------------------------------------------
    // r241119153446
    (   !(Some(1): Option[Int]).isInstanceOf[Iterable[_]] &&
         (Some(1): Option[Int]).isInstanceOf[IterableOnce[_]])
      .checkTrue()

    // ---------------------------------------------------------------------------
    // allowed...
    ( listMap("foo" -> 1, "bar" -> 2) ++
      listMap("foo" -> 1, "bar" -> 2)).check(
      listMap("foo" -> 1, "bar" -> 2))

    { // r241122115353
      val listMap1: ListMap[String, Int] = listMap("foo" -> 1, "bar" -> 2)
      val listMap2: ListMap[String, Int] = listMap("foo" -> 1, "bar" -> 2)

      val iterable1: Iterable[(String, Int)] = listMap1
      val iterable2: Iterable[(String, Int)] = listMap2

      (listMap1  ++ listMap2) .check(listMap ("foo" -> 1, "bar" -> 2))
      (iterable1 ++ iterable2).check(Iterable("foo" -> 1, "bar" -> 2, "foo" -> 1, "bar" -> 2)) }

    // ---------------------------------------------------------------------------
    // does not fail on duplicate keys because JSON (and therefore gson) does not
    """{"foo": "bar1", "baz": 1, "foo": "bar2"}""".dyn.formatCompactJson.check("""{"foo":"bar2","baz":1.0}""") } }

// ===========================================================================
