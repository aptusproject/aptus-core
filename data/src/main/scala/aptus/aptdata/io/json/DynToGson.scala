package aptus
package aptdata
package io
package json

import org.apache.commons.math3.linear.RealMatrix

// ===========================================================================
private[aptdata] object DynToGson { // TODO: t214360121145 - switch from gson to lihaoyi's ujson
  import com.google.gson._

  // ---------------------------------------------------------------------------
  private lazy val GsonInstance = new com.google.gson.GsonBuilder().create()

  // ---------------------------------------------------------------------------
  def apply(o: Dyn): JsonObject = // TODO: t201230140315 - hopefully there's a more efficient way (no access to "members"?)...
      new JsonObject()
        .tap { mut =>
          o .entries
            .foreach { entry =>
              mut.add(
                /* property = */ entry.key.name, // note: underlying map "uses insertion order for iteration order"
                /* value    = */ entry.valew.naked match {
                  // note: no nested Dynz/Iterator (see a241119155444) - TODO: t250402132436
                  case dyns: Dyns  => jsonArray(dyns.exoMap(element))
                  case seq: Seq[_] => jsonArray(seq.map    (element))
                  case sgl         => element(sgl) }) } }

    // ===========================================================================
    private val dynOnly: PartialFunction[Any, Any] = {
      case x: Dyn => apply(x) // nesting

      // ---------------------------------------------------------------------------
      // 2+D arrays:
      case x: RealMatrix => x.getData.map { _.map(element).toList.pipe(jsonArray) }.toList.pipe(jsonArray)
      case x: Seq[_] => x.map(element).pipe(jsonArray)

      // ---------------------------------------------------------------------------
      case x => x.getClass.pipe(aptus.aptdata.io.json.customJsonFormatters.get)
        match {
          case Some(customFormatter) => customFormatter.format(x)
          case None                  => x.toString /* fall back on toString (avoid if possible */ } }

    // ===========================================================================
    private def element(value: Any): JsonElement =
       BasicTypeJsonValueNormalization.partial.orElse(dynOnly)
        .apply(value)
        .pipe (GsonInstance.toJsonTree) }

// ===========================================================================