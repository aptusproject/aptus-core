package aptus
package aptdata
package ops
package common

import domain.{IntegerLike, RealLike}
import CommonMoreTransformsUtils.{_TransformType, _TransformIfType}

// ===========================================================================
trait CommonMoreTransforms[Data] {
    self: CommonTransformTrait[Data] /* eg transform(x).string(...) */
       with common.CommonHasTransformTargetSelectorTrait[Data]
       with HasDataEntityErrorFormatter[Data] =>
  import Error.TransformSpecificType

  // ===========================================================================
  @nonovrd final def transformSole(f: Valew => NakedValue): Data = transform(_.soleKey).using(f)

    @PartialTypeMatching
    @nonovrd final def transformSoleBoolean(f: Boolean => NakedValue): Data = transformSole(_.booleanx.map(f))
    @nonovrd final def transformSoleInt    (f: Int     => NakedValue): Data = transformSole(_.intx    .map(f))
    @nonovrd final def transformSoleDouble (f: Double  => NakedValue): Data = transformSole(_.doublex .map(f))
    @nonovrd final def transformSoleString (f: String  => NakedValue): Data = transformSole(_.stringx .map(f))
    // only these 4

  // ===========================================================================
  // nesting

  // ---------------------------------------------------------------------------
  @nonovrd final def transformNesting(sel: Sel)                      : _TransformNesting = new _TransformNesting(Sel(sel).in.left)
  @nonovrd final def transformNesting(targets: Targets)              : _TransformNesting = new _TransformNesting(targets.in.right)
  @nonovrd final def transformNesting(target1: Target, more: Target*): _TransformNesting = transformNesting(Targets(target1 +: more, guaranteed = false))

  @nonovrd final def transformNestings(sel: Sel)                      : _TransformNestings = new _TransformNestings(Sel(sel).in.left)
  @nonovrd final def transformNestings(targets: Targets)              : _TransformNestings = new _TransformNestings(targets.in.right)
  @nonovrd final def transformNestings(target1: Target, more: Target*): _TransformNestings = transformNestings(Targets(target1 +: more, guaranteed = false))

    // ---------------------------------------------------------------------------
    class _TransformNesting(targets: TargetEitheR) {
      def using(f: Dyn => NakedValue): Data =
        targets.map(TargetData.parse).pipe { x => self.transformTarget(x,
          _.nestingOpt.map(f).getOrElse(TransformSpecificType(deef, x, sngl.Dyn).thro)) } }

    // ---------------------------------------------------------------------------
    class _TransformNestings(targets: TargetEitheR) { // no nested Dynz (see a241119155444)
      def using(f: Dyns => NakedValue): Data =
        targets.map(TargetData.parse).pipe { x => self.transformTarget(x,
          _.nestingsOpt.map(f).getOrElse(TransformSpecificType(deef, x, sngl.Dyn).thro)) } }

  // ===========================================================================
  @nonovrd final def transformBoolean(target1: Target, more: Target*): _TransformBoolean = new _TransformBoolean(Targets(target1 +: more, guaranteed = false))
  @nonovrd final def transformInt    (target1: Target, more: Target*): _TransformInt     = new _TransformInt    (Targets(target1 +: more, guaranteed = false))
  @nonovrd final def transformDouble (target1: Target, more: Target*): _TransformDouble  = new _TransformDouble (Targets(target1 +: more, guaranteed = false))
  @nonovrd final def transformString (target1: Target, more: Target*): _TransformString  = new _TransformString (Targets(target1 +: more, guaranteed = false))
  // only these 4

  @nonovrd final def transformBoolean(targets: Targets): _TransformBoolean = new _TransformBoolean(targets)
  @nonovrd final def transformInt    (targets: Targets): _TransformInt     = new _TransformInt    (targets)
  @nonovrd final def transformDouble (targets: Targets): _TransformDouble  = new _TransformDouble (targets)
  @nonovrd final def transformString (targets: Targets): _TransformString  = new _TransformString (targets)

    // ---------------------------------------------------------------------------
    final class _TransformString  private[aptdata] (targets: Targets) { def using(f: String  => NakedValue): Data = transform(targets).string(f) }
    final class _TransformInt     private[aptdata] (targets: Targets) { def using(f: Int     => NakedValue): Data = transform(targets).int    (f) }
    final class _TransformBoolean private[aptdata] (targets: Targets) { def using(f: Boolean => NakedValue): Data = transform(targets).boolean(f) }
    final class _TransformDouble  private[aptdata] (targets: Targets) { def using(f: Double  => NakedValue): Data = transform(targets).double (f) }

