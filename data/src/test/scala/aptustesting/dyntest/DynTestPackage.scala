package aptustesting

import aptus.{Tuple2_, Tuple3_, Tuple4_, Double_}

// ===========================================================================
package object dyntest
    extends DynTestData
       with DynTestAliases
       with aptus.apttraits.AptusChaining
       with aptus.apttraits.AptusMinExtensions {
  import aptus.dyn._

  val _t = true
  val _f = false

  // ---------------------------------------------------------------------------
  type NakedValue = Any

  type Valew = aptus.aptdata.domain.valew.Valew
  val  Valew = aptus.aptdata.domain.valew.Valew

  type Seq2D[T] = aptus.dyn.Seq2D[T]
  type Seq3D[T] = aptus.dyn.Seq3D[T]

  // ===========================================================================
  implicit class StringSeq2D_(valuess: Seq2D[String]) { // TODO: toaptus
    def formatTable: String = utils.TableUtils.formatTable(valuess) }

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
  implicit class Cls_(u: aptus.aptdata.meta.schema.Cls) {
    def toOptional(skey: String): aptus.aptdata.meta.schema.Cls = u.replaceExactlyOneItem(_.skey == skey)(_.toOptional) }

  // ===========================================================================
  implicit class TryTests_[T](val value: Try[T]) extends TestsTrait[Try[T]] { val format = _.toString /* TODO */
    def checkFailure: Unit = _check(_.isFailure) // favor one with explicit error rather

    // ---------------------------------------------------------------------------
    def check(err: HasErrorId)                     : Unit = _check { DynTestUtils.checkError(err, superTypeOpt = None)(_).get }
    def check(err: HasErrorId, expected: SuperType): Unit = _check { DynTestUtils.checkError(err, expected.in.some)   (_).get } // TODO: a actual/expected version

    // ---------------------------------------------------------------------------
    def checkGuaranteeError(): Unit = checkIllegalArgument("E241118160516") // TODO: use class version

    def checkIllegalArgument(msg1: String, more: String*) = checkException(classOf[IllegalArgumentException], msg1, more:_*)
    def checkIllegalState   (msg1: String, more: String*) = checkException(classOf[IllegalStateException]   , msg1, more:_*)

    // ---------------------------------------------------------------------------
    def checkException(klass: Class[_] /* use tag rather */)                             : Unit = _check(_.failed.get.getClass == klass)
    def checkException(klass: Class[_] /* use tag rather */, msg1: String, more: String*): Unit = _check { x =>
      val throwable = x.failed.get

      throwable.getClass == klass &&
      (msg1 +: more).forall(msg => throwable.getMessage.contains(msg)) }

    // ---------------------------------------------------------------------------
    private def _check(pred: Try[T] => Boolean): Unit = { assert(pred(value), value) } }

  // ===========================================================================
  implicit class AnythingTests_[T](value: T) {
      def noop(f: T => T)   : Unit = { f(value).check(value ) }
      def check(expected: T): Unit = {
        if(value != expected) {
          val msg: String =
            formatValue(value)   .act(value).newline.append(
            formatValue(expected).exp(expected))
          aptus.illegalState(msg) } } }

    // ---------------------------------------------------------------------------
    implicit class AnythingsTests_[T](values: Seq[T]) {
      def check0()                                    : Unit = _check(Nil)
      def check1(value: T)                            : Unit = _check(Seq(value))
      def checkN(expected1: T, expected2: T, more: T*): Unit = _check((Seq(expected1, expected2) ++ more).toList)

      // ---------------------------------------------------------------------------
      private def _check(expected: Seq[T]): Unit = {
        if(values != expected) {
          val msg: String =
            values  .map(formatValue2).joinln.act.newline.append(
            expected.map(formatValue2).joinln.exp)
          aptus.illegalState(msg) } } }

    // ---------------------------------------------------------------------------
    private implicit class String__(diss: String) {
      def act = diss.prepend(  "actual\n\n").newline
      def exp = diss.prepend("expected\n\n").newline

      // ---------------------------------------------------------------------------
      def act(value: Any) = diss.prepend(  s"actual: ${value.getClass}\n\n").newline
      def exp(value: Any) = diss.prepend(s"expected: ${value.getClass}\n\n").newline }

    // ---------------------------------------------------------------------------
    private def formatValue (value: Any): String = Option(value).map(_.toString).getOrElse("[null!]")
    private def formatValue2(value: Any): String = formatValue(value).append(s" (${value.getClass.getSimpleName})")

  // ===========================================================================
  implicit class BooleanTests_(val value: Boolean) extends TestsTrait[Boolean] {
    val format = _.toString
    def checkTrue () = check(true)
    def checkFalse() = check(false) }

  // ---------------------------------------------------------------------------
  implicit class StringTests_(val value: String) extends TestsTrait[String] { val format = identity; def key: Key = Key.from(value) }
  implicit class DynTests_   (val value: Dyn)    extends TestsTrait[Dyn]    { val format = _.formatPrettyJson }
  implicit class DynsTests_  (val value: Dyns)   extends TestsTrait[Dyns]   { val format = _.formatPrettyJson }

  // ---------------------------------------------------------------------------
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
    def check          (expected: T): Unit = DynTestUtils.checkResult(value, expected)(format)
    def checkCharArrays(expected: T): Unit = DynTestUtils.checkResultCharArray(value, expected)(format) } }

// ===========================================================================
