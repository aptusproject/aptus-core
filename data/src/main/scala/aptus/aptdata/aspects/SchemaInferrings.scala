package aptus
package aptdata
package aspects

import sngl.DynData

// ===========================================================================
trait DynSchemaInferrer
      extends ops.common.CanInferSchema[DynData] {
        self: DynData =>
    @nonovrd final           def inferSchema                : Schema = inferSchema(this)
    override final protected def inferSchema(value: DynData): Schema = io.AptusSchemaInferrer.klass(this) }

  // ---------------------------------------------------------------------------
trait DynsSchemaInferrer
      extends ops.common.CanInferSchema[Dyns] {
        self: Dyns =>
    @nonovrd final           def inferSchema: Schema = inferSchema(this)
    override final protected def inferSchema(value: Dyns): Schema = io.AptusSchemaInferrer.klass(values) }

// ===========================================================================
trait DynzSchemaInferrer2
//extends ops.common.CanInferSchema[Dynz]
       {
        self: Dynz =>
/** unlike `Dyns`, this is not idempotent */
def inferSchemaAndConsumeEntirely(): Schema = io.AptusSchemaInferrer.klass(values)
  }

// ===========================================================================
