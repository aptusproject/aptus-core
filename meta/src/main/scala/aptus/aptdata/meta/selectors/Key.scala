package aptus
package aptdata
package meta
package selectors

// ===========================================================================
case class Key(und: Symbol) extends AnyVal {
    def name : SKey   = und.name
    def quote: String = name.quote

    // ---------------------------------------------------------------------------
    def ~> (key: Key): Ren  = Ren (    und , key.und)
    def |> (key: Key): Path = Path(Seq(und), key.und)

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        und.name }

  // ===========================================================================
  object Key {
    implicit def _toSymbol(value: Key): Symbol = value.und
    implicit def _toString(value: Key): String = value.name

    implicit def _fromSymbol(value: Symbol): Key = Key(und = value)
    implicit def _fromString(value: String): Key = Key(und = Symbol(value)) }

// ===========================================================================
