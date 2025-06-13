package aptus
package aptdata
package meta
package selectors

// ===========================================================================
case class Keyz(values: Seq[Key]) extends AnyVal {
    def bkeys   = values.map(_.und)
    def bkeySet = values.map(_.und).toSet

    // ---------------------------------------------------------------------------
    def map[T](f: Key => T): Seq[T] = values.map(f)

    def skeys = values.map(_.name)

    def    containsKey(value: Key): Boolean =  values.contains(value)
    def notContainsKey(value: Key): Boolean = !values.contains(value)

    def size = values.size

    def  isEmpty: Boolean = values.isEmpty
    def nonEmpty = values.nonEmpty

    def forceOne: Key = values.force.one

    def diffKeys(that: Keyz): Keyz = this.values.diff(that.values) }

  // ===========================================================================
  case class BKeyz(values: Seq[BKey]) extends AnyVal {
    def map[T](f: BKey => T): Seq[T] = values.map(f)

    def skeys = values.map(_.name)

    def    containsKey(value: BKey): Boolean =  values.contains(value)
    def notContainsKey(value: BKey): Boolean = !values.contains(value)

    def size = values.size

    def  isEmpty: Boolean = values.isEmpty
    def nonEmpty = values.nonEmpty

    def forceOne: BKey = values.force.one

    def diffKeys(that: BKeyz): BKeyz = this.values.diff(that.values) }

  // ===========================================================================
  object  Keyz {
    implicit def _from       (values: Seq[ Key]):  Keyz =  Keyz(values)
    implicit def _fromStrings(values: Seq[SKey]):  Keyz = Keyz(values.map(Key.from)) }
  object BKeyz { implicit def _to(values: Seq[BKey]): BKeyz = BKeyz(values) }

// ===========================================================================