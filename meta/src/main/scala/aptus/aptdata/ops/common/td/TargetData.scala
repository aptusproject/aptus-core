package aptus
package aptdata
package ops
package common
package td

import aptus.aptdata.meta.selectors._

// ===========================================================================
case class TargetData(
      data: ListMap[Key, TargetDataEntryType],
      guaranteed: Boolean)
    extends TargetDataEntryType
       with meta.selectors.PresenceGuarantee[TargetData] {

  override final def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value)

  // ---------------------------------------------------------------------------
  // mostly for readability
  def entryTypeOpt(key: Key): Option[TargetDataEntryType] = data.get(key)

  // ===========================================================================
  override def toString: String = formatDefault

    // ---------------------------------------------------------------------------
    def formatDefault: String =
      data
        .mapListMapValues {
          case AsIs           => "-"
          case WithRename(x)  => x.formatDefault
          case td: TargetData => td.formatDefault }
        .map { case (k, v) => s"${k.formatDefault.quote}:${v}" }
        .toList
        .section(if (guaranteed) "guaranteed" else "loose") }

// ===========================================================================
object TargetData extends TargetDataParsing {
  type Self = TargetData

  // ---------------------------------------------------------------------------
  /** mostly for tests */
  def build(guaranteed: Boolean)(
             entry1: (SKey, TargetDataEntryType),
             more  : (SKey, TargetDataEntryType)*)
      : Self =
    (entry1 +: more)
      .toList
      .map (_.mapFirst(Key.from))
      .pipe(aptus.listMap[Key, TargetDataEntryType])
      .pipe(TargetData.apply(_, guaranteed))

  // ===========================================================================
  protected def parse(guaranteed: Boolean)(values: Seq[Target]): Self = values.map(_.und).map(pair).pipe(parsePairs(guaranteed))

    // ---------------------------------------------------------------------------
    def parseSKeys(guaranteed: Boolean)(values: Seq[SKey]): Self = values.map(Key.from).map(pair).pipe(parsePairs(guaranteed))
    def parseRens (guaranteed: Boolean)(values: Seq[Ren ]): Self = values              .map(pair).pipe(parsePairs(guaranteed))

    // ---------------------------------------------------------------------------
    def parse(targets:   Targets  ): Self = targets.values.map(_.und).map(pair).pipe(parsePairs(targets.guaranteed))
    def parse(targets:   Renargets): Self = targets.values.map(_.und).map(pair).pipe(parsePairs(targets.guaranteed))
    def parse(targets: NoRenargets): Self = targets.values.map(_.und).map(pair).pipe(parsePairs(targets.guaranteed)) }

// ===========================================================================