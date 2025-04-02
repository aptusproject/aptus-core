/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.experimental.dyn.aillag /* do not split this line */
package data
package json

import aptus.{JsonObjectString, JsonArrayString}
import aptus.experimental.dyn.aillag.data.json.jsonArray // leave this line (used in the case of gallia)

// ===========================================================================
/** formatting is less efficient when we don't know the schema (eg have to match on value for scalar vs sequences) */
object ObjToGson { // TODO: t214360121145 - switch from gson to lihaoyi's ujson
  import com.google.gson._

  // ===========================================================================
  def formatCompact(o: Obj ): JsonObjectString = apply(o).pipe(formatCompact)
  def formatCompact(o: Objs): JsonArrayString  = apply(o).pipe(formatCompact)

  def formatPretty (o: Obj ): JsonObjectString = apply(o).pipe(formatPretty)
  def formatPretty (o: Objs): JsonArrayString  = apply(o).pipe(formatPretty)

  // ===========================================================================
  private def formatCompact(value: JsonObject): String = aptus.aptjson.GsonFormatter.compact(value)
  private def formatCompact(value: JsonArray) : String = aptus.aptjson.GsonFormatter.compact(value)

  private def formatPretty(value: JsonObject): String = aptus.aptjson.GsonFormatter.pretty(value)
  private def formatPretty(value: JsonArray) : String = aptus.aptjson.GsonFormatter.pretty(value)

  // ===========================================================================
          private def apply(o: Objs): JsonArray  = o.exoMap(apply).pipe(jsonArray)
  @inline private def apply(o: Obj ): JsonObject = ObjToGson2.apply(o) }

// ===========================================================================
