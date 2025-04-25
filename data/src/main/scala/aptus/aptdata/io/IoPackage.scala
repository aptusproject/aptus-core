package aptus
package aptdata

// ===========================================================================
package object io {

  lazy val AptusObjToGson =
      new io.json.ObjToGson[Dyns, Dyn](
        toGson             = io.json.ObjToGson2.apply,
        consumeSelfClosing = _.valuesIterator)

    // ---------------------------------------------------------------------------
    lazy val AptusGsonToObj =
      new io.json.GsonToObj[Dyns, Dyn](
        instantiateSingle   = aptdata._build,
        instantiateMultiple = Dyns.build)

  // ===========================================================================
  lazy val AptusGsonToAptusData =
      new io.json.GsonToGalliaData[Dyn](
        jsonObjectStringParser = AptusGsonToObj.fromObjectString,

        unknownKeys = _ unknownKeys _,
         attemptKey = _  attemptKey _,

        instantiateSingle = aptdata._build)

    // ---------------------------------------------------------------------------
    lazy val AptusTableToAptusData =
      new io.table.TableToGalliaData[Dyn](
        unknownKeys = _ unknownKeys _,
         attemptKey = _  attemptKey _,
        instantiateSingle = aptdata._build)

  // ===========================================================================
  lazy val AptusSchemaInferrer =
      new io.inferring.SchemaInferrer[Dyn](
        entries    = _.galliaPairs,
        nestingOpt = aptus.castIfTypeMatching[Dyn])

    // ---------------------------------------------------------------------------
    lazy val AptusTableSchemaInferrer =
      new io.inferring.table.TableSchemaInferrer[Dyns, Dyn](
        attemptKey         = _  attemptKey _,
        consumeSelfClosing = _.valuesIterator) }

// ===========================================================================
