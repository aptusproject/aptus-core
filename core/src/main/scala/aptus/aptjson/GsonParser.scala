package aptus
package aptjson

import scala.util.chaining._
import scala.jdk.CollectionConverters._
import com.google.gson._

// ===========================================================================
object GsonParser {

  /** we're not interested in literals */
  def parseString(value: JsonString): Option[Either[JsonObject, JsonArray]] =
    value
      .dropWhile(c =>
        c != '{' &&
        c != '[')
      .headOption
      .map { first =>
        if (first == '{') Left (stringToJsonObject(value))
        else              Right(stringToJsonArray (value)) }

  // ===========================================================================
  @inline def stringToJsonElement(value: JsonObjectString): JsonElement = JsonParser.parseString(value)

    // ---------------------------------------------------------------------------
    def stringToJsonObject   (value: JsonObjectString): JsonObject    = stringToJsonElement(value).getAsJsonObject
    def stringToJsonPrimitive(value: JsonObjectString): JsonPrimitive = stringToJsonElement(value).getAsJsonPrimitive
    def stringToJsonArray    (value: JsonArrayString) : JsonArray     = stringToJsonElement(value).getAsJsonArray

  // ---------------------------------------------------------------------------
  def stringToJsonObjects(value: JsonArrayString): Iterator[JsonObject] =
    stringToJsonArray(value)
      .iterator().asScala
      .map(_.getAsJsonObject)

  // ---------------------------------------------------------------------------
  def stringToPrimitiveValueAny (value: String):     Any  = jsonPrimitiveToAny(stringToJsonPrimitive(value))
  def stringToPrimitiveValueAnys(value: String): Seq[Any] = stringToJsonArray(value).asScala.toList.map(_.getAsJsonPrimitive.pipe(jsonPrimitiveToAny))

  // ===========================================================================
  /** unfortunately no access to underlying Object value in gson; TODO: t201103154749 - look into cost of reflection setAccessible(true) or do a PR */
  def jsonPrimitiveToAny(x: JsonPrimitive): Any =
    /**/ if (x.isString ) x.getAsString
    else if (x.isBoolean) x.getAsBoolean
    else
      /*
       * TODO: can it actually be any of:
       * - AtomicInteger, AtomicLong,
       * - DoubleAccumulator, DoubleAdder,
       * - LongAccumulator, LongAdder */
      x.getAsNumber match { // TODO: in which case will "value: Object" actually already be a Number (aot a LazilyParsedNumber)?
        case x: java.math.BigDecimal => BigDecimal(x)
        case x: java.math.BigInteger => BigInt    (x)
        case x                       => x.doubleValue() /* may also be com.google.gson.internal.LazilyParsedNumber, which stores it as a String (but only accessible as int, long, float and double, ...) */ }

}

// ===========================================================================
