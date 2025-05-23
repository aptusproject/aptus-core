package aptus

// ===========================================================================
/* NOTE: none of those are enforced, they just act as documentation */
trait AptusAnnotations { import scala.annotation.StaticAnnotation

  /** to convey that something isn't meant to remain in production code */
  class fordevonly(val message: String = "") extends StaticAnnotation // TODO: actually enforce at build time (t201022170436)

  /** in cases where should be sealed but can't because we refactored some code to another file for clarity */
  class pseudosealed(val message: String = "") extends StaticAnnotation

  /** to convey something is intended to be public */
  class publik(val message: String = "") extends StaticAnnotation

  /** to convey something is final conceptually in the corner case of multiple inheritance */
  class finl(val message: String = "") extends StaticAnnotation

  /** to convey explicitly that something is intended to be overriden in some situations (as opposed to having forgotten to make it final). */
  class nonfinl     (val message: String = "") extends StaticAnnotation
  class nonovrd     (val message: String = "") extends StaticAnnotation
  class notsealed   (val message: String = "") extends StaticAnnotation
  class notprivate  (val message: String = "") extends StaticAnnotation
  class notprotected(val message: String = "") extends StaticAnnotation

  /** just to highlight it*/
  class abstrct (val message: String = "") extends StaticAnnotation

  /** to convey that the order of the following code is important */
  class ordermatters(val message: String = "") extends StaticAnnotation

  class notthreadsafe(val message: String = "") extends StaticAnnotation }

// ===========================================================================
