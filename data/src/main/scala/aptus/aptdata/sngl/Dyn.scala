package aptus
package aptdata
package sngl

import aptdata.static.DynDynamicToStatic
import aptdata.{Dyn => _Self}
import ops._
import ops.common._

// ===========================================================================
// no duplicate keys (a250423153423), order matters (250423153424), can be empty (a250423153425)
case class Dyn private[Dyn] (
      protected[aptdata] val data: List[Entry])
    extends AllCommons[_Self]

       with accessors.ValewGetterAccessors /* eg myDyn.string("name") */
       with DynOpsImpl       /* eg retain (concrete) */
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

    override final def formatDebug: String = data.map(_.formatDebug).section

    // ---------------------------------------------------------------------------
    def unknownKeys(c: Cls): Set[Key] = keys.diff(c.akeys).toSet

    // ---------------------------------------------------------------------------
    /** mostly to convert doubles to int when applicable ("JSON number tax" - see gallia) - overkill in aptus (vs gallia) */
    def fromJson: Dyn = inferSchema.pipe { c => io.AptusGsonToSingleEntityWithSchema.convertRecursively(c)(self) }

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
    val Empty: Dyn = new Dyn(Nil)

    /* only for builder */ private[sngl] def _build(data: List[Entry]): Dyn = new Dyn(data) }

// ===========================================================================
