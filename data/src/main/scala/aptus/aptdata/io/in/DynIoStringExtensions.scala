package aptus
package aptdata
package io
package in

// ===========================================================================
trait DynIoStringExtensions { val u: String
  def dyn : Dyn  = io.in.DynIn.single(u)
  def dyns: Dyns = io.in.DynIn.multiple (u)
  def dynz: Dynz = io.in.DynIn.multiplez(u)

  def dynFromJsonObjectString: Dyn  = io.in.DynInUtils.jsonO(u)
  def dynFromJsonObjectFile  : Dyn  = io.in.DynInUtils.jsono(u)

  def dynsFromJsonArrayString: Dyns = io.in.DynInUtils.jsonas(u)
  def dynsFromJsonArrayFile  : Dyns = io.in.DynInUtils.jsonAs(u)

  def dynsFromJsonLinesFile  : Dyns = io.in.DynInUtils.jsonls(u)
  def dynsFromTsvFile        : Dyns = io.in.DynInUtils.tsvs(u)

  // ---------------------------------------------------------------------------
  def dynzFromJsonLinesFile: Dynz = io.in.DynInUtils.jsonlz(u)
  def dynzFromTsvFile      : Dynz = io.in.DynInUtils.tsvz(u) }

// ===========================================================================