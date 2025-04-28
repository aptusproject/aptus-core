package aptus
package aptdata
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

    override final def formatCompactJson: String = io.json.DynToGson(self).pipe(aptjson.GsonFormatter.compact)
    override final def  formatPrettyJson: String = io.json.DynToGson(self).pipe(aptjson.GsonFormatter.pretty ) }

// ===========================================================================
