package aptus
package aptdata
package io
package in

// ===========================================================================
trait TopLevelBuildingUtils {
  private[aptdata] def _build(x: Seq[(Key, AnyValue)]) = x.map(Entry.buildn).pipe(Dyn.build)

  // ---------------------------------------------------------------------------
  def dyn(entry1: Entry, more: Entry*): Dyn = Dyn.build(entry1 +: more)
  def dyns(dyn1: Sngl, more: Sngl*): Dyns = Dyns.build(dyn1 +: more)
  def dynz(dyn1: Sngl, more: Sngl*): Dynz = Dyns.build(dyn1 +: more).asDynz

  // ---------------------------------------------------------------------------
  def dyn (string: String /* eg path or JSON string */): Dyn  = io.in.DynIn.single   (string)
  def dyns(string: String /* eg path or JSON string */): Dyns = io.in.DynIn.multiple (string)
  def dynz(string: String /* eg path or JSON string */): Dynz = io.in.DynIn.multiplez(string)

  // ---------------------------------------------------------------------------
  def fromDataClass[DC <: Product: aptreflect.lowlevel.ReflectionTypesAbstraction.WTT](value: DC): Dyn = value.toDynamic

  // ---------------------------------------------------------------------------
  def single      (string: String /* eg path or JSON string */): Dyn  = io.in.DynIn.single   (string)
  def multiple    (string: String /* eg path or JSON string */): Dyns = io.in.DynIn.multiple (string) // TODO: name conflicts with package name...
  def multipleLazy(string: String /* eg path or JSON string */): Dynz = io.in.DynIn.multiplez(string)

    // ---------------------------------------------------------------------------
    // explicit one
    def fromJsonObjectString   (value: JsonObjectString): Dyn  = io.in.DynInUtils.jsonO(value)
    def fromJsonObjectFile     (value: FilePath)        : Dyn  = io.in.DynInUtils.jsono(value)

    def fromJsonArrayString    (value: JsonArrayString): Dyns = io.in.DynInUtils.jsonAs(value)
    def fromJsonArrayFile      (value: FilePath)       : Dyns = io.in.DynInUtils.jsonas(value)

    def fromJsonLinesFile      (value: FilePath): Dyns = io.in.DynInUtils.jsonls(value)
    def fromJsonLinesFileLazily(value: FilePath): Dynz = io.in.DynInUtils.jsonlz(value)

    // ---------------------------------------------------------------------------
    def fromTsvFile          (value: FilePath): Dyns = io.in.DynInUtils.tsvs(value)
    def fromTsvFileLazily    (value: FilePath): Dynz = io.in.DynInUtils.tsvz(value) }

// ===========================================================================
