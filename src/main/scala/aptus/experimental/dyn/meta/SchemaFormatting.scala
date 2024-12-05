package aptus
package experimental
package dyn
package meta

// ===========================================================================
private[meta] trait ClsFormatting extends HasFormatDefault { self: Cls =>
    override def toString = formatDefault
    override def formatDefault: String =
      fields
        .map(_.formatDefault)
        .section("cls:") }

  // ---------------------------------------------------------------------------
  private[meta] trait FldFormatting extends HasFormatDefault { self: Fld =>
    override def toString = formatDefault
    override def formatDefault = s"${key.padRight(16, ' ')}\t${info.formatDefault}" }

  // ---------------------------------------------------------------------------
  private[meta] trait InfoFormatting extends HasFormatDefault { self: Info =>
    override def toString = formatDefault
    override def formatDefault: String = // borrowed from gallia (TODO: t241130165320 - refactor)
      //  see t210125111338 (union types)
      if (optional) s"${formatOptional(optional)}\t${union.map(_.formatDefault).join("|")}"
      else                                           union.map(_.formatDefault).join("|") }

  // ---------------------------------------------------------------------------
  private[meta] trait SubInfoFormatting extends HasFormatDefault { self: SubInfo =>
    override def toString = formatDefault
    override def formatDefault: String = // borrowed from gallia (TODO: t241130165320 - refactor)
      valueType match {
        case basic: BasicType =>
          if (multiple) s"${formatMultiple(multiple)}:${basic}"
          else          s"${basic}"
        case nesting: Cls => s"${formatMultiple(multiple)}\t${nesting.formatDefault.sectionAllOff}" } }

// ===========================================================================
