package aptustesting
package dyntest
package utils

import aptus.Size

// ===========================================================================
object TableUtils {
  type Seq2D[T] = Seq[Seq[T]]

  // ---------------------------------------------------------------------------
  // very inefficient as is
  def formatTable(valuess: Seq2D[String]): String = {

    val width: Size =
      valuess
        .map(_.size)
        .distinct
        .force.one // TODO: loosen (fill empty columns)

    val maxes: Seq[Size] =
      Range(0, width)
        .map { columnIndex =>
          valuess
            .map(_.apply(columnIndex).size)
            .max }

    maxes
      .zipSameSize(valuess)
      .map { case (max, row) =>
        row
          .map { cell =>
            cell.padLeftSpaces(max) }
          .join(" ") }
      .joinln } }

// ===========================================================================
