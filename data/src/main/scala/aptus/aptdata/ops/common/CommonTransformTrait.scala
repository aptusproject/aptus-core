package aptus
package aptdata
package ops
package common

// ===========================================================================
trait CommonTransformTrait[Data] {
    self: CommonHasTransformTargetSelectorTrait[Data]
      with HasDataEntityErrorFormatter[Data] => /* only: abstract transformTarget() */
  private   type Innie = CommonTransformByTypesHelperTrait[Data]
  private   val  diss  = this

  // ---------------------------------------------------------------------------
// TODO:t241205125816 - all the generates: generusion and generussion
  def generate(x: Any): _GenerateFrom = new _GenerateFrom(x)
    final class _GenerateFrom private[aptdata] (x: Any) { def from(y: Any): _GenerateFromUsing = new _GenerateFromUsing(x) }
      final class _GenerateFromUsing private[aptdata] (x: Any) { def using(f: Valew => Any): Data = ??? }

  // ===========================================================================
  @inline private[common] def innie(_target: TargetSelector): Innie = new _Innie(diss.deef, Left(_target))
  @inline private[common] def innie(td: TargetData)         : Innie = new _Innie(diss.deef, Right(td))

    // ---------------------------------------------------------------------------
    private[common] class _Innie private[common](val deef: DataEntityErrorFormatter, val target: TargetEither)
        extends CommonTransformByTypesHelperTrait[Data]
           with HasDataEntityErrorFormatter[Data]
           with HasTargetSelector {
      override final def using(f: ValueF): Data = transformTarget(target, f) // main transformer <--------------------

      /** favor explicit `.using(f)` version rather (but good for quick development/test) */
      @inline @nonovrd final def apply(f: ValueF): Data = using(f) }

  // ===========================================================================
  @nonovrd final def transform(target1: Target, more: Target*): Innie = transform(target1 +~ more)
  @nonovrd final def transform(targets: Targets)              : Innie = innie(TargetData.parse(targets))
  @nonovrd final def transform(sel: Sel)                      : Innie = innie(Sel(sel))

  // ---------------------------------------------------------------------------
  @nonovrd final def transformGuaranteed(target1: Target, more: Target*): Innie = transformGuaranteed(target1 +! more)
  @nonovrd final def transformGuaranteed(targets: Targets)              : Innie = targets .guaranteePresence.pipe(TargetData.parse).pipe(innie)
  @nonovrd final def transformGuaranteed(sel: Sel)                      : Innie = Sel(sel).guaranteePresence.pipe(innie) }

// ===========================================================================
