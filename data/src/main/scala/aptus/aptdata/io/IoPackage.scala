package aptus
package aptdata

// ===========================================================================
package object io {

  lazy val AptusJsonFormatting =
      new io.json.JsonFormatting[Dyns, Dyn](
        toGson             = io.json.ObjToGson2.apply,
        consumeSelfClosing = _.valuesIterator)

    // ---------------------------------------------------------------------------
    lazy val AptusGsonToSingleEntity =
      new io.json.GsonToSingleEntity[Dyns, Dyn](
        instantiate = aptdata._build)

  // ===========================================================================
  lazy val AptusGsonToAptusData =
      new io.json.GsonToGalliaData[Dyn](
        jsonObjectStringParser = AptusGsonToSingleEntity.fromObjectString,
        unknownKeys = _ unknownKeys _,
         attemptKey = _ attemptKey  _,
        instantiate = aptdata._build)

    // ---------------------------------------------------------------------------
    lazy val AptusTableToAptusData =
      new io.table.TableToGalliaData[Dyn](
        unknownKeys = _ unknownKeys _,
         attemptKey = _ attemptKey  _,
        instantiate = aptdata._build)

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
