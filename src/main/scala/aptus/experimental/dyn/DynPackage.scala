package aptus
package experimental

import scala.collection.View

// ===========================================================================
package object dyn
    extends apttraits.AptusNullShorthands
       with apttraits.AptusChaining
       with apttraits.AptusDummyImplicitShorthand
       with dyn.aliases.DynAliases                 /* eg type BasicType = _root_.gallia.basic.BasicType */
       with dyn.aliases.DynScalaAliases            /* eg private[dyn] type LocalTime     = java.time.LocalTime */
       with dyn.io.in.TopLevelBuildingUtils        /* eg dyn.single("""{"name": ..}"""") */
       with dyn.io.in.TupleBasedBuildingExtensions /* eg ("name": "Alice", "age" -> 30).dyn */
       with dyn.data.mult.iter.CloseabledIteratorTrait
     /* no AptusMinExtensions (since already under aptus) */ {

  // ===========================================================================
  trait HasTargetSelector { @abstrct protected def target: TargetSelector }

  // ---------------------------------------------------------------------------
  trait ValewGetter {
    @abstrct       protected[dyn] def get      (key: Key)               : Option[Valew]
    @nonovrd final protected      def getOrElse(key: Key, alt: => Valew):        Valew = get(key).getOrElse(alt) }

  // ===========================================================================
  val _group = "_group"

  // ===========================================================================
  object shorthands extends shorthands
    trait shorthands {

      // basic types
      val $$ = _root_.gallia.basic.BasicType

      // ---------------------------------------------------------------------------
      // selectors - TODO: worth keeping?
      object $p { def apply(p: SKeyPred)      : TargetSelector = TargetSelector.Predicate(p) }
      object $o { def apply(f: SKeys => SKey) : TargetSelector = TargetSelector.SelectOne(f) }
      object $m { def apply(f: SKeysSelection): TargetSelector = TargetSelector.SelectMultiple(f) } }

  // ===========================================================================
  implicit class Symbol___(u: Symbol) { // t241205112955 - do offer a Path+Ren, eg foo |> bar ~> BAR
      def  ~>(s: Key): Ren  = Ren(u, s); def  |>(s: Key): Path = Path(Seq(u), s)
      def ~~>(s: Key): Ren  = Ren(u, s); def ||>(s: Key): Path = Path(Seq(u), s) /* if need to avoid conflict with Gallia's */ }

    // ---------------------------------------------------------------------------
    implicit class String___(u: String) { // t241205112955 - do offer a Path+Ren, eg foo |> bar ~> BAR
      def  ~>(s: Key): Ren  = Ren(u, s); def  |>(s: Key): Path = Path(Seq(u), s)
      def ~~>(s: Key): Ren  = Ren(u, s); def ||>(s: Key): Path = Path(Seq(u), s) /* if need to avoid conflict with Gallia's */  }

  // ===========================================================================
  implicit class String__(val u: String) extends io.in.DynIoStringExtensions

  // ---------------------------------------------------------------------------
               implicit class EntriesSeq_ (values:  Seq[(SKey, NakedValue)]) {              def dyn: Dyn = Dyn.build(values.map(Entry._fromString)) }
  private[dyn] implicit class EntriesSeq2_(values:  Seq[(Key, NakedValue)])  { private[dyn] def dyn: Dyn = Dyn.build(values.map(Entry._fromKey)) }
  private[dyn] implicit class EntriesSeq3_(values:  Seq[Entry])              { private[dyn] def dyn: Dyn = Dyn.build(values) }

  // ---------------------------------------------------------------------------
  // will remove after (t241130165320 - refactor code borrowed from gallia)
  implicit class EntriesSeq2GalliaTmp_(values:  Seq[(Key, NakedValue)])  { def galliaDyn: Dyn = Dyn.build(values.map(Entry._fromKey))  }

  // ---------------------------------------------------------------------------
  private[dyn] implicit class Seq___ (values: Seq [Dyn]) {
    private[dyn] def dyns: Dyns = data.mult.list.Dyns.build(values)
    private[dyn] def dynz: Dynz = data.mult.iter.Dynz.build(values.iterator.toIteratoR)}

  // ---------------------------------------------------------------------------
  private[dyn] implicit class View___(values: View[Dyn]) {
    private[dyn] def dyns: Dyns = data.mult.list.Dyns.build(values.toList) }

  // ===========================================================================
  private[dyn] implicit class Iterator__(values: IteratoR[Dyn]) {
    private[dyn] def dyns: Dyns = data.mult.list.Dyns.build(values.toList)
    private[dyn] def dynz: Dynz = data.mult.iter.Dynz.build(values.toIteratoR) }
}


// ===========================================================================

