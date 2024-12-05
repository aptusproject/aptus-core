package gallia
package gson

import com.google.gson._
import scala.jdk.CollectionConverters._
import aptus.experimental.dyn._
import aptus.{JsonObjectString, JsonArrayString}

// ===========================================================================
object GsonParsing {
  private type Obj = aptus.experimental.dyn.data.sngl.Dyn

  // ---------------------------------------------------------------------------
  implicit class DynIterator_(u: Iterator[Dyn]) { def toListAndTrash = u.toList }

// borrowed from gallia (TODO: t241130165320 - refactor)
                                            private def illegal(anys: Any*) = throw new IllegalArgumentException(anys.mkString(","))

                                            // ===========================================================================
                                            def parseObject(value: JsonObject): Obj  = BorrowedGsonFrom.jsonObjectToObj(value)

                                            // ---------------------------------------------------------------------------
                                            def parseObject(value: JsonObjectString): Obj  =
                                              util.Try(BorrowedGsonFrom.fromObjectString(value)) match {
                                                case util.Failure(throwable) => illegal(s"\n\t${throwable.getMessage}\n\t\t${value}")
                                                case util.Success(success) => success }

                                            // ===========================================================================
                                            def parseObject(value: JsonArray): Seq[Obj] =
                                                value
                                                  .iterator
                                                  .asScala
                                                  .map(_.getAsJsonObject)
                                                  .map(BorrowedGsonFrom.jsonObjectToObj)
                                                  .toList

                                            // ---------------------------------------------------------------------------
                                            def parseArray(value: JsonArrayString): List[Obj] = // TODO: t201221175254 - stream rather than read all in memory
                                              util.Try(BorrowedGsonFrom.fromArrayString(value)) match {
                                                case util.Failure(throwable) => illegal(throwable.getMessage, value)
                                                case util.Success(success)   => success.toListAndTrash }
                                          }

                                          // ===========================================================================
