package aptustesting
package dyntest

import utils.CommonsMatrixUtils._

import utest._

// ===========================================================================
object DynTensorsTest extends TestSuite  {
  import aptus.aptdata.Dyn.dyn

  // ===========================================================================
  private val matrix = "matrix"
  private val tensor = "tensor"

  // ---------------------------------------------------------------------------
  private val m3x2 =
    _matrix(
      (1.1, 2.2),
      (3.3, 4.4),
      (5.5, 6.6))

  // ---------------------------------------------------------------------------
  private val m2x3 =
    _matrix(
      ("a", "b", "c"),
      ("d", "e", "f"))

  // ---------------------------------------------------------------------------
  private val tex2x4 =
    _tensor(
      _matrix(
        (0.111, 0.112, 0.113, 0.114),
        (0.121, 0.122, 0.123, 0.124)),
      _matrix(
        (0.211, 0.212, 0.213, 0.214),
        (0.221, 0.222, 0.223, 0.224)),
      _matrix(
        (0.311, 0.312, 0.313, 0.314),
        (0.321, 0.322, 0.323, 0.324)) )

  // ---------------------------------------------------------------------------
  val _m3x2 = m3x2.toRealMatrixCommons

  // ===========================================================================
  val tests = Tests {
    test(dyn(matrix -> m3x2).doubless(matrix).check(m3x2))

    // ---------------------------------------------------------------------------
    test(dyn(matrix ->   m3x2).formatCompactJson         .check(s"""{"$matrix":[[1.1,2.2],[3.3,4.4],[5.5,6.6]]}"""))
    test(dyn(matrix ->  _m3x2).formatCompactJson         .check(s"""{"$matrix":[[1.1,2.2],[3.3,4.4],[5.5,6.6]]}"""))
    test(dyn(matrix ->  _m3x2).transform(matrix).using(_.matrix.doubless)
                         .formatCompactJson         .check(s"""{"$matrix":[[1.1,2.2],[3.3,4.4],[5.5,6.6]]}"""))

    test(dyn(matrix ->   m2x3).formatCompactJson         .check(s"""{"$matrix":[["a","b","c"],["d","e","f"]]}"""))
    test(dyn(tensor -> tex2x4).formatCompactJson.take(50).check(s"""{"$tensor":[[[0.111,0.112,0.113,0.114],[0.121,0.122"""))

    // ===========================================================================
    // turns int matrix be turned into a double one
    test(dyn(matrix -> m3x2.toInts.toRealMatrixCommons).realMatrixCommons(matrix)
      .checkMatrix((1.0, 2.0), (3.0, 4.0), (5.0, 6.0) ))

    // ===========================================================================
    test(dyn(matrix ->  m3x2).transform(matrix).using(_.doubless.toRealMatrixCommons)
      .realMatrixCommons(matrix)
      .checkMatrix(m3x2))

    test(dyn(matrix -> _m3x2)
      .realMatrixCommons(matrix)
      .checkMatrix(m3x2))

    test(dyn(matrix -> _m3x2.transpose)
      .realMatrixCommons(matrix)
      .checkMatrix(
        (1.1, 3.3, 5.5),
        (2.2, 4.4, 6.6)))

    // ---------------------------------------------------------------------------
    test(dyn(matrix -> _matrix((1.1, 2.2), (3.3, 4.4)))
      .transform(matrix).using { _.doubless.toRealMatrixCommons }
      .transform(matrix).using { _.matrix.inverse }
      .realMatrixCommons(matrix)
      .checkMatrix(maxDecimals = 2,
        (-1.82, 0.91), (1.36, -0.45)))

    // ===========================================================================
    test(dyn(matrix -> _m3x2.scalarAdd(1.1))
      .realMatrixCommons(matrix)
      .checkMatrix(maxDecimals = 1,
          (2.2, 3.3),
          (4.4, 5.5),
          (6.6, 7.7))) } }

// ===========================================================================
