package aptus
package aptdata

// ===========================================================================
package object io {

  val AptusObjToGson =
      new io.json.ObjToGson[Dyns, Dyn](
        toGson             = io.json.ObjToGson2.apply,
        consumeSelfClosing = _.valuesIterator/*consumeSelfClosing*/)

    // ---------------------------------------------------------------------------
    val AptusGsonToObj =
      new io.json.GsonToObj[Dyns, Dyn](
        instantiateSingle =
          _   .map(Entry.fromGallia)
              .pipe(aptdata.sngl.Dyn.byPass) /* TODO: t250123135755 - confirm can always safely bypass */,
        instantiateMultiple = Dyns.build)

  // ===========================================================================
  val AptusGsonToAptusData =
      new io.json.GsonToGalliaData[Dyn](
        jsonObjectStringParser = AptusGsonToObj.fromObjectString,

        unknownKeys = _ unknownKeys _,
         attemptKey = _  attemptKey _,

        instantiateSingle = x => Dyn.build(x.map(Entry.fromGallia)))

    // ---------------------------------------------------------------------------
    val AptusTableToAptusData =
      new io.table.TableToGalliaData[Dyn](
        unknownKeys = _ unknownKeys _,
         attemptKey = _  attemptKey _,
        instantiateSingle = x => Dyn.build(x.map(Entry.buildn)))

  // ===========================================================================
  val AptusSchemaInferrer =
      new io.inferring.SchemaInferrer[Dyn](
        entries    = _.galliaPairs,
        nestingOpt = aptus.castIfTypeMatching[Dyn])

    // ---------------------------------------------------------------------------
    val AptusTableSchemaInferrer =
      new io.inferring.table.TableSchemaInferrer[Dyns, Dyn](
        attemptKey = _  attemptKey _,
        consumeSelfClosing = _.valuesIterator /* _.consumeSelfClosing, */) }

// ===========================================================================
