package aptus
package aptdata
package meta
package selectors

// ===========================================================================
case class Key(und: Symbol)
      extends AnyVal
         with Targetable
         with NoRenargetable {

    def name : SKey   = und.name
    def quote: String = name.quote

    // ---------------------------------------------------------------------------
    def target: Target = und

    // ---------------------------------------------------------------------------
    def ~> (key: Key): Ren  = Ren (    this , key)
    def |> (key: Key): Path = Path(Seq(this), key)

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        und.name }

  // ===========================================================================
  object Key {
    implicit def _toSymbol(value: Key): Symbol = value.und
    implicit def _toString(value: Key): String = value.name

    // ---------------------------------------------------------------------------
    implicit def _fromSymbol   (value: Symbol): Key = Key(und = value)
    implicit def _fromString   (value: String): Key = Key(und = Symbol(value))
    implicit def _fromEnumEntry(value: Enm)   : Key = Key(und = Symbol(value.entryName))

    // ---------------------------------------------------------------------------
    def from(value: Symbol): Key = Key(und = value)
    def from(value: String): Key = Key(und = Symbol(value))
    def from(value: Enm)   : Key = Key(und = Symbol(value.entryName)) }

// ===========================================================================
