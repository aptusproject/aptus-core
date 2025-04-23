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
        case FileExtensionMultiple.TsvExtension       => DynOut2.formatTable(dyns)(sep = "\t").writeFileLines(value)
        case _ => ??? }
      .getOrElse { ??? }

  // ---------------------------------------------------------------------------
  def write(dyns: Dynz)(value: OutputFilePath): OutputFilePath =
    FileExtensionMultiple
      .parse(value)
      .map {
        case FileExtensionMultiple.JsonArrayExtension => dyns.formatCompactJson.newline.writeFileContent(value)
        case FileExtensionMultiple.TsvExtension       => ???//DynOut2.formatTable(dyns)(sep = "\t").writeFileLines(value)
        case _ => ??? }
      .getOrElse { ??? } }

// ===========================================================================
