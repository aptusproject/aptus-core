package aptus
package aptdata
package accessors

// ===========================================================================
private[aptdata] trait ValewGetterTextAccessors {
    self: ValewGetter with ValewGetterBasicAccessors => // TODO

  // ===========================================================================
  def texts (target: NoRenarget): Seq[StringValue] = texts_(target).getOrElse(Nil)
  def texts_(target: NoRenarget): Ons[StringValue] =
    target.fold3[Ons[StringValue]]
      { key => self.get(key).map(_.texts) }
      { head => tail =>
          self.get(head).flatMap { valew =>
            valew.fold3b[Seq[StringValue]]
              { _.texts_(tail) }
              { _ => None /* supposed to be nesting here, so ignore basic values */ }
              { _.flatten } } }

  // ===========================================================================
  // to be cleanedup/migrated
  //def texts$_(key: KeyW): Option[Nes[StringValue]] = anys$_(key).map(_.map(_.str))
  def texts$$ (key: Key)  : Seq[StringValue] = ???   //anys$$(key)      .map(_.str)
  def texts$$2(path: Path): Nes[StringValue] = ??? } // ObjUNesting.scala:def texts$$2(pnk: PotentiallyNestedKey) = dis._plumbing.nestingAccess$$2(pnk)(_.accessors.texts$$)

// ===========================================================================