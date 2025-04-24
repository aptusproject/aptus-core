/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package data

import aptus.Anything_

// ===========================================================================
class TableToGalliaData[$Jbo](
   unknownKeys: (Cls, $Jbo)      => Set[Key],
    attemptKey:      ($Jbo, Key) => Option[AnyValue],

   instantiateSingle: Seq[(Key, AnyValue)] => $Jbo) {

  def convert(conf: io.CellConf /* TODO: a lighter version */)(c: Cls)(o: $Jbo): $Jbo = {
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