  // ---------------------------------------------------------------------------
  @nonovrd final def transformIfBoolean(target1: Target, more: Target*): _TransformIfBoolean = new _TransformIfBoolean(Targets(target1 +: more, guaranteed = false))
  @nonovrd final def transformIfInt    (target1: Target, more: Target*): _TransformIfInt     = new _TransformIfInt    (Targets(target1 +: more, guaranteed = false))
  @nonovrd final def transformIfDouble (target1: Target, more: Target*): _TransformIfDouble  = new _TransformIfDouble (Targets(target1 +: more, guaranteed = false))
  @nonovrd final def transformIfString (target1: Target, more: Target*): _TransformIfString  = new _TransformIfString (Targets(target1 +: more, guaranteed = false))

  @nonovrd final def transformIfBoolean(targets: Targets): _TransformIfBoolean = new _TransformIfBoolean(targets)
  @nonovrd final def transformIfInt    (targets: Targets): _TransformIfInt     = new _TransformIfInt    (targets)
  @nonovrd final def transformIfDouble (targets: Targets): _TransformIfDouble  = new _TransformIfDouble (targets)
  @nonovrd final def transformIfString (targets: Targets): _TransformIfString  = new _TransformIfString (targets)
  // only these 4

    // ---------------------------------------------------------------------------
    final class _TransformIfString  private[aptdata] (targets: Targets) { def using(f: String  => NakedValue): Data = transform(targets).ifString(f) }
    final class _TransformIfInt     private[aptdata] (targets: Targets) { def using(f: Int     => NakedValue): Data = transform(targets).ifInt    (f) }
    final class _TransformIfBoolean private[aptdata] (targets: Targets) { def using(f: Boolean => NakedValue): Data = transform(targets).ifBoolean(f) }
    final class _TransformIfDouble  private[aptdata] (targets: Targets) { def using(f: Double  => NakedValue): Data = transform(targets).ifDouble (f) }

// TODO: if IntLike...?

  // ===========================================================================
  @nonovrd def transformType(basicType: BasicType)(target1: Target, more: Target*): _TransformType[Data, basicType.T] =
      new _TransformType[Data, basicType.T](self, basicType, Targets(target1 +: more, guaranteed = false))

    // ---------------------------------------------------------------------------
    @nonovrd def transformIfType(basicType: BasicType)(target1: Target, more: Target*): _TransformIfType[Data, basicType.T] =
      new _TransformIfType[Data, basicType.T](self, basicType, Targets(target1 +: more, guaranteed = false))

    // ---------------------------------------------------------------------------
    @nonovrd def transformType(basicType: BasicType, targets: Targets): _TransformType[Data, basicType.T] =
      new _TransformType[Data, basicType.T](self, basicType, targets)

    // ---------------------------------------------------------------------------
    @nonovrd def transformIfType(basicType: BasicType, targets: Targets): _TransformIfType[Data, basicType.T] =
      new _TransformIfType[Data, basicType.T](self, basicType, targets)

  // ===========================================================================
  @nonovrd final def transformIntegerLike[T](target: TargetSelector)(f: IntegerLike[_] => T): Data = _transformIntegerLike[T](target: TargetSelector)(f)
  @nonovrd final def transformRealLike   [T](target: TargetSelector)(f:    RealLike[_] => T): Data = _transformRealLike   [T](target: TargetSelector)(f)

    // ---------------------------------------------------------------------------
    @inline private def _transformIntegerLike[T](target: TargetSelector)(f: IntegerLike[_] => T): Data =
      innie(target).using { _.integerLikeOpt.map(f).getOrElse {
        TransformSpecificType(deef, target.in.left, IntegerLike).thro /* including Double/Float - see a241125115501 */ } }

    // ---------------------------------------------------------------------------
    @inline private def _transformRealLike[T](target: TargetSelector)(f: RealLike[_] => T): Data =
      innie(target).using { _.realLikeOpt.map(f).getOrElse {
        TransformSpecificType(deef, target.in.left, RealLike).thro } } }

// ===========================================================================
