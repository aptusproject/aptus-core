package aptus
package aptdata
package sngl

// ===========================================================================
trait DynValewPresenceAbsence { self: DynData with DynValewGetter =>
  def isPresent         (target: NoRenarget): Boolean =  isSometimesPresent(target)
  def isSometimesPresent(target: NoRenarget): Boolean =  target.fold(keySet.contains)(containsPath(total = false))
  def isAlwaysPresent   (target: NoRenarget): Boolean =  target.fold(keySet.contains)(containsPath(total = true))
  def isAbsent          (target: NoRenarget): Boolean = !isSometimesPresent(target)

  // ---------------------------------------------------------------------------
  protected def containsPath(total: Boolean)(target: Path): Boolean = {
    target.fold[Boolean](containsKey) {
      (root, tailpath) => get(root)
        .map { _.fold3
          { _.containsPath(total)(tailpath) }
          { _ => false }
          { x => if (total) x.forall(identity) else x.exists(identity) } }
        .getOrElse(false) } } }

// ===========================================================================

