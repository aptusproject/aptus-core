package aptus
package aptdata

import aptus.aptreflect.nodes.TypeNode

// ===========================================================================
package object meta
    extends AptdataAnything
       with AptusDataTraits {

  private[aptdata] type AnyValue = Any

  // ---------------------------------------------------------------------------
  implicit class AptusMetaTypeNode_(protected val diss: TypeNode) extends AptusMetaTypeNode

    // ---------------------------------------------------------------------------
    /* indirection so TypeNode doesn't directly refer to Cls & Info */
    trait AptusMetaTypeNode { protected val diss: TypeNode
      def forceNestedClass: Cls  = meta.schema.TypeNodeToSchemaUtils.forceNestedClass(diss.leaf)
      def forceNonBObjInfo: Info = meta.schema.TypeNodeToSchemaUtils.forceInfo(diss) } }

// ===========================================================================