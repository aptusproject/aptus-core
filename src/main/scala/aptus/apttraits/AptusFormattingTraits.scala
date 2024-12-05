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
    trait HasFormatJson
        extends HasFormatCompactJson
           with HasFormatPrettyJson

      // ---------------------------------------------------------------------------
      trait HasFormatCompactJson {
        @abstrct       def formatCompactJson: String
        @nonovrd final def  printCompactJson: Unit = { println(formatCompactJson) } }

      // ---------------------------------------------------------------------------
      trait HasFormatPrettyJson {
        @abstrct       def formatPrettyJson: String
        @nonovrd final def  printPrettyJson: Unit = { println(formatPrettyJson) } }

    // ---------------------------------------------------------------------------
    /** with header */
    trait HasFormatTable {
        @abstrct       def formatTable          : String
        @nonovrd final def formatTableWithHeader: String = formatTable }

      // ---------------------------------------------------------------------------
      trait HasFormatTableWithoutHeader {
        @abstrct def formatTableWithoutHeader: String } }

// ===========================================================================