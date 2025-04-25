package aptus
package aptdata

// ===========================================================================
/** aillag = gallia backward */
package object aillag {

  // ---------------------------------------------------------------------------
  val ObjToGson = new data.json.ObjToGson[Dyns, Dyn](
      toGson = data.json.ObjToGson2.apply,
      consumeSelfClosing = _.valuesIterator/*consumeSelfClosing*/)

    // ---------------------------------------------------------------------------
    val GsonToObj = new data.json.GsonToObj[Dyns, Dyn](
      instantiateSingle =
        _   .map(Entry.fromGallia)
            .pipe(aptdata.sngl.Dyn.byPass) /* TODO: t250123135755 - confirm can always safely bypass */,
      instantiateMultiple = Dyns.build)

  // ===========================================================================
  val GsonToAptusData = new data.json.GsonToGalliaData[Dyn](
      jsonObjectStringParser = GsonToObj.fromObjectString,

      unknownKeys = (c, o) => o.unknownKeys(c),
       attemptKey = _  attemptKey _,

      instantiateSingle = x => Dyn.build(x.map(Entry.fromGallia)))

    // ---------------------------------------------------------------------------
    val TableToAptusData = new data.TableToGalliaData[Dyn](
      unknownKeys = (c, o) => o.unknownKeys(c),
       attemptKey = _  attemptKey _,
      instantiateSingle = x => Dyn.build(x.map(Entry.buildn)))

  // ===========================================================================
  val SchemaInferrer = new inferring.SchemaInferrer[Dyn](
      entries    = _.galliaPairs,
      nestingOpt = aptus.optionallyCast[Dyn])

    // ---------------------------------------------------------------------------
    val TableSchemaInferrer = new inferring.table.TableSchemaInferrer[Dyns, Dyn](
      consumeSelfClosing = _.valuesIterator,//_.consumeSelfClosing,
      string             = _ string _) }

// ===========================================================================
