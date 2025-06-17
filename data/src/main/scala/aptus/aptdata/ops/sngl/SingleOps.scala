package aptus
package aptdata
package ops
package sngl

// ===========================================================================
trait SingleOps
      extends common.CommonHasTransformTargetSelectorTrait[Dyn]
         with SingleRenameOps
         with SingleRemoveRetainOps
         with SingleAddReplacePutOps {
    sngl: Dyn => // TODO: limit

  // ---------------------------------------------------------------------------
  protected def _fold[T](target: TargetEither)(f: (TargetData, Dyn) => T): T =
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
  override final def nest(nestee: Key)                 : NestOps[Dyn] = new SglNestOps(sngl, nestee.in.seq)
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
