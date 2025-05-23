/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.aptdata.aillag /* do not split this line */
package data
package json

import com.google.gson.{JsonObject, JsonArray}
import scala.collection.JavaConverters._

import aptus.{JsonObjectString, JsonArrayString}

// ===========================================================================
object GsonParsing {
  private def illegal(anys: Any*) = throw new IllegalArgumentException(anys.mkString(","))

  // ===========================================================================
  def parseObject(value: JsonObject): Obj  = GsonToObj.jsonObjectToObj(value) // TODO: t250110115612 -?

  // ---------------------------------------------------------------------------
  def parseObject(value: JsonObjectString): Obj  =
    util.Try(GsonToObj.fromObjectString(value)) match {
      case util.Failure(throwable) => illegal(s"\n\t${throwable.getMessage}\n\t\t${value}")
      case util.Success(success) => success }

  // ===========================================================================
  def parseObject(value: JsonArray): Seq[Obj] =
      value
        .iterator
        .asScala
        .map(_.getAsJsonObject)
        .map(GsonToObj.jsonObjectToObj)
        .toList

  // ---------------------------------------------------------------------------
  def parseArray(value: JsonArrayString): List[Obj] = // TODO: t201221175254 - stream rather than read all in memory
    util.Try(GsonToObj.fromArrayString(value)) match {
      case util.Failure(throwable) => illegal(throwable.getMessage, value)
      case util.Success(success)   => success.toListAndTrash } }

// ===========================================================================