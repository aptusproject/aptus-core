package gallia
package gson

import com.google.gson._
import aptus.experimental.dyn._
import aptus.{JsonArrayString, JsonObjectString}
import data.sngl.DynData
import adaptor.GalliaScalaAliases._
import org.apache.commons.math3.linear.RealMatrix

// ===========================================================================
/*private[dyn] */object BorrowedGsonTo { // TODO: t214360121145 - switch from gson to lihaoyi's ujson
  private lazy val Gson = new GsonBuilder().create()

  // ===========================================================================
  def formatCompact(o: DynData): JsonObjectString = apply(o).pipe(formatCompact)
  def formatCompact(o: Dyns): JsonArrayString  = apply(o).pipe(formatCompact)

  def formatPretty (o: DynData): JsonObjectString = apply(o).pipe(formatPretty)
  def formatPretty (o: Dyns): JsonArrayString  = apply(o).pipe(formatPretty)

  // ===========================================================================
  private def formatCompact(x: JObject): String = aptus.aptjson.GsonFormatter.compact(x)
  private def formatCompact(x: JArray) : String = aptus.aptjson.GsonFormatter.compact(x)

  private def formatPretty(x: JObject): String = aptus.aptjson.GsonFormatter.pretty(x)
  private def formatPretty(x: JArray) : String = aptus.aptjson.GsonFormatter.pretty(x)

  // ===========================================================================
  private def apply(o: Dyns)   : JArray = array(o.exoMap(apply).toList)
  private def apply(o: DynData): JObject = // TODO: t201230140315 - hopefully there's a more efficient way (no access to "members"?)...
      new JsonObject()
        .tap { mut =>
          o .entries
            .foreach { entry =>
            mut.add(
              /* property = */ entry.key.name, // note: underlying map "uses insertion order for iteration order"
              /* value    = */ entry.valew match {
case Valew(data.mult.list.Dyns(_))   => ???//array(seq.map(element)) // no nested Dynz/Iterator (see a241119155444) - TODO
                case Valew(seq: Seq[_]) => array(seq.map(element))
                case Valew(sgl)         => element(sgl) }) } }

    // ===========================================================================
    private def array(seq: Seq[JsonElement]): JArray =
        new JsonArray(/*elements.size; FIXME: t210315113950 - causes issues with EMR: look into more*/)
            .tap { array => seq.foreach(array.add) }

    // ---------------------------------------------------------------------------
import _root_.gallia.data.DataFormatting
    //@TypeMatching
    private def element(value: Any): JsonElement =
      Gson.toJsonTree(
        value match { // see BasicType

                                            case x: String     => x
                                            case x: Int        => x
                                            case x: Double     => x
                                            case x: Boolean    => x
//case x: java.lang.Integer => x.toInt
                                            // ---------------------------------------------------------------------------
                                            case x: Dyn        => apply(x)

                                            // ---------------------------------------------------------------------------
//          case x: EnumValue   => x.stringValue

                                            // ---------------------------------------------------------------------------
                                            case x: Byte       => x
                                            case x: Short      => x
                                            case x: Long       => x
                                            case x: Float      => x

                                            // ---------------------------------------------------------------------------
                                            case x: BigInt     => DataFormatting.formatBigInt(x) // can't trust JSON with bignums
                                            case x: BigDec     => DataFormatting.formatBigDec(x) // can't trust JSON with bignums

                                            // ---------------------------------------------------------------------------

                                            case x: LocalDate      => DataFormatting.formatLocalDate(x)
                                            case x: LocalDateTime  => DataFormatting.formatLocalDateTime (x)
                                            case x: Instant        => DataFormatting.formatInstant(x)

                                            // ---------------------------------------------------------------------------
                                            case x: ByteBuffer => DataFormatting.formatBinary(x)
// 2+D arrays:
case x: Seq[_] => x.map(element).pipe(array)
case x: RealMatrix => x.getData.map { _.map(element).toList.pipe(array) }.toList.pipe(array)

                                            // ---------------------------------------------------------------------------
case x => x.getClass.pipe(customJsonFormatters.get)
                                              match {
                                                case Some(customFormatter) => customFormatter.format(x)
                                                case None                  => x.toString /* fall back on toString (avoid if possible */ } })
}


// ===========================================================================
