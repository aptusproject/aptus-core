package aptus
package aptdata
package sngl

import aptus.aptdata.ops.OpsBorrowedFromGallia
import aptus.aptdata.static.DynDynamicToStatic
import dyn.{Dyn => _Self}
import ops._
import ops.common._

// ===========================================================================
// no duplicate keys (a250423153423), order matters (250423153424), can be empty (a250423153425)
case class Dyn private[Dyn] (
      protected[aptdata] val data: List[Entry])
    extends AllCommons[_Self]

       with accessors.ValewGetterAccessors /* eg myDyn.string("name") */

       with DynTransformImpl /* ONLY transform (concrete) */
       with DynOpsImpl       /* eg retain (concrete) */

       with OpsBorrowedFromGallia /* eg nest, reorderKeysRecursively, ... */
       with DynData
       with DynEntryMapping        /* map/flatMap on entry */
       with DynEntriesTransforming /* transform entries direction (full control) */
       with DynValewGetter         /* get, containsKey, ... */
       with DynRemoveRetainOpt
       with DynAppPrepend /* append/prepend */
       with DynReordering /* reorder keys, sort keys, equivalence, ... */
       with DynFormatDefault
       with DynFileWriter
       with DynJsonWriter
       with DynDataEntityErrorFormatterProvider
       with aspects.DynSchemaInferrer {
      self: Dyn =>
    override final def ident: Dyn = this

    // ---------------------------------------------------------------------------
    /** mostly to convert doubles to int when applicable ("JSON number tax" - see gallia) - overkill in aptus (vs gallia) */
    def fromJson: Dyn = inferSchema.pipe { c => aillag.GsonToAptusData.convertRecursively(c)(self) }

    // ---------------------------------------------------------------------------
    def toStatic[DC <: Product: aptreflect.lowlevel.ReflectionTypesAbstraction.WTT]: DC = {
      val (schema, dynamicToStatic) = DynDynamicToStatic.toStatic[DC]
      dynamicToStatic.instantiateStaticRecursively(schema)(self).asInstanceOf[DC] }

    // ---------------------------------------------------------------------------
    def asList    : Dyns = List(this).dyns
    def asIterator: Dynz = List(this).dynz }

  // ===========================================================================
  object Dyn
    extends aspects.DynBuilding
       with aspects.DynFluentBuilding
       with aspects.DynDummies {
    /* only for builder */
    private[sngl] def _build(data: List[Entry]): Dyn =
      new Dyn(data) }

// ===========================================================================
