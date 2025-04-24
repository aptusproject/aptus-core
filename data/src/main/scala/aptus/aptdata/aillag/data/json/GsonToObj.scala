/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package data
package json

import aptus.Anything_
import aptus.{JsonObjectString, JsonArrayString}
import aptus.aptjson.GsonParser
import scala.jdk.CollectionConverters._
import com.google.gson._

// ===========================================================================
/* important note: 201117103600 - JsonObject maintains its entries in a com.google.gson.internal.LinkedTreeMap
 * which despite its name maintains element order, though it requires a trick (see 201117103735) */
class GsonToObj[$Jbos, $Jbo]( // TODO: t214360121145 - switch from gson to lihaoyi's ujson
    ObjfromIterable: Iterable[(Key, Any)] => $Jbo,
    Objsfrom       : List[$Jbo] => $Jbos) {

  // ---------------------------------------------------------------------------
  def fromGsonObject  (value: JsonObject)      : $Jbo  =                                            jsonObjectToObj(value)
  def fromObjectString(value: JsonObjectString): $Jbo  = GsonParser.stringToJsonObject (value).pipe(jsonObjectToObj)
  def fromArrayString (value: JsonArrayString) : $Jbos = GsonParser.stringToJsonObjects(value).map (jsonObjectToObj).toList.pipe(Objsfrom) // TODO: from iterator

  // ===========================================================================
  private[json] def jsonObjectToObj(value: JsonObject): $Jbo =
      value
        .entrySet()
        .iterator().asScala.toList // trick 201117103735; only iterator has been overriden to maintain order... (see 201117103600); likewise for KeySet
        .flatMap { entry =>
          jsonRootToAnyValue(entry.getValue)
            .map(entry.getKey.pipe(aptus.aptdata.meta.schema.Key._fromString) -> _) }
        .pipe(ObjfromIterable) //TODO: catch duplicates and so on

    // ---------------------------------------------------------------------------
    private def jsonRootToAnyValue(value: JsonElement): Option[AnyValue] =
        value match {
          case x: JsonPrimitive              => Some(GsonParser.jsonPrimitiveToAny(x))
          case x: JsonObject if (x.size > 0) => Some(jsonObjectToObj(x)) //FIXME: t210116150154 - must remove nulls prior to size
          case x: JsonArray                  => x.iterator().asScala.toList.flatMap(jsonRootToAnyValue).in.noneIf(_.isEmpty)
          case _: JsonObject                 => None
          case _: JsonNull                   => None } }

// ===========================================================================