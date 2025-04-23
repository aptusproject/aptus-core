package aptus
package aptreflect
package nodes

// ===========================================================================
object TypeNodeBuiltIns {
  import names.FullNameBuiltIns._

  // ---------------------------------------------------------------------------
  val JavaString  = TypeNode.trivial(_JavaString) // same for scala.Predef.String
  val ScalaString = JavaString // just an alias, unlias eg scala.Int
  val String      = JavaString // for convenience

  // ---------------------------------------------------------------------------
  val ScalaNothing   = TypeNode.trivial(_ScalaNothing)

  val ScalaAny       = TypeNode.trivial(_ScalaAny)

  // ---------------------------------------------------------------------------
  val ScalaBoolean   = TypeNode.trivial(_ScalaBoolean)
  val ScalaInt       = TypeNode.trivial(_ScalaInt)
  val ScalaDouble    = TypeNode.trivial(_ScalaDouble)

  // ---------------------------------------------------------------------------
  val ScalaByte    = TypeNode.trivial(_ScalaByte)
  val ScalaShort   = TypeNode.trivial(_ScalaShort)
  val ScalaLong    = TypeNode.trivial(_ScalaLong)

  val ScalaFloat   = TypeNode.trivial(_ScalaFloat)

  // ---------------------------------------------------------------------------
  val ScalaMathBigInt     = TypeNode.trivial(_ScalaMathBigInt)
  val ScalaMathBigDecimal = TypeNode.trivial(_ScalaMathBigDecimal)

  // ---------------------------------------------------------------------------
  val JavaTimeLocalDate      = TypeNode.trivial(_JavaTimeLocalDate)
  val JavaTimeLocalDateTime  = TypeNode.trivial(_JavaTimeLocalDateTime)
  val JavaTimeInstant        = TypeNode.trivial(_JavaTimeInstant)

  // ---------------------------------------------------------------------------
  val JavaNioByteBuffer      = TypeNode.trivial(_JavaNioByteBuffer).bytes(value = true)

  // ---------------------------------------------------------------------------
  def scalaOption(typeArg: TypeNode) = TypeNode(TypeLeaf.ScalaOption, List(typeArg))
  def scalaSeq   (typeArg: TypeNode) = TypeNode(TypeLeaf.ScalaSeq,    List(typeArg))
  
  // ---------------------------------------------------------------------------
  val AptusEnumValue = TypeNode.trivial(_AptusEnumValue).enumValue(value = true) }

// ===========================================================================
