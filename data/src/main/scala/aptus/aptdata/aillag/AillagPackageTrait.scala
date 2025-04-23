package aptus
package aptdata.aillag /* don't split up this line */

import dyn.{Dyn, Dyns, Entry}

// ===========================================================================
trait AillagPackageTrait {
  type AnyValue = Any
  type Key      = aptdata.meta.schema.Key

  // ===========================================================================
  // enums

  type EnumValue = aptdata.meta.basic.EnumValue
  val  EnumValue = aptdata.meta.basic.EnumValue

  val _Enm = aptdata.meta.basic.BasicType._Enm

  // ===========================================================================
  // tables
  val DefaultNullValue      = ""
  val DefaultArraySeparator = ","

  // ===========================================================================
  implicit class AillagCls_(u: Cls) {
    def unknownKeys(o: Dyn): Set[Key] =
      o.keys.diff(u.akeys).toSet }

  // ===========================================================================
  implicit class AillagDyns_(u: Dyns) {
      def toListAndTrash    : List              [Dyn] = u.values.toList
      def consumeSelfClosing: CloseabledIterator[Dyn] = u.valuesIterator }

    // ---------------------------------------------------------------------------
    implicit class AillagDynComp_(u: Dyn.type) {
      def fromIterable(values: Iterable[(Key, Any)]): Dyn =
        values
          .map(Entry.fromGallia)
          .pipe(aptdata.sngl.Dyn.byPass) /* TODO: t250123135755 - confirm can always safely bypass */ }

    // ---------------------------------------------------------------------------
    implicit class AillagDynsComp_(u: Dyns.type) {
      def from(values: List[Dyn]): Dyns = Dyns.build(values) /* because List vs Seq */ }

  // ===========================================================================
  implicit class AillagInfo_(u: Info) { import u._
    def forceBasicType: BasicType = subInfo1.valueType.forceBasicType
    def updateValueType(value: ValueType): Info = copy(union = Seq(subInfo1.copy(valueType = value))) } }

// ===========================================================================
package object enumeratum {
  trait EnumEntry { def entryName: String =
    NullString } /* actually N/A for aptus (only gallia) */ }

// ---------------------------------------------------------------------------
package object gallia {
  def obj(entries: Seq[(Key, AnyValue)]): Dyn =
    entries.map(Entry.fromGallia).pipe(Dyn.build) }

// ===========================================================================
