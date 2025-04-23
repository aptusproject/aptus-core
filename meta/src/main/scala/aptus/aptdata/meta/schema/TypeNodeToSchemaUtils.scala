package aptus
package aptdata
package meta
package schema

import aptreflect.nodes.{TypeNode, TypeLeaf}

// ===========================================================================
object TypeNodeToSchemaUtils {
  private type _EnmOpt = Option[BasicType._Enm]

  // ---------------------------------------------------------------------------
  private val ScalaAnyPlaceHolder = BasicType._Boolean // 240208154224 - arbitrarily use _Boolean as placeholder for Any (for internal use only)
  // TODO: t240208161247 - create a proper placeholder rather, so can easily identify this use case (eg .forceAny("f"))

  // ---------------------------------------------------------------------------
  def forceNestedClass(leaf: TypeLeaf): Cls =
    leaf
      .fields
      .map { field =>
        Fld(
            key  = field.key.symbol,
            info = field.typeNode.pipe(forceInfo) )
          .setEnumName(field.typeNode.leaf.fullName.format) /* mostly for macros */ }
      .pipe   (Cls.apply) // expects at least one field
      .setName(leaf.fullName.items.last /* TODO: see t210325105833 - need to be in scope for macros */) // mostly for macros

  // ---------------------------------------------------------------------------
  def forceInfo(node: TypeNode): Info =
      node
        .forceValidContainer
        .pipe(valueType(enmOpt = None /* TODO? */)(node.isContainedDataClass))
        .pipe(node.containerType.info)

  // ---------------------------------------------------------------------------
  def forceSubInfo(node: TypeNode): SubInfo =
    SubInfo(
      node.containerType.isMultiple, // note: ignores optionality
      valueType(None /* ok if used for comparisons only (see 220506101842) */)(
        node.isContainedDataClass)(
        node.forceValidContainer))

  // ---------------------------------------------------------------------------
  def forceSubInfo(enmOpt: _EnmOpt)(node: TypeNode): SubInfo =
    SubInfo(
      node.containerType.isMultiple, // note: ignores optionality
      valueType(enmOpt)(
        node.isContainedDataClass)(
        node.forceValidContainer))

    // ---------------------------------------------------------------------------
    private def valueType(enmOpt: _EnmOpt)(isContainedDataClass: Boolean)(leaf: TypeLeaf): ValueType =
         if (isContainedDataClass) forceNestedClass(leaf)
         else                      valueTypeOpt(enmOpt)(leaf).getOrElse {
           if (leaf.isAny) ScalaAnyPlaceHolder
           else            illegalState(s"${leaf.formatDebug -> enmOpt}") }

      // ---------------------------------------------------------------------------
      private def valueTypeOpt(enmOpt: _EnmOpt)(leaf: TypeLeaf): Option[ValueType] =
        /**/ if (leaf.enumValue)    enmOpt.orElse(Some(BasicType._Enm.Dummy) /* typically for validations, see 220506101842 */)
        else if (leaf.isEnumeratum) Some(BasicType._Enm(leaf.forceEnumeratumEnum))
        else if (leaf.bytes)        Some(BasicType._Binary)
        else                        leaf.fullName.pipe(BasicType.fromFullNameOpt) }

// ===========================================================================
