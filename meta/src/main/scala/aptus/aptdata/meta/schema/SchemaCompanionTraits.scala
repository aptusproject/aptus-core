package aptus
package aptdata
package meta
package schema

import aptreflect.lowlevel.ReflectionTypesAbstraction.{WTT, WeakTypeTag_}

// ===========================================================================
trait ClsCompanionTrait {
  lazy val DummyCls: Cls = soleField(Fld.DummyFld)

  // ---------------------------------------------------------------------------
  /* to do eg: meta.cls("foo" -> 3, ...) */
  def cls[T: WTT]                 : Cls = implicitly[WTT[T]].typeNode.leaf.pipe(TypeNodeToSchemaUtils.forceNestedClass)
  def cls(field1: Fld, more: Fld*): Cls = Cls((field1 +: more).toList)
  def cls(fields: Seq[Fld])       : Cls = Cls(fields.toList)

  // ---------------------------------------------------------------------------
  def soleField(field: Fld): Cls = Cls(Seq(field)) }

// ===========================================================================
trait FldCompanionTrait {
  lazy val DummyFld = string("_dummy")

  // ---------------------------------------------------------------------------
  /** to do eg: "foo".string -> Fld */
  implicit class SKey_(name: String)
    extends aptus.aptdata.meta.schema.FldCreator {
      protected val _key: Key = aptus.aptdata.meta.schema.Key._fromString(name) }

  // ---------------------------------------------------------------------------
  def typed[T: WTT](key: Key): Fld = implicitly[WTT[T]].typeNode.forceNonBObjInfo.pipe(Fld(key, _))

  // ---------------------------------------------------------------------------
  def required(key: Key, subInfo: SubInfo): Fld = Fld(key, Info.required(subInfo))
  def optional(key: Key, subInfo: SubInfo): Fld = Fld(key, Info.optional(subInfo))

  // ---------------------------------------------------------------------------
  def one(key: Key, cls: Cls) = Fld(key, Info.one(cls))
  def opt(key: Key, cls: Cls) = Fld(key, Info.opt(cls))
  def nes(key: Key, cls: Cls) = Fld(key, Info.nes(cls))
  def pes(key: Key, cls: Cls) = Fld(key, Info.pes(cls))

  // ---------------------------------------------------------------------------
  def one(key: Key, basic: BasicType.Selector) = Fld(key, Info.one(basic))
  def opt(key: Key, basic: BasicType.Selector) = Fld(key, Info.opt(basic))
  def nes(key: Key, basic: BasicType.Selector) = Fld(key, Info.nes(basic))
  def pes(key: Key, basic: BasicType.Selector) = Fld(key, Info.pes(basic))

  // ---------------------------------------------------------------------------
  def one(key: Key, valueType: ValueType) = Fld(key, Info.one(valueType))
  def opt(key: Key, valueType: ValueType) = Fld(key, Info.opt(valueType))
  def nes(key: Key, valueType: ValueType) = Fld(key, Info.nes(valueType))
  def pes(key: Key, valueType: ValueType) = Fld(key, Info.pes(valueType))

  // ===========================================================================
  @PartialTypeMatching
  def string (key: Key) = Fld(key, Info.string)
  def int    (key: Key) = Fld(key, Info.int)
  def double (key: Key) = Fld(key, Info.double)
  def boolean(key: Key) = Fld(key, Info.boolean) }

// ===========================================================================
trait InfoCompanionTrait {
  def forceFrom[T : WTT]: Info = implicitly[WTT[T]].typeNode.forceNonBObjInfo

  // ---------------------------------------------------------------------------
  def    union(optional: Boolean, subInfo1: SubInfo, subInfo2: SubInfo, more: SubInfo*): Info = Info(optional, List(subInfo1, subInfo2) ++ more)
  def nonUnion(optional: Boolean, subInfo: SubInfo): Info = Info(optional, Seq(subInfo))

  // ---------------------------------------------------------------------------
  def required(subInfo: SubInfo) = Info(_Required, union = Seq(subInfo))
  def optional(subInfo: SubInfo) = Info(_Optional, union = Seq(subInfo))

  // ---------------------------------------------------------------------------
  def one(cls: Cls): Info = Info(_Required, union = Seq(SubInfo.sngl(cls)))
  def opt(cls: Cls): Info = Info(_Optional, union = Seq(SubInfo.sngl(cls)))
  def nes(cls: Cls): Info = Info(_Required, union = Seq(SubInfo.mult(cls)))
  def pes(cls: Cls): Info = Info(_Optional, union = Seq(SubInfo.mult(cls)))

