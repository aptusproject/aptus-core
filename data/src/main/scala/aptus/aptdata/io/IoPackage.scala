package aptus
package aptdata

// ===========================================================================
package object io {

  lazy val AptusGsonToSingleEntity =
    new io.json.GsonToSingleEntity[Dyn](
      instantiate = aptdata._build)

    // ---------------------------------------------------------------------------
    lazy val AptusGsonToSingleEntityWithSchema =
      new io.json.GsonToSingleEntityWithSchema[Dyn](
        unknownKeys = _ unknownKeys _,
         attemptKey = _ attemptKey  _,
        instantiate = aptdata._build)

  // ===========================================================================
  lazy val AptusTableParsing =
    new io.table.TableParsing[Dyn](
      unknownKeys = _ unknownKeys _,
       attemptKey = _ attemptKey  _)

  // ===========================================================================
  lazy val AptusSchemaInferrer =
      new io.inferring.SchemaInferrer[Dyn](
        entries    = _.entries.map(_.aptusPair),
        nestingOpt = aptus.castIfTypeMatching[Dyn])

    // ---------------------------------------------------------------------------
    lazy val AptusTableSchemaInferrer =
      new io.inferring.table.TableSchemaInferrer[Dyns, Dyn](
        attemptKey  = _  attemptKey _,
        consumer    = _.valuesIterator) }

// ===========================================================================
