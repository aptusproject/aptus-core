package aptus
package experimental
package dyn
package ops
package common

// ===========================================================================
/** generated, don't edit directly */
trait CommonUpperCaseLikeOps[Data] { // xzy: all final no override, all use transform(uber)
    self: CommonTransformTrait[Data] =>

  // TODO: t241204111122 - more such ops
     // booleans: flip
     // numbers: square, sqrt, log, maxDecimals
     // string: reverseString, unquote
     // arrays: toSize, toSum, toMean, toStdev
     // time: addDay, addMinute, ...

  @inline private def _upperCase(uber: TargetSelector): Data = transform(uber).ifString(_.toUpperCase)
  @inline private def _lowerCase(uber: TargetSelector): Data = transform(uber).ifString(_.toLowerCase)

  @inline private def _increment(uber: TargetSelector): Data = transform(uber).integerLike(_ + 1)
  @inline private def _decrement(uber: TargetSelector): Data = transform(uber).integerLike(_ - 1)

  @inline private def _floor    (uber: TargetSelector): Data = transform(uber).realLike { _.toDouble.pipe(Math.floor).toInt }
  @inline private def _ceiling  (uber: TargetSelector): Data = transform(uber).realLike { _.toDouble.pipe(Math.ceil) .toInt }

  // ===========================================================================
  // codegened (see 241121238316)

@nonovrd final def upperCaseGuaranteed(key : Key ): Data =  upperCase(_.explicitKey(key).guaranteePresence)
  final def upperCaseIfPresent (key : Key ): Data =  upperCase(_.explicitKey(key).mayBeMissing)
  final def upperCase          (key : Key ): Data =  upperCase(_.explicitKey(key))
  final def upperCase          (ren : Ren ): Data =  upperCase(_.renaming(ren))
  final def upperCase          (path: Path): Data =  upperCase(_.nesting (path))
@nonovrd final def upperCase          (sel : Sel ): Data =  upperCase(Sel(sel))
@nonovrd final def upperCase          (uber: TargetSelector): Data = _upperCase(uber)

  // ---------------------------------------------------------------------------
  final def lowerCaseGuaranteed(key : Key ): Data =  lowerCase(_.explicitKey(key).guaranteePresence)
  final def lowerCaseIfPresent (key : Key ): Data =  lowerCase(_.explicitKey(key).mayBeMissing)
final def lowerCase          (key : Key ): Data =  lowerCase(_.explicitKey(key))
  final def lowerCase          (ren : Ren ): Data =  lowerCase(_.renaming(ren))
  final def lowerCase          (path: Path): Data =  lowerCase(_.nesting (path))
  final def lowerCase          (sel : Sel ): Data =  lowerCase(Sel(sel))
  final def lowerCase          (uber: TargetSelector): Data = _lowerCase(uber)

  // ---------------------------------------------------------------------------
  final def incrementGuaranteed(key : Key ): Data =  increment(_.explicitKey(key).guaranteePresence)
  final def incrementIfPresent (key : Key ): Data =  increment(_.explicitKey(key).mayBeMissing)
  final def increment          (key : Key ): Data =  increment(_.explicitKey(key))
  final def increment          (ren : Ren ): Data =  increment(_.renaming(ren))
  final def increment          (path: Path): Data =  increment(_.nesting (path))
  final def increment          (sel : Sel ): Data =  increment(Sel(sel))
  final def increment          (uber: TargetSelector): Data = _increment(uber)

  // ---------------------------------------------------------------------------
  final def decrementGuaranteed(key : Key ): Data =  decrement(_.explicitKey(key).guaranteePresence)
  final def decrementIfPresent (key : Key ): Data =  decrement(_.explicitKey(key).mayBeMissing)
  final def decrement          (key : Key ): Data =  decrement(_.explicitKey(key))
  final def decrement          (ren : Ren ): Data =  decrement(_.renaming(ren))
  final def decrement          (path: Path): Data =  decrement(_.nesting (path))
  final def decrement          (sel : Sel ): Data =  decrement(Sel(sel))
  final def decrement          (uber: TargetSelector): Data = _decrement(uber)

  // ---------------------------------------------------------------------------
  final def floorGuaranteed(key : Key ): Data =  floor(_.explicitKey(key).guaranteePresence)
  final def floorIfPresent (key : Key ): Data =  floor(_.explicitKey(key).mayBeMissing)
  final def floor          (key : Key ): Data =  floor(_.explicitKey(key))
  final def floor          (ren : Ren ): Data =  floor(_.renaming(ren))
  final def floor          (path: Path): Data =  floor(_.nesting (path))
  final def floor          (sel : Sel ): Data =  floor(Sel(sel))
  final def floor          (uber: TargetSelector): Data = _floor(uber)

  // ---------------------------------------------------------------------------
  final def ceilingGuaranteed(key : Key ): Data =  ceiling(_.explicitKey(key).guaranteePresence)
  final def ceilingIfPresent (key : Key ): Data =  ceiling(_.explicitKey(key).mayBeMissing)
  final def ceiling          (key : Key ): Data =  ceiling(_.explicitKey(key))
  final def ceiling          (ren : Ren ): Data =  ceiling(_.renaming(ren))
  final def ceiling          (path: Path): Data =  ceiling(_.nesting (path))
  final def ceiling          (sel : Sel ): Data =  ceiling(Sel(sel))
  final def ceiling          (uber: TargetSelector): Data = _ceiling(uber) }

// ===========================================================================
