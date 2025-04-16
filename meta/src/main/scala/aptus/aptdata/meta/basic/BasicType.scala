package aptus
package aptdata
package meta
package basic

import lowlevel.{DataFormatting, DataParsing, DataValueTransforming}

// ===========================================================================
/** Which data type gets a proper `BasicType`? Those most likely to be manipulated. */
@pseudosealed
trait BasicType // note: intentionally not called "PrimitiveType"
        extends schema.ValueType
        with    schema.HasValuePredicate
        with    ClassTagRelated
        with    HasFormatString
        with    BasicTypeBooleanChecks {
      type T
    lazy val name: String = getClass.getSimpleName.stripSuffixIfApplicable /* not so for _Enm */ ("$")
    def formatDefault: String = name
    protected[basic] val ordinal: Int /* scala 3 */ }

  // ===========================================================================
  @TypeMatching
  object BasicType {
      type Selector = BasicType.type => BasicType

      // ---------------------------------------------------------------------------
      def unparameterizedBasicType: Seq[UnparameterizedBasicType] =
        Seq( // order matters
            _String, _Boolean, _Int, _Double,
            _Byte, _Short, _Long, _Float,
            _BigInt, _BigDec,
            _Date, _DateTime, _Instant,
            _Binary )

      // ---------------------------------------------------------------------------
      def withName(value: String): BasicType = unparameterizedBasicType.find(_.name == value).get

    // ===========================================================================
    case object _String extends UnparameterizedBasicType {
      type T = String

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
      override val formatString   = DataFormatting.formatBoolean
      override val parseString    = _.toBoolean }

    // ===========================================================================
    case object _Int extends UnparameterizedBasicType with IntegerLikeType { type T = Int; override val ordinal = 2; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toInt; override val valuePredicate = _.isInstanceOf[T]
      override val formatString   = DataFormatting.formatInt
      override val parseString    = _.toInt
      override val parseDouble    = DataValueTransforming.intFromDouble }

    // ---------------------------------------------------------------------------
    case object _Double extends UnparameterizedBasicType with RealLikeType { type T = Double; override val ordinal = 3; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toRealLike(value: Double) = value; override val valuePredicate = _.isInstanceOf[T]
      override val formatString   = DataFormatting.formatDouble
      override val parseString    = _.toDouble
      override val parseDouble    = identity }

    // ===========================================================================
    //TODO: t250326103249 - rename these to Int{8, 16, 64}?
    case object _Byte  extends UnparameterizedBasicType with IntegerLikeType { type T = Byte; override val ordinal = 4; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toByte; override val valuePredicate = _.isInstanceOf[T]
      override val formatString   = DataFormatting.formatByte
      override val parseString    = _.toByte
      override val parseDouble    = DataValueTransforming.byteFromDouble }

    // ---------------------------------------------------------------------------
    case object _Short extends UnparameterizedBasicType with IntegerLikeType { type T = Short; override val ordinal = 5; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toShort; override val valuePredicate = _.isInstanceOf[T]
      override val formatString   = DataFormatting.formatShort
      override val parseString    = _.toShort
      override val parseDouble    = DataValueTransforming.shortFromDouble }

    // ---------------------------------------------------------------------------
    case object _Long extends UnparameterizedBasicType with IntegerLikeType { type T = Long; override val ordinal = 6; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toIntegerLike(value: Double) = value.toLong; override val valuePredicate = _.isInstanceOf[T]
      override val formatString   = DataFormatting.formatLong
      override val parseString    = _.toLong
      override val parseDouble    = DataValueTransforming.longFromDouble }

    // ---------------------------------------------------------------------------
    case object _Float extends UnparameterizedBasicType with RealLikeType { type T = Float; override val ordinal = 7; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override def toRealLike(value: Double) = value.toFloat; override val valuePredicate = _.isInstanceOf[T]
      override val formatString   = DataFormatting.formatFloat
      override val parseString    = _.toFloat
      override val parseDouble    = DataValueTransforming.floatFromDouble }

    // ===========================================================================
    case object _BigInt extends UnparameterizedBasicType with UnboundedNumber { type T = BigInt; override val ordinal = 8; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      override val  parseString   = BigInt.apply
      override val formatString   = DataFormatting.formatBigInt }

    // ---------------------------------------------------------------------------
    case object _BigDec extends UnparameterizedBasicType with UnboundedNumber { type T = BigDecimal; override val ordinal = 9; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      override val  parseString   = BigDecimal.apply
      override val formatString   = DataFormatting.formatBigDec }

    // ===========================================================================
    case object _Date extends UnparameterizedBasicType with HasPair { type T = Date; override val ordinal = 10; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      override val pair           = (DataParsing   . parseLocalDate, _.toLocalDate /* aptus' */)
      override val formatString   =  DataFormatting.formatDate

      private implicit val ord: Ordering[T] = aptus.localDateOrdering }

    // ---------------------------------------------------------------------------
    case object _DateTime extends UnparameterizedBasicType with HasPair { type T = DateTime; override val ordinal = 11; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      override val pair           = (DataParsing   . parseLocalDateTime, _.toLocalDateTime /* aptus' */)
      override val formatString   =  DataFormatting.formatDateTime

      private implicit val ord: Ordering[T] = aptus.localDateTimeOrdering }

    // ---------------------------------------------------------------------------
    case object _Instant extends UnparameterizedBasicType with HasPair { type T = Instant; override val ordinal = 12; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      override val pair         = (DataParsing   . parseInstant, _.toInstant /* aptus' */)
      override val formatString =  DataFormatting.formatInstant

      private implicit val ord: Ordering[T] = Ordering.by(identity) /* not sure why needed */ }

    // ===========================================================================
    case object _Binary extends UnparameterizedBasicType with HasParseAny { type T = ByteBuffer; override val ordinal = 13; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse; override val valuePredicate = _.isInstanceOf[T]
      override val  parseString   = DataParsing   . parseBinary
      override val formatString   = DataFormatting.formatBinary

      private implicit val ord: Ordering[T] = aptus.byteBufferOrdering }

    // ===========================================================================
    // TODO:
    // - t210330102827 - capture enum name for macros (currently stored in Fld hackily)
    case class _Enm(values: Seq[EnumValue]) extends ParameterizedBasicType { type T = EnumValue; override val ordinal = 14; /* boilerplate: */ override lazy val _ctag: CT[T] = ctag[T]; override lazy val nctag: CT[Iterable[T]] = ctag[Iterable[T]]; override lazy val octag: CT[Option [T]] = ctag[Option [T]]; override lazy val pctag: CT[Option[Iterable[T]]] = ctag[Option[Iterable[T]]]; override lazy val ordA: Ordering[T] = implicitly[Ordering[T]]; override lazy val ordD: Ordering[T] = implicitly[Ordering[T]].reverse
        require(EnumValue.valid(values), formatDefault)

        // ---------------------------------------------------------------------------
        override def formatDefault: String = name.colon(values.map(_.stringValue.surroundWith("|")).mkString(","))
        def stringValues: Seq[EnumStringValue] = values.map(_.stringValue)

        // ---------------------------------------------------------------------------
        override val valuePredicate = _.isInstanceOf[EnumValue]
        override val formatString   = _.stringValue

        // ---------------------------------------------------------------------------
        private implicit val ord: Ordering[T] = basic.EnumValue.enumValueOrdering }

      // ===========================================================================
      object _Enm extends HasParseString with HasParseAny {
        type T = EnumValue // still needed?
        override def parseString = EnumValue.apply

        // ---------------------------------------------------------------------------
        val Dummy = _Enm(Seq(EnumValue("_"))) /* useful for internal comparisons in validation, see 220506101842 */ } }

// ===========================================================================
