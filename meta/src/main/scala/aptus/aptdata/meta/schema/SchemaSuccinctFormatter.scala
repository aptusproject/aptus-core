package aptus
package aptdata
package meta
package schema

// ===========================================================================
private object SchemaSuccinctFormatter {

  def apply(value: Cls): DebugString =
      value
        .fields
        .map(formatFieldDebug)
        .section("cls:")

    // ---------------------------------------------------------------------------
    private def formatFieldDebug(value: Fld): DebugString =
        value
          .skey
          .padRight(16, ' ')
          .tab(value.info.pipe(formatInfoDebug))

      // ---------------------------------------------------------------------------
      private def formatInfoDebug(value: Info): DebugString = {
          if(value.isRequired)                              value.union.map(formatSubInfoDebug).join("|")
          else                 s"${formatOptional(true)}\t${value.union.map(formatSubInfoDebug).join("|")}" }

        // ---------------------------------------------------------------------------
        private def formatSubInfoDebug(value: SubInfo): DebugString =
            if (value.single)                                formatValueTypeDebug(value.valueType)
            else                s"${formatMultiple(true)}\t${formatValueTypeDebug(value.valueType)}"

          // ---------------------------------------------------------------------------
          private def formatValueTypeDebug(value: ValueType): DebugString =
              value match {
                case tipe   : BasicType => tipe.name
                case nesting: Cls       => apply(nesting).sectionAllOff } }

// ===========================================================================
