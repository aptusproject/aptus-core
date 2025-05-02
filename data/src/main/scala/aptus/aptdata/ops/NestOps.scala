package aptus
package aptdata
package ops

// ===========================================================================
trait NestOps[Self] {
    def under(nester: Key): Self }

  // ---------------------------------------------------------------------------
  trait SglNestOps extends NestOps[Sngl] {
    protected val _sngl  : sngl.Dyn
    protected val _nestee: Key

    // ---------------------------------------------------------------------------
    final override def under(nester: Key): Sngl =
      _sngl.nest(_nestee.in.seq, nester) }

  // ===========================================================================
  trait MultNestOps[Mult] extends NestOps[Mult] {
    protected val _hem: ops.mult.HasEndoMap[Mult]
    protected val _nestee: Key

    // ---------------------------------------------------------------------------
    final override def under(nester: Key): Mult =
      _hem.endoMap(_.nest(_nestee).under(nester)) }

// ===========================================================================
