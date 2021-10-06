package aptustesting

import aptus._
import utest._
import scala.util.chaining._

// ===========================================================================
object IoTests extends TestSuite {
  
  val tests = Tests {
    
    // plain:
    
    // read/write file content    
    test(
      compareIfUnix(
        "/tmp/aptus_content.plain"
            .pipe("hello world".writeFileContent)
            .readFileContent(),
      "hello world") )

    // ---------------------------------------------------------------------------
    // read/write file lines
    test(
      compareIfUnix(
        "/tmp/aptus_lines.plain"
            .pipe(Seq("hello", "world").writeFileLines)
            .readFileLines(),
      Seq("hello", "world")) )


    // ===========================================================================
    // gzip

    // read/write file content    
    test(
      compareIfUnix(
        "/tmp/aptus_content.gz"
            .pipe("hello world".writeFileContent)
            .readFileContent(),
      "hello world") )

    // ---------------------------------------------------------------------------
    // read/write file lines
    test(
      compareIfUnix(
        "/tmp/aptus_lines.gz"
            .pipe(Seq("hello", "world").writeFileLines)
            .readFileLines(),
      Seq("hello", "world")) )

    // note: "file -i /tmp/content.gz".systemCall().p // prints "/tmp/content.gz: application/gzip; charset=binary"


    // ===========================================================================
    // URL

    val EnableUrl = false
    val TestResources = "https://raw.githubusercontent.com/aptusproject/aptus-core/main/src/test/resources"
    test(compare(EnableUrl)(s"${TestResources}/content".readUrlContent(), "hello word"))
    test(compare(EnableUrl)(s"${TestResources}/lines"  .readUrlContent(), Seq("hello", "world")))   
  }
}

// ===========================================================================
