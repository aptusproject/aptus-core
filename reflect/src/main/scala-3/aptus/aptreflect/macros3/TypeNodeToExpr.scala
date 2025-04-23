package aptus
package aptreflect
package macros3

import scala.quoted.{Quotes, Expr, ToExpr}
import nodes.{TypeNode, TypeLeaf, TypeField}
import names.FullyQualifiedName

// ===========================================================================
object TypeNodeToExpr {

  given ToExpr[TypeField] = new ToExpr[TypeField] {
    def apply(x: TypeField)(using Quotes) =
      fieldToExpr(x) }

  // ---------------------------------------------------------------------------
  given ToExpr[FullyQualifiedName] = new ToExpr[FullyQualifiedName] {
    def apply(x: FullyQualifiedName)(using Quotes) =
      fullyQualifiedNameToExpr(x) }

  // ---------------------------------------------------------------------------
  given ToExpr[TypeLeaf] = new ToExpr[TypeLeaf] {
    def apply(x: TypeLeaf)(using Quotes) =
      typeLeafToExpr(x) }

  // ---------------------------------------------------------------------------
  given ToExpr[TypeNode] = new ToExpr[TypeNode] {
    def apply(x: TypeNode)(using Quotes) =
      typeNodeToExpr(x) }

  // ===========================================================================
  def typeNodeToExpr(x: TypeNode)(using Quotes) =
      '{TypeNode(
          leaf = ${Expr(x.leaf)},
          args = ${Expr(x.args)}) }

    // ---------------------------------------------------------------------------
    private def fullyQualifiedNameToExpr(x: FullyQualifiedName)(using Quotes) =
      '{FullyQualifiedName(items = ${Expr(x.items)}) }

    // ---------------------------------------------------------------------------
    private def typeLeafToExpr(x: TypeLeaf)(using Quotes) =
      '{TypeLeaf(
          fullName    = ${Expr(x.fullName)},

          dataClass   = ${Expr(x.dataClass)},
          enumValue   = ${Expr(x.enumValue)},
          bytes       = ${Expr(x.bytes)},
          inheritsSeq = ${Expr(x.inheritsSeq)},

          enumeratumValueNamesOpt = ${Expr(x.enumeratumValueNamesOpt)},
          fields = ${Expr(x.fields)}) }

    // ---------------------------------------------------------------------------
    private def fieldToExpr(x: TypeField)(using Quotes) =
      '{TypeField(
        key      = ${Expr(x.key)},
        typeNode = ${Expr(x.typeNode)})} }

// ===========================================================================
