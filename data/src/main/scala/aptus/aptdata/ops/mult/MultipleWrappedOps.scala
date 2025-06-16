package aptus
package aptdata
package ops
package mult

import common._

// ===========================================================================
// this is mostly boilerplate code - not bothering to split it up
// rational for not abstracting for endoMap is: will be warned by compiler if missing + (mostly) minimal set that shouldn't change much
trait MultipleWrappedOps[Mult]
      extends AllCommons[Mult]
         with CommonHasTransformTargetSelectorTrait[Mult] /* transformTarget */ {
    hem: HasEndoMap[Mult]
      with HasDataEntityErrorFormatter [Mult] =>

  // TODO: t241127134106 - macros for boilerplate (eg wrapped ops) - or codegen at least

  // ===========================================================================
  override final def reorderKeys           (f: SKeysFunction): Mult = endoMap(_.reorderKeys           (f))
  override final def reorderKeysRecursively(f: SKeysFunction): Mult = endoMap(_.reorderKeysRecursively(f))

// ===========================================================================
  override final protected[ops] def _ensurePresent(target: TargetEither): Mult = endoMap(_._ensurePresent(target))
  override final protected[ops] def _ensureAbsent (target: TargetEither): Mult = endoMap(_._ensureAbsent (target))

  // ===========================================================================
  protected[aptdata] override final def transformTarget(either: TargetEither, f: ValueF): Mult = endoMap(_.transformTarget(either, f))

  // ===========================================================================
  override final def convert(targets: Targets): ConvertOps[Mult] = new MultConvertOps[Mult](hem, targets)
  override final def nest   (nestee: Key)     : NestOps   [Mult] = new MultNestOps   [Mult](hem, nestee)

  // ===========================================================================
  override final protected[ops] def _rename(target: TargetData): Mult = endoMap(_._rename(target))

  override final def rename(mapping: Map[Key, Key]): Mult = endoMap(_.rename(mapping))
  override final def rename(sel: Sel2): _Rename = new _Rename { def to(to: Key): Mult = endoMap(_.rename(sel ).to(to)) }

  override final def rename          (from: NoRenarget)  : _Rename = new _Rename { def to(to: Key): Mult = endoMap(_.rename          (from).to(to)) }
  override final def renameGuaranteed(from: NoRenarget)  : _Rename = new _Rename { def to(to: Key): Mult = endoMap(_.renameGuaranteed(from).to(to)) }

  // ===========================================================================
  override final protected[ops] def _retain(target: TargetEither): Mult = endoMap(_._retain(target))

  override final def retainMultiple(values: Set[Key]): Mult = endoMap(_.retainMultiple(values))
  override final def retainKey     (value : Key)     : Mult = endoMap(_.retainKey (value))

  // ---------------------------------------------------------------------------
  override final protected[ops] def _remove(target: TargetEither): Mult = endoMap(_._remove(target))

  override final def removeMultiple(values: Set[Key]): Mult = endoMap(_.removeMultiple(values))
  override final def removeKey     (value : Key)     : Mult = endoMap(_.removeKey (value))

  // ===========================================================================
  override final protected[ops] def _add    (target: NoRenarget, value: NakedValue): Mult = endoMap(_._add    (target, value))
  override final protected[ops] def _replace(target: NoRenarget, value: NakedValue): Mult = endoMap(_._replace(target, value))
  override final protected[ops] def _put    (target: NoRenarget, value: NakedValue): Mult = endoMap(_._put    (target, value))

    // ---------------------------------------------------------------------------
    // add: must not exist
    override final def add(entry: Entry)        : Mult = endoMap(_.add(entry))
    override final def add(entries: List[Entry]): Mult = endoMap(_.add(entries))

    // ---------------------------------------------------------------------------
    // replace: must exist
    override final def replace(key: Key, value: NakedValue): Mult = endoMap(_.replace(key, value))
    override final def replace(entries: List[Entry])       : Mult = endoMap(_.replace(entries))

    // ---------------------------------------------------------------------------
    // put: may or may not exist
    override final def put(key: Key, value: NakedValue): Mult = endoMap(_.put(key, value))
    override final def put(entries: List[Entry])       : Mult = endoMap(_.put(entries)) }

// ===========================================================================
