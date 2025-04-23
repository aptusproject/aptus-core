package aptus
package aptdata
package io

// ===========================================================================
sealed trait FileExtensionSingle

  object FileExtensionSingle {
    case object JsonObjectExtension extends FileExtensionSingle
    case object TsvExtension        extends FileExtensionSingle

    // ---------------------------------------------------------------------------
    def parse(value: FilePath): Option[FileExtensionSingle] =
      if (value.startsWith("/"))
             if (value.endsWith(".json") || value.endsWith(".jsono")) Some(JsonObjectExtension)
        else if (value.endsWith(".tsv"))                              Some(TsvExtension)
        else                                                          None
      else None }

// ===========================================================================
sealed trait FileExtensionMultiple

  object FileExtensionMultiple {
    case object JsonLinesExtension extends FileExtensionMultiple
    case object JsonArrayExtension extends FileExtensionMultiple
    case object TsvExtension       extends FileExtensionMultiple

    // ---------------------------------------------------------------------------
    def parse(value: FilePath): Option[FileExtensionMultiple] =
     if (value.startsWith("/"))
             if (value.endsWith(".jsonl") || value.endsWith(".jsons")) Some(JsonLinesExtension)
        else if (value.endsWith(".json")  || value.endsWith(".jsona")) Some(JsonArrayExtension)
        else if (value.endsWith(".tsv"))                               Some(TsvExtension)
        else None
      else None }

// ===========================================================================
sealed trait StringContent

  object StringContent {
    case object JsonObjectString extends StringContent
    case object JsonArrayString  extends StringContent

    // ---------------------------------------------------------------------------
    def parse(value: InputFilePath): Option[StringContent] =
           if (value.startsWith("[")) Some(JsonArrayString)
      else if (value.startsWith("{")) Some(JsonObjectString)
      else                            None }

// ===========================================================================
