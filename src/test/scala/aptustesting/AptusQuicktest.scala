package aptustesting

import aptus._

// ===========================================================================
@deprecated object AptusQuicktest { // TODO: t230623142128 - to be ported to actual tests

  // ===========================================================================
  def main(args: Array[String]): Unit = { // run with: sbt "Test / runMain aptustesting.AptusQuicktest"
    init ()
    init2()

    println("ok")
  }
  
  // ===========================================================================
  def init2() = {    
    "hello world".writeFileContent("/tmp/content")
    "/tmp/content".readFileContent().p // prints: "hello world"

    Seq("hello", "world").writeFileLines("/tmp/lines")
    "/tmp/lines".readFileLines().p // prints: Seq("hello", "world")

    // ---------------------------------------------------------------------------
    "hello world".writeFileContent("/tmp/content.gz")
    "/tmp/content.gz".readFileContent().p // prints: "hello world"    

    Seq("hello", "world").writeFileLines("/tmp/lines.gz")
    "/tmp/lines.gz".readFileLines().p // prints: Seq("hello", "world")
    
    // note:
    "file -i /tmp/content.gz".systemCall().p // prints "/tmp/content.gz: application/gzip; charset=binary"
    
    // ---------------------------------------------------------------------------
    val TestResources = "https://raw.githubusercontent.com/aptusproject/aptus-core/main/src/test/resources"
    s"${TestResources}/content".readUrlContent() // prints "hello word"
    s"${TestResources}/lines"  .readUrlLines().p // prints: Seq("hello", "world")

    ()
  }
  
