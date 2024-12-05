package gallia
package reflect

// ===========================================================================
case class TypeNode(leaf: TypeLeaf)

  // ---------------------------------------------------------------------------
  case class TypeLeaf(name: String) {
    def fullName: FullyQualifiedName =
      FullyQualifiedName(Seq(name)) }

// ---------------------------------------------------------------------------
case class FullyQualifiedName(values: Seq[String]) {
    def format: String = values.mkString(".") }

  // ---------------------------------------------------------------------------
  object FullyQualifiedName {
    def normalizeFullName(value: Any) = ??? }

// ===========================================================================