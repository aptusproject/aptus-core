/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package data
package json

import aptus.{JsonObjectString, JsonArrayString}
import com.google.gson._

// ===========================================================================
/** formatting is less efficient when we don't know the schema (eg have to match on value for scalar vs sequences) */
class ObjToGson[$Jbos, $Jbo]( // TODO: t214360121145 - switch from gson to lihaoyi's ujson
    toGson            : $Jbo  => JsonObject,
    consumeSelfClosing: $Jbos => aptus.CloseabledIterator[$Jbo]) {

  // ===========================================================================
  def formatCompact(o: $Jbo ): JsonObjectString = toGson(o).pipe(formatCompact)
  def formatCompact2(o: $Jbos): JsonArrayString  = consumeSelfClosing(o).map(toGson).pipe(jsonArray).pipe(formatCompact)

  def formatPretty (o: $Jbo ): JsonObjectString = toGson(o).pipe(formatPretty)
  def formatPretty2 (o: $Jbos): JsonArrayString  = consumeSelfClosing(o).map(toGson).pipe(jsonArray).pipe(formatPretty)

  // ===========================================================================
  private def formatCompact(value: JsonObject): String = aptus.aptjson.GsonFormatter.compact(value)
  private def formatCompact(value: JsonArray) : String = aptus.aptjson.GsonFormatter.compact(value)

  private def formatPretty(value: JsonObject): String = aptus.aptjson.GsonFormatter.pretty(value)
  private def formatPretty(value: JsonArray) : String = aptus.aptjson.GsonFormatter.pretty(value) }

// ===========================================================================
