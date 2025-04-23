package aptus
package aptdata
package ops
package mult

import common._

// ===========================================================================
// rational for not abstracting for endoMap is: will be warned by compiler if missing + (mostly) minimal set that shouldn't change much
trait MultipleWrappedOps[Mult]
      extends AllCommons[Mult]
         with CommonHasTransformTargetSelectorTrait[Mult] /* transformTargetSelector */ {
    hem: HasEndoMap[Mult]
      with HasDataEntityErrorFormatter [Mult] =>

  // TODO: t241127134106 - macros for boilerplate (eg wrapped ops)

  protected[aptdata] final override def transformTargetSelector(target: TargetSelector, f: ValueF): Mult = endoMap(_.transformTargetSelector(target, f))

  // ===========================================================================
  final override def ensurePresent(key1: Key, more: Key*): Mult = endoMap(_.ensurePresent(key1, more: _*))
  final override def ensureMissing(key1: Key, more: Key*): Mult = endoMap(_.ensureMissing(key1, more: _*))

  // ===========================================================================
  final override def rename(mapping: Map[Key, Key]): Mult = endoMap(_.rename(mapping))
  final override def rename(from: Key) = new _Rename(from: Key) { def to(to: Key): Mult = endoMap(_.rename(from).to(to)) }

  // ---------------------------------------------------------------------------
  final override def retain(targets: Set[Key]): Mult = endoMap(_.retain(targets))
  final override def remove(targets: Set[Key]): Mult = endoMap(_.remove(targets))

  // ---------------------------------------------------------------------------
  final override def add(entry: Entry)        : Mult = endoMap(_.add(entry))
  final override def add(entries: List[Entry]): Mult = endoMap(_.add(entries))

  // ===========================================================================
  final override def convert(key: Key): ConvertOps[Mult] =
    new MultConvertOps[Mult] {
      protected val _hem = hem
      protected val _key: Key = key }

  // ---------------------------------------------------------------------------
  final override def nest(nestee: Key): NestOps[Mult] =
    new MultNestOps[Mult] {
      protected val _hem         = hem
      protected val _nestee: Key = nestee } }

// ===========================================================================
