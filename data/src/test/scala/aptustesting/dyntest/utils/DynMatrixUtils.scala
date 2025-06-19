package aptustesting
package dyntest
package utils

import aptus.{Double_, Tuple2_, Tuple3_, Tuple4_}

// ===========================================================================
trait DynMatrixUtils {
  def _matrix[T](tuple1: (T, T),       more: (T, T)*)      : Seq2D[T] = (tuple1 +: more).map(_.toSeq)
  def _matrix[T](tuple1: (T, T, T),    more: (T, T, T)*)   : Seq2D[T] = (tuple1 +: more).map(_.toSeq)
  def _matrix[T](tuple1: (T, T, T, T), more: (T, T, T, T)*): Seq2D[T] = (tuple1 +: more).map(_.toSeq)

  def _tensor[T](matrix1: Seq2D[T], more: Seq2D[T]*): Seq[Seq2D[T]] = (matrix1 +: more).map(_.toSeq)

  // ===========================================================================
  private implicit class StringSeq2D_(valuess: Seq2D[String]) { // TODO: toaptus
    def formatTable: String = utils.TableUtils.formatTable(valuess) }

  // ===========================================================================
  implicit class RealMatrixCommons__(val value: RealMatrixCommons) extends TestsTrait[RealMatrixCommons] {
    import utils.CommonsMatrixUtils._
    override val format = _.doubless.transformCells(_.formatExplicit).formatTable
    private type T = Double

    // ---------------------------------------------------------------------------
    def checkMatrix(expected: Seq2D[Double]): Unit = check(expected.toRealMatrixCommons)

    // ---------------------------------------------------------------------------
    def checkMatrix                  (tuple1: (T, T)   , more: (T, T)*)   : Unit = value                         .check(_matrix(tuple1, more:_*).toRealMatrixCommons)
    def checkMatrix(maxDecimals: Int, tuple1: (T, T)   , more: (T, T)*)   : Unit = value.maxDecimals(maxDecimals).check(_matrix(tuple1, more:_*).toRealMatrixCommons)
    def checkMatrix                  (tuple1: (T, T, T), more: (T, T, T)*): Unit = value                         .check(_matrix(tuple1, more:_*).toRealMatrixCommons)
    def checkMatrix(maxDecimals: Int, tuple1: (T, T, T), more: (T, T, T)*): Unit = value.maxDecimals(maxDecimals).check(_matrix(tuple1, more:_*).toRealMatrixCommons) } }

// ===========================================================================