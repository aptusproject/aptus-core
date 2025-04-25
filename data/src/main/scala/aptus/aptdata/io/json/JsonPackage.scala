package aptus
package aptdata
package io

// ===========================================================================
package object json {
  import com.google.gson.{JsonElement, JsonArray}

  // ---------------------------------------------------------------------------
  def jsonArray(elements: Seq     [JsonElement]): JsonArray = elements.iterator                           .pipe(jsonArray)
  def jsonArray(elements: Iterator[JsonElement]): JsonArray = CloseabledIterator.fromUncloseable(elements).pipe(jsonArray)

  // ---------------------------------------------------------------------------
  def jsonArray(elements: CloseabledIterator[JsonElement]): JsonArray =
      new JsonArray(/*elements.size; FIXME: t210315113950 - causes issues with EMR: look into more*/)
        .tap { array => elements.foreach(array.add) }

  // ===========================================================================
  /* low-tech solution */
  val customJsonFormatters: collection.mutable.Map[Class[_], CustomJsonFormatter] =
    collection.mutable.Map(
      classOf[java.io.File] -> JavaIoFileJsonFormatter) }

// ===========================================================================