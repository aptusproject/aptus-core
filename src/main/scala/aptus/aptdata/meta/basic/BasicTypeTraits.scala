package aptus
package aptdata
package meta
package basic

// ===========================================================================
@pseudosealed trait HasParseString { type T
    def parseString: String => T }

  // ---------------------------------------------------------------------------
  @pseudosealed trait HasParseDouble { type T
    def parseDouble: Double => T }

  // ---------------------------------------------------------------------------
  @pseudosealed trait HasParseAnyToDouble { self: HasParseDouble =>; type T
      final def parseAnyToDouble: Any => T =
        _.asInstanceOf[Double].pype(parseDouble) }

    // ---------------------------------------------------------------------------
    @pseudosealed trait HasParseAny { self: HasParseString =>; type T
      final def parseAny: Any => T =
        _.asInstanceOf[String].pype(parseString) }

  // ---------------------------------------------------------------------------
  @pseudosealed trait HasFormatString { type T
    def formatString: T => String }

// ---------------------------------------------------------------------------
@pseudosealed trait HasFieldHasType { def has: Fld => Boolean }

// ===========================================================================
@pseudosealed trait HasPair { x: HasParseString =>
  type T
  def pair: (String => T, Long => T)
  final override def parseString =
    pair.pype { case (ifString, ifLong) =>
      (s: String) =>
        if (!s.forall(_.isDigit)) ifString(s)
        else                      ifLong  (s.toLong) } }

// ===========================================================================
