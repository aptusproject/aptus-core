package aptus
package aptreflect
package nodes

import aptus.{Anything_, String_, Seq_}
import names.FullyQualifiedName
import aptdata.meta.basic.EnumValue

// ===========================================================================
case class TypeLeaf(
      fullName   : FullyQualifiedName, // eg ["java", "lang", "String"]

      dataClass  : Boolean = false, // eg "case class Foo(a: String, b: Int)", but not necessarily all case classes (eg not scala.Some)
      enumValue  : Boolean = false,
      bytes      : Boolean = false, // as ByteBuffer
      inheritsSeq: Boolean = false,

      enumeratumValueNamesOpt: Option[Seq[String]] = None, // not used currently

      fields: Seq[TypeField] = Nil) {

    def inScopeName: String = fullName.lastItem

    def keys: Seq[String] = fields.map(_.key)

    // ---------------------------------------------------------------------------
    def formatDebug: String = // TODO: use PPrint
      "[" + fullName.format.quote + " - " +
      Seq(
            dataClass  .in.someIf(_ == true).map(_ => "dataClass"),
            enumValue  .in.someIf(_ == true).map(_ => "enumValue"),
            bytes      .in.someIf(_ == true).map(_ => "bytes"),
            inheritsSeq.in.someIf(_ == true).map(_ => "inheritsSeq"),

            enumeratumValueNamesOpt.map(_.map(_.quote).join("|")))
          .flatten.join(", ") +
        (if  (fields.isEmpty) "]"
         else                 fields.map(_.formatDebug).section2 + "]")

    // ---------------------------------------------------------------------------
    def isAny: Boolean = this == TypeNodeBuiltIns.ScalaAny.leaf

    def isSeq       : Boolean = inheritsSeq
    def isEnumeratum: Boolean = enumeratumValueNamesOpt.nonEmpty

    def isOption: Boolean = fullName.isOption

    def isNone: Boolean = fullName.isNone
    def isSome: Boolean = fullName.isSome

    def isNotOne: Boolean = isSeq || isOption

    // ===========================================================================
    // TODO: t231017103243 - use lens lib

    // ---------------------------------------------------------------------------
    def dataClass  (value: Boolean): TypeLeaf = copy(dataClass   = value)
    def enumValue  (value: Boolean): TypeLeaf = copy(enumValue   = value)
    def bytes      (value: Boolean): TypeLeaf = copy(bytes       = value)
    def inheritsSeq(value: Boolean): TypeLeaf = copy(inheritsSeq = value)
    
    // ===========================================================================
    def forceEnumeratumEnum: Seq[EnumValue] = enumeratumValueNamesOpt.get.map(EnumValue.apply) }

  // ===========================================================================
  object TypeLeaf {
    val Dummy: TypeLeaf = TypeLeaf.trivial(FullyQualifiedName(Seq("gallia", "Dummy")))

    // ---------------------------------------------------------------------------
    lazy val ScalaOption: TypeLeaf = TypeLeaf.trivial(FullyQualifiedName.ScalaOption)
    lazy val ScalaSeq   : TypeLeaf = TypeLeaf.trivial(FullyQualifiedName.ScalaSeq).inheritsSeq(value = true)

    // ---------------------------------------------------------------------------
    def trivial(name    : String)            : TypeLeaf = TypeLeaf(fullName = FullyQualifiedName.from(name))
    def trivial(fullName: FullyQualifiedName): TypeLeaf = TypeLeaf(fullName = fullName)}

// ===========================================================================
