package aptus
package aptdata
package meta

// ===========================================================================
package object basic {
  type pseudosealed = aptus.pseudosealed

  type EnumStringValue = String /* eg "Red" for color enum */

  // ---------------------------------------------------------------------------
  trait ClassTagRelated {
      type T

      // ---------------------------------------------------------------------------
      def _ctag: CT[                T  ]
      def nctag: CT[       Iterable[T] ]
      def octag: CT[       Option  [T] ]
      def pctag: CT[Option[Iterable[T]]]

      // ---------------------------------------------------------------------------
      def ordA: Ordering[T]
      def ordD: Ordering[T] }

    // ---------------------------------------------------------------------------
    private[basic] type CT  [T]     = scala.reflect.ClassTag[T]
    private[basic] def  ctag[T: CT] = scala.reflect.classTag[T]

  // ===========================================================================
  // inferring
  def combine(values: Seq[BasicType]): BasicType = // TODO: subtype these 3?
    values.distinct.sortBy(_.name) match {
      case Seq(                   BasicType._Int                   ) => BasicType._Int
      case Seq(BasicType._Double                                   ) => BasicType._Double
      case Seq(BasicType._Double, BasicType._Int                   ) => BasicType._Double
      case Seq(                                   BasicType._String) => BasicType._String
      case Seq(_                                , BasicType._String) => BasicType._String
      case Seq(_                , _             , BasicType._String) => BasicType._String } }

// ===========================================================================
