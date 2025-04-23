package aptus
package aptreflect
package lowlevel

import scala.reflect.ClassTag

// ===========================================================================
object ReflectionTypesAbstraction extends ReflectionTypesAbstraction

// ---------------------------------------------------------------------------
/** differs based on 2.x vs 3.x */
trait ReflectionTypesAbstraction {

  /** stands for WeakTypeTag in scala 2.x, has a life of its own in scala 3.x */
  type WTT[T] = scala.reflect.runtime.universe.WeakTypeTag[T]

  // ---------------------------------------------------------------------------
  // don't rely on implicits for Any
  val AnyWTT: WTT[scala.Any] = scala.reflect.runtime.universe.weakTypeTag[scala.Any]

  // ===========================================================================
  /** only for scala 2... */
  implicit class WeakTypeTag_[T: WTT](wtt: WTT[T]) {
    def typeNode       : TypeNode             = TypeLeafParserRuntime2.parseTypeNode[T]
    def ctag           : ClassTag[T]          = runtimeClassTag[T]
    def instantiatorOpt: Option[Instantiator] = Some(runtimeInstantiator[T](to = typeNode)) }

  // ===========================================================================
  // until macro versions are ready for scala 2 (t23112114209)
  private def runtimeInstantiator[T: WTT](to: TypeNode): Instantiator = {
      if(!to.isContainedDataClass && !to.isContainedEnumeratum) null //FIXME
      else if (!to.isContainedDataClass)
        Instantiator.enumeratumWithName[String](
          f = x => CompanionReflection[T](methodName = "withName")(x))
      else if (to.isOptionOfSeq)        RuntimeInstantiatorCreator.fromFirstTypeArgFirstTypeArg[T] /* eg Option[Seq[MyCc]] */
      else if (to.isSeq || to.isOption) RuntimeInstantiatorCreator.fromFirstTypeArg            [T] /* eg Option[    MyCc ] */
      else                              RuntimeInstantiatorCreator.fromTypeDirectly            [T] /* eg            MyCc   */ }

  // ===========================================================================
  // see https://stackoverflow.com/questions/18729321/how-to-get-classtag-form-typetag-or-both-at-same-time
  private def runtimeClassTag[T : WTT]: ClassTag[T] = {
    val tag = runiverse.weakTypeTag[T]
    ClassTag[T](
      tag.mirror.runtimeClass(tag.tpe)) } }

// ===========================================================================
