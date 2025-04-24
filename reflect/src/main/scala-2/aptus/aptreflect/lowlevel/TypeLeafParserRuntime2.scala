package aptus
package aptreflect
package lowlevel

import names.FullyQualifiedName
import aptdata.meta.basic.EnumValue.EnumStringValue

// ===========================================================================
/*private 250404180740 */object TypeLeafParserRuntime2 extends ReflectionTypesAbstraction {
  def parseTypeNode[A: WTT]: TypeNode = _parseTypeNode(runiverse.weakTypeTag[A].tpe)

  // ---------------------------------------------------------------------------
  def _parseTypeNode(tpe: UType): TypeNode =
    nodes.TypeNode(
        leaf = apply(tpe),
        args = tpe.typeArgs.map(_parseTypeNode))

  // ===========================================================================
  private def apply(tpe: UType): nodes.TypeLeaf = {
    val symbol = tpe.typeSymbol

    val fullName      : FullyQualifiedName   = symbol.fullName.pipe(FullyQualifiedName.from)
    val enumValue     : Boolean              = fullName.isAptusEnumValue
    val baseClassNames: List[FullNameString] = tpe.baseClasses.map(_.fullName)

    // ---------------------------------------------------------------------------
    val enumeratumValueNamesOpt: Option[Seq[EnumStringValue]] =
      if (FullyQualifiedName.containsEnumEntry(baseClassNames)) Some(ReflectUtils.enumValueNames(tpe))
      else                                                      None

    // ---------------------------------------------------------------------------
    val caseClass: Boolean =
      symbol.isClass &&
      symbol.asClass.isCaseClass

    // ---------------------------------------------------------------------------
    val dataClass: Boolean =
      isDataClass(caseClass, baseClassNames, enumeratumValueNamesOpt, fullName)

    // ---------------------------------------------------------------------------
    nodes.TypeLeaf(
      fullName    = fullName,

      dataClass   = dataClass,
      enumValue   = enumValue,
      bytes       = fullName.isByteBuffer,
      inheritsSeq = FullyQualifiedName.containsSeq(baseClassNames),

      enumeratumValueNamesOpt = enumeratumValueNamesOpt,

      // may in theory be empty
      fields =
        if (!dataClass) List.empty
        else
          ReflectUtils
            .parseFields(tpe)
            .map { case (name, returnTpe) =>
              nodes.TypeField(name, _parseTypeNode(returnTpe)) } ) } }

// ===========================================================================
