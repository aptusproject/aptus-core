package aptus
package experimental
package dyn
package data
package sngl

import ops.common.CommonOutputter

// ===========================================================================
trait DynFormatDefault
      extends HasFormatDefault {
        self: DynJsonWriter =>
    override final def toString     : String = formatDefault
    override final def formatDefault: String = formatCompactJson }

  // ---------------------------------------------------------------------------
  trait DynFileWriter
    extends CommonOutputter /* eg .write("/my/file") */ {
        self: DynJsonWriter =>
    override final def write(s: OutputFilePath): OutputFilePath = io.out.DynOut.write(this)(s) }

  // ---------------------------------------------------------------------------
  trait DynJsonWriter
    extends CommonOutputter /* eg .write("/my/file") */ {
      self: Dyn =>
    override final def formatCompactJson: String = aillag.data.json.ObjToGson.formatCompact(this)
    override final def formatPrettyJson : String = aillag.data.json.ObjToGson.formatPretty (this) }

// ===========================================================================
