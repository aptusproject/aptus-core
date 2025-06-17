package aptus
package aptdata
package meta
package selectors
package td

// ===========================================================================
trait TargetDataParsing { self: TargetData.type =>

  def pair(target: meta.selectors.Targetable): (Key /* leaf or nesting key */, TargetDataEntryTypeBase) =
    target match {

      case key: Key         => key  -> AsIs
      case Path(Nil, leaf)  => leaf -> AsIs

      // ---------------------------------------------------------------------------
      case ren: Ren         => ren .from -> WithRename(ren.to)
      case RPath(Nil, leaf) => leaf.from -> WithRename(leaf.to)

      // ---------------------------------------------------------------------------
      case  path:  Path     =>  path.forcePair.mapSecond( Nesting.apply)
      case rpath: RPath     => rpath.forcePair.mapSecond(RNesting.apply) }

  // ===========================================================================
  def parsePairs(guaranteed: Boolean)(pairs: Seq[(Key, TargetDataEntryTypeBase)]): TargetData =
    pairs
      .groupByKeyWithListMap
      .mapListMapValues(combine(guaranteed))
      .pipe(TargetData.apply(_, guaranteed))

  // ===========================================================================
  private def combine(guaranteed: Boolean)(values: Seq[TargetDataEntryTypeBase]): TargetDataEntryType =
    values match {
      case Seq(sole: PathOrRPath) => sole.target.in.seq.pipe(parse(guaranteed))
      case Seq(sole: WithRename)  => sole
      case Seq(AsIs)              => AsIs
      case multiple =>
        multiple
          .map {
            case x: PathOrRPath => x.target
            case _              => aptus.illegalArgument(s"E250529135612 - can't be both nested and non-nested: ${multiple}") }
          .pipe(parse(guaranteed)) } }

// ===========================================================================