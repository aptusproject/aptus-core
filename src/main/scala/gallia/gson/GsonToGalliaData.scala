package gallia
package gson

import aptus.aptjson.GsonParser
import aptus.{Anything_, JsonString}
import aptus.experimental.dyn.{BasicType => _, _}
import adaptor.GalliaAdaptor0._

// ===========================================================================
object GsonToGalliaData {
  import meta.Cls

  // ---------------------------------------------------------------------------
// borrowed from gallia (TODO: t241130165320 - refactor)

                                      def convertRecursively(c: Cls)(o: Obj): Obj =
                                          c .fields
                                            .flatMap { field =>
                                              c.unknownKeys(o).assert(_.isEmpty) // necessary for union types (see 220615165554)

                                              o .attemptKey(field.key)
                                                .map { value =>
                                                  field.key ->
processField(field/*.info*/)(value) } }
.map(aptus.experimental.dyn.domain.Entry.fromGallia2).pipe(aptus.experimental.dyn.data.sngl.Dyn.build)//.pipe(gallia.obj)

                                      // ===========================================================================
                                      def parseGsonJsonElement(info: meta.InfoLike)(value: String): AnyValue =
// not used?
                                          parseJsonString(info.isNesting, info.subInfo1.multiple)(value)
                                            .pipe(processField(info))

                                      // ===========================================================================
                                      private def processField(info: meta.InfoLike)(value: AnyValue): AnyValue =
                                        info.valueExtractionWithFailures {

                                          // ---------------------------------------------------------------------------
                                          nestedGalliaClass => multiple =>
                                            if (!multiple) value.asInstanceOf[    Obj  ].pipe(convertRecursively(nestedGalliaClass))
                                            else           value.asInstanceOf[Seq[Obj ]].map (convertRecursively(nestedGalliaClass)) } {

                                          // ---------------------------------------------------------------------------
                                          bsc => multiple =>
                                            if (!multiple) value                            .pipe(_basicValue(bsc))
                                            else           value.asInstanceOf[Seq[_]].toList.map (_basicValue(bsc)) }

                                      // ===========================================================================
                                      private def _basicValue(basicType: BasicType)(value: AnyValue): AnyValue =
                                        basicType match {
                                          case BasicType._String  => value.asInstanceOf[String]
                                          case BasicType._Boolean => value.asInstanceOf[Boolean]
                                          case BasicType._Double  => value.asInstanceOf[Double]
                                          case BasicType._Int     => value.pipe(toDouble)

                                          // ---------------------------------------------------------------------------
                                          case BasicType._Long    => BasicType._Long .parseAnyToDouble(value)
                                          case BasicType._Float   => BasicType._Float.parseAnyToDouble(value)

                                          case BasicType._Byte    => BasicType._Byte .parseAnyToDouble(value)
                                          case BasicType._Short   => BasicType._Short.parseAnyToDouble(value)

                                          // ---------------------------------------------------------------------------
                                          case BasicType._BigInt  => stringOrLong  (BigInt    .apply, BigInt    .apply)(value)
                                          case BasicType._BigDec  => stringOrDouble(BigDecimal.apply, BigDecimal.apply)(value)

                                          // ---------------------------------------------------------------------------
                                          case BasicType._Date     => stringOrLong(BasicType._Date    .pair)(value)
                                          case BasicType._DateTime => stringOrLong(BasicType._DateTime.pair)(value)
                                          case BasicType._Instant  => stringOrLong(BasicType._Instant      .pair)(value)

                                          // ---------------------------------------------------------------------------
                                          case BasicType._Binary => BasicType._Binary.parseStringAsAny(value)

                                          // ---------------------------------------------------------------------------
//                                          case _: BasicType._Enm  => BasicType._Enm.parseStringAsAny(value)
                                        }

                                      // ===========================================================================
                                      private[gson] def stringOrLong[T](pair:    (String => T,         Long => T)): Any => T = stringOrLong(pair._1, pair._2)
                                      private[gson] def stringOrLong[T](ifString: String => T, ifLong: Long => T) : Any => T =
                                        _ match {
                                          case s: String => ifString(s)
                                          case n: Number => ifLong  (numberToLong(n)) }

                                      // ---------------------------------------------------------------------------
                                      private[gson] def stringOrDouble[T](ifString: String => T, ifDouble: Double => T): Any => T =
                                        _ match {
                                          case s: String => ifString(s)
                                          case n: Number => ifDouble(n.doubleValue) }

                                      // ===========================================================================
                                      private[gson] def numberToLong(n: Number): Long =
                                        n .doubleValue
.assert(basic.BasicTypeUtils.doubleFitsLong) // not used?
                                          .pipe(d => d.toLong.ensuring(_.toDouble == d))

                                      // ===========================================================================
                                      private def parseJsonString(nesting: Boolean, multiple: Boolean)(value: JsonString): AnyValue =
                                        (nesting, multiple) match { // spilling for instance does not support union types
                                          case (false, false) => GsonParser.stringToPrimitiveValueAny (value) /*     Vle  */
                                          case (false, true ) => GsonParser.stringToPrimitiveValueAnys(value) /* Seq[Vle] */
                                          case (true , false) => BorrowedGsonFrom .fromObjectString          (value) /*     Obj  */
                                          case (true , true ) => BorrowedGsonFrom .fromArrayString           (value) /* Seq[Obj] */ }

                                    }

// ===========================================================================
