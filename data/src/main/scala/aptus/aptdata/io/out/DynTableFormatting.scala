package aptus
package aptdata
package io
package out

// ===========================================================================
private[aptdata] object DynTableFormatting {
  private val Missing = ""

  // ---------------------------------------------------------------------------
  def formatTable(dyns: Dyns)(sep: String): Seq[String] = {
    val (header, data) = tableData(dyns)

    (header +: data)
      .map(_.mkString /* t241024104357 - use xsv lib */ (sep)) }

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
              .map(_.formatValew)
              .getOrElse(Missing) } } } }

// ===========================================================================
