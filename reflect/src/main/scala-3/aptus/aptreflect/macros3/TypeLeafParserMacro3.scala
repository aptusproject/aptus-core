package aptus
package aptreflect
package macros3

import nodes.{TypeNode, TypeLeaf, TypeField}
import names.FullyQualifiedName
import aptdata.meta.basic.EnumValue.EnumStringValue

// ===========================================================================
private object TypeLeafParserMacro3 {
  import scala.quoted.{Quotes, quotes, Type, Expr}
  import TypeNodeToExpr.*

  // ===========================================================================
  /** recursion for both potential type arguments and nesting */
  def rec(using q: Quotes)(typeRepr: q.reflect.TypeRepr): TypeNode =
    TypeNode(
      leaf = typeRepr.pipe(typeLeaf),
      args = typeRepr.     typeArgs.map(rec))

  // ===========================================================================
  private def typeLeaf(using q: Quotes)(typeRepr: q.reflect.TypeRepr): TypeLeaf = {
    // TODO: add lookup for built-ins here

    val typeSymbol: q.reflect.Symbol = typeRepr.dealias.typeSymbol
    assert(!typeSymbol.isAliasType) // TODO: when is it ever? doesn't seem to work when using an alias type

    val fullNameString: FullNameString = typeSymbol.fullName

    val fullName : FullyQualifiedName = fullNameString.pipe(FullyQualifiedName.from)
    val enumValue: Boolean            = fullName.isAptusEnumValue

    val baseClassNames: List[FullNameString] = baseClassesFullNames(typeRepr)

    // ---------------------------------------------------------------------------
    val enumeratumValueNamesOpt: Option[Seq[EnumStringValue]] =
      if FullyQualifiedName.containsEnumEntry(baseClassNames) then Some(enumValueNames(typeSymbol))
      else                                                         None

    // ---------------------------------------------------------------------------
    val caseClass: Boolean = isCaseClass(typeSymbol)(baseClassNames)

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

      fields =
        if !dataClass then List.empty // may in theory be empty
        else
            typeSymbol
              .caseFields
              .map { caseFieldSymbol =>
                TypeField(
                  key      = caseFieldSymbol.name,
                  typeNode = caseFieldSymbol.pipe(typeRepr.memberType).pipe(rec) ) }
              .toList) }

  // ===========================================================================
  private def baseClassesFullNames(using q: Quotes)(typeRepr: q.reflect.TypeRepr): List[FullNameString] =
    typeRepr
      .baseClasses
      .map { (symbol: q.reflect.Symbol) =>
        (symbol.fullName: String) }

  // ---------------------------------------------------------------------------
  private def enumValueNames(using q: Quotes)(typeSymbol: q.reflect.Symbol): List[EnumStringValue] =
    typeSymbol
      .companionModule
      .declaredFields
      .filter(_.flags.is(q.reflect.Flags.Case))
      .map(_.name)

  // ---------------------------------------------------------------------------
  private def isCaseClass(using q: Quotes)(typeSymbol: q.reflect.Symbol)(baseClassNames: List[FullNameString]): Boolean =
     typeSymbol.isClassDef &&
     typeSymbol.flags.is(q.reflect.Flags.Case) &&
     typeSymbol.caseFields.nonEmpty }

// ===========================================================================
