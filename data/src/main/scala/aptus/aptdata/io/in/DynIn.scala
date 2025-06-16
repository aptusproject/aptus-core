package aptus
package aptdata
package io
package in

// ===========================================================================
private[aptdata] object DynIn { import DynInUtils._

  def single(value: InputFilePath): Dyn =
    gzipNormalisingOpt(value)
      .map(FileExtensionSingle.parse)
      .flatMap(_.map(_single(value)))
    .orElse {
      FileExtensionSingle
        .parse(value)
        .map(_single(value)) }
    .orElse { StringContent.parse(value).map {
      case StringContent.JsonArrayString  =>  value.p__; ??? // TODO: t241029154956
      case StringContent.JsonObjectString => jsonO(value) } }
    .getOrElse {  value.p__; ??? }

  // ---------------------------------------------------------------------------
  def multiple(value: InputFilePath): Dyns =
    if (value.endsWith(".gz"))
      value
        .stripSuffixGuaranteed(".gz")
        .pipe(FileExtensionMultiple.parse)
        .map(_multiple(value))
        .getOrElse(???)
    else
    FileExtensionMultiple
      .parse(value)
      .map(_multiple(value))
      .orElse { StringContent.parse(value).map {
        case StringContent.JsonArrayString  => jsonAs(value)
        case StringContent.JsonObjectString =>  value.p__; ??? /* TODO: t241029154956 */ } }
      .getOrElse {  value.p__; ??? }

  // ---------------------------------------------------------------------------
  def multiplez(value: InputFilePath): Dynz =
    if (value.endsWith(".gz"))
      value
        .stripSuffixGuaranteed(".gz")
        .pipe(FileExtensionMultiple.parse)
        .map(_multiplez(value))
        .getOrElse(???)
    else
        FileExtensionMultiple
          .parse(value)
          .map(_multiplez(value))
          .orElse { StringContent.parse(value).map {
            case StringContent.JsonArrayString  => jsonAz(value)
            case StringContent.JsonObjectString =>  value.p__; ??? /* TODO: t241029154956 */ } }
          .getOrElse { value.p__; ??? }

  // ===========================================================================
  private def gzipNormalisingOpt(value: InputFilePath): Option[InputFilePath] =
    value
      .in.someIf(_.endsWith(".gz"))
      .map(_.stripSuffixGuaranteed(".gz"))

  // ---------------------------------------------------------------------------
  private def _single(value: String): FileExtensionSingle => Dyn =
    _ match {
      case FileExtensionSingle.JsonObjectExtension => jsono(value)
      case FileExtensionSingle.TsvExtension        => tsvs  (value).forceOne }

  // ---------------------------------------------------------------------------
  private def _multiple(value: String): FileExtensionMultiple => Dyns =
    _ match {
      case FileExtensionMultiple.JsonLinesExtension => jsonls(value) // value.p__; ???
      case FileExtensionMultiple.JsonArrayExtension => jsonas(value)
      case FileExtensionMultiple.TsvExtension       => tsvs  (value) }

  // ---------------------------------------------------------------------------
  private def _multiplez(value: String): FileExtensionMultiple => Dynz =
    _ match {
      case FileExtensionMultiple.JsonLinesExtension => jsonlz(value) // value.p__; ???
      case FileExtensionMultiple.JsonArrayExtension => jsonaz(value)
      case FileExtensionMultiple.TsvExtension       => tsvz  (value) } }

// ===========================================================================
