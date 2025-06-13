package aptus
package aptdata
package ops
package common
package td

// ===========================================================================
trait TargetDataOps {
  protected val self: TargetData

  // ---------------------------------------------------------------------------
  def ensurePresence(dyn: Dyn): Unit = {
      self.data.foreach {
        case (_  , WithRename(_))  => illegalState() // can't happen for ensurePresence
        case (key, AsIs)           => if (!dyn.containsKey(key)) Error.EnsurePresenceError(key).thro
        case (key, td: TargetData) =>
          dyn.get(key) match {
            case None        => if (!dyn.containsKey(key)) Error.EnsurePresenceError(key).thro
            case Some(valew) => valew.nesting(td.ensurePresence) } } }

    // ---------------------------------------------------------------------------
    def ensureAbsence(dyn: Dyn): Unit = {
      self.data.foreach {
        case (_  , WithRename(_))  => illegalState() // can't happen for ensureAbsence
        case (key, AsIs)           => if (dyn.containsKey(key)) Error.EnsureAbsenceError(key).thro
        case (key, td: TargetData) =>
          dyn.get(key) match {
            case None        => ()
            case Some(valew) => valew.nesting(td.ensureAbsence) } } }

  // ---------------------------------------------------------------------------
  def transform(dyn: Dyn)(valueF: ValueF): Dyn = {
    var encountered: Count = 0

    checkIfGuaranteed(
      dyn.mapEntries { e =>
        self.entryTypeOpt(e.key)
          .map {
            case AsIs           => encountered += 1; e.transformValew    (_. transformValew(valueF))
            case WithRename(to) => encountered += 1; e.transformValew(to)(_. transformValew(valueF))
            case td: TargetData => e.transformNesting { x => encountered += 1; td.transform(x)(valueF) } }
          .getOrElse(e) } ->
        encountered ) }

  // ---------------------------------------------------------------------------
  def rename(dyn: Dyn): Dyn = {
    var encountered = 0

    checkIfGuaranteed(
      dyn.mapEntries { e =>
        self.entryTypeOpt(e.key)
           .map {
            case AsIs               => illegalState() // can't happen for renaming
            case WithRename(newKey) => encountered += 1; e.reKey(newKey)
            case td: TargetData     => e.transformNesting { x => encountered += 1; td.rename(x) } }
          .getOrElse(e) } ->
        encountered ) }

  // ---------------------------------------------------------------------------
  def remove(dyn: Dyn): Dyn = {
    var encountered = 0

    checkIfGuaranteed(
      dyn.flatMapEntries { e =>
        self.entryTypeOpt(e.key)
          .map {
            case AsIs           => encountered += 1; None
            case WithRename(_)  =>                   illegalState() // can't happen for removing
            case td: TargetData => e.transformNesting { x => encountered += 1; td.remove(x) }.in.some }
          .getOrElse { Some(e) /* not removed */ } } ->
        encountered ) }

  // ---------------------------------------------------------------------------
  def retain(dyn: Dyn): Dyn = {
    var encountered = 0

    checkIfGuaranteed(
      dyn.flatMapEntries { e =>
        self.entryTypeOpt(e.key)
         .map {
            case AsIs               => encountered += 1; e
            case WithRename(newKey) => encountered += 1; e.reKey(newKey)
            case td: TargetData     => e.transformNesting { x => encountered += 1; td.retain(x) } } } ->
        encountered) }

  // ===========================================================================
  private def checkIfGuaranteed(pair: (Dyn, Count)) = {
    val (value, encountered) = pair

    if (self.guaranteed && encountered != self.data.size)
      Error.TransformGuaranteeFailure(encountered -> self.data.size).thro

    value } }

// ===========================================================================