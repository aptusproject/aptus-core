package aptus
package aptdata
package mult

// ===========================================================================
package object iter {
  def extenalSort[T: Ordering](values: CloseabledIterator[T]): CloseabledIterator[T] = ??? } // TODO: t241203101839 - implement external sort (using Ordering somehow)

// ===========================================================================