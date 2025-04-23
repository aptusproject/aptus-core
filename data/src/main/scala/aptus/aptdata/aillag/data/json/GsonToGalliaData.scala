/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package data
package json

import aptus.Anything_
import aptus.JsonString
import aptus.aptjson.GsonParser
import aptus.aptdata.meta.basic.BasicTypeValueTransformer

// ===========================================================================
/** parsing is more efficient when we know the schema already */
object GsonToGalliaData {
  type InfoLike = Info // TODO: t250115161608

  // ---------------------------------------------------------------------------
  def parseRecursively(c: Cls, jsonString: String): Obj =
    jsonString
      .pipe(GsonParsing.parseObject)
      .pipe(GsonToGalliaData.convertRecursively(c))

  // ---------------------------------------------------------------------------
  def convertRecursively(c: Cls)(o: Obj): Obj = {
      c.unknownKeys(o).assert(_.isEmpty) // necessary for union types (see 220615165554)

      c .fields
        .flatMap { field =>
          o .attemptKey(field.key)
            .map { value =>
              field.key ->
                processField(field.info)(value) } }
      .pipe(gallia.obj) }

  // ===========================================================================
  def parseGsonJsonElement(info: Info)(value: String): AnyValue =
      parseJsonString(info.subInfo1.valueType.isNesting, info.subInfo1.multiple)(value)
        .pipe(processField(info))

  // ===========================================================================
  private def processField(info: Info)(value: AnyValue): AnyValue =
    info.valueExtractionWithFailures {

      // ---------------------------------------------------------------------------
      nestedGalliaClass => multiple =>
        if (!multiple) value.asInstanceOf[    Obj  ].pipe(convertRecursively(nestedGalliaClass))
        else           value.asInstanceOf[Seq[Obj ]].map (convertRecursively(nestedGalliaClass)) } {

      // ---------------------------------------------------------------------------
      bsc => multiple =>
        if (!multiple) value                            .pipe(BasicTypeValueTransformer(bsc))
        else           value.asInstanceOf[Seq[_]].toList.map (BasicTypeValueTransformer(bsc)) }

  // ===========================================================================
  private def parseJsonString(nesting: Boolean, multiple: Boolean)(value: JsonString): AnyValue =
    (nesting, multiple) match { // spilling for instance does not support union types
      case (false, false) => GsonParser.stringToPrimitiveValueAny (value) /*     Vle  */
      case (false, true ) => GsonParser.stringToPrimitiveValueAnys(value) /* Seq[Vle] */
      case (true , false) => GsonToObj .fromObjectString          (value) /*     Obj  */
      case (true , true ) => GsonToObj .fromArrayString           (value) /* Seq[Obj] */ } }

// ===========================================================================