package aptus
package experimental
package dyn
package io
package in

// ===========================================================================
trait TupleBasedBuildingExtensions { // these are mostly for test/quick prototyping
  private type E = (SKey, NakedValue)

  private type E1 =  E
  private type E2 = (E, E)
  private type E3 = (E, E, E)
  private type E4 = (E, E, E, E)
  private type E5 = (E, E, E, E, E)

  implicit class EntryTuple1_(values:  E1) { def dyn: Dyn = values.in.seq.dyn }
  implicit class EntryTuple2_(values:  E2) { def dyn: Dyn = values. toSeq.dyn }
  implicit class EntryTuple3_(values:  E3) { def dyn: Dyn = values. toSeq.dyn }
  implicit class EntryTuple4_(values:  E4) { def dyn: Dyn = values. toSeq.dyn }
  implicit class EntryTuple5_(values:  E5) { def dyn: Dyn = values. toSeq.dyn }

  // ---------------------------------------------------------------------------
  implicit class SeqE2_(u: Seq[E2]) { def dyns = u.map(_.dyn).dyns }
  implicit class SeqE3_(u: Seq[E3]) { def dyns = u.map(_.dyn).dyns }

  // ---------------------------------------------------------------------------
  private type E11  = (E1, E1)
  private type E111 = (E1, E1, E1)

  private type E22  = (E2, E2)
  private type E222 = (E2, E2, E2)

  private type E33  = (E3, E3)
  private type E333 = (E3, E3, E3)

  implicit class E11_ (u: E11)  { def dyns = u.toSeq.map(_.dyn).dyns }
  implicit class E111_(u: E111) { def dyns = u.toSeq.map(_.dyn).dyns }

  implicit class E22_ (u: E22)  { def dyns = u.toSeq.map(_.dyn).dyns }
  implicit class E222_(u: E222) { def dyns = u.toSeq.map(_.dyn).dyns }

  implicit class E33_ (u: E33)  { def dyns = u.toSeq.map(_.dyn).dyns }
  implicit class E333_(u: E333) { def dyns = u.toSeq.map(_.dyn).dyns }
}

// ===========================================================================
