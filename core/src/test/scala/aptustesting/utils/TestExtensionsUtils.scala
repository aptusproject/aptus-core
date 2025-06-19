package aptustesting
package utils

import aptus.String_

// ===========================================================================
private object TestExtensionsUtils {

  implicit class String__(diss: String) {
    def act = diss.prepend(  "actual\n\n").newline
    def exp = diss.prepend("expected\n\n").newline

    // ---------------------------------------------------------------------------
    def act(value: Any) = diss.prepend(  s"actual: ${value.getClass}\n\n").newline
    def exp(value: Any) = diss.prepend(s"expected: ${value.getClass}\n\n").newline }

  // ===========================================================================
  def formatValue1(value: Any): String = Option(value).map(_.toString).getOrElse("[null!]")
  def formatValue2(value: Any): String = formatValue1(value).append(s" (${value.getClass.getSimpleName})") }

// ===========================================================================