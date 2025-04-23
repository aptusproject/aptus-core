package aptus

import aptus.aptreflect.nodes.{TypeLeaf, TypeNode}

// ===========================================================================
package object aptdata extends AptdataPackage

// ---------------------------------------------------------------------------
package aptdata {
  trait AptdataPackage
    extends apttraits.AptusNullShorthands
       with apttraits.AptusDummyImplicitShorthand

       // ---------------------------------------------------------------------------
       with AptdataAnything // pipe & tap
       with AptusDataTraits // Date, BigDec, Fld, Info, _Optional, TypeMatching, ...
       with static.DynStaticToDynamic  /* eg Person("Bob", 30).toDynamic */

       // ---------------------------------------------------------------------------
       with aliases.DynAliases                 /* eg Dyn, Key, ... */
       with aliases.DynScalaAliases            /* eg Seq2D */
       with io.in.TopLevelBuildingUtils        /* eg dyn.single("""{"name": ..}"""") */
       with io.in.TupleBasedBuildingExtensions /* eg ("name": "Alice", "age" -> 30).dyn */
     /* no AptusMinExtensions (since already under aptus) */ {

  private[aptdata] implicit def _symbol2String(value: Symbol): String = value.name
  private[aptdata] implicit def _string2Symbol(value: String): Symbol = Symbol(value)

  // ===========================================================================
  /* indirection so TypeNode doesn't directly refer to Dyn */
  implicit class AptusDataTypeNode_(protected val diss: TypeNode) extends AptusDataTypeNode

  /* indirection so TypeLeaf doesn't directly refer to Dyn */
  implicit class AptusDataTypeLeaf_(protected val diss: TypeLeaf) extends AptusDataTypeLeaf

  // ===========================================================================
  private[aptdata] trait HasTargetSelector {
      @abstrct protected def target: TargetSelector }

    // ---------------------------------------------------------------------------
    private[aptdata] trait ValewGetter {
      @abstrct       protected[aptdata] def get      (key: Key)               : Option[Valew]
      @nonovrd final protected      def getOrElse(key: Key, alt: => Valew):        Valew = get(key).getOrElse(alt) }

  // ===========================================================================
  @publik val _group = "_group"

  // ===========================================================================
  object selectors {
    val TargetSelectorShorthands = aptdata.meta.selectors.TargetSelectorShorthands
    val TargetSelector           = aptdata.meta.selectors.TargetSelector }

  // ===========================================================================
  object shorthands extends shorthands
    trait shorthands {

      // basic types
      val $$ = aptus.aptdata.meta.basic.BasicType

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
  @publik implicit class DynIoStringExtensions_(val u: String) extends io.in.DynIoStringExtensions

  // ---------------------------------------------------------------------------
               implicit class EntriesSeq_ (values:  Seq[(SKey, NakedValue)]) {              def dyn: Dyn = Dyn.build(values.map(Entry._fromString)) }
  private[aptdata] implicit class EntriesSeq2_(values:  Seq[(Key, NakedValue)])  { private[aptdata] def dyn: Dyn = Dyn.build(values.map(Entry._fromKey)) }
  private[aptdata] implicit class EntriesSeq3_(values:  Seq[Entry])              { private[aptdata] def dyn: Dyn = Dyn.build(values) }

  // ---------------------------------------------------------------------------
  // will remove after (t241130165320 - refactor code borrowed from gallia)
  private [aptus] implicit class EntriesSeq2GalliaTmp_(values:  Seq[(Key, NakedValue)])  {
    def galliaDyn: Dyn = Dyn.build(values.map(Entry._fromKey))  }

  // ---------------------------------------------------------------------------
  private[aptdata] implicit class DynSeq_(values: Seq [Dyn]) {
    private[aptdata] def dyns: Dyns = mult.list.Dyns.build(values)
    private[aptdata] def dynz: Dynz = mult.iter.Dynz.build(CloseabledIterator.fromSeq(values)) }

  // ---------------------------------------------------------------------------
  private[aptdata] implicit class DynView_(values: collection.View[Dyn]) {
    private[aptdata] def dyns: Dyns = mult.list.Dyns.build(values.toList) }

  // ===========================================================================
  private[aptdata] implicit class DynIterator_(values: CloseabledIterator[Dyn]) {
    private[aptdata] def dyns: Dyns = mult.list.Dyns.build(values.toList)
    private[aptdata] def dynz: Dynz = mult.iter.Dynz.build(values) } } }

// ===========================================================================