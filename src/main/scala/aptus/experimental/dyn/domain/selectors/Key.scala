package aptus
package experimental
package dyn
package domain
package selectors

// ===========================================================================
case class Key(und: Symbol) {
                                  def ~>(s: Key): Ren  = Ren(und, s.und)
                                  def |>(s: Key): Path = Path(Seq(und), s.und)
    def quote = name.quote
  def name = und.name

    // ---------------------------------------------------------------------------
    override def toString: String = formatDefault
      def formatDefault: String =
        und.name }

  // ---------------------------------------------------------------------------
  object Key {
    implicit def _from(value: Symbol): Key = Key(und = value)
    implicit def _from(value: String): Key = Key(und = Symbol(value))

  }

// ===========================================================================
