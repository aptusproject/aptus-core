package aptus
package aptutils

// ===========================================================================
object IterableUtils {

  /** matches the semantics of uniq in Unix (only remove adjacent duplicates) */
  def distinctByAdjacency[A](iter: Iterable[A]): Iterable[A] = { // TODO: t200331143621 - version memoizes more than just the previous one; TODO: test for 1		
    var previousOpt: Option[A] = None
    
    iter
      .flatMap { current =>      
        val tmp =
          if (previousOpt.forall(_ != current)) Some(current) // new value
          else                                  None          // redundant value
        
        previousOpt = Some(current)
        
        tmp
      }
  }

}

// ===========================================================================
