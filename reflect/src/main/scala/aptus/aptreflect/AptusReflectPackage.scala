package aptus

// ===========================================================================
package object aptreflect {
  private[aptreflect] type Container = aptus.aptdata.meta.schema.Container
  private[aptreflect] val  Container = aptus.aptdata.meta.schema.Container

  // ---------------------------------------------------------------------------
  // used throughout
  private[aptreflect] type TypeNode = aptreflect.nodes.TypeNode

  // ---------------------------------------------------------------------------
  /** eg "java.lang.File" */ type FullNameString = String /* favor FullyQualifiedName now (items: Seq[String]) */

  // ===========================================================================
  private[aptreflect] def isDataClass(
         caseClass              : Boolean,
         baseClassNames         : List[FullNameString],
         enumeratumValueNamesOpt: Option[Seq[String]],
         fullName               : names.FullyQualifiedName)
      : Boolean =
    /**/  caseClass &&
    /**/ !baseClassNames.exists(names.FullNameBuiltIns.isScalaAnyVal) &&
    /**/  enumeratumValueNamesOpt.isEmpty &&
    /**/ !fullName.startsWithScalaPackage &&
    /**/ !fullName.startsWithAptusPackage  /* exclude eg EnumValue, ... */ &&
    /**/ !fullName.startsWithGalliaPackage /* exclude eg BObj, ... */

  // ===========================================================================
  private[aptreflect] implicit class AptreflectAnything_[A](value: A) { // so as to not import chaining._ everywhere
    private[aptreflect] def tap    (f: A => Unit): A = { f(value); value }
    private[aptreflect] def pipe[B](f: A => B)   : B =   f(value) } }

// ===========================================================================
