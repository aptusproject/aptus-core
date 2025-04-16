package aptus
package experimental
package dyn
package ops

// ===========================================================================
trait DynTransformImpl
      extends common.CommonHasTransformTargetSelectorTrait[Dyn] {
        dyn: Dyn =>

    // ---------------------------------------------------------------------------
    protected[dyn] final override def transformTargetSelector(target: TargetSelector, valueF: ValueF): Dyn =
      DynTransformImpl(dyn)(target, valueF) }

  // ===========================================================================
  object DynTransformImpl { import TargetSelector._

    // ---------------------------------------------------------------------------
    @inline private def apply(self: Dyn)(target: TargetSelector, valueF: ValueF): Dyn =
      target match {
        case Explicit      (targetKey, guaranteed) => transform(self)(valueF, guaranteed)(_ => targetKey.name.in.seq, renamings = Map.empty)
        case Predicate     (p, guaranteed)         => transform(self)(valueF, guaranteed)(_.filter(p),                renamings = Map.empty)
        case SelectOne     (f, guaranteed)         => transform(self)(valueF, guaranteed)(_.pipe(f).in.seq,           renamings = Map.empty)
        case SelectMultiple(f, guaranteed)         => transform(self)(valueF, guaranteed)(f,                          renamings = Map.empty)
        case Nesting(path, guaranteed)             => nesting  (self)(valueF, guaranteed)(path)
        case Renaming(ren, guaranteed)             => transform(self)(valueF, guaranteed)(
          selection = _ => ren.from.name.in.seq,
          renamings =      ren.pair.pipe(Map.apply(_))) }

      // ===========================================================================
      private def nesting(self: Dyn)(valueF: ValueF, guaranteed: Boolean)(path: Path) =
        path.tailPair match {

          // ---------------------------------------------------------------------------
          case (key, None) =>
            key
              .pipe(Explicit(_, guaranteed))
              .pipe(explicitKey => apply(self)(explicitKey, valueF))

          // ---------------------------------------------------------------------------
          case (headKey, Some(tailPath)) =>
            val sub: TargetSelector = Nesting(tailPath, guaranteed)

            headKey
              .pipe(Explicit(_, guaranteed))
              .pipe(head => apply(self)(head, _nesting(sub)(valueF))) }

        // ---------------------------------------------------------------------------
        private def _nesting(sub: TargetSelector)(valueF: ValueF): ValueF =
          _.nestingEither match {
            // no Dynz allowed here (see a241119123645)
            case Left (dyn)  =>                       apply(dyn)(sub, valueF)
            case Right(dyns) => dyns.endoMap { dyn => apply(dyn)(sub, valueF) } }

  // ===========================================================================
  private def transform
          (self: Dyn)
          (valueF: ValueF, guaranteed: Boolean)
          (selection: SKeysSelection, renamings: Map[Key, Key])
        : Dyn = {
      val keySet: SKeySet =
        selection
          .apply(self.skeys)
          .toSet

      var encoutered = 0

      self
        .data
        .map { case Entry(key, valew) =>
          if (!keySet.contains(key.name)) key.ensuring(_.notContainedIn(renamings.keySet)).pipe(_ -> valew)
          else                            { encoutered += 1; renamings.getOrElse(key, key) -> valew.transform(valueF) } }
        .map(Entry.buildn)
        .pipe(data.sngl.Dyn.byPass)
        .tap { _ => if (guaranteed && encoutered != keySet.size) Error.TransformGuaranteeFailure(()).thro } } }

// ===========================================================================
