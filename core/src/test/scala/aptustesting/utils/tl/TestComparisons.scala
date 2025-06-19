package aptustesting
package utils
package tl

// ===========================================================================
trait TestComparisons {
  def noop   [A](actual: A)             : Unit = compare(actual, actual)
  def compare[A](actual: A, expected: A): Unit = { Predef.assert(actual == expected, message = s"\n\texpected: |${expected}|\n\tactual:   |${actual}|") }

  def compare      [A](enabled: Boolean)(actual: => A, expected: A): Unit = { if (enabled) compare(actual, expected) else () }
  def compareIfUnix[A]                  (actual: => A, expected: A): Unit = compare(aptus.system.isUnix())(actual, expected)

  // ---------------------------------------------------------------------------
  def isTrue(actual: Boolean): Unit = compare[Boolean](actual, true)
  def isFlse(actual: Boolean): Unit = compare[Boolean](actual, false)

  // ---------------------------------------------------------------------------
  def isSome[T](actual: Option[T], someValue: T): Unit = compare[Option[T]](actual, Some(someValue))
  def isNone[T](actual: Option[T])              : Unit = compare[Option[T]](actual, Option.empty[T]) }

// ===========================================================================