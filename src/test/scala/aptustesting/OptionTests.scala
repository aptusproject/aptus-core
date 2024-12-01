package aptustesting

import aptus._
import utest._

// ===========================================================================
object OptionTests extends TestSuite {
  val tests = Tests {    
    test { compare( Some("foo")          .swap("bar"), None) }
    test { compare((None: Option[String]).swap("bar"), Some("bar")) } } }

// ===========================================================================
