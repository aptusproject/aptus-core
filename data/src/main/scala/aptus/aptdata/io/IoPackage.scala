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

        unknownKeys = (c, o) => o.unknownKeys(c),
         attemptKey = _  attemptKey _,

        instantiateSingle = x => Dyn.build(x.map(Entry.fromGallia)))

    // ---------------------------------------------------------------------------
    val AptusTableToAptusData =
      new io.table.TableToGalliaData[Dyn](
        unknownKeys = (c, o) => o.unknownKeys(c),
         attemptKey = _  attemptKey _,
        instantiateSingle = x => Dyn.build(x.map(Entry.buildn)))

  // ===========================================================================
  val AptusSchemaInferrer =
      new io.inferring.SchemaInferrer[Dyn](
        entries    = _.galliaPairs,
        nestingOpt = aptus.optionallyCast[Dyn])

    // ---------------------------------------------------------------------------
    val AptusTableSchemaInferrer =
      new io.inferring.table.TableSchemaInferrer[Dyns, Dyn](
        consumeSelfClosing = _.valuesIterator,//_.consumeSelfClosing,
        string             = _ string _) }

// ===========================================================================
