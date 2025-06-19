package aptustesting
package dyntest
package utils

// ===========================================================================
trait DynTesting
    extends aptustesting.utils.TestExtensions
       with DynTestData
       with DynTestAliases
       with DynMatrixUtils
       with aptus.apttraits.AptusChaining
       with aptus.apttraits.AptusMinExtensions
       with aptus.apttraits.AptusBooleanShorthands {
  import aptus.dyn._

  // ===========================================================================
  trait TestsTrait[T] {
    protected val value : T
    val format: T => String
    def check          (expected: T): Unit = aptustesting.utils.TestUtils.checkResult         (value, expected)(format)
    def checkCharArrays(expected: T): Unit = aptustesting.utils.TestUtils.checkResultCharArray(value, expected)(format) }

  // ===========================================================================
  implicit class Cls_(diss: aptus.aptdata.meta.schema.Cls) {
    def toOptional(skey: String): aptus.aptdata.meta.schema.Cls =
      diss.replaceExactlyOneItem(_.skey == skey)(_.toOptional) }

  // ===========================================================================
  implicit class DynTryTests_[T](val value: Try[T]) {
    private def _check(pred: Try[T] => Boolean): Unit = { assert(pred(value), value) }

    // ---------------------------------------------------------------------------
    def check(err: HasErrorId)                     : Unit = _check { checkError(err, superTypeOpt = None)(_).get }
    def check(err: HasErrorId, expected: SuperType): Unit = _check { checkError(err, expected.in.some)   (_).get } // TODO: a actual/expected version

    // ---------------------------------------------------------------------------
    private def checkError[T](err: HasErrorId, superTypeOpt: Option[SuperType]): Try[T] => Try[Boolean] =
      _ .failed
        .map { _.getMessage.pipe { msg =>
          msg.contains(err.id) &&
          superTypeOpt.map(_.formatErrorMessageString).forall(msg.contains)} } }

  // ===========================================================================
  implicit class DynString_(val value: String) extends TestsTrait[String] { val format = identity; def key: Key = Key.from(value) }
  implicit class Dyn_      (val value: Dyn)    extends TestsTrait[Dyn]    { val format = _.formatPrettyJson }
  implicit class Dyns_     (val value: Dyns)   extends TestsTrait[Dyns]   { val format = _.formatPrettyJson
    def testDynz(f: Dynz => Dynz): Dyns = value.asDynz.pipe(f).asList }

  // ---------------------------------------------------------------------------
  implicit class Dynz_(val value: Dynz) {
    def check(that: Dynz): Unit =
      aptustesting.utils.TestUtils.checkResult(
        value.asDyns, that.asDyns)(
        _.formatPrettyJson) } }

// ===========================================================================


