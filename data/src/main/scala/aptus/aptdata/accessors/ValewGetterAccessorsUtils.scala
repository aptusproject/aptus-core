package aptus
package aptdata
package accessors

// ===========================================================================
private object ValewGetterAccessorsUtils {

  def text_(self: ValewGetter)(head: Key, nestedPath: Path): Option[StringValue] =
      self.get(head).flatMap { valew =>
        valew.naked match { // note: no nested Dynz/Iterator (see a241119155444)
          case dyn : Dyn    => dyn.text_(nestedPath)
          case dyns: Dyns   => dyns.exoFlatMap(_.text_(nestedPath)).consumeAll().in.noneIf(_.isEmpty).map(_.join(","))
          case seq : Seq[_] => seq.flatMap { case dyn: Dyn => dyn.text_(nestedPath); case _ => None }.in.noneIf(_.isEmpty).map(_.join(",")) } }

    // ---------------------------------------------------------------------------
    def texts_(self: ValewGetter)(head: Key, nestedPath: Path): Option[Seq[StringValue]] =
      self.get(head).flatMap { valew =>
        valew.naked match { // note: no nested Dynz/Iterator (see a241119155444)
          case dyn : Dyn    => dyn.texts_(nestedPath)
          case dyns: Dyns   => dyns.exoFlatMap(_.texts_(nestedPath).toSeq.flatten).consumeAll().in.noneIf(_.isEmpty)
          case seq : Seq[_] => seq.flatMap { case dyn: Dyn => dyn.texts_(nestedPath).toSeq.flatten; case _ => None }.in.noneIf(_.isEmpty) }}

  // ---------------------------------------------------------------------------
  def nakedValues(self: ValewGetter)(head: Key, nestedPath: Path): Seq[NakedValue] =
    self.get(head).toSeq.flatMap { valew =>
      valew.naked match { // note: no nested Dynz/Iterator (see a241119155444)
        case dyn : Dyn    => dyn.nakedValues(nestedPath)
        case dyns: Dyns   => dyns.exoFlatMap(_.nakedValues(nestedPath)).consumeAll()
        case seq : Seq[_] => seq.flatMap { case dyn: Dyn => dyn.nakedValues(nestedPath); case _ => Nil } }} }

// ===========================================================================