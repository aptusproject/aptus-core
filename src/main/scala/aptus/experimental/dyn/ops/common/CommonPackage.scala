package aptus
package experimental
package dyn
package ops

// ===========================================================================
/** alias Data = Dyn|Dyns|Dynz = Dyn|Mult = Sngl|Mult */
package object common {

  trait CommonHasTransformTargetSelectorTrait[Data] {
    // most important transformation, most actions use that
    @abstrct protected[dyn] def transformTargetSelector(uber: TargetSelector, f: ValueF): Data }

  // ===========================================================================
  trait HasIdent[Data] {
    @abstrct def ident: Data }

  // ---------------------------------------------------------------------------
  trait HasDataEntityErrorFormatter[Data] {
    /* MUST be a def here, for Dynz */
    @abstrct protected[dyn] def deef: DataEntityErrorFormatter }

  // ---------------------------------------------------------------------------
  trait CanInferSchema[Data] {
    @abstrct protected def inferSchema(value: Data): Schema }

  // ===========================================================================
  trait CommonOutputter
      extends aptus                   .HasFormatJson
         with aptus.AptusWritingTraits.HasWriteOutputFile /* eg write to file */

  // ===========================================================================
  trait AllCommons[Data]
       extends HasIdent[Data]
          with CommonOpsTrait        [Data] /* eg abstract retain */
          with CommonTransformTrait  [Data] /* eg transform(x).using(...) */
         	// TODO: t241205125816 - all the generates: generusion and generussion
          with CommonMoreTransforms  [Data] /* eg @nonovrd final transformString(x).using(...) */
          with CommonUpperCaseLikeOps[Data] /* eg @nonovrd final upperCase(x) */
          with CommonShorthands      [Data] /* eg @nonovrd final final convert(x).toInt */ {
      self: CommonHasTransformTargetSelectorTrait[Data] /* only: abstract transformTargetSelector() */
          with HasDataEntityErrorFormatter [Data] => } }

// ===========================================================================