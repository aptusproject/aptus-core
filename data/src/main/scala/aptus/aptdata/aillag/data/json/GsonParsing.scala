package aptus
package aptdata
package aillag
package data
package json

import com.google.gson.{JsonObject, JsonArray}
import scala.jdk.CollectionConverters._

// ===========================================================================
class GsonParsing[$Jbo] {

  def parseObject(value: JsonObject): $Jbo  = GsonToObj.jsonObjectToObj(value) // TODO: t250110115612 -?
.asInstanceOf[$Jbo]

  // ---------------------------------------------------------------------------
  def parseObject(value: JsonObjectString): $Jbo  =
    util.Try(GsonToObj.fromObjectString(value)) match {
      case util.Failure(throwable) => aptus.illegalArgument(s"\n\t${throwable.getMessage}\n\t\t${value}")
      case util.Success(success) => success
.asInstanceOf[$Jbo]
    }

  // ===========================================================================
  def parseObject(value: JsonArray): Seq[$Jbo] =
      value
        .iterator
        .asScala
        .map(_.getAsJsonObject)
        .map(GsonToObj.jsonObjectToObj)
        .toList
.asInstanceOf[Seq[$Jbo]]
  // ---------------------------------------------------------------------------
  def parseArray(value: JsonArrayString): List[$Jbo] = // TODO: t201221175254 - stream rather than read all in memory
    util.Try(GsonToObj.fromArrayString(value)) match {
      case util.Failure(throwable) => aptus.illegalArgument(throwable.getMessage, value)
      case util.Success(success)   =>
        ???
//        success.toListAndTrash // u.values.toList
//.asInstanceOf[List[$Jbo]]
    } }

// ===========================================================================