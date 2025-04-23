package aptus
package aptdata
package domain
package errors

// ===========================================================================
trait DataEntityErrorFormatter { def formatErrorEntity: String }

  // ---------------------------------------------------------------------------
  object DataEntityErrorFormatter {

    def fromSingle(value: sngl.DynJsonWriter): DataEntityErrorFormatter = new DataEntityErrorFormatter {
      override final def formatErrorEntity: String = value.formatPrettyJson }

    // ---------------------------------------------------------------------------
    def fromIterator(valuesIterator: Sngls): DataEntityErrorFormatter = new DataEntityErrorFormatter {
        override final def formatErrorEntity: String =
          valuesIterator.pipe(_fromIterator) }

      // ---------------------------------------------------------------------------
      private def _fromIterator(valuesIterator: Sngls): String =
        valuesIterator
          .take(10)
          .map (_.formatCompactJson)
          .consumeAll() // consuming Iterator is okay because this is always in the context of a runtime error anyway
          .joinln
          .append("[possibly truncated]" /* TODO: better */) }

  // ===========================================================================
