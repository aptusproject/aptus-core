package aptus

import aptus.aptreflect.nodes.{TypeLeaf, TypeNode}

// ===========================================================================
package object aptdata
    extends AptdataAnything
       with AptusDataTraits {

  /* indirection so TypeNode doesn't directly refer to Dyn */
  implicit class AptusDataTypeNode_(protected val diss: TypeNode) extends AptusDataTypeNode

  /* indirection so TypeLeaf doesn't directly refer to Dyn */
  implicit class AptusDataTypeLeaf_(protected val diss: TypeLeaf) extends AptusDataTypeLeaf }

// ===========================================================================