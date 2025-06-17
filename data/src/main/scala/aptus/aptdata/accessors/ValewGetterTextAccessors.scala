package aptus
package aptdata
package accessors

import lowlevel.AnyValueFormatter.formatLeaf

// ===========================================================================
private[aptdata] trait ValewGetterTextAccessors {
    self: ValewGetter with ValewGetterBasicAccessors => // TODO

  // ---------------------------------------------------------------------------
  def text (target: NoRenarget): One[StringValue] = text_ (target).forceOrError(target)
  def texts(target: NoRenarget): Seq[StringValue] = texts_(target).forceOrError(target)

    // ---------------------------------------------------------------------------
    def text_(target: NoRenarget): Opt[StringValue] = target.und match {
      case key: Key => self.geT(key).map { case _: Seq[_] => multipleValuesError(target); case sgl => formatLeaf(sgl) }
      case Path(Nil,          leaf) => text_(leaf)
      case Path(head +: rest, leaf) => ValewGetterAccessorsUtils.text_(self)(head, Path(rest, leaf)) }

    // ---------------------------------------------------------------------------
    def texts_(target: NoRenarget): Ons[StringValue] = target.und match {
      case key: Key                 => self.get(key ).map(toStringValues /* permissive */)
      case Path(Nil,          leaf) => self.get(leaf).map(toStringValues /* permissive */)
      case Path(head +: rest, leaf) => ValewGetterAccessorsUtils.texts_(self)(head, Path(rest, leaf)) }

    // ---------------------------------------------------------------------------
    private def toStringValues(valew: Valew): Seq[StringValue] =
      valew.fold[Seq[StringValue]]
        { dyns => dyns.formatCompactJson.in.seq }
        { seq  => seq.map(formatLeaf) }
        { dyn  => dyn.formatCompactJson.in.seq }
        { sgl  => formatLeaf(sgl).in.seq }

  // ===========================================================================
  // to be cleanedup/migrated
  //def texts$_(key: KeyW): Option[Nes[String]] = anys$_(key).map(_.map(_.str))
  def texts$$ (key: Key)  : Seq[String] = ???   //anys$$(key)      .map(_.str)
  def texts$$2(path: Path): Nes[String] = ??? } // ObjUNesting.scala:def texts$$2(pnk: PotentiallyNestedKey) = dis._plumbing.nestingAccess$$2(pnk)(_.accessors.texts$$)

// ===========================================================================