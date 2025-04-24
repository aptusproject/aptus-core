package aptus
package aptdata
package meta
package schema

// ===========================================================================
private[aptus] trait ClsOps { self: Cls => // TODO: use lenses for transformations?
  private val spr = self /* just for highligh usage of super (Items) */

  // ===========================================================================
  @deprecated("until homogenize keys across aptus/gallia") lazy val keys: Seq[BKey] = bkeys

  lazy val akeys: Seq[ Key] = spr.exoMap(_.key)
  lazy val bkeys: Seq[BKey] = spr.exoMap(_.key.und)
  lazy val skeys: Seq[SKey] = spr.exoMap(_.key.name)

  // ---------------------------------------------------------------------------
  @deprecated("until homogenize keys across aptus/gallia") lazy val keySet: Set[BKey] = bkeySet

  lazy val akeySet: Set[ Key] = fields.map(_.key)     .toSet
  lazy val bkeySet: Set[BKey] = fields.map(_.key.und) .toSet
	lazy val skeySet: Set[SKey] = fields.map(_.key.name).toSet

  // ---------------------------------------------------------------------------
	private lazy val keyVector: Vector[Key] = fields.map(_.key).toVector

    def indexOfKey(key  : Key)  : Index = keyVector.indexOf(key)
    def keyByIndex(index: Index): Key   = keyVector.apply  (index)

  // ---------------------------------------------------------------------------
  def intersectKeys(that: Cls): Keys = intersectKeys(that.bkeys)
  def diffKeys     (that: Cls): Keys = diffKeys     (that.bkeys)

  def intersectKeys (values: Seq[BKey]): Keys = bkeys .intersect(values).map(Key.apply)
  def diffKeys      (values: Seq[BKey]): Keys = bkeys .diff     (values).map(Key.apply)
  def complementKeys(values: Seq[BKey]): Keys = values.diff     (bkeys) .map(Key.apply)

  // ---------------------------------------------------------------------------
  def field_(key: Key): Option[Item] = spr. findValue(_.key == key)
  def field (key: Key):        Item  = spr.forceValue(_.key == key)

  // ---------------------------------------------------------------------------
  def add    (field: Item)         : Self = spr.append(field)
  def replace(key: Key, info: Info): Self = spr.replaceExactlyOneItem(_.key == key)(_.updateInfo(info)) }

// ===========================================================================
trait FldOrInfo {
  def isOptional: Boolean
  def union: Seq[SubInfo]

  // ---------------------------------------------------------------------------
  def subInfo1: SubInfo

  // TODO: t250401115230 - bring from gallia: def nestedClassesOpt(disambiguatorOpt: UnionObjectDisambiguatorOpt): Seq   [Cls] = ...

  // ---------------------------------------------------------------------------
  def  isBasicType(value: BasicType): Boolean = subInfo1.valueType == value
  def hasBasicType(value: BasicType): Boolean = valueTypes.flatMap(_.basicTypeOpt).contains(value)

  // ---------------------------------------------------------------------------
  def isRequired: Boolean = !isOptional
  def isSingle  : Boolean = !subInfo1.multiple
  def isMultiple: Boolean =  subInfo1.multiple

  // ---------------------------------------------------------------------------
  def isOne: Boolean = isRequired &&  subInfo1.single
  def isOpt: Boolean = isOptional &&  subInfo1.single
  def isNes: Boolean = isRequired && !subInfo1.single
  def isPes: Boolean = isOptional && !subInfo1.single

  // ---------------------------------------------------------------------------
  protected def valueTypes: Seq[ValueType] = union.map(_.valueType)

    // ---------------------------------------------------------------------------
    def   basicTypeOpt: Option[BasicType] = valueTypes.flatMap(_.basicTypeOpt).pipe(forceUnionOption)
    def nestedClassOpt: Option[Cls]       = valueTypes.flatMap(_.  nestingOpt).pipe(forceUnionOption)

    // ---------------------------------------------------------------------------
    def forceBasicType  : BasicType =   basicTypeOpt.force
    def forceNestedClass: Cls       = nestedClassOpt.force }

// ===========================================================================
private[aptus] trait FldOps extends FldOrInfo { self: Fld =>
  type Self = Fld

  // ---------------------------------------------------------------------------
  override def union      = self.info.union
  override def isOptional = self.info.optional

  // ---------------------------------------------------------------------------
  def skey: String = key.name

  // ---------------------------------------------------------------------------
  def toOptional: Self = copy(info = info.toOptional)
  def toDouble  : Self = copy(info = info.toDouble)

  def updateSoleNestedClass(nc: Cls): Self = copy(info = info.updateSoleNestedClass(nc))

  def updateInfo(value: Info): Self = copy(info = value)

  // ---------------------------------------------------------------------------
  def subInfo1: SubInfo = info.subInfo1 }

// ===========================================================================
private[aptus] trait InfoOps extends FldOrInfo with SchemaValueExtraction { self: Info =>
  type Self = Info

  override def isOptional = self.optional

  // ---------------------------------------------------------------------------
  def toOptional: Self = copy(optional = true)
  def toDouble  : Self = updateValueType(value = BasicType._Double)

  // ---------------------------------------------------------------------------
  def updateValueType      (value: ValueType): Self = copy(union = subInfo1.copy(valueType = value).in.seq)
  def updateSoleNestedClass(nc: Cls)         : Self = updateValueType(value = nc) }

// ===========================================================================
private[aptus] trait SubInfoOps { self: SubInfo =>
  type Self = SubInfo

  // ---------------------------------------------------------------------------
  def basicTypeOpt  : Option[BasicType] = valueType.basicTypeOpt
  def nestedClassOpt: Option[Cls]       = valueType.nestingOpt

  // ---------------------------------------------------------------------------
  def toMultiple: Self = copy(multiple = true)
  def toSingle  : Self = copy(multiple = false)

  // ---------------------------------------------------------------------------
  def isEnmMatching(multiple: Multiple): Boolean =
    self.multiple == multiple &&
    basicTypeOpt.exists(_.isInstanceOf[BasicType._Enm]) }

// ===========================================================================
private[aptus] trait Info1Ops { self: Info1 =>
  def basicTypeOpt  : Option[BasicType] = valueType.basicTypeOpt
  def nestedClassOpt: Option[Cls]       = valueType.nestingOpt }

// ===========================================================================
