package gallia
package adaptor

import aptus.apttraits.AptusChaining
import aptus.experimental.dyn.meta._
import aptus.experimental.dyn.domain.Entry
import aptus.experimental.dyn.data.sngl.Dyn

// ===========================================================================
trait Foo2 extends GalliaCommonAliases with AptusChaining
trait Foo3 extends GalliaCommonAliases with GalliaBasicAliases

// ===========================================================================
object GalliaAdaptor0
    extends GalliaAdaptor
       with GalliaAnnotations
       with GalliaCommonAliases
       with GalliaBasicAliases

// ---------------------------------------------------------------------------
object GalliaAdaptor
  extends GalliaAdaptor
     with AptusChaining

// ===========================================================================
trait GalliaAdaptor
    extends GalliaAnnotations
       with GalliaCommonAliases
       with GalliaBasicAliases {

  // tables
  val DefaultNullValue      = ""
  val DefaultArraySeparator = ","

  // ===========================================================================
  /*private[gallia] */implicit class Map__[K, V](map: Map[K, V]) {
    def mapValues0[V2](f: V => V2): Map[K, V2] = map.map { case (k, v) => k -> f(v) } }

  // ===========================================================================
  // reflect
  type Container = reflect.Container
  val  Container = reflect.Container

    // ---------------------------------------------------------------------------
    implicit class Container_(container: Container) {
      def info(valueType: ValueType): Info =
        container match {
          case Container._One => Info.one(valueType)
          case Container._Opt => Info.opt(valueType)
          case Container._Nes => Info.nes(valueType)
          case Container._Pes => Info.pes(valueType) } }

  // ===========================================================================
  // meta: TODO: move some of those to actual

  // ---------------------------------------------------------------------------
  implicit class Fld_(val u: Fld) extends adaptor.GalliaFld_
  implicit class Info_(val u: Info) extends adaptor.GalliaInfo_
  implicit class SubInfo_(val u: SubInfo) extends adaptor.GalliaSubInfo_

  //TODO: case _: BasicType._Enm           => BasicType._Enm.parseString(value)

  // ---------------------------------------------------------------------------
  import aptus.Seq_
  case class FldPair(field1: Fld, field2: Fld) {
    override def toString: String = formatDefault
      def formatDefault: String = Seq(field1.formatDefault, field2.formatDefault).section }

  // ===========================================================================
  def toDouble(value: Any): Double =
    value match { // FIXME: t241203145950
      case x: Double => x
      case x: Int    => x.toDouble
      case _ => BasicType._Int.parseAnyToDouble(value) }

  // ===========================================================================
  val _Single   = false
  val _Multiple = true

  val _Optional = true
  val _Required = false

  // ---------------------------------------------------------------------------
  implicit class Objs_(u: Objs) {
    def consumeSelfClosing: List[Obj] =
      u.valueList.toList }
  }

// ===========================================================================
trait GalliaBasicAliases {
  type BasicType = _root_.gallia.basic.BasicType
  val  BasicType = _root_.gallia.basic.BasicType

  val BasicTypeUtils = _root_.gallia.basic.BasicTypeUtils

  val _LocalDate     = _root_.gallia.basic.BasicType._Date
  val _LocalDateTime = _root_.gallia.basic.BasicType._DateTime }

// ===========================================================================
trait GalliaCommonAliases {
  type Cls = aptus.experimental.dyn.meta.Cls
  type Fld = aptus.experimental.dyn.meta.Fld

  type AnyValue = Any

  // ---------------------------------------------------------------------------
  object gallia {
    def obj(x: Seq[(String, AnyValue)]): Dyn = x.map(Entry.fromGallia2).pipe(Dyn.build) }

  // ---------------------------------------------------------------------------
  type Obj = aptus.experimental.dyn.data.sngl.Dyn
  val  Obj = aptus.experimental.dyn.data.sngl.Dyn

  type Objs = aptus.experimental.dyn.data.mult.list.Dyns
  val  Objs = aptus.experimental.dyn.data.mult.list.Dyns }

// ===========================================================================
object GalliaSymbolKey {
  type Key = Symbol

  implicit def _to(value: Symbol): String = value.name }

// ===========================================================================
object GalliaDKey { import aptus.experimental.dyn._
  type DKey = aptus.experimental.dyn.domain.selectors.Key
  val  DKey = aptus.experimental.dyn.domain.selectors.Key

  // ---------------------------------------------------------------------------
  /*private[dyn]*/ def _tmp(f: Seq[String] => Seq[String]): Seq[DKey] => Seq[DKey] = (keys: Seq[DKey]) =>
    keys.map(_.name).pipe(f).map(Symbol.apply).map(DKey.apply) }

// ===========================================================================