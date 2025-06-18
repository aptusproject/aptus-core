package aptus
package aptreflect
package names

import aptus.{String_, Class_}

// ===========================================================================
object FullNameBuiltIns {
  private[aptreflect] lazy val ScalaPackageName        = "scala"    .intern()
  private[aptreflect] lazy val JavaPackageName         = "java"     .intern()
  private[aptreflect] lazy val JavaLangPackageName     = "java.lang".intern()

  // ---------------------------------------------------------------------------
  private[aptreflect] lazy val AptusPackageName        = "aptus"    .intern()
  private[aptreflect] lazy val GalliaPackageName       = "gallia"   .intern()

  // ---------------------------------------------------------------------------
  private[aptreflect] lazy val ScalaPackageNameDot    = ScalaPackageName   .dot.intern()
  private[aptreflect] lazy val JavaLangPackageNameDot = JavaLangPackageName.dot.intern()

  // ===========================================================================
  private[aptreflect] lazy val _Array : FullNameString = classOf[scala.Array[_]].fullPath.intern()

  private[aptreflect] lazy val _Seq   : FullNameString = classOf[scala.collection.immutable.Seq[_]].fullPath.intern() // don't use collection.Seq
  private[aptreflect] lazy val _Set   : FullNameString = classOf[scala.collection.immutable.Set[_]].fullPath.intern()

  private[aptreflect] lazy val _Option: FullNameString = classOf[scala.Option[_]]                  .fullPath.intern()

  private[aptreflect] lazy val _Some  : FullNameString = classOf[scala.Some[_]].fullPath.intern()
  private[aptreflect] lazy val _None  : FullNameString = scala.None.getClass   .fullPath.intern()

  private[aptreflect] lazy val _Nil   : FullNameString = scala.collection.immutable.Nil.getClass.fullPath.intern()

  private[aptreflect] lazy val _Map   : FullNameString = classOf[scala.collection.immutable.Map[_, _]].fullPath.intern()

  // ---------------------------------------------------------------------------
  private[aptreflect] lazy val _Product: FullNameString = classOf[scala.Product].fullPath.intern()

  // ---------------------------------------------------------------------------
  private[aptreflect] lazy val _Either: FullNameString = classOf[scala.util.Either[_, _]].fullPath.intern()
  private[aptreflect] lazy val _Left  : FullNameString = classOf[scala.util.Left[_, _]].fullPath.intern()
  private[aptreflect] lazy val _Right : FullNameString = classOf[scala.util.Left[_, _]].fullPath.intern()

  private[aptreflect] lazy val _Try    : FullNameString = classOf[scala.util.Try   [_]]   .fullPath.intern()
  private[aptreflect] lazy val _Failure: FullNameString = classOf[scala.util.Failure[_]].fullPath.intern()
  private[aptreflect] lazy val _Success: FullNameString = classOf[scala.util.Success[_]].fullPath.intern()

  // ===========================================================================
  private[aptreflect] lazy val _JavaString     : FullNameString = classOf[java.lang.String ].fullPath.intern()
  private[aptreflect] lazy val _JavaLangInteger: FullNameString = classOf[java.lang.Integer].fullPath.intern()
  private[aptreflect] lazy val _JavaLangDouble : FullNameString = classOf[java.lang.Double ].fullPath.intern()
  private[aptreflect] lazy val _JavaLangBoolean: FullNameString = classOf[java.lang.Boolean].fullPath.intern()

  // ---------------------------------------------------------------------------
  private[aptus] lazy val _JavaTimeLocalDate     : FullNameString = classOf[java.time.LocalDate    ].fullPath.intern()
  private[aptus] lazy val _JavaTimeLocalDateTime : FullNameString = classOf[java.time.LocalDateTime].fullPath.intern()
  private[aptus] lazy val _JavaTimeInstant       : FullNameString = classOf[java.time.Instant      ].fullPath.intern()

  // ---------------------------------------------------------------------------
  private[aptus] lazy val _JavaNioByteBuffer = classOf[java.nio.ByteBuffer].fullPath.intern()

  // ===========================================================================
  // note: not String primitive

  private[aptreflect] lazy val _BooleanPrimitive: FullNameString = classOf[scala.Boolean].fullPath.ensuring(_ == "boolean").intern()
  private[aptreflect] lazy val _IntPrimitive    : FullNameString = classOf[scala.Int]    .fullPath.ensuring(_ == "int")    .intern()
  private[aptreflect] lazy val _DoublePrimitive : FullNameString = classOf[scala.Double] .fullPath.ensuring(_ == "double") .intern()

  private[aptreflect] lazy val _BytePrimitive   : FullNameString = classOf[scala.Byte]   .fullPath.ensuring(_ == "byte")   .intern()
  private[aptreflect] lazy val _ShortPrimitive  : FullNameString = classOf[scala.Short]  .fullPath.ensuring(_ == "short")  .intern()
  private[aptreflect] lazy val _LongPrimitive   : FullNameString = classOf[scala.Long]   .fullPath.ensuring(_ == "long")   .intern()

  private[aptreflect] lazy val _FloatPrimitive  : FullNameString = classOf[scala.Float]  .fullPath.ensuring(_ == "float")  .intern()

  // ===========================================================================
  // can't use classOf for these (since they return lowercase primitives such as "boolean")

  private[aptus] lazy val _ScalaBoolean: FullNameString = "scala.Boolean".intern()
  private[aptus] lazy val _ScalaInt    : FullNameString = "scala.Int"    .intern()
  private[aptus] lazy val _ScalaDouble : FullNameString = "scala.Double" .intern()

  private[aptus] lazy val _ScalaByte   : FullNameString = "scala.Byte"   .intern()
  private[aptus] lazy val _ScalaShort  : FullNameString = "scala.Short"  .intern()
  private[aptus] lazy val _ScalaLong   : FullNameString = "scala.Long"   .intern()

  private[aptus] lazy val _ScalaFloat  : FullNameString = "scala.Float"  .intern()

  // ---------------------------------------------------------------------------
  private[aptus] lazy val _ScalaMathBigInt    : FullNameString = classOf[scala.math.BigInt    ].fullPath.intern()
  private[aptus] lazy val _ScalaMathBigDecimal: FullNameString = classOf[scala.math.BigDecimal].fullPath.intern()

  // ---------------------------------------------------------------------------
                      lazy val _ScalaNothing: FullNameString = "scala.Nothing"
                      lazy val _ScalaAny    : FullNameString = "scala.Any"    // classOf returns java.lang.Object
  private[aptreflect] lazy val _ScalaAnyVal : FullNameString = "scala.AnyVal" // classOf returns java.lang.Object

  def isScalaAnyVal(name: FullNameString): Boolean = name == _ScalaAnyVal

  // ===========================================================================
  private[aptreflect] lazy val _EnumEntry: FullNameString = "enumeratum.EnumEntry".intern()

  // ===========================================================================
  private[aptreflect] lazy val _AptusEnumValue: FullNameString = classOf[aptus.aptdata.meta.basic.EnumValue].fullPath.intern()

  // ---------------------------------------------------------------------------
  private[aptreflect] lazy val _AptusDyn : FullNameString = "aptus.aptdata.sngl.Dyn"
  private[aptreflect] lazy val _AptusDyns: FullNameString = "aptus.aptdata.mult.list.Dyns"
  private[aptreflect] lazy val _AptusDynz: FullNameString = "aptus.aptdata.mult.iter.Dynz" }

// ===========================================================================
