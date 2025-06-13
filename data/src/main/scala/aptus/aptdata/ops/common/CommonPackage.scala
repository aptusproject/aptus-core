package aptus
package aptdata
package ops

// ===========================================================================
/** alias Data = Dyn|Dyns|Dynz = Dyn|Mult = Sngl|Mult */
package object common {

  trait CommonHasTransformTargetSelectorTrait[Data] {
    // most important transformation, most actions use that
    @abstrct protected[aptdata] def transformTarget(either: TargetEither, f: ValueF): Data }

  // ===========================================================================
  trait HasIdent[Data] {
    @abstrct def ident: Data }

  // ---------------------------------------------------------------------------
  trait HasDataEntityErrorFormatter[Data] {
    /* MUST be a def here, for Dynz */
    @abstrct protected[aptdata] def deef: DataEntityErrorFormatter }

  // ---------------------------------------------------------------------------
  trait CanInferSchema[Data] {
    @abstrct protected def inferSchema(value: Data): Schema }

  // ===========================================================================
  trait CommonOutputter
      extends aptus                   .HasFormatJson
         with aptus.AptusWritingTraits.HasWriteOutputFile /* eg write to file */
         with CommonFormatDebug

    // ---------------------------------------------------------------------------
    trait CommonFormatDebug { def formatDebug: DebugString }

  // ===========================================================================
  trait AllCommons[Data]
       extends HasIdent[Data]
          with CommonFormatDebug
          with CommonOpsTrait        [Data] /* eg abstract retain */
          with CommonTransformTrait  [Data] /* eg transform(x).using(...) */
         	// TODO: t241205125816 - all the generates: generusion and generussion
          with CommonMoreTransforms  [Data] /* eg @nonovrd final transformString(x).using(...) */
          with CommonUpperCaseLikeOps[Data] /* eg @nonovrd final upperCase(x) */
          with CommonShorthands      [Data] /* eg @nonovrd final final convert(x).toInt */ {
      self: CommonHasTransformTargetSelectorTrait[Data] /* only: abstract transformTarget() */
          with HasDataEntityErrorFormatter [Data] => } }

// ===========================================================================