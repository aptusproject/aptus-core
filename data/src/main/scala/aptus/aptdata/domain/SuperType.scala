package aptus
package aptdata
package domain

// ===========================================================================
trait SuperType {
    def formatTypeName: String
    final def formatErrorMessageString: String = formatTypeName.surroundWith("`", "`") // so can more easily parse error message
    override def toString() = formatTypeName
    protected final def className(value: Any): String =
      value.getClass.getSimpleName.stripSuffixGuaranteed("$") }

  // ---------------------------------------------------------------------------
  object SuperType {
    implicit def from(value: BasicType)          : SuperType = new SuperType { def formatTypeName: String = value.name.stripPrefixGuaranteed("_") }
    implicit def from(value: sngl.Dyn .type)     : SuperType = new SuperType { def formatTypeName: String = className(value) }
    implicit def from(value: mult.list.Dyns.type): SuperType = new SuperType { def formatTypeName: String = className(value) }
    implicit def from(value: mult.iter.Dynz.type): SuperType = new SuperType { def formatTypeName: String = className(value) }
    implicit def from(value: IntegerLike.type)   : SuperType = new SuperType { def formatTypeName: String = className(value) }
    implicit def from(value:    RealLike.type)   : SuperType = new SuperType { def formatTypeName: String = className(value) } }

// ===========================================================================
