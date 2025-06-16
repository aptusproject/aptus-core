package aptus
package aptdata
package ops
package common

// ===========================================================================
/** generated, don't edit directly */
trait CommonUpperCaseLikeOps[Data] { // xzy: all final no override, all use transform(target)
    self: CommonTransformTrait[Data] =>

  // TODO: t241204111122 - more such ops
     // booleans: flip
     // numbers: square, sqrt, log, maxDecimals
     // string: reverseString, unquote
     // arrays: toSize, toSum, toMean, toStdev
     // time: addDay, addMinute, ...

  def upperCase(targets: TargetEitheR): Data = targets.fold(innie, transform).ifString(_.toUpperCase)
  def lowerCase(targets: TargetEitheR): Data = targets.fold(innie, transform).ifString(_.toLowerCase)
  def increment(targets: TargetEitheR): Data = targets.fold(innie, transform).integerLike(_ + 1)
  def decrement(targets: TargetEitheR): Data = targets.fold(innie, transform).integerLike(_ - 1)
  def floor    (targets: TargetEitheR): Data = targets.fold(innie, transform).realLike { _.toDouble.pipe(Math.floor).toInt }
  def ceiling  (targets: TargetEitheR): Data = targets.fold(innie, transform).realLike { _.toDouble.pipe(Math.ceil) .toInt }

  // ===========================================================================
  // codegened (see 250605161806)

  @nonovrd final def upperCase          (sel: Sel)                      : Data = upperCase(Sel(sel).in.left)
  @nonovrd final def upperCase          (targets: Targets)              : Data = upperCase(targets.in.right)
  @nonovrd final def upperCase          (target1: Target, more: Target*): Data = upperCase(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def upperCaseGuaranteed(target1: Target, more: Target*): Data = upperCase(Targets(target1 +: more, guaranteed = true ).in.right)

// TODO: regenerate below
  @nonovrd final def lowerCase          (sel: Sel)                      : Data = lowerCase(Sel(sel).in.left)
  @nonovrd final def lowerCase          (target1: Target, more: Target*): Data = lowerCase(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def lowerCaseIfPresent (target1: Target, more: Target*): Data = lowerCase(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def lowerCaseGuaranteed(target1: Target, more: Target*): Data = lowerCase(Targets(target1 +: more, guaranteed = true).in.right)

  // ---------------------------------------------------------------------------
  @nonovrd final def increment          (sel: Sel)                      : Data = increment(Sel(sel).in.left)
  @nonovrd final def increment          (target1: Target, more: Target*): Data = increment(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def incrementIfPresent (target1: Target, more: Target*): Data = increment(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def incrementGuaranteed(target1: Target, more: Target*): Data = increment(Targets(target1 +: more, guaranteed = true).in.right)

  @nonovrd final def decrement          (sel: Sel)                      : Data = decrement(Sel(sel).in.left)
  @nonovrd final def decrement          (target1: Target, more: Target*): Data = decrement(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def decrementIfPresent (target1: Target, more: Target*): Data = decrement(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def decrementGuaranteed(target1: Target, more: Target*): Data = decrement(Targets(target1 +: more, guaranteed = true).in.right)

  // ---------------------------------------------------------------------------
  @nonovrd final def floor          (sel: Sel)                      : Data = floor(Sel(sel).in.left)
  @nonovrd final def floor          (target1: Target, more: Target*): Data = floor(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def floorIfPresent (target1: Target, more: Target*): Data = floor(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def floorGuaranteed(target1: Target, more: Target*): Data = floor(Targets(target1 +: more, guaranteed = true).in.right)

  // ---------------------------------------------------------------------------
  @nonovrd final def ceiling          (sel: Sel)                      : Data = ceiling(Sel(sel).in.left)
  @nonovrd final def ceiling          (target1: Target, more: Target*): Data = ceiling(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def ceilingIfPresent (target1: Target, more: Target*): Data = ceiling(Targets(target1 +: more, guaranteed = false).in.right)
  @nonovrd final def ceilingGuaranteed(target1: Target, more: Target*): Data = ceiling(Targets(target1 +: more, guaranteed = true).in.right) }

// ===========================================================================
