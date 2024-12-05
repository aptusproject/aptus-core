package aptus
package experimental
package dyn
package io
package out

// ===========================================================================
trait DynzTables { self: Dynz =>
  import io.out.TableFormatting._

  // ---------------------------------------------------------------------------
  def formatTsv(key1: Key, more: Key*): FormattedTable = formatTable(key1, more:_*)(_.tsv)
  def formatTsv(keys: Keys)           : FormattedTable = formatTable(keys,          _.tsv)

  def formatCsv(key1: Key, more: Key*): FormattedTable = formatTable(key1, more:_*)(_.csv)
  def formatCsv(keys: Keys)           : FormattedTable = formatTable(keys,          _.csv)

  // ---------------------------------------------------------------------------
  def formatTable(key1: Key, more: Key*)(ctxBuilder: TableCtx.Builder): FormattedTable = formatTable(key1 +: more, ctxBuilder)
  def formatTable(keys: Keys,            ctxBuilder: TableCtx.Builder): FormattedTable = formatRows(keys, ctxBuilder).toList.joinln.newline

  def formatRows(key1: Key, more: Key*)(ctxBuilder: TableCtx.Builder): IteratoR[FormattedRow] =  formatRows(key1 +: more,  ctxBuilder)
  def formatRows(keys: Keys,            ctxBuilder: TableCtx.Builder): IteratoR[FormattedRow] = _formatRows(TableCtx.build(ctxBuilder))(keys)(values)

  def formatCells(key1: Key, more: Key*)(missingValue: String = TableCtx.Default.missingValue): IteratoR[Seq[Cell]] =  formatCells(key1 +: more, missingValue)
  def formatCells(keys: Keys,            missingValue: String)                                : IteratoR[Seq[Cell]] = _formatCells(missingValue)(keys)(values)

  // ===========================================================================
  /** not very optimized, but then again the version where keys are provided should be preferred most of the time */
  def formatTableLike                                  : FormattedTable = formatTableLike(ctxBuilder = identity)
  def formatTableLike(ctxBuilder: TableLikeCtx.Builder): FormattedTable = formatTableLikeRows(ctxBuilder).toList.joinln.newline

  def formatTableLikeRows                                  : IteratoR[FormattedRow] = formatTableLikeRows(ctxBuilder = identity)
  def formatTableLikeRows(ctxBuilder: TableLikeCtx.Builder): IteratoR[FormattedRow] = TableLikeCtx.build(ctxBuilder).pipe { ctx =>
    formatTableLikeCells(ctx.missingValue).map(_.join(ctx.separator)) }

  // ---------------------------------------------------------------------------
  def formatTableLikeCells                      : IteratoR[List[Cell]] =  formatTableLikeCells(missingValue = TableLikeCtx.Default.missingValue)
  def formatTableLikeCells(missingValue: String): IteratoR[List[Cell]] = _formatTableLikeCells(missingValue)(values) }

// ===========================================================================
