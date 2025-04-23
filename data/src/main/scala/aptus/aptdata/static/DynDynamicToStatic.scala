package aptus
package aptdata
package static

import aptreflect.lowlevel.ReflectionTypesAbstraction.{WTT, WeakTypeTag_}

// ===========================================================================
object DynDynamicToStatic {

  private[aptus] def toStatic[T <: Product : WTT]: (Cls, DynamicToStatic[Dyn]) = {
    val wtt = implicitly[WTT[T]]

    (wtt
      .typeNode
      .require( // excepts "unwrapped" data class, no Option, Seq, ...
        !_.leaf.fullName.startsWithScalaPackage,
        x => "E250415171031" -> x.leaf.fullName.format)
      .forceNestedClass,
     wtt
      .instantiatorOpt
      .get
      .pipe(new DynamicToStatic[Dyn](_, _ attemptKey _)))  } }

// ===========================================================================
