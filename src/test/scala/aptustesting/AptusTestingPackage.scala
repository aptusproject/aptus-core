// ===========================================================================
package object aptustesting {  
  def noop   [A](actual: A)              = compare(actual, actual)
  def compare[A](actual: A, expected: A) = { assert(actual == expected, message = actual -> expected) }  
}

// ===========================================================================