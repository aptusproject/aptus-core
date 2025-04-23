package aptus
package aptdata
package static

import aptreflect.nodes.{TypeNode, TypeLeaf, TypeField}
import experimental.dyn.Dyn

// ===========================================================================
object DynTypeNode {

  def typeNode(value: TypeNode): Dyn =
        Dyn.dyn(
          "leaf" -> typeLeaf(value.leaf),
          "args" -> value.args.map(typeNode))

    // ---------------------------------------------------------------------------
    def typeLeaf(value: TypeLeaf): Dyn =
        Dyn.dyn(
          "name"        -> value.fullName.format,

          "dataClass"   -> value.dataClass,
          "enumValue"   -> value.enumValue,
          "bytes"       -> value.bytes,
          "inheritsSeq" -> value.inheritsSeq,

          "enumeratumValueNamesOpt" -> value.enumeratumValueNamesOpt,

          "fields" -> value.fields.map(typeField))

      // ---------------------------------------------------------------------------
      def typeField(value: TypeField): Dyn =
        Dyn.dyn(
          "key"  -> value.key,
          "node" -> value.typeNode.pipe(typeNode)) }

// ===========================================================================
