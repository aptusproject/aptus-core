package gallia
package adaptor

// ===========================================================================
object GalliaAnnotations extends GalliaAnnotations

  trait GalliaAnnotations {
    /*private[dyn]*/ class        TypeMatching(val message: String = "") extends scala.annotation.StaticAnnotation
    /*private[dyn]*/ class PartialTypeMatching(val message: String = "") extends scala.annotation.StaticAnnotation
    /*private[dyn]*/ class NumberAbstraction  (val message: String = "") extends scala.annotation.StaticAnnotation }

// ===========================================================================