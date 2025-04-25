package aptus
package aptdata
package io
package json

import aptus.Anything_
import aptus.aptjson.GsonParser
import aptus.aptdata.meta.basic.BasicTypeValueTransformer

// ===========================================================================
/** parsing is more efficient when we know the schema already */
class GsonToGalliaData[$Single](
    jsonObjectStringParser: JsonObjectString => $Single,

    unknownKeys: ($Single, Cls) => Set[Key],
     attemptKey: ($Single, Key) => Option[AnyValue],

     instantiateSingle: Seq[(Key, AnyValue)] => $Single) {

  // ---------------------------------------------------------------------------
  def parseRecursively(c: Cls, jsonString: String): $Single =
    jsonString
      .pipe(jsonObjectStringParser)
      .pipe(convertRecursively(c))

  // ---------------------------------------------------------------------------
  def convertRecursively(c: Cls)(o: $Single): $Single = {
      unknownKeys(o, c).assert(_.isEmpty) // necessary for union types (see 220615165554)

      c .fields
        .flatMap { field =>
          attemptKey(o, field.key)
            .map { value =>
              field.key ->
                processField(field.info)(value) } }
      .pipe(instantiateSingle) }

  // ===========================================================================
  def parseGsonJsonElement(info: Info)(value: String): AnyValue =
      parseJsonString(info.subInfo1.valueType.isNesting, info.subInfo1.multiple)(value)
        .pipe(processField(info))

  // ===========================================================================
  private def processField(info: Info)(value: AnyValue): AnyValue =
    info.valueExtractionWithFailures {

      // ---------------------------------------------------------------------------
      nestedGalliaClass => multiple =>
        if (!multiple) value.asInstanceOf[    $Single  ].pipe(convertRecursively(nestedGalliaClass))
        else           value.asInstanceOf[Seq[$Single ]].map (convertRecursively(nestedGalliaClass)) } {

      // ---------------------------------------------------------------------------
      bsc => multiple =>
        if (!multiple) value                            .pipe(BasicTypeValueTransformer(bsc))
        else           value.asInstanceOf[Seq[_]].toList.map (BasicTypeValueTransformer(bsc)) }

  // ===========================================================================
  private def parseJsonString(nesting: Boolean, multiple: Boolean)(value: JsonString): AnyValue =
    (nesting, multiple) match { // spilling for instance does not support union types
      case (false, false) => GsonParser.stringToPrimitiveValueAny (value) /*     Vle  */
      case (false, true ) => GsonParser.stringToPrimitiveValueAnys(value) /* Seq[Vle] */
      case (true , false) => AptusGsonToObj .fromObjectString          (value) /*     Obj  */
      case (true , true ) => AptusGsonToObj .fromArrayString           (value) /* Seq[Obj] */ } }

// ===========================================================================
