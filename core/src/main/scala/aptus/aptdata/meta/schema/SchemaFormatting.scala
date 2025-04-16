package aptus
package aptdata
package meta
package schema

// ===========================================================================
private[meta] trait ClsFormatting extends HasFormatDefault { ignored: Cls =>
    override def toString = formatDefault
    override def formatDefault: String =
      fields
        .map(_.formatDefault)
        .section("cls:") }

  // ---------------------------------------------------------------------------
  private[meta] trait FldFormatting extends HasFormatDefault { ignored: Fld =>
    override def toString = formatDefault
    override def formatDefault = s"${key.name.padRight(16, ' ')}\t${info.formatDefault}" }

  // ---------------------------------------------------------------------------
  private[meta] trait InfoFormatting extends HasFormatDefault { ignored: Info =>
    override def toString = formatDefault
    override def formatDefault: String =
      if (optional) s"${formatOptional(optional)}\t${union.map(_.formatDefault).join("|")}"
      else                                           union.map(_.formatDefault).join("|") }

  // ---------------------------------------------------------------------------
  private[meta] trait Info1Formatting extends HasFormatDefault { ignored: Info1 =>
    override def toString = formatDefault
    override def formatDefault: String =
      formatOptional(optional).colon(SubInfo(multiple, valueType).formatDefault) }

  // ---------------------------------------------------------------------------
  private[meta] trait SubInfoFormatting extends HasFormatDefault { ignored: SubInfo =>
    override def toString = formatDefault
    override def formatDefault: String =
      valueType match {
        case basic: BasicType =>
          if (multiple)      s"${formatMultiple(multiple)}:${basic}"
          else               s"${basic}"
        case nesting: Cls => s"${formatMultiple(multiple)}\t${nesting.formatDefault.sectionAllOff}" } }

// ===========================================================================
