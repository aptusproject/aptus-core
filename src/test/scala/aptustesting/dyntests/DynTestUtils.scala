package aptustesting
package dyntest

// ===========================================================================
object DynTestUtils {

  def checkResult[T](value: T, expected: T)(format: T => String): Unit = {
    assert(
      value == expected,
      "\n" +
      value   .pipe(format).swp.sectionAllOff("actual:"  , 2).newline +
      expected.pipe(format).swp.sectionAllOff("expected:", 2).newline) }

  // ---------------------------------------------------------------------------
  def checkError[T](err: HasErrorId, superTypeOpt: Option[SuperType]): Try[T] => Try[Boolean] =
    _ .failed
      .map { _.getMessage.pipe { msg =>
        msg.contains(err.id) &&
        superTypeOpt.map(_.formatErrorMessageString).forall(msg.contains)} } }

// ===========================================================================
