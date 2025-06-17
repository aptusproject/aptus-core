package aptus
package aptdata
package accessors

// ===========================================================================
private[aptdata] trait ValewGetterNestingAccessors { self: ValewGetterBasicAccessors => /* only need typed TODO */

  def nesting  (target: NoRenarget): One[Dyn] = nesting_ (target).forceOrError(target)
  def nestings (target: NoRenarget): Nes[Dyn] = nestings_(target).forceOrError(target)

  // ---------------------------------------------------------------------------
  def nesting_ (target: NoRenarget): Opt[Dyn] = typed_[    Dyn ](target)(_.nesting)        (_ nesting_  _)
  def nestings_(target: NoRenarget): Ons[Dyn] = typed_[Seq[Dyn]](target)(_.nestings.values)(_ nestings_ _)

  // ===========================================================================
  // to be cleanedup/migrated
  def objs$_(key: Key): Option[Nes[Dyn]] = ???//values$_[Obj](key)
  def objs$$(key: Key):        Seq[Dyn]  = ???//values$$[Obj](key)
  def objs$$2_(path: Path): Ons[Dyn]     = ???
  def objs$$2 (path: Key) : Nes[Dyn]     = ??? // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

  def objs$$2 (path: Path): Nes[Dyn]  = ??? } // dis._plumbing.nestingAccess$$2(pnk)(_.accessors.objs$$)

// ===========================================================================