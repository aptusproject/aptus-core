package aptus
package experimental
package dyn
package data
package aspects

import sngl.DynData

// ===========================================================================
trait DynSchemaInferrer
      extends ops.common.CanInferSchema[DynData] {
        self: DynData =>
    @nonovrd final           def inferSchema                : Schema = inferSchema(this)
    override final protected def inferSchema(value: DynData): Schema = aillag.inferring.SchemaInferrer.klass(this) }

  // ---------------------------------------------------------------------------
trait DynsSchemaInferrer
      extends ops.common.CanInferSchema[Dyns] {
        self: Dyns =>
    @nonovrd final           def inferSchema: Schema = inferSchema(this)
    override final protected def inferSchema(value: Dyns): Schema = aillag.inferring.SchemaInferrer.klass(values) }

// ===========================================================================
trait DynzSchemaInferrer2
//extends ops.common.CanInferSchema[Dynz]
       {
        self: Dynz =>
/** unlike `Dyns`, this is not idempotent */
def inferSchemaAndConsumeEntirely(): Schema = aillag.inferring.SchemaInferrer.klass(values)
  }

// ===========================================================================
