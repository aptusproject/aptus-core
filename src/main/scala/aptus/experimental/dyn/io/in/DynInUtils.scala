package aptus
package experimental
package dyn
package io
package in

// ===========================================================================
object DynInUtils {
  private val fromJson = _root_.gallia.gson.BorrowedGsonFrom

  // ===========================================================================
  def jsono(value: InputFilePath): Dyn =
      value
        .readFileContent()
        .pipe(fromJson.fromObjectString)

    // ---------------------------------------------------------------------------
    def jsonO(value: JsonObjectString): Dyn =
      fromJson.fromObjectString(value)

  // ===========================================================================
  def jsonas(value: InputFilePath): Dyns =
    value
      .readFileContent() // TODO: stream JSON array - t241022104721
      .pipe(fromJson.fromArrayString)
      .dyns

  // ---------------------------------------------------------------------------
  def jsonaz(value: InputFilePath): Dynz =
    value
      .readFileContent() // TODO: stream JSON array - t241022104721
      .pipe(fromJson.fromArrayString)
      .dynz

  // ===========================================================================
  def jsonAs(value: JsonArrayString): Dyns =
    value
      .pipe(fromJson.fromArrayString)
      .dyns
  // ---------------------------------------------------------------------------
  def jsonAz(value: JsonArrayString): Dynz =
    value
      .pipe(fromJson.fromArrayString)
      .dynz

  // ===========================================================================
  def jsonls(value: InputFilePath): Dyns =
      value
        .streamFileLines3()
        .map (fromJson.fromObjectString)
        .dyns

    // ---------------------------------------------------------------------------
    def jsonlz(value: InputFilePath): Dynz =
      value
        .streamFileLines3()
        .map (fromJson.fromObjectString)
        .dynz

  // ===========================================================================
  def tsvs(s: InputFilePath): Dyns = _tsv(s).dyns
  def tsvz(s: InputFilePath): Dynz = _tsv(s).dynz

    // ---------------------------------------------------------------------------
    private def _tsv(s: InputFilePath): IteratoR[Dyn] = {
      val cells = s .streamFileTsv3()
      val first: Seq[Key] = cells.next().map(Key._from)

      cells
        .map { x =>
          first
            .zipSameSize(x) // TODO: more permissive flag?
.map(Entry.buildn)
        .dyn } } }

// ===========================================================================
