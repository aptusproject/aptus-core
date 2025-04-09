package aptustesting
package dyntest

// ===========================================================================
object DynOutputTableStringsTests {
  import aptus.experimental.dyn._
  import Dyn.dyn

  // ===========================================================================
  private val _Multiple01  = s"""[{"$foo": "$bar1", "$baz": 1}, {"$foo": "$bar2", "$baz": 2}]"""

  // ---------------------------------------------------------------------------
  private val Header = "foo\tbaz"
  private val Row1   = "bar1\t1"
  private val Row2   = "bar2\t2"
  private val Row3   = "bar3\tNA"

  // ---------------------------------------------------------------------------
  private val Rows12         = Row1.newline(Row2)
  private val HeaderRows12nl = Header.newline(Rows12).newline

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply() }

  // ---------------------------------------------------------------------------
  def apply(): Unit = { _apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  private def _apply(): Unit = {

    // TODO: t241031141912 - more complex cases (!= keys, ...)

    _Multiple01.dyns.formatTable                 .check(HeaderRows12nl)

    _Multiple01.dynz.formatTsv  (foo, baz)       .check(HeaderRows12nl)
    _Multiple01.dynz.formatTable(foo, baz)(_.tsv).check(HeaderRows12nl)

    _Multiple01.dynz.formatCsv  (foo, baz)       .check(HeaderRows12nl.replace("\t", ","))
    _Multiple01.dynz.formatTable(foo, baz)(_.csv).check(HeaderRows12nl.replace("\t", ","))

    _Multiple01.dynz.formatTable(foo, baz)(_.noHeader).check(Rows12.newline)

    _Multiple01.dynz.append(dyn(foo -> "bar3")).formatTable(foo, baz)(_.missingValue("NA")).check(
      Header.newline(Rows12).newline(Row3).newline)

    _Multiple01.dynz.formatRows(foo, baz)(_.missingValue("NA")).consumeAll().joinln.newline.check(HeaderRows12nl)

    // ===========================================================================
    _Multiple01.dynz.formatTableLike.check(Rows12.newline)
  }

}

// ===========================================================================
