package aptus
package experimental
package dyn
package meta

// ===========================================================================
private[meta] trait ClsCompanionTrait {
//def cls[T: WTT]                         : Cls = typeNode[T].leaf.forceDataClass -- TODO (borrow from gallia)
//def cls(schemaFilePath: String)         : Cls = -- TODO (borrow from gallia)
  def cls(field1: Fld, more: Fld*)        : Cls = Cls((field1 +: more).toList)
  def cls               (fields: Seq[Fld]): Cls = Cls(fields.toList) }

// ---------------------------------------------------------------------------
private[meta] trait FldCompanionTrait {
  implicit class SKey_(name: SKey)
    extends FldCreator {
      protected val _key: String = name }

  // ---------------------------------------------------------------------------
  def one(key: SKey, valueType: ValueType) = Fld(key, Info.one(valueType))
  def opt(key: SKey, valueType: ValueType) = Fld(key, Info.opt(valueType))
  def nes(key: SKey, valueType: ValueType) = Fld(key, Info.nes(valueType))
  def pes(key: SKey, valueType: ValueType) = Fld(key, Info.pes(valueType)) }

// ---------------------------------------------------------------------------
private[meta] trait InfoCompanionTrait {
  def nonUnion(optional: Boolean, subInfo: SubInfo): Info = Info(optional, Seq(subInfo))

  // ---------------------------------------------------------------------------
  def one(valueType: ValueType): Info = Info(optional = false, union = SubInfo.sngl(valueType).in.seq)
  def opt(valueType: ValueType): Info = Info(optional = true , union = SubInfo.sngl(valueType).in.seq)
  def nes(valueType: ValueType): Info = Info(optional = false, union = SubInfo.mult(valueType).in.seq)
  def pes(valueType: ValueType): Info = Info(optional = true , union = SubInfo.mult(valueType).in.seq) }

// ---------------------------------------------------------------------------
private[meta] trait SubInfoCompanionTrait {
  def sngl(valueType: ValueType): SubInfo = SubInfo(multiple = false, valueType)
  def mult(valueType: ValueType): SubInfo = SubInfo(multiple = true , valueType) }

// ===========================================================================
