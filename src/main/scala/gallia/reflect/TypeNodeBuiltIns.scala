package gallia
package reflect

// ===========================================================================
object TypeNodeBuiltIns {

  // TODO: codegen (see 241121238316)
  val String: TypeNode = TypeNode(TypeLeaf("String"))

  val ScalaBoolean: TypeNode = TypeNode(TypeLeaf("Boolean"))
  val ScalaInt: TypeNode = TypeNode(TypeLeaf("Int"))
  val ScalaDouble: TypeNode = TypeNode(TypeLeaf("Double"))
  val ScalaByte: TypeNode = TypeNode(TypeLeaf("Byte"))
  val ScalaShort: TypeNode = TypeNode(TypeLeaf("Short"))
  val ScalaLong: TypeNode = TypeNode(TypeLeaf("Long"))
  val ScalaFloat: TypeNode = TypeNode(TypeLeaf("Float"))
  val ScalaMathBigInt: TypeNode = TypeNode(TypeLeaf("BigInt"))
  val ScalaMathBigDecimal: TypeNode = TypeNode(TypeLeaf("BigDecimal"))
  val JavaTimeLocalDate: TypeNode = TypeNode(TypeLeaf("LocalDate"))
  val JavaTimeLocalTime: TypeNode = TypeNode(TypeLeaf("LocalTime"))
  val JavaTimeLocalDateTime: TypeNode = TypeNode(TypeLeaf("LocalDateTime"))
  val JavaTimeOffsetDateTime: TypeNode = TypeNode(TypeLeaf("OffsetDateTime"))
  val JavaTimeZonedDateTime: TypeNode = TypeNode(TypeLeaf("ZonedDateTime"))
  val JavaTimeInstant: TypeNode = TypeNode(TypeLeaf("Instant"))
  val JavaNioByteByffer: TypeNode = TypeNode(TypeLeaf("ByteBuffer"))

  val GalliaEnumValue: TypeNode = TypeNode(TypeLeaf("")) }

// ===========================================================================