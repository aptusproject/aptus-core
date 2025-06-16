package aptus
package aptdata
package ops

// ===========================================================================
trait NestOps[Self] { def under(nester: Key): Self }

  // ===========================================================================
  class SglNestOps private[aptdata](
        protected val _sngl  : Dyn,
        protected val _nestees: Keyz)
      extends NestOps[Sngl] {
    final override def under(nester: Key): Sngl =
      _sngl.retainRemoveOpt(_nestees) match {
        case (None           , _            ) => _sngl // nothing to nest, leave as-is
        case (Some(nesteeDyn), None         ) => dyn(nester -> nesteeDyn) // since nothing left otherwise
        case (Some(nesteeDyn), Some(restDyn)) =>
          _sngl
            .get(nester)
            .map(_.transformNesting(_.merge(nesteeDyn))) // note: denormalizes if multiple
            .map      (restDyn.replace(nester, _))
            .getOrElse(restDyn.add    (nester, nesteeDyn )) } }

  // ===========================================================================
  class MultNestOps[Mult] private[aptdata](
        protected val _hem: ops.mult.HasEndoMap[Mult],
        protected val _nestee: Key)
      extends NestOps[Mult] {
    final override def under(nester: Key): Mult =
      _hem.endoMap(_.nest(_nestee).under(nester)) }

// ===========================================================================
