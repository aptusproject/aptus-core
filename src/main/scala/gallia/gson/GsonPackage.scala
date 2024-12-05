package gallia

// ===========================================================================
package object gson {
  /* low-tech solution */
  val customJsonFormatters: collection.mutable.Map[Class[_], CustomJsonFormatter] =
    collection.mutable.Map(
      classOf[java.io.File] -> JavaIoFileJsonFormatter)

  // ---------------------------------------------------------------------------
  private[gson] type JArray  = com.google.gson.JsonArray
  private[gson] type JObject = com.google.gson.JsonObject }

// ===========================================================================