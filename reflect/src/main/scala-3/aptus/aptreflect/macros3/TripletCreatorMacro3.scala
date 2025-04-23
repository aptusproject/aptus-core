package aptus
package aptreflect
package macros3

// ===========================================================================
object PairCreatorMacro3 {
  import scala.quoted.{Quotes, quotes, Type, Expr}

  // ---------------------------------------------------------------------------
  def apply[T: Type](using q: Quotes): Expr[(TypeNode, Instantiator, scala.reflect.ClassTag[T])] = {
    import quotes.reflect.*

    val tpe      = TypeRepr.of[T]
    val typeNode = TypeLeafParserMacro3.rec(using q)(tpe)

    '{ (${TypeNodeToExpr.typeNodeToExpr(typeNode)},
        ${InstantiatorCreatorMacro3.rec  (using q)(tpe)(typeNode).asExprOf[Instantiator]},

        // try with: '{ ClassTag[T](classOf[T]) }
        ${ClassTagCreatorMacro3    .apply(using q)(tpe)          .asExprOf[scala.reflect.ClassTag[T]] } ) } }
}

// ===========================================================================
