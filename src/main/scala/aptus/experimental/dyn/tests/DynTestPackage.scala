package aptus
package experimental

// ===========================================================================
package object dyntest
    extends DynTestData
       with DynTestAliases
       with aptus.apttraits.AptusChaining {
  import dyn._

  // ---------------------------------------------------------------------------
implicit class StringSeq2D_(valuess: Seq2D[String]) { // TODO: toaptus
    def formatTable: String = TableUtils.formatTable(valuess) }

  // ---------------------------------------------------------------------------
  def _matrix[T](tuple1: (T, T),       more: (T, T)*)      : Seq2D[T] = (tuple1 +: more).map(_.toSeq)
  def _matrix[T](tuple1: (T, T, T),    more: (T, T, T)*)   : Seq2D[T] = (tuple1 +: more).map(_.toSeq)
  def _matrix[T](tuple1: (T, T, T, T), more: (T, T, T, T)*): Seq2D[T] = (tuple1 +: more).map(_.toSeq)

  def _tensor[T](matrix1: Seq2D[T], more: Seq2D[T]*): Seq[Seq2D[T]] = (matrix1 +: more).map(_.toSeq)

  // ---------------------------------------------------------------------------
  def msg(clazz: Class[_]): String = s"OK: ${clazz.getSimpleName.stripSuffix("$")}"

  // ===========================================================================
  implicit class DynUnit_(u: Unit) {
    def |>(key: Key) : Path = Path(parent = Nil, key) }

  // ---------------------------------------------------------------------------
  implicit class Dyns_(u: Dyns) {
    def testDynz(f: Dynz => Dynz): Dyns = u.asDynz.pipe(f).asList }

  // ---------------------------------------------------------------------------
  implicit class Cls_(u: aptdata.meta.schema.Cls) {
    def toOptional(skey: String): aptdata.meta.schema.Cls = u.replaceExactlyOneItem(_.skey == skey)(_.toOptional) }

  // ===========================================================================
  implicit class TryTests_[T](val value: Try[T]) extends TestsTrait[Try[T]] { val format = _.toString /* TODO */
    def checkFailure: Unit = _check(_.isFailure) // favor one with explicit error rather

    def check(err: HasErrorId)                     : Unit = _check { DynTestUtils.checkError(err, superTypeOpt = None)(_).get }
    def check(err: HasErrorId, expected: SuperType): Unit = _check { DynTestUtils.checkError(err, expected.in.some)   (_).get } // TODO: a actual/expected version

    def checkException(klass: Class[_] /* use tag rather */): Unit = _check(_.failed.get.getClass == klass)

    // ---------------------------------------------------------------------------
    private def _check(pred: Try[T] => Boolean): Unit = { assert(pred(value), value) } }

  // ===========================================================================
  implicit class AnythingTests_[T](value: T) {
    def check(expected: T)        = { assert(value == expected, value -> expected) }
    def check(pred: T => Boolean) = { assert(pred(value), value) } }

  // ===========================================================================
  implicit class BooleanTests_(val value: Boolean) extends TestsTrait[Boolean] {
    val format = _.toString
    def checkTrue () = check(true)
    def checkFalse() = check(false) }

  // ---------------------------------------------------------------------------
  implicit class StringTests_(val value: String) extends TestsTrait[String] { val format = identity }
  implicit class DynTests_   (val value: Dyn)    extends TestsTrait[Dyn]    { val format = _.formatPrettyJson }
  implicit class DynsTests_  (val value: Dyns)   extends TestsTrait[Dyns]   { val format = _.formatPrettyJson }

  // ---------------------------------------------------------------------------
  implicit class RealMatrixCommons__(val value: RealMatrixCommons) extends TestsTrait[RealMatrixCommons] {
    import CommonsMatrixUtils._
    override val format = _.doubless.transformCells(_.formatExplicit).formatTable
    private type T = Double

    // ---------------------------------------------------------------------------
    def checkMatrix(expected: Seq2D[Double]): Unit = check(expected.toRealMatrixCommons)

    // ---------------------------------------------------------------------------
    def checkMatrix                  (tuple1: (T, T)   , more: (T, T)*)   : Unit = value                         .check(_matrix(tuple1, more:_*).toRealMatrixCommons)
    def checkMatrix(maxDecimals: Int, tuple1: (T, T)   , more: (T, T)*)   : Unit = value.maxDecimals(maxDecimals).check(_matrix(tuple1, more:_*).toRealMatrixCommons)
    def checkMatrix                  (tuple1: (T, T, T), more: (T, T, T)*): Unit = value                         .check(_matrix(tuple1, more:_*).toRealMatrixCommons)
    def checkMatrix(maxDecimals: Int, tuple1: (T, T, T), more: (T, T, T)*): Unit = value.maxDecimals(maxDecimals).check(_matrix(tuple1, more:_*).toRealMatrixCommons) }

  // ---------------------------------------------------------------------------
  implicit class DynzTests_  (val value: Dynz) {
    def check(that: Dynz): Unit =
      DynTestUtils.checkResult(
        value.asDyns, that.asDyns)(
        _.formatPrettyJson) }

  // ===========================================================================
  trait TestsTrait[T] {
    val value : T
    val format: T => String
    def check(expected: T): Unit =
      DynTestUtils.checkResult(value, expected)(format) } }

// ===========================================================================