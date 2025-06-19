package aptus
package aptdata
package io
package out

// ===========================================================================
private[aptdata] object DynOut {

  // ---------------------------------------------------------------------------
  def write(dyn: sngl.DynJsonWriter)(value: OutputFilePath): OutputFilePath =
    FileExtensionSingle
      .parse(value)
      .map {
        case FileExtensionSingle.JsonObjectExtension => dyn.formatCompactJson.newline.writeFileContent(value)
        case FileExtensionSingle.TsvExtension        => ???
        case _ => ??? }
      .getOrElse { ??? }

  // ===========================================================================
  def write(dyns: Dyns)(value: OutputFilePath): OutputFilePath =
    FileExtensionMultiple
      .parse(value)
      .map {
        case FileExtensionMultiple.JsonArrayExtension => dyns.formatCompactJson.newline.writeFileContent(value)
        case FileExtensionMultiple.TsvExtension       => DynTableFormatting.formatTable(dyns)(sep = "\t").writeFileLines(value)
        case _ => ??? }
      .getOrElse { ??? }

  // ---------------------------------------------------------------------------
  def write(dynz: Dynz)(value: OutputFilePath): OutputFilePath =
    FileExtensionMultiple
      .parse(value)
      .map {
        case FileExtensionMultiple.JsonArrayExtension => dynz.formatCompactJson.newline.writeFileContent(value)
        case FileExtensionMultiple.TsvExtension       => dynz.formatTableLike          .writeFileContent(value) /* avoid in favor of explicit call with keys */
        case _ => ??? }
      .getOrElse { ??? } }

// ===========================================================================
