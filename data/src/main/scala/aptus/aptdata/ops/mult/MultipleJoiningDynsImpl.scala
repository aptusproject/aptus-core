package aptus
package aptdata
package ops
package mult

import aptus.Seq_ // for .data.{leftJoin, ...}

// ===========================================================================
trait MultipleJoiningDynsImpl {
      //extends MultipleJoiningTrait[Dyns] - TODO
    self: Dyns /*HasValuesIterator[Dyns] */=>

  /** costly, favor providing join key whenever possible */
  def bringAll(that: Dyns): Dyns = bringAll(that, commonKey(that))

  /** costly, favor providing join key whenever possible */
  def join(that: Dyns): Dyns = join(that, commonKey(that))

  // ---------------------------------------------------------------------------
  private def commonKey(that: Dyns): Key =
    keysCostly
      .intersect(that.keysCostly)
      .force.one /* TODO: custom error msg rather */

  // ===========================================================================
  // TODO: t241203101540: left, right, outer, coGroup ...
  def coGroup(that: Dyns)(on: Key): Dyns = ??? // TODO: t241203101541

  // ---------------------------------------------------------------------------
  def join(that: Dyns, via: Key)                   : Dyns = join(that, via, via)
  def join(that: Dyns, viaLeft: Key, viaRight: Key): Dyns = // TODO: fluent interface rather
    values
      .data /* from aptus.Seq_ */
      .innerJoin(that.values)
        .on(_.get(viaLeft), _.get(viaRight))
      .map { case (_, (l, r)) =>
        r .remove(viaRight)
          .pipe(l.merge) }
      .pipe(Dyns.build)

  // ---------------------------------------------------------------------------
  def bringAll(that: Dyns, via: Key)                   : Dyns = bringAll(that, via, via)
  def bringAll(that: Dyns, viaLeft: Key, viaRight: Key): Dyns = // TODO: fluent interface rather
    values
      .data /* from aptus.Seq_ */
      .leftJoin(that.values)
        .on(_.get(viaLeft), _.get(viaRight))
      .map { case (_, (l, rOpt)) =>
        rOpt
          .map(_.remove(viaRight))
          .map(l.merge)
          .getOrElse(l) }
      .pipe(Dyns.build)
 }

// ===========================================================================
