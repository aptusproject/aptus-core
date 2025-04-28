package aptus
package aptdata
package static

import aptreflect.nodes.TypeNode
import aptreflect.lowlevel.ReflectionTypesAbstraction.{WTT, WeakTypeTag_}

// ===========================================================================
object DynStaticToDynamic extends DynStaticToDynamic

// ---------------------------------------------------------------------------
trait DynStaticToDynamic {

  private val staticToDynamic = new StaticToDynamic[Dyn](
    normalizeEntry = (k: BKey) => (v: AnyValue) => Some.apply((k, v)),
    builder        = (e: Seq[(BKey, AnyValue)]) => e.toList.map(Entry._fromSymbol).pipe(Dyn.build) )

  // ===========================================================================
  implicit class Product_[T <: Product : WTT](diss: T) {
    private lazy val tn: TypeNode = implicitly[WTT[T]].typeNode

    // ---------------------------------------------------------------------------
    def cls      : Cls = tn.forceNestedClass
    def toDynamic: Dyn = tn.forceNonBObjInfo.pipe(staticToDynamic(diss)).asInstanceOf[Dyn] }

  // ---------------------------------------------------------------------------
  implicit class ProductList_              [T <: Product : WTT](val values: List[T])               extends Products_[T](CloseabledIterator.fromSeq        (values), implicitly[WTT[T]].typeNode)
  implicit class ProductIterator_          [T <: Product : WTT](val values: Iterator[T])           extends Products_[T](CloseabledIterator.fromUncloseable(values), implicitly[WTT[T]].typeNode)
  implicit class ProductCloseabledIterator_[T <: Product : WTT](val values: CloseabledIterator[T]) extends Products_[T](values,                                     implicitly[WTT[T]].typeNode)

    // ---------------------------------------------------------------------------
    abstract class Products_[T <: Product](
                  val valuesIterator: CloseabledIterator[T],
        protected val tn            : TypeNode) {
      def toDynamic: Dyns = valuesIterator.map { x =>
        tn.forceNonBObjInfo.pipe(staticToDynamic(x)).asInstanceOf[Dyn] /* TODO: error msg */ }.consumeAll().pipe(x => Dyns.build(x)) } }

// ===========================================================================
