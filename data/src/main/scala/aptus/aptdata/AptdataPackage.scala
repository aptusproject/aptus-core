package aptus

import aptus.aptreflect.nodes.{TypeLeaf, TypeNode}

// ===========================================================================
package object aptdata extends AptdataPackage

// ---------------------------------------------------------------------------
package aptdata {

  trait AptdataPackage
    extends AptdataTrait
       with apttraits.AptusNullShorthands
       with apttraits.AptusDummyImplicitShorthand

       // ---------------------------------------------------------------------------
       with AptdataChaining // pipe & tap
       with AptusDataTraits // Date, BigDec, Fld, Info, _Optional, TypeMatching, ...
       with static.DynStaticToDynamic  /* eg Person("Bob", 30).toDynamic */

       // ---------------------------------------------------------------------------
       with aliases.DynAliases                 /* eg Dyn, Key, ... */
       with aliases.DynScalaAliases            /* eg Seq2D */
       with io.in.TopLevelBuildingUtils        /* eg dyn.single("""{"name": ..}"""") */
       with io.in.TupleBasedBuildingExtensions /* eg ("name": "Alice", "age" -> 30).dyn */
     /* no AptusMinExtensions (since already under aptus) */ {

  // ---------------------------------------------------------------------------
  case class Trio(
        start: String,
        sep  : String,
        end  : String) {
      def format2(values: Seq[String]): String =
        values.mkString(start, sep, end) }

    // ---------------------------------------------------------------------------
    object Trio {
      val Json    = Trio("[", ",", "]")
      val Default = Json }

  // ===========================================================================
  private[aptdata] type AnyValue = Any

  // ===========================================================================
  /* indirection so TypeNode doesn't directly refer to Dyn */
  implicit class AptusDataTypeNode_(protected val diss: TypeNode) extends AptusDataTypeNode

  /* indirection so TypeLeaf doesn't directly refer to Dyn */
  implicit class AptusDataTypeLeaf_(protected val diss: TypeLeaf) extends AptusDataTypeLeaf

  // ===========================================================================
  /* indirection so Cls doesn't directly refer to Dyn */
  implicit class AptusDataCls_(diss: Cls) {
      def toDyn: Dyn = meta.converter.MetaToDataConverter.clsToDyn(diss) }

    // ---------------------------------------------------------------------------
    /* indirection so Cls doesn't directly refer to Dyn */
    implicit class AptusDataClsType_(diss: Cls.type) {
      def fromDyn(value: Dyn): Cls =
        meta.converter.DataToMetaConverter.dynToCls(value) }

  // ===========================================================================
  private[aptdata] trait HasTargetSelector {
      @abstrct protected def target: TargetEither }

    // ---------------------------------------------------------------------------
    private[aptdata] type TargetData = aptus.aptdata.meta.selectors.td.TargetData
    private[aptdata] val  TargetData = aptus.aptdata.meta.selectors.td.TargetData

    // ---------------------------------------------------------------------------
    private[aptdata] type TargetEitheR = Either[TargetSelector, Targets]
    private[aptdata] type TargetEither = Either[TargetSelector, TargetData]

    // ---------------------------------------------------------------------------
    implicit class TargetData_ (protected val self: TargetData) extends meta.selectors.td.TargetDataOps

  // ===========================================================================
  private[aptdata] trait ValewGetter {
    @abstrct       protected[aptdata] def get          (key: Key)               : Option[Valew]
    @nonovrd final protected          def getOrElse    (key: Key, alt: => Valew):        Valew       = get(key).getOrElse(alt)
    @nonovrd final protected          def getNakedValue(key: Key)               : Option[NakedValew] = get(key).map(_.naked) }

  // ===========================================================================
  private[aptdata] val _group = "_group"

  // ===========================================================================
  object selectors {
      val TargetSelectorShorthands = aptdata.meta.selectors.TargetSelectorShorthands
      val TargetSelector           = aptdata.meta.selectors.TargetSelector }

    // ---------------------------------------------------------------------------
    object shorthands extends shorthands
      trait shorthands {
        /** basic types */
        val $$ = aptus.aptdata.meta.basic.BasicType }

  // ===========================================================================
  @publik implicit class AptDataString_(val u: String) extends io.in.DynIoStringExtensions {
      /** will work for all but rename & remove (who don't use Target but have explicit Guaranteed counterparts) */
      def guaranteePresence: Targets = meta.selectors.Target.explicit(u).guaranteePresence }

    // ---------------------------------------------------------------------------
    @publik implicit class AptDataSymbol_(val u: Symbol) {
      /** will work for all but rename & remove (who don't use Target but have explicit Guaranteed counterparts) */
      def guaranteePresence: Targets = meta.selectors.Target.explicit(u).guaranteePresence }

    // ---------------------------------------------------------------------------
    @publik implicit class AptDataEnumEntry_(val u: enumeratum.EnumEntry) {
      /** will work for all but rename & remove (who don't use Target but have explicit Guaranteed counterparts) */
      def guaranteePresence: Targets = meta.selectors.Target.explicit(u).guaranteePresence }

  // ===========================================================================
                   implicit class EntriesSeq_ (values:  Seq[(SKey, NakedValue)]) {                  def dyn: Dyn = Dyn.build(values.map(Entry._fromString)) }
  private[aptdata] implicit class EntriesSeq2_(values:  Seq[(Key, NakedValue)])  { private[aptdata] def dyn: Dyn = Dyn.build(values.map(Entry._fromKey)) }
  private[aptdata] implicit class EntriesSeq3_(values:  Seq[Entry])              { private[aptdata] def dyn: Dyn = Dyn.build(values) }

  // ---------------------------------------------------------------------------
  private[aptdata] implicit class DynSeq_(values: Seq [Dyn]) {
    private[aptdata] def dyns: Dyns = mult.list.Dyns.build(values)
    private[aptdata] def dynz: Dynz = mult.iter.Dynz.build(CloseabledIterator.fromSeq(values)) }

  // ---------------------------------------------------------------------------
  private[aptdata] implicit class DynView_(values: collection.View[Dyn]) {
    private[aptdata] def dyns: Dyns = mult.list.Dyns.build(values.toList) }

  // ===========================================================================
  private[aptdata] implicit class DynIterator_(values: CloseabledIterator[Dyn]) {
    private[aptdata] def dyns: Dyns = mult.list.Dyns.build(values.consumeAll())
    private[aptdata] def dynz: Dynz = mult.iter.Dynz.build(values) } } }

// ===========================================================================
