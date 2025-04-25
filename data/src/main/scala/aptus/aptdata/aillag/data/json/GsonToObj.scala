package aptus
package aptdata
package aillag
package data
package json

import aptus.Anything_
import aptus.aptjson.GsonParser
import scala.jdk.CollectionConverters._
import com.google.gson._

// ===========================================================================
/* important note: 201117103600 - JsonObject maintains its entries in a com.google.gson.internal.LinkedTreeMap
 * which despite its name maintains element order, though it requires a trick (see 201117103735) */
class GsonToObj[$Multiple, $Single]( // TODO: t214360121145 - switch from gson to lihaoyi's ujson
    instantiateSingle  : Seq[(Key, AnyValue)] => $Single,
    instantiateMultiple: List[$Single] => $Multiple) {

  // ---------------------------------------------------------------------------
  def fromObjectString(value: JsonObjectString): $Single  = GsonParser.stringToJsonObject (value).pipe(jsonObjectToObj)
  def fromArrayString (value: JsonArrayString) : $Multiple = GsonParser.stringToJsonObjects(value).map (jsonObjectToObj).toList.pipe(instantiateMultiple) // TODO: from iterator

  // ===========================================================================
  def jsonObjectToObj(value: JsonObject): $Single =
      value
        .entrySet()
        .iterator().asScala.toList // trick 201117103735; only iterator has been overriden to maintain order... (see 201117103600); likewise for KeySet
        .flatMap { entry =>
          jsonRootToAnyValue(entry.getValue)
            .map(entry.getKey.pipe(aptus.aptdata.meta.schema.Key._fromString) -> _) }
        .pipe(instantiateSingle) //TODO: catch duplicates and so on

    // ---------------------------------------------------------------------------
    private def jsonRootToAnyValue(value: JsonElement): Option[AnyValue] =
        value match {
          case x: JsonPrimitive              => Some(GsonParser.jsonPrimitiveToAny(x))
          case x: JsonObject if (x.size > 0) => Some(jsonObjectToObj(x)) //FIXME: t210116150154 - must remove nulls prior to size
          case x: JsonArray                  => x.iterator().asScala.toList.flatMap(jsonRootToAnyValue).in.noneIf(_.isEmpty)
          case _: JsonObject                 => None
          case _: JsonNull                   => None } }

// ===========================================================================
