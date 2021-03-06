import org.apache.commons.lang3
import com.google.common.base.CaseFormat

import scala.collection.{mutable,immutable}
import scala.util.chaining._

import java.time._
import java.time.format.DateTimeFormatter
import java.lang.IllegalStateException
import aptus.aptutils._

// ===========================================================================
package object aptus
    extends AptusAnnotations
    with    AptusAliases
    with    AptusCommonAliases {
  // TODO: t211006141756 - should most of them have @inline? is there a downside?

  /* for extends AnyVals, see https://stackoverflow.com/questions/14929422/should-implicit-classes-always-extend-anyval */

  def illegalState   (x: Any*): Nothing = { throw new IllegalStateException   (x.mkString(", ")) }
  def illegalArgument(x: Any*): Nothing = { throw new IllegalArgumentException(x.mkString(", ")) }

  // ---------------------------------------------------------------------------
  def iterableOrdering[T : Ordering]: Ordering[Iterable[T]] = SeqUtils.iterableOrdering[T] // note: Ordering is invariant

  def   seqOrdering[T : Ordering]: Ordering[Seq  [T]] = SeqUtils.  seqOrdering[T]
  def  listOrdering[T : Ordering]: Ordering[List [T]] = SeqUtils. listOrdering[T]
  def arrayOrdering[T : Ordering]: Ordering[Array[T]] = SeqUtils.arrayOrdering[T]

  // ---------------------------------------------------------------------------
  def zip[T1, T2, T3]        (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3])                                  : Iterable[(T1, T2, T3)]         = a.zip(b).zip(c)              .map { case   ((a, b), c)         => (a, b, c) }
  def zip[T1, T2, T3, T4]    (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4])                 : Iterable[(T1, T2, T3, T4)]     = a.zip(b).zip(c).zip(d)       .map { case  (((a, b), c), d)     => (a, b, c, d) }
  def zip[T1, T2, T3, T4, T5](a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4], e: Iterable[T5]): Iterable[(T1, T2, T3, T4, T5)] = a.zip(b).zip(c).zip(d).zip(e).map { case ((((a, b), c), d), e) => (a, b, c, d, e) }
  def zip[T1, T2](a: Iterable[T1], b: Iterable[T2]): Iterable[(T1, T2)] = a.zip(b) // for good measure, should favor: a.zip(b)

  // ===========================================================================
  implicit class Unit_(val u: Unit) extends AnyVal { // TODO: t201213095810 anyway to add that to Predef? implicit class doesn't seem to work
    def system      = aptmisc.AptusSystem
    def fs          = aptmisc.Fs
    def hardware    = aptmisc.Hardware
    def random      = aptmisc.Random
    def reflect     = aptmisc.Reflect
    def time        = aptmisc.Time

    // ---------------------------------------------------------------------------
    // convenience

    //@fordevonly def tid(): Long = concurrency.threadId
  }

  // ===========================================================================
  implicit class Anything_[A](private val a: A) extends AnyVal {
    def str: String = a.toString
    def prt: A      = { System.out.println(a); a }

    def inspect(f: A => Any): A = { f(a).prt; a }

    // ---------------------------------------------------------------------------
   //@inline def pipe      [B     ]                    (f: A => B)           : B =              f(a)
             def pipeIf            (test: Boolean)     (f: A => A)           : A = if (test)    f(a) else   a
             def pipeIf    [B <: A](pred: A => Boolean)(f: A => B)           : A = if (pred(a)) f(a) else   a
             def pipeOpt   [B     ](opt : Option[B]   )(f: B => A => A)      : A = opt.map(f(_)(a)).getOrElse(a)

    //def tap                          (f: A => Unit)  : A = {                f(a)  ; a }
      def tapIf    (test: Boolean)     (f: A => Unit)  : A = { if (test)    { f(a) }; a }
      def tapIf    (pred: A => Boolean)(f: A => Unit)  : A = { if (pred(a)) { f(a) }; a }
      def tapOpt[B](opt : Option[B]   )(f: B => A => A): A = { opt.map(f(_)(a)).getOrElse(a); a }

      // ---------------------------------------------------------------------------
      // formerly (before pipe/tap available via import scala.util.chaining._):

      /** thrush combinator (see https://users.scala-lang.org/t/implicit-class-for-any-and-or-generic-type/501);
       *  I guess the altenative is to do ` match { case ... ` */
      @deprecated("use pipe now") @inline def thn      [B     ]                    (f: A => B)           : B =              f(a)
      @deprecated("use pipe now")         def thnIf            (test: Boolean)     (f: A => A)           : A = if (test)    f(a) else   a
      @deprecated("use pipe now")         def thnIf    [B <: A](pred: A => Boolean)(f: A => B)           : A = if (pred(a)) f(a) else   a
      @deprecated("use pipe now")         def thnOpt   [B     ](opt : Option[B]   )(f: B => A => A)      : A = opt.map(f(_)(a)).getOrElse(a)

      @deprecated("use tap now") def sideEffect                          (f: A => Unit)              : A  = {                f(a)                ; a }
      @deprecated("use tap now") def sideEffectIf    (pred: A => Boolean)(f: A => Unit)              : A  = { if (pred(a)) { f(a) }              ; a }

    // ---------------------------------------------------------------------------
    @fordevonly def __exit: Nothing = { ReflectionUtils.formatExitTrace(().reflect.stackTrace(), "intentionally stopping").p; System.exit(0); illegalState("can't happen") }

    @fordevonly def p      : A =   prt
    @fordevonly def p__    : A = { prt; __exit }
    @fordevonly def pp     : A = { System.out.print(s"${a}\n\n"); a      }

    // "i" for "inspect"
    @fordevonly def i  (f: A => Any                ): A = inspect(f)
    @fordevonly def i  (f: A => Any, prefix: String): A = { System.out.println(s"${prefix}\t${f(a)}"); a }
    @fordevonly def i__(f: A => Any                ): A = { System.out.println(               f(a)  ); __exit }
    @fordevonly def i__(f: A => Any, prefix: String): A = { System.out.println(s"${prefix}\t${f(a)}"); __exit }

    // ---------------------------------------------------------------------------
    def assert(p: A => Boolean):              A = assert(p, identity)
    def assert(p: A => Boolean, f: A => Any): A = { Predef.assert(p(a), f(a)); a }

    def require(p: A => Boolean):              A = require(p, identity)
    def require(p: A => Boolean, f: A => Any): A = { Predef.require(p(a), f(a)); a }

    // ---------------------------------------------------------------------------
    def in: aptus.aptmisc.As[A] = new aptus.aptmisc.As[A](a)

      // most common ones
      def inNoneIf(p: A => Boolean): Option[A] = in.noneIf(p)
      def inSomeIf(p: A => Boolean): Option[A] = in.someIf(p)

    // ---------------------------------------------------------------------------
    @inline def    containedIn(values: Set[A]): Boolean =  values.contains(a)
    @inline def    containedIn(values: Seq[A]): Boolean =  values.contains(a)

    @inline def notContainedIn(values: Seq[A]): Boolean = !values.contains(a)
    @inline def notContainedIn(values: Set[A]): Boolean = !values.contains(a)

    // ---------------------------------------------------------------------------
    def associateLeft [K](f: A => K): (K, A) = (f(a), a)
    def associateRight[V](f: A => V): (A, V) = (a, f(a))

    // ---------------------------------------------------------------------------
    def mkString[C, D](sep: String)(implicit ev: A <:< (C, D)): String = s"${a._1}${sep}${a._2}"
  }

  // ===========================================================================
  implicit class String_(val str: String) extends AnyVal {
    def symbol : Symbol  = Symbol(str)

    def regex  : Regex       = str.r // more explicit
    def pattern: JavaPattern = str.r.pattern

    // ---------------------------------------------------------------------------
    def sys()                           : String = SystemUtils.runCommand                    (str).stripSuffix("\n")
    def systemCall()                    : String = SystemUtils.runCommand                    (str).stripSuffix("\n")
    def systemCallWithErrorRedirection(): String = SystemUtils.runCommandWithErrorRedirection(str).stripSuffix("\n")

    def systemCallLines()               : Seq[String] = systemCall().splitBy("\n")

    // ---------------------------------------------------------------------------
    // encoding
    def toBase64   : String = str.getBytes.toBase64
    def toHexString: String = str.getBytes.toHexString

    // ===========================================================================
    def path = new aptmisc.AptusPath(if (str.startsWith("~/")) ().fs.homeDirectoryPath() / str.drop(2) else str)

      // ---------------------------------------------------------------------------
      // TODO: t211004131206 - move to path?
      def writeFileContent(path: String): FilePath = FileUtils.writeContent(path, content = str)
      def  readFileContent()            : Content  = FileUtils.readFileContent(path = str)

        // ---------------------------------------------------------------------------
        def readFileLines(): List[Line] = FileUtils.readFileLines(path = str)
        def readFileTsv  (): List[Vector[Cell]] = readFileLines().map(_.splitBy("\t").toVector)
        def readFileCsv  (): List[Vector[Cell]] = readFileLines().map(_.splitBy( ",").toVector)

        def streamFileLines(): (Iterator[Line],         Closeable) = FileUtils.streamFileLines(path = str)
        def streamFileTsv  (): (Iterator[Vector[Cell]], Closeable) = str.streamFileLines().mapFirst(_.map(_.splitXsv('\t').toVector))
        def streamFileCsv  (): (Iterator[Vector[Cell]], Closeable) = str.streamFileLines().mapFirst(_.map(_.splitXsv(',') .toVector))

        // TODO: rename
        def streamFileLines2(): CloseabledIterator[Line]         = FileUtils.streamFileLines(path = str).pipe(CloseabledIterator.fromPair)
        def streamFileTsv2  (): CloseabledIterator[Vector[Cell]] = str.streamFileLines2().map(_.splitXsv('\t').toVector)
        def streamFileCsv2  (): CloseabledIterator[Vector[Cell]] = str.streamFileLines2().map(_.splitXsv(',') .toVector)
        
    // ===========================================================================
    def readUrlContent(): Content   = UrlUtils.content(str)
    def readUrlLines()  : Seq[Line] = UrlUtils.  lines(str)

    // ===========================================================================
    def prepend(prefix: String)                      = s"$prefix$str"
    def append (suffix: String)                      = s"$str$suffix"

    def surroundWith(boundary: String)               = s"$boundary$str$boundary"
    def surroundWith(prefix: String, suffix: String) = s"$prefix$str$suffix"
    
    @fordevonly def swp: String = surroundWith("|") // convenient for debugging (shows leading/trailing whitespaces)

    // ---------------------------------------------------------------------------
    // to improve fluency
    def newline    : String = append("\n"); def newline   (suffix: Any): String = newline   .append(suffix.toString /* TODO: restrict this and below */)
    def tab        : String = append("\t"); def tab       (suffix: Any): String = tab       .append(suffix.toString)
    def slash      : String = append("/" ); def slash     (suffix: Any): String = slash     .append(suffix.toString)
    def dot        : String = append("." ); def dot       (suffix: Any): String = dot       .append(suffix.toString)
    def colon      : String = append(":" ); def colon     (suffix: Any): String = colon     .append(suffix.toString)
    def semicolon  : String = append(";" ); def semicolon (suffix: Any): String = semicolon .append(suffix.toString)
    def comma      : String = append("," ); def comma     (suffix: Any): String = comma     .append(suffix.toString)
    def dash       : String = append("-" ); def dash      (suffix: Any): String = dash      .append(suffix.toString)
    def underscore : String = append("_" ); def underscore(suffix: Any): String = underscore.append(suffix.toString)
    def pound      : String = append("#" ); def pound     (suffix: Any): String = pound     .append(suffix.toString)
    def at         : String = append("@" ); def at        (suffix: Any): String = at        .append(suffix.toString)
    def space      : String = append(" " ); def space     (suffix: Any): String = space     .append(suffix.toString)

    def / (suffix: String): String = slash(suffix)

    // ---------------------------------------------------------------------------
    def padLeft (length: Int, char: Char): String = lang3.StringUtils. leftPad(str, length, char.toString)
    def padRight(length: Int, char: Char): String = lang3.StringUtils.rightPad(str, length, char.toString)

    def trimLines: String = str.replaceAll("\\s*\n\\s*", "\n")

    // ===========================================================================
    // TODO: quite inefficient
      def indent                          : String = StringUtils.indent(1, indenter = "\t")(str)
      def indent(n: Int                  ): String = StringUtils.indent(n, indenter = "\t")(str)
      def indent(n: Int, indenter: String): String = StringUtils.indent(n, indenter       )(str)

      // ---------------------------------------------------------------------------
      def indentAll                          : String = StringUtils.indentAll(n = 1, indenter = "\t")(str)
      def indentAll(n: Int                  ): String = StringUtils.indentAll(n    , indenter = "\t")(str)
      def indentAll(n: Int, indenter: String): String = StringUtils.indentAll(n    , indenter       )(str)

      // ---------------------------------------------------------------------------
      def sectionAllOff                : String =                StringUtils.sectionAllOff(n = 1, indenter = "\t")(str)
      def sectionAllOff(n: Int)        : String =                StringUtils.sectionAllOff(n    , indenter = "\t")(str)

      def sectionAllOff(prefix: String): String = s"${prefix}" + StringUtils.sectionAllOff(n = 1, indenter = "\t")(str)

    // ===========================================================================
    def isTrimmed     : Boolean = str == str.trim
    def isQuoted      : Boolean = str.size >= 2 && str.startsWith("\"") && str.endsWith("\"")
    def isSingleQuoted: Boolean = str.size >= 2 && str.startsWith( "'") && str.endsWith( "'")

    def isDigits      : Boolean = str.nonEmpty && str.forall(_.isDigit) // TODO: vs org.apache.commons.lang3.math.NumberUtils.isDigits?
    def isValidInt    : Boolean = NumberUtils.isValidInt(str) // FIXME

    def notContains(s: CharSequence): Boolean = !str.contains(s) // TODO: or "containsNot"?

    // ===========================================================================
    def extractGroup (pattern: JavaPattern): Option[    String ] = extractGroup (pattern.pattern.r)
    def extractGroups(pattern: JavaPattern): Option[Seq[String]] = extractGroups(pattern.pattern.r)

    def extractGroup (regex: Regex): Option[    String ] = regex.findFirstMatchIn(str).map(_.group(1)) // TODO: check contains only X groups
    def extractGroups(regex: Regex): Option[Seq[String]] = regex.findFirstMatchIn(str).map { matsh => Range(1, matsh.groupCount + 1).map(matsh.group) }

    // ---------------------------------------------------------------------------
    // TODO: replaceGroup (see 211004151531)

    // ===========================================================================
    def splitBy(separator: String        ): Seq[String] = if (str.isEmpty()) List(str) else lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator   ).toList
    def splitBy(separator: String, n: Int): Seq[String] = if (str.isEmpty()) List(str) else lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, n).toList
    def splitBy(regex: JavaPattern)       : Seq[String] = regex.split(str).toList
    def splitBy(regex: Regex)             : Seq[String] = regex.split(str).toList // TODO: keep?

    // ---------------------------------------------------------------------------
    def splitXsv(sep: Char): List[Cell] = StringUtils.splitXsv(str, sep)
    def splitTabs          : List[Cell] = splitXsv('\t')
    def splitCommas        : List[Cell] = splitXsv(',')

    // ===========================================================================
    import aptutils.TimeUtils._

      def parseInstant        : Instant        = Instant.from(             IsoFormatterInstant.parse(str))
      def parseLocalDateTime  :  LocalDateTime =  LocalDateTime.parse(str, IsoFormatterLocalDateTime)
      def parseOffsetDateTime : OffsetDateTime = OffsetDateTime.parse(str, IsoFormatterOffsetDateTime)
      def parseZonedDateTime  :  ZonedDateTime =  ZonedDateTime.parse(str, IsoFormatterZonedDateTime)

      def parseLocalDate      :  LocalDate     =  LocalDate    .parse(str, IsoFormatterLocalDate)
      def parseLocalTime      :  LocalTime     =  LocalTime    .parse(str, IsoFormatterLocalTime)

      // ===========================================================================
      def parseLocalDateTime(pattern: String): LocalDateTime = DateTimeFormatter.ofPattern(pattern).pipe(LocalDateTime.parse(str, _))
      def parseLocalDate    (pattern: String): LocalDate     = DateTimeFormatter.ofPattern(pattern).pipe(LocalDate    .parse(str, _))
      def parseLocalTime    (pattern: String): LocalTime     = DateTimeFormatter.ofPattern(pattern).pipe(LocalTime    .parse(str, _))

      // ---------------------------------------------------------------------------
      def parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime = LocalDateTime.parse(str, formatter)
      def parseLocalDate    (formatter: DateTimeFormatter): LocalDate     = LocalDate    .parse(str, formatter)
      def parseLocalTime    (formatter: DateTimeFormatter): LocalTime     = LocalTime    .parse(str, formatter)

      // ---------------------------------------------------------------------------
      def dateTime = parseLocalDateTime // eg "2021-01-08T01:02:03".dateTime
      def date     = parseLocalDate     // eg "2021-01-08"         .date
      def time     = parseLocalTime     // eg            "01:02:03".time

    // ===========================================================================
    @deprecated
    def removeIfApplicable( potentialSubStr: String) = str.replace( potentialSubStr, "")
    def remove            ( potentialSubStr: String) = str.replace( potentialSubStr, "")
    def removeGuaranteed  (guaranteedSubStr: String) = str.replace(guaranteedSubStr, "").assert(_ != str)

    // ---------------------------------------------------------------------------
    def stripTrailingZeros: String = if (!org.apache.commons.lang3.math.NumberUtils.isCreatable(str)) str else new java.math.BigDecimal(str).stripTrailingZeros().toPlainString()

    // ---------------------------------------------------------------------------
    def   quote      : String = if (isQuoted)       str else s""""$str""""
    def   quoteSingle: String = if (isSingleQuoted) str else   s"'$str'"
    def unquote      : String = StringUtils.unquoteLeft(str).pipe(StringUtils.unquoteRight)

    def escapeQuotes       = str.replace("\"", "\\\"")
    def escapeSingleQuotes = str.replace("\"", "\\\"")

    // ---------------------------------------------------------------------------
    def uncapitalizeFirst: String = str.headOption.map(x => x.toLower +: str.tail).getOrElse(str)
    def   capitalizeFirst: String = str.headOption.map(x => x.toUpper +: str.tail).getOrElse(str)

    // ---------------------------------------------------------------------------
    def snakeToCamelCase: String = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL     , str)
    def camelCaseToSnake: String = CaseFormat.UPPER_CAMEL     .to(CaseFormat.LOWER_UNDERSCORE, str)
    // TODO: other common combinations?

    // ===========================================================================
    // json; TODO: t210204095517 - replace gson in the long run

    def jsonObject: com.google.gson.JsonObject = aptus.aptjson.GsonParser.stringToJsonObject(str)
    def jsonArray : com.google.gson.JsonArray  = aptus.aptjson.GsonParser.stringToJsonArray (str)

    def prettyJson : String = aptus.aptjson.GsonFormatter.pretty (str).get
    def compactJson: String = aptus.aptjson.GsonFormatter.compact(str).get
  }

  // ===========================================================================
  // TODO: switch it all to List? see https://users.scala-lang.org/t/seq-vs-list-which-should-i-choose/5412/16
  implicit class Seq_[A](val coll: Seq[A]) extends AnyVal { // TODO: t210124092716 - codegen specializations (List, Vector, ...?)
    def requireDistinct   ()           : Seq[A] = SeqUtils.distinct(coll, Predef.require(_, _))
    def requireDistinctBy[B](f: A => B): Seq[A] = SeqUtils.requireDistinctBy(coll)(f)

    // ---------------------------------------------------------------------------
    def writeFileLines(path: FilePath): FilePath = FileUtils.writeLines(path, coll.map(_.toString))

    def systemCall                    ()(implicit ev: A =:= String): String = SystemUtils.run                    (coll.asInstanceOf[Seq[String]]:_*).stripSuffix("\n")
    def systemCallWithErrorRedirection()(implicit ev: A =:= String): String = SystemUtils.runWithErrorRedirection(coll.asInstanceOf[Seq[String]]:_*).stripSuffix("\n")

    // ---------------------------------------------------------------------------
    def force = new aptus.aptmisc.Force(coll) // TODO: conflicts with view's...

    // ---------------------------------------------------------------------------
    def join(sep: Separator) = coll.mkString(sep)
    def join                 = coll.mkString
    def joinln               = coll.mkString("\n")
    def joinlnln             = coll.mkString("\n\n")
    def jointab              = coll.mkString("\t")

    // ---------------------------------------------------------------------------
    def  @@ = coll.mkString("[", ", ", "]")
    def #@@ = s"#${coll.size}:${@@}"

    @fordevonly def debug: Seq[A] = { println(coll.map(_.toString.swp).section(coll.size.str)); coll } // convenient for debugging (shows leading/trailing whitespaces)
    
    // ---------------------------------------------------------------------------
    def section                : String = section("")
    def section (title: String): String = StringUtils.section(coll, 1, title)

    def section2               : String = section2("")
    def section2(title: String): String = StringUtils.section(coll, 2, title)

    // ---------------------------------------------------------------------------
    def isDistinct                               : Boolean = coll.size == coll.toSet.size
    def isDisjointWith[B >: A](that: Iterable[B]): Boolean = coll.intersect(that.toSeq).isEmpty
    def isSorted(implicit ev: Ordering[A])       : Boolean = coll.sorted == coll // TODO: more efficient version

    // ---------------------------------------------------------------------------
    def duplicates                : Seq[A] = coll.diff(coll.distinct)

    def filterBy        [B](p: B => Boolean)(f: A => B)= coll.filter   (x =>  p(f(x)))
    def filterByNot     [B](p: B => Boolean)(f: A => B)= coll.filterNot(x =>  p(f(x)))

    def mapIf    [B <: A](pred: A => Boolean)(f: A => B) = coll.map { x => if (pred(x)) f(x) else   x }

    // ---------------------------------------------------------------------------
    def zipWithRank: Seq[(A, aptus.Rank)] = coll.zipWithIndex.map { case (value, index) => value -> (index + 1) }

    def zipSameSize[B](that: Seq[B])                      : Seq[(A, B)] = zipSameSize(that, _.size)
    def zipSameSize[B](that: Seq[B], debug: Seq[_] => Any): Seq[(A, B)] = { require(coll.size == that.size, (debug(coll), debug(that))); coll.zip(that) }

    def zipWithIsFirst: Seq[(A, Boolean)] = coll.zipWithIndex.map { case (x, i) => x -> (i == 0) }             // TODO: more efficient versions
    def zipWithIsLast : Seq[(A, Boolean)] = coll.zipWithIndex.map { case (x, i) => x -> (i == coll.size - 1) } // TODO: more efficient versions

    // ---------------------------------------------------------------------------
    def slidingList(n: Int): List[List[A]] = coll.sliding(n).toList.map(_.toList)
    def slidingPairs: List[(A, A)] = coll match {
        case Seq() | Seq(_) => Nil
        case seq            => seq.slidingList(2).map(_.force.tuple2) }

    def slidingPairsWithPrevious: Seq[(Option[A], A)] = (None -> coll.head) +: coll.slidingPairs.map(_.mapFirst(Some.apply))

    // ---------------------------------------------------------------------------
    def splitAtHead: (A, Seq[A]) = coll.splitAt(            1).mapFirst (_.force.one)
    def splitAtLast: (Seq[A], A) = coll.splitAt(coll.size - 1).mapSecond(_.force.one)

    // ---------------------------------------------------------------------------
    def distinctByAdjacency: Seq[A] = IterableUtils.distinctByAdjacency(coll).toList // TODO: test for 1

    // ===========================================================================
    def mean(implicit num: Numeric[A]): Double = (num.toDouble(coll.foldLeft(num.zero)(num.plus)) / coll.size)

    // ---------------------------------------------------------------------------
    def stdev              (implicit num: Numeric[A]): Double = stdev(coll.mean(num))(num.asInstanceOf[Numeric[A]])
    def stdev(mean: Double)(implicit num: Numeric[A]): Double = MathUtils.stdev(coll, mean)

    // ---------------------------------------------------------------------------
    def median               (implicit num: Numeric[A]): Double = MathUtils.percentile(coll, 50)
    def percentile(n: Double)(implicit num: Numeric[A]): Double = MathUtils.percentile(coll,  n)

    // ---------------------------------------------------------------------------
    def range[B >: A](implicit cmp: Ordering[B],  num: Numeric[B]): B      = num.minus(coll.max(cmp), coll.min(cmp)) // TODO: optimize; TODO: max if double?
    def IQR          (implicit                    num: Numeric[A]): Double = (coll.percentile(75) - coll.percentile(25)) // TODO: optimize

    // ===========================================================================
    def toMutableMap[K, V](implicit ev: A <:< (K, V))                   = MapUtils.toMutableMap(coll)
    def toListMap   [K, V](implicit ev: A <:< (K, V))                   = MapUtils.toListMap(coll)
    def toTreeMap   [K, V](implicit ev: A <:< (K, V), ord: Ordering[K]) = MapUtils.toTreeMap(coll)

    // ---------------------------------------------------------------------------
    def groupByKey           [K, V](implicit ev: A <:< (K, V)                  ):               Map[K, Seq[V]] = MapUtils.groupByKey           (coll.iterator.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithListMap[K, V](implicit ev: A <:< (K, V)                  ): immutable.ListMap[K, Seq[V]] = MapUtils.groupByKeyWithListMap(coll.iterator.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithTreeMap[K, V](implicit ev: A <:< (K, V), ord: Ordering[K]): immutable.TreeMap[K, Seq[V]] = MapUtils.groupByKeyWithTreeMap(coll.iterator.asInstanceOf[Iterator[(K, V)]])

    def groupByAdjacency[B](f: A => B): Seq[(B, Seq[A])] = MapUtils.groupByAdjacency(coll)(f)

    def countBySelf: List[(A, Int)] = coll.groupBy(identity).view.map { x => x._1 -> x._2.size }.toList.sortBy(-_._2) // TODO: t211004120452 - more efficient version

    // ---------------------------------------------------------------------------
    def toOptionalSeq[B](implicit ev: A <:< Option[B]): Option[Seq[B]] = if (coll.contains(None)) None else Some(coll.map(_.get))
  }

  // ===========================================================================
  implicit class Iterator_[A](val itr: Iterator[A]) extends AnyVal {
    def last(): A = itr.next().assert(_ => !itr.hasNext)

    // ---------------------------------------------------------------------------
    def groupByKey           [K, V](implicit ev: A <:< (K, V))                  :               Map[K, Seq[V]]  = MapUtils.groupByKey              (itr.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithListMap[K, V](implicit ev: A <:< (K, V))                  : immutable.ListMap[K, Seq[V]]  = MapUtils.groupByKeyWithListMap   (itr.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithTreeMap[K, V](implicit ev: A <:< (K, V), ord: Ordering[K]): immutable.TreeMap[K, Seq[V]]  = MapUtils.groupByKeyWithTreeMap   (itr.asInstanceOf[Iterator[(K, V)]])
    def groupByPreSortedKey[K, V](implicit ev: A <:< (K, V))                    :         Iterator[(K, Seq[V])] = IteratorUtils.groupByPreSortedKey(itr.asInstanceOf[Iterator[(K, V)]])
  }

  // ===========================================================================
  implicit class Map_[K, V](val mp: Map[K, V]) extends AnyVal {
    def force(key: K): V = mp.get(key).get // stdlib polyseme (see Option's)

    // ---------------------------------------------------------------------------
    def toMutableMap                        :   mutable.    Map[K, V] = MapUtils.toMutableMap(mp)
    def toListMap                           : immutable.ListMap[K, V] = MapUtils.toListMap   (mp)
    def toTreeMap(implicit ord: Ordering[K]): immutable.TreeMap[K, V] = MapUtils.toTreeMap   (mp)
  }

  // ===========================================================================
  implicit class Option_[A](val opt: Option[A]) extends AnyVal {
    @inline def force = opt.get // stdlib polyseme (see Map's)

    def swap[B](f: => B): Option[B] = if (opt.isDefined) None else Some(f)

    def isExclusiveWith[B](that: Option[B]): Boolean = (opt.isDefined && that.isEmpty) || (opt.isEmpty && that.isDefined) // convenient for assertions
  }

  // ===========================================================================
  implicit class Tuple2_[A, B](val tup: Tuple2[A, B]) extends AnyVal {
    def mapFirst [A2](fa: A => A2) = (fa(tup._1),   tup._2)
    def mapSecond[B2](fb: B => B2) = (   tup._1, fb(tup._2))

    def mapAll[A2, B2](fa: A => A2, fb: B => B2)                            = (fa(tup._1), fb(tup._2))
    def mapAll[C1, C2](f : C1 => C2)(implicit ev1: A <:< C1, ev2: B <:< C1) = (f (tup._1), f (tup._2))

    def toOptionalTuple[Z, Y](implicit ev1: A <:< Option[Z], ev2: B <:< Option[Y]): Option[(Z, Y)] = for { a <- tup._1; b <- tup._2 } yield (a, b)

    /** TODO: proper FP name? */
    def combine[Z, Y, T](f: (Z, Y) => T)(implicit ev1: A <:< Option[Z], ev2: B <:< Option[Y]): Option[T] = for { a <- tup._1; b <- tup._2 } yield (f(a, b))

    def isExclusivelyDefined(implicit ev1: A <:< Option[_], ev2: B <:< Option[_]): Boolean =
      (tup._1.nonEmpty && tup._2. isEmpty) ||
      (tup._1. isEmpty && tup._2.nonEmpty)

    def toSeq[Z](implicit ev1: A <:< Z, ev2: B <:< Z) = Seq[Z](tup._1, tup._2)

    // ---------------------------------------------------------------------------
    def join              = Seq(tup._1, tup._2).mkString("")
    def join(sep: String) = Seq(tup._1, tup._2).mkString(sep)
  }

  // ---------------------------------------------------------------------------
  implicit class Tuple3_[A, B, C](val tup: Tuple3[A, B, C]) extends AnyVal {
    def toSeq[Z](implicit ev1: A <:< Z, ev2: B <:< Z, ev3: C <:< Z) = Seq[Z](tup._1, tup._2, tup._3)

    def mapFirst [A2](fa: A => A2) = (fa(tup._1),   tup._2,     tup._3 )
    def mapSecond[B2](fb: B => B2) = (   tup._1, fb(tup._2),    tup._3 )
    def mapThird [C2](fc: C => C2) = (   tup._1,    tup._2 , fc(tup._3))
  }

  // ---------------------------------------------------------------------------
  implicit class Tuple4_[A, B, C, D](val tup: Tuple4[A, B, C, D]) extends AnyVal {
    def toSeq[Z](implicit ev1: A <:< Z, ev2: B <:< Z, ev3: C <:< Z, ev4: D <:< Z) = Seq[Z](tup._1, tup._2, tup._3, tup._4)
  }

  // ---------------------------------------------------------------------------
  implicit class Tuple5_[A, B, C, D, E](val tup: Tuple5[A, B, C, D, E]) extends AnyVal {
    def toSeq[Z](implicit ev1: A <:< Z, ev2: B <:< Z, ev3: C <:< Z, ev4: D <:< Z, ev5: E <:< Z) = Seq[Z](tup._1, tup._2, tup._3, tup._4, tup._5)
  }

  // ===========================================================================
  // ===========================================================================
  // ===========================================================================
  implicit class Int_(val nmbr: Int) extends AnyVal {
      def isInBetween         (fromInclusive: Int, toExclusive: Int): Boolean = nmbr >= fromInclusive && nmbr <  toExclusive
      def isInBetweenInclusive(fromInclusive: Int, toInclusive: Int): Boolean = nmbr >= fromInclusive && nmbr <= toInclusive

      // ---------------------------------------------------------------------------
      def assertRange         (fromInclusive: Int, toExclusive: Int): Int     = nmbr.assert(_.isInBetween         (fromInclusive, toExclusive), x => s"out of range: ${x} ([${fromInclusive}, ${toExclusive}[)")
      def assertRangeInclusive(fromInclusive: Int, toInclusive: Int): Int     = nmbr.assert(_.isInBetweenInclusive(fromInclusive, toInclusive), x => s"out of range: ${x} ([${fromInclusive}, ${toInclusive}])")

      // ---------------------------------------------------------------------------
      def add       (n: Int): Int = nmbr + n
      def subtract  (n: Int): Int = nmbr - n

      def multiplyBy(n: Int)   : Int    = nmbr * n
      def multiplyBy(n: Number): Double = nmbr * n.doubleValue()

      def divideBy(n: Int)   : Int    = nmbr / n
      def divideBy(n: Number): Double = nmbr / n.doubleValue()

      // ---------------------------------------------------------------------------
      def formatUsLocale: String = NumberUtils.IntegerFormatter.format(nmbr)
      def formatExplicit: String = formatUsLocale.replace(",", "") //def formatExplicit: String = f"${int}%0f" // TODO: try

      // ---------------------------------------------------------------------------
      def toLocalDateTime: LocalDateTime = java.time.LocalDateTime.ofEpochSecond(nmbr.assertRange(0, 2000000000), 0, TimeUtils.currentZoneOffset()) // in seconds; eg     1,647,447,105 -> 2022-03-16T12:11:45
      def toLocalDate    : LocalDate     = java.time.LocalDate    .ofEpochDay   (nmbr.assertRange(0, 50000))                                        // in days   ; eg            19,067 -> 2022-03-16
    }

    // ---------------------------------------------------------------------------
    implicit class Long_(val nmbr: Long) extends AnyVal {
      def isInBetween         (fromInclusive: Long, toExclusive: Long): Boolean = nmbr >= fromInclusive && nmbr <  toExclusive
      def isInBetweenInclusive(fromInclusive: Long, toInclusive: Long): Boolean = nmbr >= fromInclusive && nmbr <= toInclusive

      // ---------------------------------------------------------------------------
      def assertRange         (fromInclusive: Long, toExclusive: Long): Long    = nmbr.assert(_.isInBetween         (fromInclusive, toExclusive), x => s"out of range: ${x} ([${fromInclusive}, ${toExclusive}[)")
      def assertRangeInclusive(fromInclusive: Long, toInclusive: Long): Long    = nmbr.assert(_.isInBetweenInclusive(fromInclusive, toInclusive), x => s"out of range: ${x} ([${fromInclusive}, ${toInclusive}])")

      // ---------------------------------------------------------------------------
      def formatUsLocale: String = NumberUtils.IntegerFormatter.format(nmbr)
      def formatExplicit: String = formatUsLocale.replace(",", "")

      // ---------------------------------------------------------------------------
      def toInstant       : Instant       = java.time.Instant      .ofEpochMilli (nmbr.assertRange(0, 2000000000000L))                               // in milliseconds ; eg 1,647,447,105,888 -> 2022-03-16T16:11:45.888Z
      def toLocalDateTime : LocalDateTime = java.time.LocalDateTime.ofEpochSecond(nmbr.assertRange(0, 2000000000), 0, TimeUtils.currentZoneOffset()) // in seconds      ; eg     1,647,447,105 -> 2022-03-16T12:11:45
      def toLocalDate     : LocalDate     = java.time.LocalDate    .ofEpochDay   (nmbr.assertRange(0, 50000))                                        // in days         ; eg            19,067 -> 2022-03-16
    }

    // ===========================================================================
    implicit class Double_(val nmbr: Double) extends AnyVal {
      def isInBetween         (fromInclusive: Double, toExclusive: Double): Boolean = nmbr >= fromInclusive && nmbr <  toExclusive
      def isInBetweenInclusive(fromInclusive: Double, toInclusive: Double): Boolean = nmbr >= fromInclusive && nmbr <= toInclusive

      // ---------------------------------------------------------------------------
      def assertRange         (fromInclusive: Double, toExclusive: Double): Double  = nmbr.assert(_.isInBetween         (fromInclusive, toExclusive), x => s"out of range: ${x} ([${fromInclusive}, ${toExclusive}[)")
      def assertRangeInclusive(fromInclusive: Double, toInclusive: Double): Double  = nmbr.assert(_.isInBetweenInclusive(fromInclusive, toInclusive), x => s"out of range: ${x} ([${fromInclusive}, ${toInclusive}])")

      // ---------------------------------------------------------------------------
      def isNanOrInfinity: Boolean = nmbr.isNaN || nmbr.isInfinity

      // ---------------------------------------------------------------------------
      def combine(n: Number)(f: (Double, Double) => Double): Double = f(nmbr, n.doubleValue)

        def add       (n: Number): Double = combine(n)(_ + _)
        def subtract  (n: Number): Double = combine(n)(_ - _)
        def divideBy  (n: Number): Double = combine(n)(_ / _)
        def multiplyBy(n: Number): Double = combine(n)(_ * _)

      // ---------------------------------------------------------------------------
      def exp : Double = math.exp  (nmbr.doubleValue)
      def log2: Double = math.log10(nmbr.doubleValue) / math.log10(2.0)

      // ---------------------------------------------------------------------------
      def formatUsLocale               : FormattedNumber = NumberUtils.NumberFormatter.format(nmbr)
      def formatExplicit               : FormattedNumber = f"${nmbr}%.16f".stripTrailingZeros
      def formatDecimals(decimals: Int): FormattedNumber = s"%.${decimals}f".format(nmbr)

      //FIXME: t210123101634 - significantFigures vs maxDecimal
        def significantFigures                   : Double = significantFigures(2)
        def significantFigures(setPrecision: Int): Double = NumberUtils.significantFigures(nmbr, setPrecision)
        def maxDecimals(n: Int): Double = org.apache.commons.math3.util.Precision.round(nmbr, n.require(_ >= 0))
    }

  // ===========================================================================
  // ===========================================================================
  // ===========================================================================
  implicit class Instant_(instant: Instant) { import TimeUtils._
      def formatIso: String = DateTimeFormatter.ISO_INSTANT.format(instant) // default behavior but more explicit

      // ---------------------------------------------------------------------------
      def atLocal        : LocalDateTime  = LocalDateTime.ofInstant(instant, DefaultZoneId)
      def atOffsetDefault: OffsetDateTime = instant.atOffset                (currentZoneOffset())
      def atZoneDefault  : ZonedDateTime  = instant.atZone                  (DefaultZoneId)

      // ---------------------------------------------------------------------------
      def truncateToSeconds: Instant = instant.truncatedTo(java.time.temporal.ChronoUnit.SECONDS)

      // ---------------------------------------------------------------------------
      def offsetFor(zone: ZoneId): ZoneOffset = zone.getRules.getOffset(instant)
    }

    // ===========================================================================
    implicit class LocalDateTime_(date: LocalDateTime) { import TimeUtils._
      def formatIso: String = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date) // default behavior but more explicit

      // ---------------------------------------------------------------------------
      def atInstantDefault: Instant        = date.toInstant(currentZoneOffset())
      def atOffsetDefault : OffsetDateTime = date.atOffset (currentZoneOffset())
      def atZoneDefault   :  ZonedDateTime = date.atZone   (DefaultZoneId)

      // ---------------------------------------------------------------------------
      def truncateToSeconds: LocalDateTime = date.truncatedTo(java.time.temporal.ChronoUnit.SECONDS)
    }

    // ===========================================================================
    implicit class OffsetDateTime_(date: OffsetDateTime) {
      def formatIso: String = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(date) // default behavior but more explicit

      // ---------------------------------------------------------------------------
      def atInstant     : Instant       = date.toInstant // for consistency
      def atLocal       : LocalDateTime = date.toLocalDateTime
      def atZoneDefault : ZonedDateTime = date.toLocalDateTime.atZoneDefault // also see atZoneSameInstant and atZoneSimilarLocal

      // ---------------------------------------------------------------------------
      def truncateToSeconds: OffsetDateTime = date.truncatedTo(java.time.temporal.ChronoUnit.SECONDS)
    }

    // ===========================================================================
    implicit class ZonedDateTime_(date: ZonedDateTime) {
      def formatIso: String = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(date) // default behavior but more explicit

      // ---------------------------------------------------------------------------
      def atInstant      : Instant        = date.toInstant // for consistency
      def atLocal        : LocalDateTime  = date.toLocalDateTime
      def atOffsetDefault: OffsetDateTime = date.toLocalDateTime.atOffsetDefault // no direct way?

      // ---------------------------------------------------------------------------
      def truncateToSeconds: ZonedDateTime = date.truncatedTo(java.time.temporal.ChronoUnit.SECONDS)
    }

  // ===========================================================================
  implicit class LocalDate_(date: LocalDate) {
      def formatIso: String = DateTimeFormatter.ISO_DATE.format(date) // default behavior but more explicit
    }

    // ---------------------------------------------------------------------------
    implicit class LocalTime_(time: LocalTime) {
      def formatIso                : String = DateTimeFormatter.ISO_TIME             .format(time) // default behavior but more explicit

      def formatHoursMinutes       : String = DateTimeFormatter.ofPattern("HH:mm")   .format(time) // TODO: cache
      def formatHoursMinutesSeconds: String = DateTimeFormatter.ofPattern("HH:mm:ss").format(time) // TODO: cache
    }

  // ===========================================================================
  implicit class ArrayByte_[T](bytes: Array[Byte]) {
    def toHexString    : String = BinaryUtils.bytesToHexString(bytes)

    // ---------------------------------------------------------------------------
    def toBase64       : String = BinaryUtils.bytesToBase64   (bytes)
    def toBase64Trimmed: String = BinaryUtils.bytesToBase64   (bytes).takeWhile(_ != '=')

    // ---------------------------------------------------------------------------
    def toUsAsciiString: String = new String(bytes, java.nio.charset.StandardCharsets.US_ASCII  )
    def toIsoString    : String = new String(bytes, java.nio.charset.StandardCharsets.ISO_8859_1)
    def toUtf8String   : String = new String(bytes, java.nio.charset.StandardCharsets.UTF_8     )
  }

  // ===========================================================================
  implicit class Throwable_(val throwable: Throwable) extends AnyVal {
    def       stackTrace: Seq[StackTraceElement] = throwable.getStackTrace.toList
    def formatStackTrace: String                 = ThrowableUtils.stackTraceString(throwable)
  }

  // ---------------------------------------------------------------------------
  implicit class Class_[A](val klass: Class[A]) extends AnyVal {
    def fullPath: String = klass.getCanonicalName.replace(".package.", ".") /* TODO */.replaceAll("\\$$", "")
  }

  // ---------------------------------------------------------------------------
  implicit class URL_(url: java.net.URL) {
    def smartCloseabledInputStream: Closeabled[java.io.InputStream] = InputStreamUtils.smartCloseabledInputStream(url.openStream())
  }

  // ---------------------------------------------------------------------------
  implicit class InputStream_(is: java.io.InputStream) {
    def closeabledBufferedReader                  : aptus.Closeabled[java.io.BufferedReader] = InputStreamUtils.closeabledBufferedReader(is, aptus.UTF_8)
    def closeabledBufferedReader(charset: Charset): aptus.Closeabled[java.io.BufferedReader] = InputStreamUtils.closeabledBufferedReader(is, charset)
  }

  // ---------------------------------------------------------------------------
  implicit class ResultSet_(val rs: java.sql.ResultSet) extends AnyVal {
    def closeable = new java.io.Closeable { override def close() = { rs.close() } }
    def rawRdbmsEntries : Iterator[RawRdbmsEntries] = SqlUtils.rawRdbmsEntries(rs)
  }

  // ---------------------------------------------------------------------------
  implicit class Future_[T](fut: concurrent.Future[T]) {
    def awaitIndefinitely() = concurrent.Await.result(fut, concurrent.duration.Duration.Inf)
  }

}

// ===========================================================================
