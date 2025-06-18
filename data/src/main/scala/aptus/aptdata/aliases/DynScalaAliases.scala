package aptus
package aptdata
package aliases

// ===========================================================================
private[aptdata] trait DynScalaAliases {
  type   Seq2D[T] =   Seq[  Seq  [T]]
  type   Seq3D[T] =   Seq[  Seq2D[T]]
  type   Seq4D[T] =   Seq[  Seq3D[T]]

  type Array2D[T] = Array[Array[T]]

  type RealMatrixCommons = org.apache.commons.math3.linear.RealMatrix

  private[aptdata] type ListMap[K, V] = collection.immutable.ListMap[K, V] }

// ===========================================================================