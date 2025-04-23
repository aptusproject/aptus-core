package aptus
package aptdata

// ===========================================================================
package object json {
  /* low-tech solution */
  val customJsonFormatters: collection.mutable.Map[Class[_], CustomJsonFormatter] =
    collection.mutable.Map(
      classOf[java.io.File] -> JavaIoFileJsonFormatter) }

// ===========================================================================