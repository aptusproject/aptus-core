package aptus
package aptreflect
package names

import aptus.String_
import names.{FullyQualifiedName => Self}

// ===========================================================================
case class FullyQualifiedName(items: Seq[String]) {
  require(items.nonEmpty)
  require(items.head != "<none>" /* typically happens when missing a ": WTT" along the way, resulting in eg <none>.T */)

  // ---------------------------------------------------------------------------
  val fullNameString: FullNameString = items.mkString(".")

  val firstItem = items.head
  val  lastItem = items.last

  // ---------------------------------------------------------------------------
  override def toString: String = format

  def format     : FullNameString = fullNameString
  def inScopeName: String         = lastItem

  // ---------------------------------------------------------------------------
  def isJavaString: Boolean = fullNameString == FullNameBuiltIns._JavaString

  // ---------------------------------------------------------------------------
  def isAny       : Boolean = fullNameString == FullNameBuiltIns._ScalaAny

  def isOption    : Boolean = fullNameString == FullNameBuiltIns._Option
  def isNone      : Boolean = fullNameString == FullNameBuiltIns._None
  def isSome      : Boolean = fullNameString == FullNameBuiltIns._Some

  // ---------------------------------------------------------------------------
  def isEither    : Boolean = fullNameString == FullNameBuiltIns._Either
  def isTry       : Boolean = fullNameString == FullNameBuiltIns._Try
  def isProduct   : Boolean = fullNameString == FullNameBuiltIns._Product
  def isByteBuffer: Boolean = fullNameString == FullNameBuiltIns._JavaNioByteBuffer

  // ---------------------------------------------------------------------------  
  def isAptusEnumValue: Boolean = fullNameString == FullNameBuiltIns._AptusEnumValue

  // ===========================================================================
  def startsWithScalaPackage : Boolean = firstItem == FullNameBuiltIns.ScalaPackageName
  def startsWithJavaPackage  : Boolean = firstItem == FullNameBuiltIns. JavaPackageName

  // ---------------------------------------------------------------------------
  def startsWithAptusPackage : Boolean = firstItem == FullNameBuiltIns.AptusPackageName
  def startsWithGalliaPackage: Boolean = firstItem == FullNameBuiltIns.GalliaPackageName }

// ===========================================================================
object FullyQualifiedName {
  def from(value: FullNameString): Self = value.splitBy(".").pipe(FullyQualifiedName.apply)

  // ===========================================================================
  def containsSeq      (fullNameStrings: Seq[FullNameString]): Boolean = fullNameStrings.contains(FullNameBuiltIns._Seq)
  def containsEnumEntry(fullNameStrings: Seq[FullNameString]): Boolean = fullNameStrings.contains(FullNameBuiltIns._EnumEntry)

  // ---------------------------------------------------------------------------
  lazy val ScalaOption: Self = FullyQualifiedName.from(FullNameBuiltIns._Option)
  lazy val ScalaSeq   : Self = FullyQualifiedName.from(FullNameBuiltIns._Seq)
  lazy val ScalaSet   : Self = FullyQualifiedName.from(FullNameBuiltIns._Set)
  lazy val ScalaMap   : Self = FullyQualifiedName.from(FullNameBuiltIns._Map)
  lazy val ScalaArray : Self = FullyQualifiedName.from(FullNameBuiltIns._Array)

  // ===========================================================================
  private[aptus] def normalizeFullName(value: Self): Self = FullyQualifiedName.from(normalizeFullName(value.format))

    // ---------------------------------------------------------------------------
    @annotation.switch
    private def normalizeFullName(value: FullNameString): FullNameString = // TODO: switch to non-String version
      value match {
        case FullNameBuiltIns._JavaString       => FullNameBuiltIns._JavaString // leave unchanged (TODO: use scala Predef's alias?)

        // ---------------------------------------------------------------------------
        case FullNameBuiltIns._JavaLangInteger  => FullNameBuiltIns._ScalaInt // all others will be handled below by the package swap (default clause)

        // ---------------------------------------------------------------------------
        case FullNameBuiltIns._BooleanPrimitive => FullNameBuiltIns._ScalaBoolean
        case FullNameBuiltIns._IntPrimitive     => FullNameBuiltIns._ScalaInt
        case FullNameBuiltIns._DoublePrimitive  => FullNameBuiltIns._ScalaDouble

        case FullNameBuiltIns._LongPrimitive    => FullNameBuiltIns._ScalaLong

        case FullNameBuiltIns._BytePrimitive    => FullNameBuiltIns._ScalaByte
        case FullNameBuiltIns._ShortPrimitive   => FullNameBuiltIns._ScalaShort
        case FullNameBuiltIns._FloatPrimitive   => FullNameBuiltIns._ScalaFloat

        // ---------------------------------------------------------------------------
        // note: no automatic conversions between scala.math.Big* and java.math.Big*, unlike the java.lang.* counterparts
        case other => other.replace(FullNameBuiltIns.JavaLangPackageNameDot, FullNameBuiltIns.ScalaPackageNameDot) /* not so for java.math (not equivalent at runtime) */ }

  // ===========================================================================
  private[aptreflect] def fromRuntimeValue(value: Any): FullNameString =
    value
      .getClass
      .getName // FIXME: t231018094951 - does not always agree with TypeNode's, eg "int" instead of "Scala.Int"
      .pipe(normalizeFullName) }

// ===========================================================================
