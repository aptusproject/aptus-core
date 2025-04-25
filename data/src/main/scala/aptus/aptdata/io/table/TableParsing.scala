package aptus
package aptdata
package io
package table

import aptus.Anything_

// ===========================================================================
class TableParsing[$Single](
     unknownKeys: ($Single, Cls) => Set[Key],
      attemptKey: ($Single, Key) => Option[AnyValue]) {

  // ---------------------------------------------------------------------------
  def convert(conf: CellConf /* TODO: a lighter version */)(c: Cls)(o: $Single): Seq[(Key, AnyValue)] = {
      unknownKeys(o, c).assert(_.isEmpty) // necessary for union types (see 220615165554)

      c .fields
        .flatMap { field =>
          attemptKey(o, field.key)
            .flatMap {
              _ .asInstanceOf[String]
                .in.noneIf(conf.isNull)
                .map(processStringField(conf)(field))
                .map(field.key -> _) } } }

    // ---------------------------------------------------------------------------
    private def processStringField(conf: CellConf)(field: Fld)(value: String): AnyValue =
      field
        .info.valueExtractionWithFailures { _ => aptus.illegalState() } { // TODO: support a form of nesting directly? eg nested JSON documents?
          bsc => multiple =>
            if (!multiple) value                      .pipe(conf.transformBasicValue(bsc))
            else           value.pipe(conf.splitArray).map (conf.transformBasicValue(bsc)) } }

// ===========================================================================