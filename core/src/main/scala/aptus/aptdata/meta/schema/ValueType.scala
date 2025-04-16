package aptus
package aptdata
package meta
package schema

// ===========================================================================
@aptus.pseudosealed /* only two: BasicType and Cls (nesting) */
trait ValueType {
    private def _either: Either[BasicType, Cls] = this match {
      case x: BasicType => Left (x)
      case x: Cls       => Right(x) }

    // ---------------------------------------------------------------------------
    def formatDefault: String

    // ---------------------------------------------------------------------------
    def isNesting: Boolean = _either.isRight
    def isLeaf   : Boolean = _either.isLeft

    // ---------------------------------------------------------------------------
    def basicTypeOpt: Option[BasicType] = _either.swap.toOption
    def nestingOpt  : Option[Cls      ] = _either     .toOption

    // ---------------------------------------------------------------------------
    def forceBasicType: BasicType = basicTypeOpt.get
    def forceNestedCls: Cls       = nestingOpt.get

    // ---------------------------------------------------------------------------
    def isBasicType(pred: BasicType => Boolean): Boolean = basicTypeOpt.exists(pred)
    def isBasicType(target: BasicType)         : Boolean = basicTypeOpt.exists(_ == target) }

  // ===========================================================================
  object ValueType {

    // TODO: ValueType.combine: t201124151910; note for union, data would also need to be transformed (int/double)
    @NumberAbstraction
    def combine(c1: ValueType, c2: ValueType): ValueType = {
      c1 // FIXME
    }

  }

// ===========================================================================