  // ===========================================================================
  def init() = {
    aptus.fs.homeDirectoryPath().p
    
    aptus.reflection.formatStackTrace().p

    "hello".p               // prints: "hello"
    "hello".p.toUpperCase.p // prints: "hello", then "HELLO"
    
    3.str     .p // prints "3"
    3.toString.p // prints "3"

    val foo = "foo"
    foo.p
    foo.i(_.quote)
    if (false) foo.p__
    
    // ---------------------------------------------------------------------------
    "hello".assert  (_.startsWith("h"))                    .toUpperCase.p // prints "HELLO"
    "hello".assert  (_.startsWith("h"), x => s"value=${x}").toUpperCase.p // prints "HELLO"
    "hello".require (_.startsWith("h"))                    .toUpperCase.p // prints "HELLO"
    "hello".ensuring(_.startsWith("h"))                    .toUpperCase.p // prints "HELLO"

  //"hello".assert (_.startsWith("H"))                    .toUpperCase.p // throws AssertionError
  //"hello".assert (_.startsWith("H"), x => s"value=${x}").toUpperCase.p // throws AssertionError: assertion failed: value=hello

    // ---------------------------------------------------------------------------
    "hello". append(" you!")  .p // prints "hello you!"
    "hello".prepend("well, ") .p // prints "well, hello"
    "hello".colon  ("human")  .p // prints "hello:human"
    "hello".tab    ("human")  .p // prints "hello<TAB>human"
    "hello".newline("human")  .p // prints "hello<new-line>human"
    "hello".quote             .p // prints "\"hello\""
    "hello|world".splitBy("|").p // prints List(hello, world)
    // .. many more String operations

    // ---------------------------------------------------------------------------
    "bonjour".pipeIf(_.startsWith("h"))(_.toUpperCase).p // prints "bonjour" (unchanged)
    "hello"  .pipeIf(_.startsWith("h"))(_.toUpperCase).p // prints "HELLO"

    3.pipeIf(_ % 2 == 0)(_ + 1).p // prints 3 (unchanged)
    4.pipeIf(_ % 2 == 0)(_ + 1).p // prints 5

    val suffixOpt = Some("?")
    "hello".pipeOpt(suffixOpt)(suffix => _ + suffix).p // prints "hello?"
    "hello".pipeOpt(None)     (suffix => _ + suffix).p // prints "hello" (unchanged)

    "hello"  .in.noneIf(_.size > 5).p // prints Some("hello")
    "bonjour".in.noneIf(_.size > 5).p // prints None

    "hello"  .in.someIf(_.size <= 5).p // prints Some("hello")
    "bonjour".in.someIf(_.size <= 5).p // prints None

    // ---------------------------------------------------------------------------
    // "force" disambiguator
    //   .get is polysemic in the stdlib, sometimes "attempting" (returns Option[T]), sometimes "forcing" (returns T)
    //   aptus' .force better conveys semantics
    val myOption = Some("foo")

    myOption.get  .p // prints "foo" -> stdlib way, forcing 
    myOption.force.p // prints "foo"
    
    val myMap = Map("bar" -> "foo")
    myMap.get  ("bar").p // prints Some("foo") -> stdlib way, attempting
    myMap.force("bar").p // prints "foo"

    // ---------------------------------------------------------------------------
    "~/.bashrc".readFileLines().head.p // on my machine, prints "# ~/.bashrc: executed by bash(1) for non-login shells."
    Seq("hello", "world").writeFileLines("/tmp/hello") // self-explanatory
    "/tmp/hello".readFileContent().p // prints "hello<new-line>world"

    // ---------------------------------------------------------------------------
    "hello".padLeft (8, ' ').p // "   hello"
    "hello".padRight(8, ' ').p // "hello   "
    1.str  .padLeft (3, '0').p // "001"
    1.str  .padRight(3, '0').p // "100"

    // ---------------------------------------------------------------------------
    Seq(1)      .force.one     .p // 1
    Seq(1)      .force.option  .p // Some(1)
    Seq( )      .force.option  .p // None
    Seq(1, 2, 3).force.distinct.p // List(1, 2, 3)
    Seq(1, 2, 3).force.set     .p // Set (1, 2, 3)

    if (false) {
      Seq(1, 2)   .force.one      // error
      Seq(1, 2)   .force.option   // error
      Seq(1, 2, 1).force.distinct // error
      Seq(1, 2, 1).force.set      // error
    }

    // ---------------------------------------------------------------------------
    Seq(Some(1), Some(2), Some(3)).toOptionalSeq.p // Some(Seq(1, 2, 3))
    Seq(Some(1), Some(2), None)   .toOptionalSeq.assert(_ == None)

    (Some(1), Some(2)).toOptionalTuple.p // Some((1, 2))
    (None   , Some(2)).toOptionalTuple.p // None
    
    // ---------------------------------------------------------------------------
    Seq(1, 2, 3, 4, 5).slidingPairs.assert(_.size == 4)
    Seq(1).slidingPairs.assert(_.isEmpty)
    Seq[Int]() .slidingPairs.assert(_.isEmpty)    
    
    Seq(1, 2, 3, 4, 5)
      .sliding(2)
      .map { x =>
        assert(x.size == 2)
        (x(0), x(1)) }
      .toList      

    // ---------------------------------------------------------------------------    
    aptus.fs.homeDirectoryPath().p
    aptus.random.uuidString().p // a1bffc1e-72aa-477e-ac84-e4133ffcafad
    aptus.hardware.totalMemory().p // 1011351552
    aptus.reflection.formatStackTrace().p
      //java.lang.Throwable
      //  at aptus.aptmisc.Reflect$.formatStackTrace(Misc.scala:62)
      //  at aptustest.AptusQuicktest$.init(AptusQuicktest.scala:147)
      //  at aptustest.AptusQuicktest$.main(AptusQuicktest.scala:11)
      //  at aptustest.AptusQuicktest.main(AptusQuicktest.scala)

    if (false) illegalState   ("freeze!") // Exception in thread "main" java.lang.IllegalStateException: freeze!
    util.Try(illegalState   ("freeze!")).p
    util.Try(illegalArgument("freeze!")).p
// Failure(java.lang.IllegalStateException: freeze!)
// Failure(java.lang.IllegalArgumentException: freeze!)
    
    // ---------------------------------------------------------------------------
    "mykey".   contains("myk").p // stdlib
    "mykey".notContains("MYK").p // negative counterpart

    // ---------------------------------------------------------------------------
    //.forPathsMatching(_.key.containedIn(Converting.IntegerKeys)).zen(_.convert(_).toInt   )
    2.   containedIn(1, 2, 3).p // true
    4.notContainedIn(1, 2, 3).p // true

    "foo".in.some .p // Some("foo")
    "foo".in.seq  .p // List("foo")
    "foo".in.list .p // List("foo")
    "foo".in.left .p // Left("foo")
    "foo".in.right.p // Right("foo")
    "foo".in.someIf(_.size == 1) .p // None

    // ---------------------------------------------------------------------------  
    Seq(1, 2, 3).zipSameSize(Seq(4, 5, 6)).p // List((1,4), (2,5), (3,6))
    
    // ---------------------------------------------------------------------------
    Seq(1 -> "foo", 2 -> "bar").force.map.p // Map(1 -> foo, 2 -> bar)
        
    // ---------------------------------------------------------------------------
    val values1: Nes[Int] = Seq(1, 2, 3)
    val values2: Pes[Int] = Some(Seq(1, 2, 3))
    println(Seq(values1, values2))

    @ordermatters
    val a = 1
    val b = 2
    println(Seq(a, b))

    // ---------------------------------------------------------------------------
    Seq(1, 2, 3).splitAtHead.p // (1,List(2, 3))
    Seq(1, 2, 3).splitAtLast.p // (List(1, 2),3)
    Seq(1, 2, 3).splitAt(2).mapSecond(_ :+ 1).p // (List(1, 2),List(3, 1))

    (1, 2).toSeq.p // Seq(1, 2)

    // ---------------------------------------------------------------------------
    Some("foo").swap("bar").p // None
    None       .swap("bar").p // Some("bar")    
    
    Seq(1 -> "foo", 2 -> "bar").groupByKeyWithListMap.p // ListMap(1 -> List(foo), 2 -> List(bar))

    // ---------------------------------------------------------------------------
    Seq(1, 2, 3). @@.p //    [1, 2, 3]
    Seq(1, 2, 3).#@@.p // #3:[1, 2, 3]
    Seq(1, 2, 3).joinln  .p
    Seq(1, 2, 3).joinlnln.p
    Seq(1, 2, 3).joinln.sectionAllOff("data:").p
    Seq(1, 2, 3).section("data:").p

    // ---------------------------------------------------------------------------
    implicit val ord: Ordering[Seq[Int]] = aptus.seqOrdering
    Seq(Seq(4, 5, 6), Seq(1, 2, 3)).sorted.p // Seq(Seq(1, 2, 3), Seq(4, 5, 6))
    Seq(Seq(4, 5, 6), Seq(1, 2   )).sorted.p // Seq(Seq(1, 2), Seq(4, 5, 6))
    Seq(Seq(4, 5)   , Seq(1, 2, 3)).sorted.p // Seq(Seq(4, 5), Seq(1, 2, 3))

    // ---------------------------------------------------------------------------    
    // Closeabled: e.g. when you don't want to carry pairs of Iterator/Closeable around
      // boils down to: class Closeabled[T](underlying: T, cls: Closeable) extends Closeable

    // let's write lines
    Seq("hello", "world").writeFileLines("/tmp/content")

    // and stream them back
    val myCloseabled: CloseabledIterator[String] = "/tmp/content".streamFileLines2()

    if (true)
      myCloseabled.consumeAll.p // we can consume the content (will automatically close)
    else
      myCloseabled.map(_.toUpperCase).consumeAll.p // with line pre-processing

    // ---------------------------------------------------------------------------
    ()
  }

  // ---------------------------------------------------------------------------
  class Nes2[T](val values: Seq[T]) extends AnyVal {
    // require(values.nonEmpty) // this statement is not allowed in value class
  }
}

// ===========================================================================
