package aptus
package aptdata
package io
package json

import com.google.gson._
import aptjson.GsonFormatter

// ===========================================================================
/** formatting is less efficient when we don't know the schema (eg have to match on value for scalar vs sequences) */
class JsonFormatting[$Multiple, $Single](
    toGson            : $Single   => JsonObject,
    consumeSelfClosing: $Multiple => CloseabledIterator[$Single]) {

  // ---------------------------------------------------------------------------
  def formatCompactSingle  (o: $Single  ): JsonObjectString =                           toGson(o)              .pipe(GsonFormatter.compact)
  def formatCompactMultiple(o: $Multiple): JsonArrayString  = consumeSelfClosing(o).map(toGson).pipe(jsonArray).pipe(GsonFormatter.compact)

  // ---------------------------------------------------------------------------
  def formatPrettySingle  (o: $Single  ): JsonObjectString =                           toGson(o)              .pipe(GsonFormatter.pretty)
  def formatPrettyMultiple(o: $Multiple): JsonArrayString  = consumeSelfClosing(o).map(toGson).pipe(jsonArray).pipe(GsonFormatter.pretty) }

// ===========================================================================
