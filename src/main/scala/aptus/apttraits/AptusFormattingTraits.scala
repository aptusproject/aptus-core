package aptus
package apttraits

// ===========================================================================
object AptusFormattingTraits
    extends AptusFormattingTraits

  // ---------------------------------------------------------------------------
  trait AptusFormattingTraits { // these are mostly of use in combination with aptus.Items for now

    // ---------------------------------------------------------------------------
    trait HasFormatDefault {
        @abstrct def formatDefault: String }

      // ---------------------------------------------------------------------------
      trait HasFormatDefaultWithPrefix extends HasFormatDefault {
        @abstrct       def formatDefault(prefix: String): String
        override final def formatDefault                : String = formatDefault(prefix = "") }

    // ===========================================================================
    trait HasFormatCompactJson {
        @abstrct def formatCompactJson: String }

      // ---------------------------------------------------------------------------
      trait HasFormatPrettyJson {
        @abstrct def formatPrettyJson: String }

    // ---------------------------------------------------------------------------
    /** with header */
    trait HasFormatTable {
        @abstrct       def formatTable          : String
        @nonovrd final def formatTableWithHeader: String = formatTable }

      // ---------------------------------------------------------------------------
      trait HasFormatTableWithoutHeader {
        @abstrct def formatTableWithoutHeader: String } }

// ===========================================================================