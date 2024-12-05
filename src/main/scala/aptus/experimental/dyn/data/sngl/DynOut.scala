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
      self: DynData =>
    override final def formatCompactJson: String = _root_.gallia.gson.BorrowedGsonTo.formatCompact(this)
    override final def formatPrettyJson : String = _root_.gallia.gson.BorrowedGsonTo.formatPretty (this) }

// ===========================================================================
