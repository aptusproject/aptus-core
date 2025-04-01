package aptus
package aptdata
package meta

import aptus.Seq_

// ===========================================================================
package object schema
  extends schema.ClsCompanionTrait /* eg: val schema: Cls = dyn.cls("name".string, "age".int) */
     with schema.FldCompanionTrait /* eg: val  field: Fld = "name".string */ {

  // ---------------------------------------------------------------------------
  type Key = selectors.Key
  val  Key = selectors.Key

  // ---------------------------------------------------------------------------
  type BKey = Symbol
  type SKey = String
  type Keys = Seq[Key]

  type AnyValue = Any

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
      def formatDefault: String = Seq(field1.formatDefault, field2.formatDefault).section } }

// ===========================================================================
