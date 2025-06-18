package aptus
package aptdata
package meta
package selectors

// ===========================================================================
/** Explicit target: Key, Ren, Path, RPath - most common case */
case class Target private(und: Targetable) extends AnyVal {
    def guaranteePresence: Targets = Targets.guaranteed  (List(this))
    def mayBeMissing     : Targets = Targets.mayBeMissing(List(this))

    // ---------------------------------------------------------------------------
    def +! (more: Seq[Target]): Targets = Targets.guaranteed  ((this +: more).toList)
    def +~ (more: Seq[Target]): Targets = Targets.mayBeMissing((this +: more).toList)

    // ---------------------------------------------------------------------------
    def format: String = und.formatDefault }

  // ===========================================================================
  object Target {
    private type Self    = Target
            type Builder = Target.type => Target

    // ---------------------------------------------------------------------------
    def explicit(value: String): Self = Target(Key.from(value))
    def explicit(value: Symbol): Self = Target(Key.from(value))
    def explicit(value: Enm)   : Self = Target(Key.from(value))

    def explicit(value:  Ren)   : Self = Target(value)
    def explicit(value:  Path)  : Self = Target(value)
    def explicit(value: RPath)  : Self = Target(value)

    // ---------------------------------------------------------------------------
    implicit def _to(value: String): Self = Target(Key.from(value))
    implicit def _to(value: Symbol): Self = Target(Key.from(value))
    implicit def _to(value: Enm)   : Self = Target(Key.from(value))

    implicit def _to(value:  Ren)   : Self = Target(value)
    implicit def _to(value:  Path)  : Self = Target(value)
    implicit def _to(value: RPath)  : Self = Target(value) }

// ===========================================================================
/** No-renaming target: Key or Path - mostly just for .remove() */
case class NoRenarget private(und: NoRenargetable) extends AnyVal {
    def guaranteePresence: NoRenargets = NoRenargets.guaranteed  (List(this))
    def mayBeMissing     : NoRenargets = NoRenargets.mayBeMissing(List(this))

    // ---------------------------------------------------------------------------
    def +! (more: Seq[NoRenarget]): NoRenargets = NoRenargets.guaranteed  ((this +: more).toList)
    def +~ (more: Seq[NoRenarget]): NoRenargets = NoRenargets.mayBeMissing((this +: more).toList)

    // ---------------------------------------------------------------------------
    def format: String = und.formatDefault

    // ---------------------------------------------------------------------------
    def key  = und.asInstanceOf[Key]
    def path = und.asInstanceOf[Path]

    // ---------------------------------------------------------------------------
    def ~>(to: Key): Renarget = fold1[Renarget](_ ~> to)(_ ~> to)

    // ---------------------------------------------------------------------------
    def fold1[T](k: Key => T)(p: Path => T): T =
      und match {
        case x: Key       => k(x)
        case Path(Nil, x) => k(x)
        case x: Path      => p(x) }

    // ---------------------------------------------------------------------------
    def fold2[T](k: Key => T)(p: Path /* init */ => Key /* leaf */ => T): T =
      und match {
        case x: Key       => k(x)
        case x: Path      =>
          x.initPathOpt match {
            case None           => k          (x.key)
            case Some(initPath) => p(initPath)(x.key) } }

    // ---------------------------------------------------------------------------
    def fold3[T](k: Key => T)(p: Key /* head */ => Path /* tail */ => T): T =
      und match {
        case x: Key       => k(x)
        case x: Path      =>
          x.tailPair match {
            case (headKey, None          ) => k(headKey)
            case (headKey, Some(tailPath)) => p(headKey)(tailPath) } } }

  // ===========================================================================
  object NoRenarget {
    private type Self    = NoRenarget
            type Builder = NoRenarget.type => NoRenarget

    // ---------------------------------------------------------------------------
    implicit def _toNoRenarget(value: Key): NoRenarget = NoRenarget(value)

    // ---------------------------------------------------------------------------
    def explicit(value: String): Self = NoRenarget(Key.from(value))
    def explicit(value: Symbol): Self = NoRenarget(Key.from(value))
    def explicit(value: Enm)   : Self = NoRenarget(Key.from(value))
    def explicit(value: Path)  : Self = NoRenarget(value)

    // ---------------------------------------------------------------------------
    implicit def _to(value: String): Self = NoRenarget(Key.from(value))
    implicit def _to(value: Symbol): Self = NoRenarget(Key.from(value))
    implicit def _to(value: Enm)   : Self = NoRenarget(Key.from(value))
    implicit def _to(value: Path)  : Self = NoRenarget(value) }

// ===========================================================================
/** Renaming Target: Ren or RPath - mostly just for .rename() */
case class Renarget private(und: Renargetable) extends AnyVal {
    def guaranteePresence: Renargets = Renargets.guaranteed  (List(this))
    def mayBeMissing     : Renargets = Renargets.mayBeMissing(List(this))

    // ---------------------------------------------------------------------------
    def +! (more: Seq[Renarget]): Renargets = Renargets.guaranteed  ((this +: more).toList)
    def +~ (more: Seq[Renarget]): Renargets = Renargets.mayBeMissing((this +: more).toList)

    // ---------------------------------------------------------------------------
    def format: String = und.formatDefault }

  // ===========================================================================
  object Renarget {
    private type Self    = Renarget
            type Builder = Renarget.type => Renarget

    // ---------------------------------------------------------------------------
    def explicit(value:  Ren)   : Self = Renarget(value)
    def explicit(value: RPath)  : Self = Renarget(value)

    // ---------------------------------------------------------------------------
    implicit def _to(value:  Ren)   : Self = Renarget(value)
    implicit def _to(value: RPath)  : Self = Renarget(value) }

// ===========================================================================
case class Targets(values: Seq[Target], guaranteed: Boolean) extends PresenceGuarantee[Targets] {
      def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ---------------------------------------------------------------------------
    case class NoRenargets(values: Seq[NoRenarget], guaranteed: Boolean) extends PresenceGuarantee[NoRenargets] {
      def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

    // ---------------------------------------------------------------------------
    case class Renargets(values: Seq[Renarget], guaranteed: Boolean) extends PresenceGuarantee[Renargets] {
      def setPresenceGuarantee(value: Boolean) = copy(guaranteed = value) }

  // ===========================================================================
  object Targets {
      def guaranteed  (values: Seq[Target]): Targets = Targets(values, guaranteed = true)
      def mayBeMissing(values: Seq[Target]): Targets = Targets(values, guaranteed = false) }

    // ---------------------------------------------------------------------------
    object NoRenargets {
      def guaranteed  (values: Seq[NoRenarget]): NoRenargets = NoRenargets(values, guaranteed = true)
      def mayBeMissing(values: Seq[NoRenarget]): NoRenargets = NoRenargets(values, guaranteed = false) }

    // ---------------------------------------------------------------------------
    object Renargets {
      def guaranteed  (values: Seq[Renarget]): Renargets = Renargets(values, guaranteed = true)
      def mayBeMissing(values: Seq[Renarget]): Renargets = Renargets(values, guaranteed = false) }

// ===========================================================================