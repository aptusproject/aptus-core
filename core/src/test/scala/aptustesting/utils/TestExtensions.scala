package aptustesting
package utils

import scala.util._
import aptus._

// ===========================================================================
trait TestExtensions { import TestExtensionsUtils._

  // ---------------------------------------------------------------------------
  implicit class AptusTestingTry_[T](protected val value: Try[T]) {
    private def _check(pred: Try[T] => Boolean): Unit = { assert(pred(value), value) }
    def checkFailure: Unit = _check(_.isFailure) // favor one with explicit error rather

    // ---------------------------------------------------------------------------
    def checkGuaranteeError(): Unit = checkIllegalArgument("E241118160516") // TODO: use class version

    def checkIllegalArgument(msg1: String, more: String*) = checkException(classOf[IllegalArgumentException], msg1, more:_*)
    def checkIllegalState   (msg1: String, more: String*) = checkException(classOf[IllegalStateException]   , msg1, more:_*)

    // ---------------------------------------------------------------------------
    def checkException(klass: Class[_] /* use tag rather */)                             : Unit = _check(_.failed.get.getClass == klass)
    def checkException(klass: Class[_] /* use tag rather */, msg1: String, more: String*): Unit = _check { x =>
      val throwable = x.failed.get

      throwable.getClass == klass &&
      (msg1 +: more).forall(msg => throwable.getMessage.contains(msg)) } }

  // ===========================================================================
  implicit class AptusTestingBoolean_(val value: Boolean) {
    def checkTrue () = TestUtils.checkResult(value, true )(_.toString)
    def checkFalse() = TestUtils.checkResult(value, false)(_.toString) }

  // ===========================================================================
  implicit class AptusTestingAnything_[T](value: T) {
        def noop(f: T => T)   : Unit = { f(value).check(value ) }
        def check(expected: T): Unit = {
          if(value != expected) {
            val msg: String =
              formatValue1(value)   .act(value).newline.append(
              formatValue1(expected).exp(expected))
            aptus.illegalState(msg) } } }

    // ---------------------------------------------------------------------------
    implicit class AptusTestingSeq_[T](values: Seq[T]) {
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

      // ===========================================================================
      implicit class AptusTestingOption_[T](values: Option[T]) {
        def check0()                                    : Unit = _check(None)
        def check1(value: T)                            : Unit = _check(Some(value))

        // ---------------------------------------------------------------------------
        private def _check(expected: Option[T]): Unit = {
          if(values != expected) {
            val msg: String =
              values  .map(formatValue2).toSeq.joinln.act.newline.append(
              expected.map(formatValue2).toSeq.joinln.exp)
            aptus.illegalState(msg) } } } }

// ===========================================================================