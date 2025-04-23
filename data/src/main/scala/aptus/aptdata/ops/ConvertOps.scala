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
  trait SglConvertOps extends ConvertOps[Sngl] {
    protected val _ctt: common.CommonTransformTrait[Sngl]
    protected val _key: Key

    // ---------------------------------------------------------------------------
    import _ctt._

    final override def toStr    : Sngl = transform(_key).using { _.format }
    final override def toBoolean: Sngl = transform(_key).using { _.format /* TODO: t241101134033 or just toString? */ }

    /* TODO: more efficient versions of these? */
    final override def toInt    : Sngl = transform(_key).using { _.format.toInt    }
    final override def toDouble : Sngl = transform(_key).using { _.format.toDouble }

    final override def toLong   : Sngl = transform(_key).using { _.format.toLong } }

  // ===========================================================================
  trait MultConvertOps[Mult] extends ConvertOps[Mult] {
    protected val _hem: ops.mult.HasEndoMap[Mult]
    protected val _key: Key

    // ---------------------------------------------------------------------------
    import _hem._

    final override def toBoolean: Mult = endoMap(_.convert(_key).toBoolean)
    final override def toInt    : Mult = endoMap(_.convert(_key).toInt)
    final override def toDouble : Mult = endoMap(_.convert(_key).toDouble)
    final override def toStr    : Mult = endoMap(_.convert(_key).toStr)

  //TODO: codegen
    final override def toLong  : Mult = endoMap(_.convert(_key).toLong) }

// ===========================================================================
