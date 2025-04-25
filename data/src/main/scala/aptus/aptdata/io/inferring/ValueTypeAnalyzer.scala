package aptus
package aptdata
package io
package inferring

import meta.basic.BasicType._
import scala.{Any => AnySingleValue}

// ===========================================================================
object ValueTypeAnalyzer {

  def apply(value: AnySingleValue): ValueType =
    value match {
      case _: String     => _String
      case x: Double     => // 201119115427
        if (x.isValidInt /* Long? */) _Int // _Long?
        else                          _Double
      case _: Int        => _Int
      case _: Boolean    => _Boolean

      case _: Byte       => _Byte
      case _: Short      => _Short
      case _: Long       => _Long

      case x: Float      => // 201119115427
        if (x.isValidInt /* Long? */) _Int
        else                          _Float

      case _: BigInt     => _BigInt
      case _: BigDec     => _BigDec

      case _: Date       => _Date
      case _: DateTime   => _DateTime
      case _: Instant    => _Instant

      case _: ByteBuffer => _Binary

      case x: meta.basic.EnumValue => _Enm(Seq(x))
      case x: enumeratum.EnumEntry => _Enm(Seq(meta.basic.EnumValue(x.entryName))) } }

// ===========================================================================