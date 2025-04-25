package aptus
package aptdata
package io
package in

// ===========================================================================
object DynInUtils {
  
  private[aptdata] def fromArrayString(value: JsonArrayString): Dyns =
    aptjson.GsonParser
      .stringToJsonObjects(value)
      .map(io.AptusGsonToSingleEntity.jsonObjectToObj)
      .toList.pipe(Dyns.build) // TODO: from iterator

  // ===========================================================================
  def jsono(value: InputFilePath): Dyn =
      value
        .readFileContent()
        .pipe(io.AptusGsonToSingleEntity.fromObjectString)

    // ---------------------------------------------------------------------------
    def jsonO(value: JsonObjectString): Dyn =
      io.AptusGsonToSingleEntity.fromObjectString(value)

  // ===========================================================================
  def jsonas(value: InputFilePath): Dyns =
    value
      .readFileContent() // TODO: stream JSON array - t241022104721
      .pipe(fromArrayString)

  // ---------------------------------------------------------------------------
  def jsonaz(value: InputFilePath): Dynz =
    value
      .readFileContent() // TODO: stream JSON array - t241022104721
      .pipe(fromArrayString)
      .asIterator

  // ===========================================================================
  def jsonAs(value: JsonArrayString): Dyns =
    value
      .pipe(fromArrayString)

  // ---------------------------------------------------------------------------
  def jsonAz(value: JsonArrayString): Dynz =
    value
      .pipe(fromArrayString)
      .asIterator

  // ===========================================================================
  def jsonls(value: InputFilePath): Dyns =
      value
        .streamFileLines2() // for CloseabledIterator
        .map (io.AptusGsonToSingleEntity.fromObjectString)
        .dyns

    // ---------------------------------------------------------------------------
    def jsonlz(value: InputFilePath): Dynz =
      value
        .streamFileLines2() // for CloseabledIterator
        .map (io.AptusGsonToSingleEntity.fromObjectString)
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
