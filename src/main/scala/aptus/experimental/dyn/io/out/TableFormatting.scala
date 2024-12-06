package aptus
package experimental
package dyn
package io
package out

// ===========================================================================
private[dyn] object TableFormatting {

  case class TableCtx(
        separator    : String  = "\t",
        includeHeader: Boolean = true,
        missingValue : String  = "") {
      import TableCtx.Self

      def   noHeader                 : Self = copy(includeHeader = false)
      def withHeader                 : Self = copy(includeHeader = true)

      def missingValue(value: String): Self = copy(missingValue = value)

      def separator   (value: String): Self = copy(separator = value)
      def tsv                        : Self = separator("\t")
      def csv                        : Self = separator(",") }

    // ---------------------------------------------------------------------------
    object TableCtx { private type Self = TableCtx
      def build(builder: Builder): Self = builder(Default)
      type Builder = Self => Self
      val Default: Self = TableCtx() }

  // ===========================================================================
  // TODO: t241031160012 - macros for boilerplate
  case class TableLikeCtx(
        separator    : String  = "\t",
        missingValue : String  = "") {
      import TableLikeCtx.Self

      def missingValue(value: String): Self = copy(missingValue = value)

      def separator   (value: String): Self = copy(separator = value)
      def tsv                        : Self = separator("\t")
      def csv                        : Self = separator(",") }

    // ---------------------------------------------------------------------------
    object TableLikeCtx { private type Self = TableLikeCtx
      def build(builder: Builder): Self = builder(Default)
      type Builder = Self => Self
      val Default: Self = TableLikeCtx() }

  // ===========================================================================
  def _formatRows(ctx: TableCtx)(keys: Keys)(values: IteratoR[Dyn]): IteratoR[FormattedRow] =
      keys.in.someIf(_ => ctx.includeHeader).map(_.mkString(ctx.separator)).toList.iteratoR ++
      _formatCells(ctx.missingValue)(keys)(values).map(_.mkString(ctx.separator))

    // ---------------------------------------------------------------------------
    def _formatCells(missingValue: String)(keys: Keys)(values: IteratoR[Dyn]): IteratoR[Seq[Cell]] =
      values
        .map { row =>
          keys
            .map { key =>
              row
                .text_(key)
                .getOrElse(missingValue) } }

  // ===========================================================================
  /** not very optimized, but then again the version where keys are provided should be preferred most of the time.
   *
   * note that table may not have the same number of columns for each row */
  def _formatTableLikeCells(missingValue: String)(values: IteratoR[Dyn]): IteratoR[List[Cell]] = {
    var encountered = collection.mutable.LinkedHashSet[Key]()

    // ---------------------------------------------------------------------------
    // TODO: possible optimizations:
    // - handle first element differently, to populate known keys (may contains most)
    // - have both a HashSet+Seq pair instead of just one LinkedHashSet

    // ---------------------------------------------------------------------------
    values
      .map { row =>
        val knownKeys: Iterable[StringValue] =
          encountered
            .map { encounteredKey =>
              row
                .text_(encounteredKey)
                .getOrElse(missingValue) }

        // ---------------------------------------------------------------------------
        val newKeys: Iterable[StringValue] =
          row
            .data
            .flatMap { entry =>
              entry
                .key
                .in.noneIf(encountered.contains)
                .map { newKey =>
                  encountered += newKey
                  entry.valew.format } }

        // ---------------------------------------------------------------------------
        (knownKeys ++ newKeys).toList } } }

// ===========================================================================