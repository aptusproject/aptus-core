package aptus
package experimental
package dyn
package meta

// ===========================================================================
private[meta] trait ClsOps { 
    self: Cls =>  // TODO: use lenses for transformations?
  private val spr = this /* just for highligh usage of super (Items) */

  // ---------------------------------------------------------------------------
  def unknownKeys(value: Dyn): Seq[SKey] = value.skeys.diff(this.keys)

  // ===========================================================================
  def keys: Seq[SKey] = spr.exoMap(_.key)

  // ---------------------------------------------------------------------------
  def field_(key: SKey): Option[Item] = spr. findValue(_.key == key)
  def field (key: SKey):        Item  = spr.forceValue(_.key == key)

  // ---------------------------------------------------------------------------
  def add    (field: Item)          : Self = spr.append(field)
  def replace(key: SKey, info: Info): Self = spr.replaceExactlyOneItem(_.key == key)(_.replaceInfo(info))

  // ---------------------------------------------------------------------------
  def toOptional(key: SKey): Self = spr.replaceExactlyOneItem(_.key == key)(_.toOptional) }

// ===========================================================================
private[meta] trait FldOps { 
    self: Fld =>
  type Container  = Cls
  type Self       = Fld

  // ---------------------------------------------------------------------------
  def basicTypeOpt  : Option[BasicType] = subInfo1.valueType.either.left.toOption
  def nestedClassOpt: Option[Container] = subInfo1.valueType.either.swap.left.toOption

  // ---------------------------------------------------------------------------
  def hasBasicType(value: BasicType): Boolean = subInfo1.valueType == value

  def isRequired: Boolean = !info.optional

  // ---------------------------------------------------------------------------
  def replaceInfo(value: Info): Self = copy(info = value)
  def toOptional: Self = copy(info = info.toOptional)
  def toDouble: Self = copy(info = info.toDouble)

  def updateSoleNestedClass(nc: Cls): Self = copy(info = info.replaceNestedClass(nc))

  // ---------------------------------------------------------------------------
  def subInfo1: SubInfo = info.subInfo1 }

// ===========================================================================
private[meta] trait InfoOps { 
    self: Info =>
  type Self = Info

  // ---------------------------------------------------------------------------
  def forceBasicType  : BasicType = subInfo1.valueType.forceBasicType
  def forceNestedClass: Cls       = subInfo1.valueType.forceNestedClass

  // ---------------------------------------------------------------------------
  def toOptional: Self = copy(optional = true)
  def toDouble  : Self = replaceValueType(value = BasicType._Double)

  // ---------------------------------------------------------------------------
  def replaceValueType  (value: ValueType): Self = copy(union = subInfo1.copy(valueType = value).in.seq)
  def replaceNestedClass(nc: Cls)         : Self = replaceValueType(value = nc)

  // ---------------------------------------------------------------------------
// TODO:!!! proper error
  def subInfo1: SubInfo = union.force.one }

// ===========================================================================
private[meta] trait SubInfoOps { 
    self: SubInfo =>
  type Self = SubInfo

  // ---------------------------------------------------------------------------
  def toMultiple: Self = copy(multiple = true)
  def toSingle  : Self = copy(multiple = false) }

// ===========================================================================
