package aptus
package aptdata
package ops
package sngl

// ===========================================================================
trait SingleRemoveRetainOps { sngl: Dyn =>

  override final protected[ops] def _retain(target: TargetEither): Dyn = _fold(target)(_ retain _)

  // ---------------------------------------------------------------------------
  override final def retainMultiple(values: Set[Key]): Dyn = data.filter(x => values.contains(x.key)).dyn
  override final def retainKey     (key   : Key)     : Dyn = data.filter(_.key == key).dyn

  // ---------------------------------------------------------------------------
  override final protected[ops] def _remove(target: TargetEither): Dyn = _fold(target)(_ remove _)

  // ---------------------------------------------------------------------------
  override final def removeMultiple(values: Set[Key]): Dyn = data.filterNot(x => values.contains(x.key)).dyn
  override final def removeKey     (value   : Key)   : Dyn = data.filterNot(_.key == value).dyn }

// ===========================================================================