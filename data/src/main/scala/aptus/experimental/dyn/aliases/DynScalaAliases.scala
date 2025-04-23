package aptus
package experimental
package dyn
package aliases

// ===========================================================================
private[dyn] trait DynScalaAliases {
  type   Seq2D[T] =   Seq[  Seq[T]]
  type Array2D[T] = Array[Array[T]]

  type RealMatrixCommons = org.apache.commons.math3.linear.RealMatrix

  private[dyn] type ListMap[K, V] = collection.immutable.ListMap[K, V] }

// ===========================================================================