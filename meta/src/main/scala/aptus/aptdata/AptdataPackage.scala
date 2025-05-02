package aptus

import aptus.aptdata.meta.selectors._

// ===========================================================================
package object aptdata {

  trait ApdataKey_ { protected val name: String // t241205112955 - do offer a Path+Ren, eg foo |> bar ~> BAR
       /* if need to avoid conflict with Gallia's */
        def ~~>(s: Key): Ren  = Ren(name, s); def ||>(s: Key): Path = Path(Seq(name), s); def ||>(s: Ren): RPath = RPath(Seq(name), s)

        // ---------------------------------------------------------------------------
        def  ~>(s: Key): Ren  = Ren(name, s); def  |>(s: Key): Path = Path(Seq(name), s); def  |>(s: Ren): RPath = RPath(Seq(name), s) }

    // ===========================================================================
    implicit class ApdataString_   (protected val name: String) extends ApdataKey_
    implicit class ApdataSymbol_   (diss: Symbol)               extends ApdataKey_ { protected val name = diss.name }
    implicit class ApdataEnumEntry_(diss: enumeratum.EnumEntry) extends ApdataKey_ { protected val name = diss.entryName } }

// ===========================================================================