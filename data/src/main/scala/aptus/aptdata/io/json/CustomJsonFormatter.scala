package aptus
package aptdata
package io
package json

// ===========================================================================
trait CustomJsonFormatter {
    def format(value: Any): String }

  // ---------------------------------------------------------------------------
  object JavaIoFileJsonFormatter
      extends CustomJsonFormatter {
    def format(value: Any): String =
      value.asInstanceOf[java.io.File].getAbsolutePath }

// ===========================================================================
