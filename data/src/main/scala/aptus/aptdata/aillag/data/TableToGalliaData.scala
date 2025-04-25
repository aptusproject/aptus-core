package aptus
package aptdata
package aillag
package data

import aptus.Anything_

// ===========================================================================
class TableToGalliaData[$Single](
   unknownKeys: (Cls, $Single)      => Set[Key],
    attemptKey:      ($Single, Key) => Option[AnyValue],

   instantiateSingle: Seq[(Key, AnyValue)] => $Single) {

  def convert(conf: io.CellConf /* TODO: a lighter version */)(c: Cls)(o: $Single): $Single = {
      unknownKeys(c, o).assert(_.isEmpty) // necessary for union types (see 220615165554)

      c .fields
        .flatMap { field =>
          attemptKey(o, field.key)
            .flatMap {
              _ .asInstanceOf[String]
                .in.noneIf(conf.isNull)
                .map(processStringField(conf)(field))
                .map(field.key -> _) } }
        .pipe(instantiateSingle) }

    // ---------------------------------------------------------------------------
    private def processStringField(conf: io.CellConf)(field: Fld)(value: String): AnyValue =
      field
        .info.valueExtractionWithFailures { _ => aptus.illegalState() } { // TODO: support a form of nesting directly? eg nested JSON documents?
          bsc => multiple =>
            if (!multiple) value                      .pipe(conf.transformBasicValue(bsc))
            else           value.pipe(conf.splitArray).map (conf.transformBasicValue(bsc)) } }

// ===========================================================================