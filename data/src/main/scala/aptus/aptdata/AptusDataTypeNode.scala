package aptus
package aptdata

import aptreflect.nodes.{TypeLeaf, TypeNode}
import aptus.aptdata.static.DynTypeNode
import experimental.dyn.Dyn

// ===========================================================================
trait AptusDataTypeNode extends meta.AptusMetaTypeNode {
    protected val diss: TypeNode

    // ---------------------------------------------------------------------------
    def dyn: Dyn = DynTypeNode.typeNode(diss) // 250416100113

    // ---------------------------------------------------------------------------
    def formatDefault    : String = dyn.formatPrettyJson

    def formatPrettyJson : String = dyn.formatPrettyJson
    def formatCompactJson: String = dyn.formatCompactJson }

  // ===========================================================================
  trait AptusDataTypeLeaf {
    protected val diss: TypeLeaf

    def dyn: Dyn = DynTypeNode.typeLeaf(diss)

    // ---------------------------------------------------------------------------
    def formatDefault    : String = dyn.formatPrettyJson

    def formatPrettyJson : String = dyn.formatPrettyJson
    def formatCompactJson: String = dyn.formatCompactJson }

// ===========================================================================
