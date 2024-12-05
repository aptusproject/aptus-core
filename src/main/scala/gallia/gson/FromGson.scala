package gallia
package gson

import com.google.gson._
import scala.jdk.CollectionConverters._
import aptus.experimental.dyn._
import aptus.{Anything_, JsonObjectString, JsonArrayString, aptjson}

// ===========================================================================
/*private[dyn] */object BorrowedGsonFrom {
  def fromObjectString(value: JsonObjectString) = aptjson.GsonParser.stringToJsonObject (value).pipe(apply)
  def fromArrayString (value: JsonArrayString)  = aptjson.GsonParser.stringToJsonObjects(value).map (apply)

  // ---------------------------------------------------------------------------
  private def apply(value: JObject): Dyn = jsonObjectToObj(value)

  // ===========================================================================
  def jsonObjectToObj(value: JObject): Dyn =
      value
        .entrySet()
        .iterator().asScala.toList // trick 201117103735; only iterator has been overriden to maintain order... (see 201117103600); likewise for KeySet
        .flatMap { entry =>
          entry
            .getValue
            .pipe(jsonRootToAnyValue)
            .map (Valew.build)
            .map { valew =>
              entry
                .getKey
                .pipe(Key._from) -> valew } }
.map(aptus.experimental.dyn.domain.Entry.buildw)
        .pipe(data.sngl.Dyn.byPass) // TODO: confirm can always safely bypass

    // ---------------------------------------------------------------------------
    private def jsonRootToAnyValue(value: JsonElement): Option[Any] =
        value match {
          case x: JsonPrimitive              => Some(jsonPrimitiveToAny(x))
          case x: JsonObject if (x.size > 0) => Some(jsonObjectToObj(x)) //FIXME: t210116150154 - must remove nulls prior to size
          case x: JsonArray                  => x.iterator().asScala.toList.flatMap(jsonRootToAnyValue).in.noneIf(_.isEmpty)
          case _: JsonObject                 => None
          case _: JsonNull                   => None }

  // ===========================================================================
  /** unfortunately no access to underlying Object value in gson; TODO: t201103154749 - look into cost of reflection setAccessible(true) or do a PR */
  private def jsonPrimitiveToAny(x: JsonPrimitive): Any =
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
