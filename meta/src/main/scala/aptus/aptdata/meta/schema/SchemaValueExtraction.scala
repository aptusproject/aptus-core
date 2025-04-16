package aptus
package aptdata
package meta
package schema

// ===========================================================================
trait SchemaValueExtraction { self: Info =>

  // ---------------------------------------------------------------------------
  /** used when we know the data is compliant with the schema already (as opposed to when we are trying to fit the data to the schema) */
  def valueExtractionWithMatching
          (debug: DebugString)
          (nestingPredicate: PartialFunction[AnyValue, Boolean])
          (value: AnyValue /* eg [1, 2, 3], "foo", object, ... */)
        (nesting: Cls       => Multiple => AnyValue)
        (basic  : BasicType => Multiple => AnyValue)
      : AnyValue =
    _valueExtractionWithMatching(nestingPredicate)(value) match {
      case Seq(soleSubInfo) => valueExtraction(soleSubInfo)(nesting)(basic)
      case Nil              => illegalState(Seq("E250128092618" /* TODO */, debug /* eg key */, value))
      case other            => illegalState(Seq("E250128092619" /* TODO */, debug /* eg key */, value, other.size)) }

  // ---------------------------------------------------------------------------
  /** used when we know the data is compliant with the schema already (as opposed to when we are trying to fit the data to the schema) */
  def _valueExtractionWithMatching
        (nestingPredicate: PartialFunction[AnyValue, Boolean])
        (value: AnyValue /* eg [1, 2, 3], "foo", object, ... */)
      : Seq[SubInfo] =
    union match {
      case Seq(sole) => sole.in.list
      case _         => // union type
        (value match {
            case seq: Seq[_] => _Multiple -> seq.head // first suffices since all values will share the same type (+ can't be empty) - (FIXME: t250328160857 only for gallia)
            case sgl         => _Single   -> sgl })
          .pype { case (multiple, value /* eg 3, "foo", object, ... */) =>
            basic.BasicTypeMatchingSubInfos(nestingPredicate)(self)(multiple)(value) } }

  // ---------------------------------------------------------------------------
  /** used trying to fit the data to the schema (as opposed to when we know the data is compliant already) - more costly */
  def valueExtractionWithFailures
          (nesting: Cls       => Multiple => AnyValue)
          (basic  : BasicType => Multiple => AnyValue)
        : AnyValue =
      union match {
        case Seq(sole) =>                                     valueExtraction(sole)   (nesting)(basic)
        case _         => union.flatMap { subInfo => util.Try(valueExtraction(subInfo)(nesting)(basic) /* relies on 220615165554 failures */).toOption }.force.one }

// ===========================================================================
private[schema] def valueExtraction(subInfo: SubInfo)
        (nesting: Cls       => Multiple => AnyValue)
        (basic  : BasicType => Multiple => AnyValue)
      : AnyValue =
    subInfo.valueType match {
      case nc : Cls       => nesting(nc) (subInfo.multiple)
      case bsc: BasicType => basic  (bsc)(subInfo.multiple) } }

// ===========================================================================