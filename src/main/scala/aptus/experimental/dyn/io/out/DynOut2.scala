package aptus
package experimental
package dyn
package io
package out

// ===========================================================================
private[dyn] object DynOut2 {
  private val Missing = ""

  // ---------------------------------------------------------------------------
  def formatTable(dyns: Dyns)(sep: String): Seq[String] = {
    val (header, data) = tableData(dyns)

    (header +: data)
      .map(_.mkString /* t241024104357 - use xsv lib */ (sep))
  }

  // ---------------------------------------------------------------------------
  private def tableData(dyns: Dyns): (Seq[Key], Seq[Seq[StringValue]]) = {
    val keys: Seq[Key] = dyns.keysCostly

    keys ->
      dyns
        .valueList
        .map { row =>
          keys.map {
            row
              .get(_)
              .map(_.format)
              .getOrElse(Missing) } } }

}

// ===========================================================================
