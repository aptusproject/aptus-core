package aptus
package aptdata
package ops

// ===========================================================================
trait DynOpsImpl extends common.CommonHasTransformTargetSelectorTrait[Dyn] {
    sngl: Dyn => // TODO: limit

  // ---------------------------------------------------------------------------
  private def _fold[T](target: TargetEither)(f: (TargetData, Dyn) => T): T =
    target
      .fold(_.targetData(sngl.skeys), identity)
      .pipe(f(_, sngl))

  // ===========================================================================
  override final protected[aptdata] def transformTarget(target: TargetEither, valueF: ValueF): Dyn =
    _fold(target)((x, y) => x.transform(y)(valueF))

  // ---------------------------------------------------------------------------
  /** MUST have distinct keys */ def merge(that: Dyn): Dyn =
    aptdata.sngl.Dyn.build((data: Iterable[Entry]) ++ (that.data: Iterable[Entry]))

  // ===========================================================================
  override final protected[ops] def _ensurePresent(target: TargetEither): Dyn = sngl.tap(_ => _fold(target)(_ ensurePresence _))
  override final protected[ops] def _ensureAbsent (target: TargetEither): Dyn = sngl.tap(_ => _fold(target)(_ ensureAbsence _))

  // ===========================================================================
  override final protected[ops] def _rename(target: TargetData): Dyn = target.rename(sngl)

    override final def rename(mapping: Map[Key, Key]): Dyn = mapEntries(_.rename(mapping))

    override final def rename(sel: Sel2): _Rename = new _Rename { def to(to: Key): Dyn =
      sel(meta.selectors.SelectOneShorthands).targetData(sngl.skeys, to).rename(sngl) }

    override final def rename          (from: NoRenarget)  : _Rename = new _Rename { def to(to: Key): Dyn = rename(from ~> to) }
    override final def renameGuaranteed(from: NoRenarget)  : _Rename = new _Rename { def to(to: Key): Dyn = rename(
      ((from ~> to): Renarget).guaranteePresence) }

  // ===========================================================================
  override final protected[ops] def _retain(target: TargetEither): Dyn = _fold(target)(_ retain _)

    override final def retainMultiple(values: Set[Key]): Dyn = data.filter(x => values.contains(x.key)).dyn
    override final def retainKey     (key   : Key)     : Dyn = data.filter(_.key == key).dyn

    // ---------------------------------------------------------------------------
    override final protected[ops] def _remove(target: TargetEither): Dyn = _fold(target)(_ remove _)

    // ---------------------------------------------------------------------------
    override final def removeMultiple(values: Set[Key]): Dyn = data.filterNot(x => values.contains(x.key)).dyn
    override final def removeKey     (value   : Key)   : Dyn = data.filterNot(_.key == value).dyn

  // ===========================================================================
  override final protected[ops] def _add    (target: NoRenarget, value: NakedValue): Dyn = _noRenarget(target) { (x, y) => x.add    (y -> value) }
  override final protected[ops] def _replace(target: NoRenarget, value: NakedValue): Dyn = _noRenarget(target) { (x, y) => x.replace(y -> value) }
  override final protected[ops] def _put    (target: NoRenarget, value: NakedValue): Dyn = _noRenarget(target) { (x, y) => x.put    (y -> value) }

    // ---------------------------------------------------------------------------
    // add: must not exist
    override final def add(entry: Entry)        : Dyn = Dyn.build(data :+ entry)   // errors out if already present, use .put() otherwise
    override final def add(entries: List[Entry]): Dyn = Dyn.build(data ++ entries) // errors out if already present, use .put() otherwise

    // ---------------------------------------------------------------------------
    // replace: must exist
    override final def replace(key: Key, value: NakedValue): Dyn = transformGuaranteed(key.target).using { _ => value }
    override final def replace(entries: List[Entry])       : Dyn = entries.foldLeft(sngl) { (x, entry) => x.replace(entry) }

    // ---------------------------------------------------------------------------
    // put: may or may not exist
    override final def put(entries: List[Entry])       : Dyn = entries.foldLeft(sngl) { (x, entry) => x.put(entry) }
    override final def put(key: Key, value: NakedValue): Dyn =
        if (containsKey(key)) replace(key).withValue(value)
        else                      add(key,           value)

  // ===========================================================================
  override final def nest(nestee: Key)             : NestOps[Dyn] = new SglNestOps(sngl, nestee.in.seq)
  /*override final */def nest(nestee1: Key, more: Key*): NestOps[Dyn] = new SglNestOps(sngl, nestee1 +: more)

  def unnest = ??? // TODO

  // ===========================================================================
  // converts
  override final def convert(targets: Targets): ConvertOps[Dyn] = new SglConvertOps(sngl, targets)

  // ===========================================================================
  protected def _noRenarget(value: NoRenarget)(f: (Dyn, Key) => Dyn): Dyn =
    value.fold2
      { key      => f(sngl, key) }
      { initPath => leaf => sngl.transformNesting(initPath).using(f(_, leaf)) } }

// ===========================================================================
