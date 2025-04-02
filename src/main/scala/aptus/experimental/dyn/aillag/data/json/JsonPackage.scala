package aptus.experimental.dyn.aillag
package data

// ===========================================================================
package object json {
  import com.google.gson.{JsonElement, JsonArray}

  // ---------------------------------------------------------------------------
  def jsonArray(elements: Seq[JsonElement]): JsonArray = jsonArray(elements.iterator)

  def jsonArray(elements: Iterator[JsonElement]): JsonArray =
    new JsonArray(/*elements.size; FIXME: t210315113950 - causes issues with EMR: look into more*/)
      .tap { array => elements.foreach(array.add) } }

// ===========================================================================