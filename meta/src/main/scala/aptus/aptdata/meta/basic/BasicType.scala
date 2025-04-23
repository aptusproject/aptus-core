package aptus
package aptdata
package meta
package basic

import aptdata.lowlevel.{DataFormatting, DataParsing, DataValueTransforming}
import aptus.aptreflect.nodes.TypeNodeBuiltIns

// ===========================================================================
/** Which data type gets a proper `BasicType`? Those most likely to be manipulated. */
@pseudosealed
trait BasicType // note: intentionally not called "PrimitiveType"
      extends schema.ValueType
         with schema.HasValuePredicate
         with ClassTagRelated
         with HasFormatString
         with HasTypeNode
         with HasFullyQualifiedName
         with BasicTypeBooleanChecks
         with BasicTypeModifiers {
    type T

    protected[basic] val ordinal: Int /* scala 3 */

    /** eg _String */ lazy val name: String = getClass.getSimpleName.stripSuffixIfApplicable /* not so for _Enm */ ("$")

    // ---------------------------------------------------------------------------
    /** eg java.lang.String */
    final override def fullName: FullyQualifiedName = node.leaf.fullName

    /** eg _String */
    @nonfinl /* overriden by _Enm */ override def formatDefault: String = name }

  // ===========================================================================
  object BasicType {
    type Selector = BasicType.type => BasicType

    // ---------------------------------------------------------------------------
    lazy val unparameterizedBasicType: Seq[UnparameterizedBasicType] =
      Seq( // order matters
          _String, _Boolean, _Int, _Double,
          _Byte, _Short, _Long, _Float,
          _BigInt, _BigDec,
          _Date, _DateTime, _Instant,
          _Binary )

    lazy val fullNames  : Seq[FullyQualifiedName] = unparameterizedBasicType.map(_.fullName) :+ ???//_Enm.fullName
    lazy val fullNameSet: Set[FullyQualifiedName] = fullNames.toSet

    // ---------------------------------------------------------------------------
    def withName(value: String): BasicType = unparameterizedBasicType.find(_.name == value).get

    // ---------------------------------------------------------------------------
    def isKnown        (value: FullyQualifiedName):        Boolean    = lookup.contains(FullyQualifiedName.normalizeFullName(value))
    def fromFullNameOpt(value: FullyQualifiedName): Option[BasicType] = lookup.get     (FullyQualifiedName.normalizeFullName(value))

      // ---------------------------------------------------------------------------
      private val lookup: Map[FullyQualifiedName, BasicType] =
          unparameterizedBasicType
            .map { _.associateLeft(_.fullName) }
            .force.map
            .withDefault { value => aptus.illegalState(s"E201013093225:CantFindType:${value}") }

    // ===========================================================================
    case object _String extends UnparameterizedBasicType {
      type T = String

      final override val node: TypeNode = TypeNodeBuiltIns.String

      // ---------------------------------------------------------------------------
      override val ordinal = 0

      // ---------------------------------------------------------------------------
      override val  valuePredicate = _.isInstanceOf[T]

      // ---------------------------------------------------------------------------
      override val   parseString   = identity
      override val  formatString   = identity

      // ---------------------------------------------------------------------------
      // unpacked boilerplate:

        override lazy val _ctag: CT[                T  ] = ctag[                T  ]
        override lazy val nctag: CT[       Iterable[T] ] = ctag[       Iterable[T] ]
        override lazy val octag: CT[       Option  [T] ] = ctag[       Option  [T] ]
        override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]

        // ---------------------------------------------------------------------------
        override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]
        override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse /* TODO: cost of reverse? */ }

    // ===========================================================================
    case object _Boolean extends UnparameterizedBasicType { type T = Boolean; override val ordinal = 1; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaBoolean
      override val formatString   = DataFormatting.formatBoolean
      override val parseString    = _.toBoolean }

    // ===========================================================================
    case object _Int extends UnparameterizedBasicType with IntegerLikeType { type T = Int; override val ordinal = 2; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toInt; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaInt
      override val formatString   = DataFormatting.formatInt
      override val parseString    = _.toInt
      override val parseDouble    = DataValueTransforming.intFromDouble }

    // ---------------------------------------------------------------------------
    case object _Double extends UnparameterizedBasicType with RealLikeType { type T = Double; override val ordinal = 3; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toRealLike(value: Double) = value; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaDouble
      override val formatString   = DataFormatting.formatDouble
      override val parseString    = _.toDouble
      override val parseDouble    = identity }

    // ===========================================================================
    //TODO: t250326103249 - rename these to Int{8, 16, 64}?
    case object _Byte extends UnparameterizedBasicType with IntegerLikeType { type T = Byte; override val ordinal = 4; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toByte; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaByte
      override val formatString   = DataFormatting.formatByte
      override val parseString    = _.toByte
      override val parseDouble    = DataValueTransforming.byteFromDouble }

    // ---------------------------------------------------------------------------
    case object _Short extends UnparameterizedBasicType with IntegerLikeType { type T = Short; override val ordinal = 5; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toShort; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaShort
      override val formatString   = DataFormatting.formatShort
      override val parseString    = _.toShort
      override val parseDouble    = DataValueTransforming.shortFromDouble }

    // ---------------------------------------------------------------------------
    case object _Long extends UnparameterizedBasicType with IntegerLikeType { type T = Long; override val ordinal = 6; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toLong; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaLong
      override val formatString   = DataFormatting.formatLong
      override val parseString    = _.toLong
      override val parseDouble    = DataValueTransforming.longFromDouble }

    // ---------------------------------------------------------------------------
    case object _Float extends UnparameterizedBasicType with RealLikeType { type T = Float; override val ordinal = 7; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toRealLike(value: Double) = value.toFloat; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaFloat
      override val formatString   = DataFormatting.formatFloat
      override val parseString    = _.toFloat
      override val parseDouble    = DataValueTransforming.floatFromDouble }

    // ===========================================================================
    case object _BigInt extends UnparameterizedBasicType with UnboundedNumber { type T = BigInt; override val ordinal = 8; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaMathBigInt
      override val  parseString   = BigInt.apply
      override val formatString   = DataFormatting.formatBigInt }

    // ---------------------------------------------------------------------------
    case object _BigDec extends UnparameterizedBasicType with UnboundedNumber { type T = BigDecimal; override val ordinal = 9; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.ScalaMathBigDecimal
      override val  parseString   = BigDecimal.apply
      override val formatString   = DataFormatting.formatBigDec }

    // ===========================================================================
    case object _Date extends UnparameterizedBasicType with HasPair { type T = Date; override val ordinal = 10; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.JavaTimeLocalDate
      override val pair           = (DataParsing   . parseLocalDate, _.toLocalDate /* aptus' */)
      override val formatString   =  DataFormatting.formatDate

      private implicit val ord: Ordering[T] = aptus.localDateOrdering }

    // ---------------------------------------------------------------------------
    case object _DateTime extends UnparameterizedBasicType with HasPair { type T = DateTime; override val ordinal = 11; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.JavaTimeLocalDateTime
      override val pair           = (DataParsing   . parseLocalDateTime, _.toLocalDateTime /* aptus' */)
      override val formatString   =  DataFormatting.formatDateTime

      private implicit val ord: Ordering[T] = aptus.localDateTimeOrdering }

    // ---------------------------------------------------------------------------
    case object _Instant extends UnparameterizedBasicType with HasPair { type T = Instant; override val ordinal = 12; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.JavaTimeInstant
      override val pair         = (DataParsing   . parseInstant, _.toInstant /* aptus' */)
      override val formatString =  DataFormatting.formatInstant

      private implicit val ord: Ordering[T] = Ordering.by(identity) /* not sure why needed */ }

    // ===========================================================================
    case object _Binary extends UnparameterizedBasicType with HasParseAny { type T = ByteBuffer; override val ordinal = 13; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      final override val node: TypeNode = TypeNodeBuiltIns.JavaNioByteBuffer
      override val  parseString   = DataParsing   . parseBinary
      override val formatString   = DataFormatting.formatBinary

      private implicit val ord: Ordering[T] = aptus.byteBufferOrdering }

    // ===========================================================================
    // TODO:
    // - t210330102827 - capture enum name for macros (currently stored in Fld hackily)
    case class _Enm(values: Seq[EnumValue]) extends ParameterizedBasicType { type T = EnumValue; override val ordinal = 14; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse
        require(EnumValue.valid(values), formatDefault)

        // ---------------------------------------------------------------------------
        def stringValues: Seq[EnumValue.EnumStringValue] = values.map(_.stringValue)

        // ---------------------------------------------------------------------------
        final override val node: TypeNode = TypeNodeBuiltIns.AptusEnumValue
        final override def formatDefault: String = name.colon(values.map(_.stringValue.surroundWith("|")).mkString(","))

        // ---------------------------------------------------------------------------
        final override val valuePredicate = _.isInstanceOf[EnumValue]
        final override val formatString   = _.stringValue

        // ---------------------------------------------------------------------------
        private implicit val ord: Ordering[T] = basic.EnumValue.enumValueOrdering }

      // ===========================================================================
      object _Enm
           extends HasParseString
              with HasParseAny {
        type T = EnumValue

        final override def parseString = EnumValue.apply

        // ---------------------------------------------------------------------------
        val Dummy = _Enm(Seq(EnumValue.Dummy)) /* useful for internal comparisons in validation, see 220506101842 */ } }

// ===========================================================================
