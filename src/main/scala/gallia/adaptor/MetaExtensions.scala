package gallia
package adaptor

import aptus.experimental.dyn.meta._
import reflect.Container
import GalliaAdaptor._

// ===========================================================================
trait GalliaFld_ extends Foo3 {
    val u: aptus.experimental.dyn.meta.Fld
    import u.info._
    import aptus.Seq_

    def isNesting: Boolean = ???

    // ---------------------------------------------------------------------------
    /** used trying to fit the data to the schema (as opposed to when we know the data is compliant already) - more costly */
    /*private[gallia] */def valueExtractionWithFailures
            (nesting: Cls       => Multiple => AnyValue)
            (basic  : BasicType => Multiple => AnyValue)
          : AnyValue =
        union match {
          case Seq(sole) =>                            sole.valueExtraction(nesting)(basic)
          case _         => union.flatMap { x => util.Try(x.valueExtraction(nesting)(basic) /* relies on 220615165554 failures */).toOption }.force.one } }

  // ===========================================================================
  trait GalliaInfo_ extends Foo3 {
    val u: aptus.experimental.dyn.meta.Info
    import u._

    // ---------------------------------------------------------------------------
    @deprecated def container1: Container =
      Container.from(optional, subInfo1.multiple)

    // ---------------------------------------------------------------------------
    def updateValueType(value: ValueType): Info = // just a different name
      u.replaceValueType(value) }

  // ===========================================================================
  trait GalliaSubInfo_ extends Foo3 {
    val u: aptus.experimental.dyn.meta.SubInfo
    import u._

    // ---------------------------------------------------------------------------
    def isMultiple = multiple

    // ---------------------------------------------------------------------------
    /*private[meta] */def valueExtraction
          (nesting: Cls       => Multiple => AnyValue)
          (basic  : BasicType => Multiple => AnyValue)
        : AnyValue =
      valueType match {
        case nc : Cls       => nesting(nc) (multiple)
        case bsc: BasicType => basic  (bsc)(multiple) } }

// ===========================================================================