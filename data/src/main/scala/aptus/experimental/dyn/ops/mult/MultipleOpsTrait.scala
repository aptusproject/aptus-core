package aptus
package experimental
package dyn
package ops
package mult

// ===========================================================================
trait MultipleOpsTrait[Mult] {
    self: HasIteratorConstructor[Mult]
       with common.HasIdent[Mult] =>

  @abstrct       protected def empty            : Mult // as "def" else scl3 error: error overriding value empty in trait MultipleOpsTrait of type aptus.experimental.dyn.data.mult.iter.Dynz
  @nonovrd final protected def unit(value: Sngl): Mult = const(CloseabledIterator.fromValues(value))

  // ===========================================================================
  override final def ident: Mult = union(empty)

  // ---------------------------------------------------------------------------
  @abstrct       def  union (that : Mult): Mult
  @abstrct       def prepend(value: Sngl): Mult
  @nonovrd final def  append(value: Sngl): Mult = union(unit(value))

  // ===========================================================================
  @abstrct       def filter   (p: Sngl => Boolean): Mult
  @nonovrd final def filterNot(p: Sngl => Boolean): Mult = filter(!p(_))

  // ===========================================================================
  @abstrct def headOption: Option[Sngl]
  @abstrct def lastOption: Option[Sngl]

  @nonovrd final def last: Sngl = lastOption.force // TODO: error
  @nonovrd final def head: Sngl = headOption.force

  // ---------------------------------------------------------------------------
  @abstrct def take(n: Int): Mult
  @abstrct def drop(n: Int): Mult
//TODO: take/drop/... (see gallia)

  @nonovrd final def take(n: Option[Int]): Mult = n.map(take).getOrElse(ident)
  @nonovrd final def drop(n: Option[Int]): Mult = n.map(drop).getOrElse(ident)

  // ===========================================================================
  @abstrct def forceOne: Sngl

  // ---------------------------------------------------------------------------
  @abstrct def writeKey(key: Key, to: FilePath): FilePath /* TODO: keep? */

  // ===========================================================================
  @abstrct def forceDoubleLookup[A, B](f1: Sngl => A, f2: Sngl => B): CoLookup[A, B]
  @abstrct def forceLookup      [A, B](f1: Sngl => A, f2: Sngl => B): Map[A, B]
  @abstrct def forceLookup2     [A, B](f1: Sngl => A, f2: Sngl => B): Map[A, Seq[B]]

}

// ===========================================================================
