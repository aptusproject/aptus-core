package gallia
package basic

// ===========================================================================
object BasicTypeUtils {

  // borrowed from gallia (TODO: t241130165320 - refactor)
                            def combine(values: Seq[BasicType]): BasicType = // TODO: subtype these 3?
                              values.distinct.sortBy(_.entryName) match {
                                case Seq(                   BasicType._Int                   ) => BasicType._Int
                                case Seq(BasicType._Double                                   ) => BasicType._Double
                                case Seq(BasicType._Double, BasicType._Int                   ) => BasicType._Double
                                case Seq(                                   BasicType._String) => BasicType._String
                                case Seq(_                                , BasicType._String) => BasicType._String
                                case Seq(_                , _             , BasicType._String) => BasicType._String }

  def doubleFitsLong (value: Any): Boolean = ???
  def doubleFitsFloat(value: Any): Boolean = ???

  // ---------------------------------------------------------------------------
  def stringOrLong(value1: Any, value2: Any)(value3: Any) = ???
  def createLookup(orderedValues: Any) = Map[reflect.FullyQualifiedName, basic.BasicType]()
  def matchingSubinfos(info: aptus.experimental.dyn.meta.InfoLike)(multiple: Boolean)(value: Any): Seq[aptus.experimental.dyn.meta.SubInfo] = ??? }

// ===========================================================================
