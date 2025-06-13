package aptus
package aptreflect
package nodes

// ===========================================================================
case class TypeField(
      key     : String,
      typeNode: TypeNode) {
    def formatDebug: String = s"${key}: ${typeNode.formatDebug}"
    def formatDefault: String = toString }

  // ---------------------------------------------------------------------------
  object TypeField {
    def string (name: String) = TypeField(name, TypeNodeBuiltIns.ScalaString)
    def int    (name: String) = TypeField(name, TypeNodeBuiltIns.ScalaInt)
    def double (name: String) = TypeField(name, TypeNodeBuiltIns.ScalaDouble)
    def boolean(name: String) = TypeField(name, TypeNodeBuiltIns.ScalaBoolean) }

// ===========================================================================
