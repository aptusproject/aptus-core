package aptustesting
package dyntest
package utils

import aptus.Double_

// ===========================================================================
/* just to give a starting point */
object CommonsMatrixUtils {
  import scala.reflect.{ClassTag => CT}
  import org.apache.commons.math3.linear

  // ---------------------------------------------------------------------------
  type RealMatrixCommons = linear.RealMatrix

  type   Seq2d[T] = Seq  [Seq  [T]]
  type Array2D[T] = Array[Array[T]]

  // ===========================================================================
  /** most operations are available direction on RealMatrix (eg transpose, ...) */
  implicit class RealMatrixCommons_(rm: RealMatrixCommons) {
    def transformCells(f: Double => Double): RealMatrixCommons = rm.getData.transformCells(f).toRealMatrixCommons

    def maxDecimals(value: Int): RealMatrixCommons = transformCells(_.maxDecimals /* from aptus */(value))
    def toIntegers             : RealMatrixCommons = transformCells(_.toInt)

    def doubless: Seq2d[Double] = rm.getData.toLists

    def inverse: RealMatrixCommons = linear.MatrixUtils.inverse(rm /* must be square */) }

  // ===========================================================================
  implicit class Array2D_[T: CT](valuess: Array2D[T]) {
      def toLists: Seq2d[T] = valuess.map(_.toList).toList
      def transformCells[U: CT](f: T => U): Array2D[U] = valuess.map(_.map(f)) }

    // ---------------------------------------------------------------------------
    implicit class Seq2D_[T: CT](valuess: Seq2d[T]) {
      def toArrays: Array2D[T] = valuess.map(_.toArray).toArray
      def transformCells[U](f: T => U): Seq2d[U] = valuess.map(_.map(f)) }

  // ===========================================================================
  implicit class DoubleArray2D_(valuess: Array2D[Double]) {
      def toInts: Array2D[Int] = valuess.map(_.map(_.toInt))
      def toRealMatrixCommons: RealMatrixCommons = linear.MatrixUtils.createRealMatrix(valuess) }

    // ---------------------------------------------------------------------------
    implicit class IntArray2D_(valuess: Array2D[Int]) {
      def toDoubles: Array2D[Double] = valuess.map(_.map(_.toDouble))
      def toRealMatrixCommons: RealMatrixCommons = valuess.transformCells(_.toDouble).toRealMatrixCommons }

    // ===========================================================================
    implicit class DoubleSeq2D_(valuess: Seq2d[Double]) {
      def toInts: Seq2d[Int] = valuess.map(_.map(_.toInt))
      def toRealMatrixCommons: RealMatrixCommons = valuess.toArrays.toRealMatrixCommons }

    // ---------------------------------------------------------------------------
    implicit class IntSeq2D_(valuess: Seq2d[Int]) {
      def toDoubles: Seq2d[Double] = valuess.map(_.map(_.toDouble))
      def toRealMatrixCommons: RealMatrixCommons = valuess.transformCells(_.toDouble).toRealMatrixCommons } }

// ===========================================================================
