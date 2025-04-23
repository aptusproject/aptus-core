package aptus
package aptdata
package meta
package basic

// ===========================================================================
@pseudosealed trait NumericalType extends BasicType

  // ---------------------------------------------------------------------------
  @pseudosealed trait UnboundedNumber extends NumericalType                                              // bignums
  @pseudosealed trait   BoundedNumber extends NumericalType with HasParseDouble with HasParseAnyToDouble // excluding bignums

    // ---------------------------------------------------------------------------
    @pseudosealed trait IntegerLikeType extends BoundedNumber /* basically: byte, short, int & long */ {
      // especially useful for JSON (since doesn't differentiate integers)
      def toIntegerLike(value: Double): Any /* eg value.toLong for _Long */ }

    // ---------------------------------------------------------------------------
    @pseudosealed trait RealLikeType    extends BoundedNumber /* basically: float & double */ {
      // especially useful for JSON (since doesn't differentiate integers)
      def toRealLike(value: Double): Any /* eg value.toDouble for _Float */ }

// ===========================================================================
@pseudosealed trait UnparameterizedBasicType
      extends BasicType
      with    HasParseString
      with    HasFieldHasType {

    final override def has: Fld => Boolean =
      _.info.subInfo1.valueType == this }

  // ---------------------------------------------------------------------------
  @pseudosealed trait ParameterizedBasicType extends BasicType

// ===========================================================================
