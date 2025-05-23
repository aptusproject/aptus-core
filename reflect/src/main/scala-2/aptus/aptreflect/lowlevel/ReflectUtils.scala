package aptus
package aptreflect
package lowlevel

import scala.reflect.api

// ===========================================================================
private object ReflectUtils {

  private[aptreflect] def parseFields(tpe: UType): List[(String, UType)] =
      _methodSymbols(tpe)
        .map { method =>
          val name = method.name.decodedName.toString
          val tpe2 = method.typeSignature.resultType

          (name, tpe2) }

    // ---------------------------------------------------------------------------
    private def _methodSymbols(tpe: UType) =
      tpe
        .decls
        .filter((x: api.Symbols#SymbolApi) => x.isMethod)
        .map   (_.asMethod)
        .filter(_.isCaseAccessor)
        .toList

    // ---------------------------------------------------------------------------
    private[aptreflect] def methodSymbols(tpe: runiverse.Type) = // can't easily refactor with above, so at least keep them together at least
      tpe
        .decls
        .filter((x: api.Symbols#SymbolApi) => x.isMethod)
        .map   (_.asMethod)
        .filter(_.isCaseAccessor)
        .toList

  // ---------------------------------------------------------------------------
  private[aptreflect] def fieldTpes(tpe: runiverse.Type): Seq[runiverse.Type] = // can't easily refactor with above, so at least keep them together at least
    methodSymbols(tpe).map(_.typeSignature.resultType)

  // ===========================================================================
  /** enum must not be nested somehow */
  private[aptreflect] def enumValueNames(tpe: UType): Seq[String] =
    tpe
      .companion
      .members
      .filter { (symbol: api.Universe#Symbol) =>
        symbol.isPublic && symbol.isStatic && symbol.isModule }
      .map { (symbol: api.Universe#Symbol) =>
        symbol.name.decodedName.toString }
      .toList
.reverse /* TODO: always? */

}

// ===========================================================================