  // ---------------------------------------------------------------------------
  def one(basic: BasicType.Selector): Info = Info(_Required, union = Seq(SubInfo.sngl(basic(BasicType))))
  def opt(basic: BasicType.Selector): Info = Info(_Optional, union = Seq(SubInfo.sngl(basic(BasicType))))
  def nes(basic: BasicType.Selector): Info = Info(_Required, union = Seq(SubInfo.mult(basic(BasicType))))
  def pes(basic: BasicType.Selector): Info = Info(_Optional, union = Seq(SubInfo.mult(basic(BasicType))))

  // ---------------------------------------------------------------------------
  def one(valueType: ValueType): Info = Info(_Required, union = Seq(SubInfo.sngl(valueType)))
  def opt(valueType: ValueType): Info = Info(_Optional, union = Seq(SubInfo.sngl(valueType)))
  def nes(valueType: ValueType): Info = Info(_Required, union = Seq(SubInfo.mult(valueType)))
  def pes(valueType: ValueType): Info = Info(_Optional, union = Seq(SubInfo.mult(valueType)))

  // ===========================================================================
  @PartialTypeMatching
  def boolean: Info = one(_._Boolean)
  def int    : Info = one(_._Int)
  def double : Info = one(_._Double)
  def string : Info = one(_._String)

  // ---------------------------------------------------------------------------
  @PartialTypeMatching
  def boolean_ : Info = opt(_._Boolean)
  def int_     : Info = opt(_._Int)
  def double_  : Info = opt(_._Double)
  def string_  : Info = opt(_._String) }

// ===========================================================================
trait Info1CompanionTrait {
  def one(cls: Cls): Info1 = Info1(_Required, _Single  , cls)
  def opt(cls: Cls): Info1 = Info1(_Optional, _Single  , cls)
  def nes(cls: Cls): Info1 = Info1(_Required, _Multiple, cls)
  def pes(cls: Cls): Info1 = Info1(_Optional, _Multiple, cls)

  // ---------------------------------------------------------------------------
  def one(basic: BasicType.Selector): Info1 = Info1(_Required, _Single  , basic(BasicType))
  def opt(basic: BasicType.Selector): Info1 = Info1(_Optional, _Single  , basic(BasicType))
  def nes(basic: BasicType.Selector): Info1 = Info1(_Required, _Multiple, basic(BasicType))
  def pes(basic: BasicType.Selector): Info1 = Info1(_Optional, _Multiple, basic(BasicType))

  // ---------------------------------------------------------------------------
  def one(valueType: ValueType): Info1 = Info1(_Required, _Single  , valueType)
  def opt(valueType: ValueType): Info1 = Info1(_Optional, _Single  , valueType)
  def nes(valueType: ValueType): Info1 = Info1(_Required, _Multiple, valueType)
  def pes(valueType: ValueType): Info1 = Info1(_Optional, _Multiple, valueType)

  // ===========================================================================
  @PartialTypeMatching
  def boolean: Info1 = one(_._Boolean)
  def int    : Info1 = one(_._Int)
  def double : Info1 = one(_._Double)
  def string : Info1 = one(_._String) }

// ===========================================================================
trait SubInfoCompanionTrait {
  def sngl(cls: Cls): SubInfo = SubInfo(_Single  , cls)
  def mult(cls: Cls): SubInfo = SubInfo(_Multiple, cls)

  // ---------------------------------------------------------------------------
  def sngl(basic: BasicType.Selector): SubInfo = SubInfo(_Single  , basic(BasicType))
  def mult(basic: BasicType.Selector): SubInfo = SubInfo(_Multiple, basic(BasicType))

  // ---------------------------------------------------------------------------
  def sngl(valueType: ValueType): SubInfo = SubInfo(_Single   , valueType)
  def mult(valueType: ValueType): SubInfo = SubInfo(_Multiple , valueType)

  // ===========================================================================
  @PartialTypeMatching
  def string  : SubInfo = sngl(_._String)
  def int     : SubInfo = sngl(_._Int)
  def double  : SubInfo = sngl(_._Double)
  def boolean : SubInfo = sngl(_._Boolean)

  // ---------------------------------------------------------------------------
  @PartialTypeMatching
  def strings : SubInfo = mult(_._String)
  def ints    : SubInfo = mult(_._Int)
  def doubles : SubInfo = mult(_._Double)
  def booleans: SubInfo = mult(_._Boolean) }

// ===========================================================================
