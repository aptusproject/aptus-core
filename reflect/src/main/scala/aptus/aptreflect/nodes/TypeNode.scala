package aptus
package aptreflect
package nodes

import aptus.{Seq_, String_}
import aptus.Option_

// ===========================================================================
case class TypeNode(
    leaf :      TypeLeaf,
    args : List[TypeNode]) {

  // ---------------------------------------------------------------------------
  override def toString = formatDebug

    // ---------------------------------------------------------------------------
    // for pretty JSON, see extension method (250416100113) - so nodes don't refer to Dyn directly
    def formatDebug: String = // TODO: use PPrint
      leaf.formatDebug.newline +
      (if (args.isEmpty) ""
       else              args.map(_.formatDebug).section)

  // ===========================================================================
  // TODO: t231017103243 - use lens lib

  def dataClass  (value: Boolean): TypeNode = copy(leaf = leaf.dataClass  (value))
  def enumValue  (value: Boolean): TypeNode = copy(leaf = leaf.enumValue  (value))
  def bytes      (value: Boolean): TypeNode = copy(leaf = leaf.bytes      (value))
  def inheritsSeq(value: Boolean): TypeNode = copy(leaf = leaf.inheritsSeq(value))

  def fields(field1: TypeField, more: TypeField*) = copy(leaf = leaf.copy(fields = field1 +: more))

  def typeArg(value: TypeNode) = copy(args = List(value))

  // ===========================================================================
  def complex: Boolean = leaf.dataClass || leaf.fields.nonEmpty || args.nonEmpty

  // ---------------------------------------------------------------------------
  /** including "one" as container */
  def isContainedDataClass : Boolean = validContainerOpt.exists(_.dataClass)
  def isContainedEnumValue : Boolean = validContainerOpt.exists(_.enumValue)
  def isContainedEnumeratum: Boolean = validContainerOpt.exists(_.isEnumeratum)

  // ---------------------------------------------------------------------------
  def flattenedEnumValueNames: Seq[String] = leaf.enumeratumValueNamesOpt.getOrElse(Nil)

  // ---------------------------------------------------------------------------
  def fieldNames: Seq[String] = leaf.fields.map(_.key) // may be empty if not a data class

  // ===========================================================================
  def ifApplicable[T](f: T => Any): Any /* value */ => Any /* value */ =
      value =>
        if (sameType(value)) f(value.asInstanceOf[T])
        else                   value

    // ---------------------------------------------------------------------------
    // TODO: t220411094433 - hopefully there's a cleaner way...
    private def sameType(value: Any): Boolean =
      leaf.fullName.format == names.FullyQualifiedName.fromRuntimeValue(value)

  // ===========================================================================
  def forceSoleTypeArg: TypeNode = args.force.one

  // ===========================================================================
  def forceValidContainer: TypeLeaf = validContainerOpt.force

    /** should return some if valid, just removing any Option/Seq containers */
    def validContainerOpt: Option[TypeLeaf] = utils.TypeNodeContainerUtils.validContainerOpt(this)

  // ---------------------------------------------------------------------------
  /** e.g for HeadV values, not necessarily restricted by the One/Opt/Nes/Pes paradigm (eg Seq[Option] is valid) */
  def underlyingFullName = removeAllContainers.leaf.fullName

    // ---------------------------------------------------------------------------
    private def removeAllContainers: TypeNode =
           if (isSeq)    forceSoleTypeArg.removeAllContainers
      else if (isOption) forceSoleTypeArg.removeAllContainers
      else               this

  // ===========================================================================
  def isNothing: Boolean = this == TypeNodeBuiltIns.ScalaNothing
  def isAny    : Boolean = this == TypeNodeBuiltIns.ScalaAny

  // ---------------------------------------------------------------------------
  def isOptionOfSeq : Boolean = isOption && args.headOption.exists(_.isSeq)
  def isSeq         : Boolean = leaf.isSeq    && args.size == 1
  def isOption      : Boolean = leaf.isOption && args.size == 1

  // ---------------------------------------------------------------------------
  def isMultiple: Boolean = isSeq || isOptionOfSeq
  def isOptional: Boolean = leaf.isOption

  def isNotOne  : Boolean = isSeq || isOptionOfSeq || isOption

  // ---------------------------------------------------------------------------
  def isSome           : Boolean = leaf.isSome && args.size == 1
  def isNone           : Boolean = leaf.isNone
  def isUnqualifiedNone: Boolean = leaf.isNone && args.isEmpty

  // ===========================================================================
  def wrapInOption: TypeNode = TypeNodeBuiltIns.scalaOption(this)
  def wrapInSeq   : TypeNode = TypeNodeBuiltIns.scalaSeq   (this)

  // ===========================================================================
  // meant for post-validation
  def containerType: Container = containerTypeOpt.getOrElse(Container._One)

  // ---------------------------------------------------------------------------
  def containerTypeOpt: Option[Container] = {
         if (isSeq        ) Some(Container._Nes)
    else if (isOptionOfSeq) Some(Container._Pes)
    else if (isOption     ) Some(Container._Opt)
    else                    None } }

// ===========================================================================
object TypeNode {
  val Dummy: TypeNode = TypeNode(TypeLeaf.Dummy, List.empty)

  // ---------------------------------------------------------------------------
  def trivial(name: FullNameString): TypeNode =
    TypeNode(
      leaf = TypeLeaf.trivial(name),
      args = List.empty) }

// ===========================================================================
