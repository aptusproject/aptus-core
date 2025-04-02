package aptus
package experimental
package dyn
package ops
package common

// ===========================================================================
trait CommonTransformTrait[Data] {
    self: CommonHasTransformTargetSelectorTrait[Data]
      with HasDataEntityErrorFormatter[Data] => /* only: abstract transformTargetSelector() */
  private   type Innie = CommonTransformByTypesHelperTrait[Data]
  private   val  diss  = this

  // ---------------------------------------------------------------------------
// TODO:t241205125816 - all the generates: generusion and generussion
  def generate(x: Any): _GenerateFrom = new _GenerateFrom(x)
    final class _GenerateFrom private[dyn] (x: Any) { def from(y: Any): _GenerateFromUsing = new _GenerateFromUsing(x) }
      final class _GenerateFromUsing private[dyn] (x: Any) { def using(f: Valew => Any): Data = ??? }

  // ===========================================================================
  @inline private def innie(_target: TargetSelector): Innie = new _Innie(diss.deef, _target)

    private class _Innie private[common](val deef: DataEntityErrorFormatter, val target: TargetSelector)
        extends CommonTransformByTypesHelperTrait[Data]
           with HasDataEntityErrorFormatter[Data]
           with HasTargetSelector {
      override final def using(f: ValueF): Data = transformTargetSelector(target, f) // main transformer <--------------------

      /** favor explicit `.using(f)` version rather (but good for quick development/test) */
      @inline @nonovrd final def apply(f: ValueF): Data = using(f) }

  // ===========================================================================
@nonovrd final def transform(key : Key ): Innie = transform(TargetSelector.Explicit(key))
  @nonovrd final def transform(ren : Ren ): Innie = transform(TargetSelector.Renaming(ren))
  @nonovrd final def transform(path: Path): Innie = transform(TargetSelector.Nesting(path))
  @nonovrd final def transform(sel : Sel ): Innie = transform(Sel(sel))
  @nonovrd final def transform(target: TargetSelector): Innie = innie(target)

  @nonovrd final def transformGuaranteed(key : Key ): Innie = transformGuaranteed(TargetSelector.Explicit(key).guaranteePresence)
  @nonovrd final def transformGuaranteed(ren : Ren ): Innie = transformGuaranteed(TargetSelector.Renaming(ren).guaranteePresence)
  @nonovrd final def transformGuaranteed(path: Path): Innie = transformGuaranteed(TargetSelector.Nesting(path).guaranteePresence)
  @nonovrd final def transformGuaranteed(sel : Sel ): Innie = transformGuaranteed(Sel(sel).guaranteePresence)
  @nonovrd final def transformGuaranteed(target: TargetSelector): Innie = transform          (target.guaranteePresence)

  @nonovrd final def transformIfPresent(key : Key ): Innie = transformIfPresent(TargetSelector.Explicit(key).mayBeMissing /* default */)
  @nonovrd final def transformIfPresent(ren : Ren ): Innie = transformIfPresent(TargetSelector.Renaming(ren).mayBeMissing /* default */)
  @nonovrd final def transformIfPresent(path: Path): Innie = transformIfPresent(TargetSelector.Nesting(path).mayBeMissing /* default */)
  @nonovrd final def transformIfPresent(sel : Sel ): Innie = transformIfPresent(Sel(sel).mayBeMissing /* default */)
  @nonovrd final def transformIfPresent(target: TargetSelector): Innie = transform          (target.mayBeMissing /* default */)
}

// ===========================================================================
