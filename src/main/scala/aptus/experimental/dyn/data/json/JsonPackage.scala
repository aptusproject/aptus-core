package aptus
package experimental
package dyn
package data

// ===========================================================================
package object json {
  /* low-tech solution */
  val customJsonFormatters: collection.mutable.Map[Class[_], CustomJsonFormatter] =
    collection.mutable.Map(
      classOf[java.io.File] -> JavaIoFileJsonFormatter) }

// ===========================================================================