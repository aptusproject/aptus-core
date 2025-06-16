package aptus
package aptdata
package ops

// ===========================================================================
trait ConvertOps[Data] {
          def asArray: Data = ??? // 241115

    // TODO: codegen
    def toInt    : Data
    def toDouble : Data
    def toStr    : Data
    def toBoolean: Data

    def toLong   : Data }

  // ===========================================================================
  class SglConvertOps private[aptdata] (
        protected val _ctt    : common.CommonTransformTrait[Sngl],
        protected val _targets: Targets)
      extends ConvertOps[Sngl] {
    import _ctt._

    final override def toStr    : Sngl = transform(_targets).using { _.format }
    final override def toBoolean: Sngl = transform(_targets).using { _.format /* TODO: t241101134033 or just toString? */ }

    /* TODO: more efficient versions of these? */
    final override def toInt    : Sngl = transform(_targets).using { _.format.toInt    }
    final override def toDouble : Sngl = transform(_targets).using { _.format.toDouble }

    final override def toLong   : Sngl = transform(_targets).using { _.format.toLong } }

  // ===========================================================================
  class MultConvertOps[Mult] private[aptdata](
        protected val _hem    : ops.mult.HasEndoMap[Mult],
        protected val _targets: Targets)
      extends ConvertOps[Mult] {
    import _hem._

    final override def toBoolean: Mult = endoMap(_.convert(_targets).toBoolean)
    final override def toInt    : Mult = endoMap(_.convert(_targets).toInt)
    final override def toDouble : Mult = endoMap(_.convert(_targets).toDouble)
    final override def toStr    : Mult = endoMap(_.convert(_targets).toStr)

  //TODO: codegen
    final override def toLong  : Mult = endoMap(_.convert(_targets).toLong) }

// ===========================================================================
