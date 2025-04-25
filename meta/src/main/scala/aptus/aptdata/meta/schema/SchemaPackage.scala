package aptus
package aptdata
package meta

import aptus.Seq_

// ===========================================================================
package object schema
  extends schema.ClsCompanionTrait /* eg: val schema: Cls = dyn.cls("name".string, "age".int) */
     with schema.FldCompanionTrait /* eg: val  field: Fld = "name".string */ {

  // ---------------------------------------------------------------------------
  type Key  = selectors.Key
  val  Key  = selectors.Key

  type Keyz = selectors.Keyz
  val  Keyz = selectors.Keyz

  type BKeyz = selectors.BKeyz
  val  BKeyz = selectors.BKeyz

  // ---------------------------------------------------------------------------
  type BKey = Symbol
  type SKey = String

  type Multiple = Boolean
  type Optional = Boolean

  type ClsName = String /* eg "Person" */
  type EnmName = String /* eg "Color"  */

  // ---------------------------------------------------------------------------
  private val UnionMessage =
      "limited support for union types (see t210125111338 and see https://github.com/galliaproject/gallia-docs/blob/master/union_types.md)"

    // ---------------------------------------------------------------------------
    def forceUnionOption[A](values: Seq[A]): Option[A] = {
      values match {
        case Nil       => None
        case Seq(sole) => Some(sole)
        case _         => aptus.unsupportedOperation(UnionMessage) } }

  // ---------------------------------------------------------------------------
  @pseudosealed trait HasValuePredicate {
    def valuePredicate: AnyValue => Boolean }

  // ---------------------------------------------------------------------------
  case class FldPair(field1: Fld, field2: Fld) {
    override def toString: String = formatDefault
      def formatDefault: String = Seq(field1.formatDefault, field2.formatDefault).section }

  // ===========================================================================
  implicit class AptusMetaContainer_(val diss: Container) extends AptusMetaContainer

    // ---------------------------------------------------------------------------
    trait AptusMetaContainer { val diss: Container

      def info(valueType: ValueType): Info =
        diss match {
          case Container._One => Info.one(valueType)
          case Container._Opt => Info.opt(valueType)
          case Container._Nes => Info.nes(valueType)
          case Container._Pes => Info.pes(valueType) }

      // ---------------------------------------------------------------------------
      def info1(valueType: ValueType): Info1 =
        diss match {
          case Container._One => Info1.one(valueType)
          case Container._Opt => Info1.opt(valueType)
          case Container._Nes => Info1.nes(valueType)
          case Container._Pes => Info1.pes(valueType) } } }

// ===========================================================================
