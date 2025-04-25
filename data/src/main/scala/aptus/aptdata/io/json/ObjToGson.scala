package aptus
package aptdata
package io
package json

import com.google.gson._

// ===========================================================================
/** formatting is less efficient when we don't know the schema (eg have to match on value for scalar vs sequences) */
class ObjToGson[$Multiple, $Single]( // TODO: t214360121145 - switch from gson to lihaoyi's ujson
    toGson            : $Single  => JsonObject,
    consumeSelfClosing: $Multiple => aptus.CloseabledIterator[$Single]) {

  // ===========================================================================
  def formatCompact(o: $Single ): JsonObjectString = toGson(o).pipe(formatCompact)
  def formatCompact2(o: $Multiple): JsonArrayString  = consumeSelfClosing(o).map(toGson).pipe(jsonArray).pipe(formatCompact)

  def formatPretty (o: $Single ): JsonObjectString = toGson(o).pipe(formatPretty)
  def formatPretty2 (o: $Multiple): JsonArrayString  = consumeSelfClosing(o).map(toGson).pipe(jsonArray).pipe(formatPretty)

  // ===========================================================================
  private def formatCompact(value: JsonObject): String = aptus.aptjson.GsonFormatter.compact(value)
  private def formatCompact(value: JsonArray) : String = aptus.aptjson.GsonFormatter.compact(value)

  private def formatPretty(value: JsonObject): String = aptus.aptjson.GsonFormatter.pretty(value)
  private def formatPretty(value: JsonArray) : String = aptus.aptjson.GsonFormatter.pretty(value) }

// ===========================================================================
