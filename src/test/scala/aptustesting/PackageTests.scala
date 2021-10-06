package aptustesting

import aptus._
import utest._

// ===========================================================================
object PackageTests extends TestSuite {
  
  val tests = Tests {    
    
    // ---------------------------------------------------------------------------    
    implicit val ord: Ordering[Seq[Int]] = aptus.seqOrdering
    test(compare(Seq(Seq(4, 5, 6), Seq(1, 2, 3)).sorted, Seq(Seq(1, 2, 3), Seq(4, 5, 6))))    
    test(compare(Seq(Seq(4, 5, 6), Seq(1, 2   )).sorted, Seq(Seq(1, 2),    Seq(4, 5, 6))))
    test(compare(Seq(Seq(4, 5)   , Seq(1, 2, 3)).sorted, Seq(Seq(4, 5),    Seq(1, 2, 3))))
    
    // ---------------------------------------------------------------------------
    test(zip(Seq(1, 2, 3), Seq(4, 5, 6), Seq(7, 8, 9)), Seq(Seq(1, 4, 7), Seq(2, 5, 8), Seq(3, 6, 9)))
  }
}

// ===========================================================================
