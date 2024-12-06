package aptus
package experimental
package dyn
package data
package sngl

import dyn.{Dyn => _Self}
import dyn.ops._
import dyn.ops.common._
import gallia.GalliaBorrowedOps

// ===========================================================================
// no duplicate keys, order matters
case class Dyn private[Dyn] (
      protected[dyn] val data: List[Entry])
    extends AllCommons[_Self]

       with accessors.ValewGetterAccessors /* eg myDyn.string("name") */

       with DynTransformImpl /* ONLY transform (concrete) */
       with DynOpsImpl       /* eg retain (concrete) */

       with GalliaBorrowedOps /* eg nest, reorderKeysRecursively, ... */
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
    def fromJson: Dyn = inferSchema.pipe(_root_.gallia.gson.GsonToGalliaData.convertRecursively(_)(self))

    def toDataClass[DC <: Product: WTT]: DC = ??? // TODO - t241204140907b - see a counterpart (dc -> dyn)

    // ---------------------------------------------------------------------------
    def asList    : Dyns = List(this).dyns
    def asIterator: Dynz = List(this).dynz }

  // ---------------------------------------------------------------------------
  object Dyn
    extends aspects.DynBuilding
       with aspects.DynFluentBuilding
       with aspects.DynDummies

// ===========================================================================