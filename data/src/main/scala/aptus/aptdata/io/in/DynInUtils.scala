package aptus
package aptdata
package io
package in

// ===========================================================================
object DynInUtils {

  // ---------------------------------------------------------------------------
  def jsono(value: InputFilePath): Dyn =
      value
        .readFileContent()
        .pipe(io.AptusGsonToObj.fromObjectString)

    // ---------------------------------------------------------------------------
    def jsonO(value: JsonObjectString): Dyn =
      io.AptusGsonToObj.fromObjectString(value)

  // ===========================================================================
  def jsonas(value: InputFilePath): Dyns =
    value
      .readFileContent() // TODO: stream JSON array - t241022104721
      .pipe(io.AptusGsonToObj.fromArrayString)

  // ---------------------------------------------------------------------------
  def jsonaz(value: InputFilePath): Dynz =
    value
      .readFileContent() // TODO: stream JSON array - t241022104721
      .pipe(io.AptusGsonToObj.fromArrayString)
      .asIterator

  // ===========================================================================
  def jsonAs(value: JsonArrayString): Dyns =
    value
      .pipe(io.AptusGsonToObj.fromArrayString)

  // ---------------------------------------------------------------------------
  def jsonAz(value: JsonArrayString): Dynz =
    value
      .pipe(io.AptusGsonToObj.fromArrayString)
      .asIterator

  // ===========================================================================
  def jsonls(value: InputFilePath): Dyns =
      value
        .streamFileLines2() // for CloseabledIterator
        .map (io.AptusGsonToObj.fromObjectString)
        .dyns

    // ---------------------------------------------------------------------------
    def jsonlz(value: InputFilePath): Dynz =
      value
        .streamFileLines2() // for CloseabledIterator
        .map (io.AptusGsonToObj.fromObjectString)
        .dynz

  // ===========================================================================
  def tsvs(s: InputFilePath): Dyns = _tsv(s).dyns
  def tsvz(s: InputFilePath): Dynz = _tsv(s).dynz

    // ---------------------------------------------------------------------------
    private def _tsv(s: InputFilePath): CloseabledIterator[Dyn] = {
      val rows = s.streamFileTsv2() // for CloseabledIterator
      val first: Seq[Key] = rows.next().map(Key._fromString)

      rows
        .map { row =>
          first
            .zipSameSize(row) // TODO: more permissive flag?
            .map(Entry.buildn)
        .dyn } } }

// ===========================================================================
