package aptus
package aptdata

// ===========================================================================
/** aillag = gallia backward */
package object aillag
    extends aptus.aptdata.AptusGalliaDataAdaptor
       with aptus.apttraits.AptusChaining
       with AillagPackageTrait {

  // ===========================================================================
  val ObjToGson = new data.json.ObjToGson[Dyns, Dyn](
      data.json.ObjToGson2.apply,
      _.consumeSelfClosing)

    // ---------------------------------------------------------------------------
    val GsonToObj = new data.json.GsonToObj[Dyns, Dyn](
      Dyn.fromIterable,
      Dyns.from)

  // ---------------------------------------------------------------------------
  val GsonParsing = new data.json.GsonParsing[Dyn]()

  // ===========================================================================
  val GsonToAptusData = new data.json.GsonToGalliaData[Dyn](
      _ unknownKeys _,
      _  attemptKey _,

      x => Dyn.build(x.map(Entry.fromGallia)))

    // ---------------------------------------------------------------------------
    val TableToAptusData = new data.TableToGalliaData[Dyn](
      unknownKeys = _ unknownKeys _,
       attemptKey = _  attemptKey _,

      instantiateSingle = x => Dyn.build(x.map(Entry.buildn)))

  // ===========================================================================
  val SchemaInferrer = new inferring.SchemaInferrer[Dyn](
      entries    = _.galliaPairs,
      nestingOpt = aptus.optionallyCast[Dyn])

    // ---------------------------------------------------------------------------
    val TableSchemaInferrer = new inferring.table.TableSchemaInferrer[Dyns, Dyn](
      consumeSelfClosing = _.consumeSelfClosing,
      string             = _ string _) }

// ===========================================================================
