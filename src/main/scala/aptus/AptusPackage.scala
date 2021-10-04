import org.apache.commons.lang3
import com.google.common.base.CaseFormat

import scala.collection.{mutable,immutable}
import scala.util.chaining._

import java.time._
import java.time.format.DateTimeFormatter
import java.lang.IllegalStateException

// ===========================================================================
package object aptus
    extends AptusAnnotations
    with    AptusAliases
    with    AptusCommonAliases {
  /* for extends AnyVals, see https://stackoverflow.com/questions/14929422/should-implicit-classes-always-extend-anyval */

  def illegalState   (x: Any*): Nothing = { throw new IllegalStateException   (x.toString) }
  def illegalArgument(x: Any*): Nothing = { throw new IllegalArgumentException(x.toString) }
  
  def listOrdering[T : Ordering]: Ordering[List[T]] = aptus.utils.SeqUtils.listOrdering[T]

  // ---------------------------------------------------------------------------
  def zip[T1, T2, T3]        (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3])                                  : Iterable[(T1, T2, T3)]         = a.zip(b).zip(c)              .map { case   ((a, b), c)         => (a, b, c) }
  def zip[T1, T2, T3, T4]    (a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4])                 : Iterable[(T1, T2, T3, T4)]     = a.zip(b).zip(c).zip(d)       .map { case  (((a, b), c), d)     => (a, b, c, d) }
  def zip[T1, T2, T3, T4, T5](a: Iterable[T1], b: Iterable[T2], c: Iterable[T3], d: Iterable[T4], e: Iterable[T5]): Iterable[(T1, T2, T3, T4, T5)] = a.zip(b).zip(c).zip(d).zip(e).map { case ((((a, b), c), d), e) => (a, b, c, d, e) }
  def zip[T1, T2](a: Iterable[T1], b: Iterable[T2]): Iterable[(T1, T2)] = a.zip(b) // for good measure, should favor: a.zip(b)  

  // ===========================================================================
  implicit class Unit_(val u: Unit) extends AnyVal { // TODO: t201213095810 anyway to add that to Predef? implicit class doesn't seem to work
    def environment = misc.Environment
    def fs          = misc.Fs
    def hardware    = misc.Hardware
    def random      = misc.Random                
    def reflect     = misc.Reflect
    def time        = misc.Time
    
    // ---------------------------------------------------------------------------
    // convenience
    
    //@fordevonly def tid(): Long = concurrency.threadId     
  }

  // ===========================================================================
  implicit class Anything_[A](private val a: A) extends AnyVal {
    def str = a.toString

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
      @inline def thn      [B     ]                    (f: A => B)           : B =              f(a)
              def thnIf            (test: Boolean)     (f: A => A)           : A = if (test)    f(a) else   a
              def thnIf    [B <: A](pred: A => Boolean)(f: A => B)           : A = if (pred(a)) f(a) else   a
              def thnOpt   [B     ](opt : Option[B]   )(f: B => A => A)      : A = opt.map(f(_)(a)).getOrElse(a)

      def sideEffect                          (f: A => Unit)              : A  = {                f(a)                ; a }
      def sideEffectIf    (pred: A => Boolean)(f: A => Unit)              : A  = { if (pred(a)) { f(a) }              ; a }
    
    // ---------------------------------------------------------------------------
    @fordevonly def __exit: A = { utils.ReflectionUtils.formatExitTrace(().reflect.stackTrace(), "intentionally stopping").p; System.exit(0); a }

    @fordevonly def p      : A = { System.out.println(a)     ; a      }
    @fordevonly def p__    : A = { System.out.println(a)     ; __exit }
    @fordevonly def pp     : A = { System.out.println(s"a\n"); a      }

    // "i" for "inspect"
    @fordevonly def i(f: A => Any                ): A = { System.out.println(               f(a)  ); a }
    @fordevonly def i(f: A => Any, prefix: String): A = { System.out.println(s"${prefix}\t${f(a)}"); a }

    // ---------------------------------------------------------------------------
    def assert(p: A => Boolean):              A = assert(p, identity)
    def assert(p: A => Boolean, f: A => Any): A = { Predef.assert(p(a), f(a)); a }

    def require(p: A => Boolean):              A = require(p, identity)
    def require(p: A => Boolean, f: A => Any): A = { Predef.require(p(a), f(a)); a }

    // ---------------------------------------------------------------------------
    // TODO: t210116165559 - rename to "in"?
    @deprecated def as: aptus.misc.As[A] = new aptus.misc.As[A](a)
                def in: aptus.misc.As[A] = new aptus.misc.As[A](a)

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
  }

  // ===========================================================================
  implicit class String_(val str: String) extends AnyVal {
    def symbol : Symbol  = Symbol(str)

    def regex  : Regex       = str.r // more explicit
    def pattern: JavaPattern = str.r.pattern

    // ---------------------------------------------------------------------------
    def systemCallLines(): Seq[String] = systemCall().splitBy("\n")

    import scala.language.postfixOps; def systemCall(): String = sys.process.Process(str) !!    

    // ===========================================================================
    def path = new misc.AptusPath(str)

      // ---------------------------------------------------------------------------
      // TODO: t211004131206 - move to path?
      def writeFileContent(path: String): FilePath = utils.FileUtils.writeContent(path, content = str)
      def  readFileContent()            : Content  = utils.FileUtils.readFileContent(path = str)
  
        // ---------------------------------------------------------------------------
        def readFileLines(): List[Line] = utils.FileUtils.readFileLines(path = str)
        def readFileTsv  (): List[Vector[Cell]] = readFileLines().map(_.splitBy("\t").toVector)    
        def readFileCsv  (): List[Vector[Cell]] = readFileLines().map(_.splitBy( ",").toVector)

        def streamFileLines(): (Iterator[Line],         Closeable) = utils.FileUtils.streamFileLines(path = str)
        def streamFileTsv  (): (Iterator[Vector[Cell]], Closeable) = str.streamFileLines().mapFirst(_.map(_.splitXsv('\t').toVector))
        def streamFileCsv  (): (Iterator[Vector[Cell]], Closeable) = str.streamFileLines().mapFirst(_.map(_.splitXsv(',') .toVector))

    // ===========================================================================
    def readUrlContent(): Content   = utils.UrlUtils.content(str)
    def readUrlLines()  : Seq[Line] = utils.UrlUtils.  lines(str)

    // ===========================================================================
    def prepend(prefix: String)                      = s"$prefix$str"
    def append (suffix: String)                      = s"$str$suffix"

    def surroundWith(boundary: String)               = s"$boundary$str$boundary"
    def surroundWith(prefix: String, suffix: String) = s"$prefix$str$suffix"

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

    // ===========================================================================
    // TODO: quite inefficient
      def indent                          : String = utils.StringUtils.indent(1, indenter = "\t")(str)
      def indent(n: Int                  ): String = utils.StringUtils.indent(n, indenter = "\t")(str)
      def indent(n: Int, indenter: String): String = utils.StringUtils.indent(n, indenter       )(str)

      // ---------------------------------------------------------------------------
      def indentAll                          : String = utils.StringUtils.indentAll(n = 1, indenter = "\t")(str)
      def indentAll(n: Int                  ): String = utils.StringUtils.indentAll(n    , indenter = "\t")(str)
      def indentAll(n: Int, indenter: String): String = utils.StringUtils.indentAll(n    , indenter       )(str)

      // ---------------------------------------------------------------------------
      def sectionAllOff                : String =                  utils.StringUtils.sectionAllOff(n = 1, indenter = "\t")(str)
      def sectionAllOff(n: Int)        : String =                  utils.StringUtils.sectionAllOff(n    , indenter = "\t")(str)

      def sectionAllOff(prefix: String): String = s"${prefix}\n" + utils.StringUtils.sectionAllOff(n = 1, indenter = "\t")(str)

    // ===========================================================================
    def isTrimmed     : Boolean = str == str.trim
    def isQuoted      : Boolean = str.size >= 2 && str.startsWith("\"") && str.endsWith("\"")
    def isSingleQuoted: Boolean = str.size >= 2 && str.startsWith( "'") && str.endsWith( "'")

    def isDigits      : Boolean = str.nonEmpty && str.forall(_.isDigit) // TODO: vs org.apache.commons.lang3.math.NumberUtils.isDigits?
    def isValidInt    : Boolean = utils.NumberUtils.isValidInt(str) // FIXME

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
    def splitXsv(sep: Char): List[Cell] = utils.StringUtils.splitXsv(str, sep)
    def splitTabs          : List[Cell] = splitXsv('\t')
    def splitCommas        : List[Cell] = splitXsv(',')

    // ===========================================================================
    def date = toLocalDateFromIso // "2021-01-08".date
      def toLocalDateFromIso                           : LocalDate     = DateTimeFormatter.ISO_LOCAL_DATE     .pipe(toLocalDate)
      def toLocalDate    (pattern:   String           ): LocalDate     = DateTimeFormatter.ofPattern(pattern) .pipe(toLocalDate)
      def toLocalDate    (formatter: DateTimeFormatter): LocalDate     = LocalDate.parse(str, formatter)

    def datetime = toLocalDateTimeFromIso // "2021-01-08T...".datetime
      def toLocalDateTimeFromIso                       : LocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.pipe(toLocalDateTime)
      def toLocalDateTime(pattern:   String           ): LocalDateTime = DateTimeFormatter.ofPattern(pattern) .pipe(toLocalDateTime)
      def toLocalDateTime(formatter: DateTimeFormatter): LocalDateTime = LocalDateTime.parse(str, formatter)

    // ===========================================================================
    def removeIfApplicable(potentialSubStr: String) = str.replace(potentialSubStr, "")

    // ---------------------------------------------------------------------------
    def stripTrailingZeros: String = if (!org.apache.commons.lang3.math.NumberUtils.isCreatable(str)) str else new java.math.BigDecimal(str).stripTrailingZeros().toPlainString()

    // ---------------------------------------------------------------------------
    def   quote      : String = if (isQuoted)       str else s""""$str""""
    def   quoteSingle: String = if (isSingleQuoted) str else   s"'$str'"
    def unquote      : String = utils.StringUtils.unquoteLeft(str).pipe(utils.StringUtils.unquoteRight)

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

    def jsonObject: com.google.gson.JsonObject = aptus.json.GsonParser.stringToJsonObject(str)
    def jsonArray : com.google.gson.JsonArray  = aptus.json.GsonParser.stringToJsonArray (str)

    def prettyJson : String = aptus.json.GsonFormatter.pretty (str).get
    def compactJson: String = aptus.json.GsonFormatter.compact(str).get
  }

  // ===========================================================================
  // TODO: switch it all to List? see https://users.scala-lang.org/t/seq-vs-list-which-should-i-choose/5412/16
  implicit class Seq_[A](val coll: Seq[A]) extends AnyVal { // TODO: t210124092716 - codegen specializations (List, Vector, ...?)
    def requireDistinct   ()           : Seq[A] = utils.SeqUtils.distinct(coll, Predef.require(_, _))
    def requireDistinctBy[B](f: A => B): Seq[A] = utils.SeqUtils.requireDistinctBy(coll)(f)

    // ---------------------------------------------------------------------------
    def writeFileLines(path: FilePath): FilePath = utils.FileUtils.writeLines(path, coll.map(_.toString))

    // ---------------------------------------------------------------------------
    def force = new aptus.misc.Force(coll) // TODO: conflicts with view's...

    // ---------------------------------------------------------------------------
    def join(sep: Separator) = coll.mkString(sep)
    def join                 = coll.mkString
    def joinln               = coll.mkString("\n")
    def joinlnln             = coll.mkString("\n\n")
    def jointab              = coll.mkString("\t")

    // ---------------------------------------------------------------------------
    def  @@ = coll.mkString("[", ", ", "]")
    def #@@ = s"#${coll.size}:${@@}"

    // ---------------------------------------------------------------------------
    def section                : String = section("")
    def section (title: String): String = utils.StringUtils.section(coll, 1, title)

    def section2               : String = section2("")
    def section2(title: String): String = utils.StringUtils.section(coll, 2, title)

    // ---------------------------------------------------------------------------
    def isDistinct                               : Boolean = coll.size == coll.toSet.size
    def isDisjointWith[B >: A](that: Iterable[B]): Boolean = coll.intersect(that.toSeq).isEmpty

    // ---------------------------------------------------------------------------
    def duplicates                : Seq[A] = coll.diff(coll.distinct)

    def filterBy        [B](p: B => Boolean)(f: A => B)= coll.filter   (x =>  p(f(x)))
    def filterByNot     [B](p: B => Boolean)(f: A => B)= coll.filterNot(x =>  p(f(x)))

    def mapIf    [B <: A](pred: A => Boolean)(f: A => B) = coll.map { x => if (pred(x)) f(x) else   x }

    // ---------------------------------------------------------------------------
    def zipWithRank: Seq[(A, aptus.Rank)] = coll.zipWithIndex.map { case (value, index) => value -> (index + 1) }

    def zipSameSize[B](that: Seq[B])                      : Seq[(A, B)] = zipSameSize(that, _.size)
    def zipSameSize[B](that: Seq[B], debug: Seq[_] => Any): Seq[(A, B)] = { require(coll.size == that.size, (debug(coll), debug(that))); coll.zip(that) }
    
    // ---------------------------------------------------------------------------
    def slidingList(n: Int): List[List[A]] = coll.sliding(n).toList.map(_.toList)
    def slidingPairs: List[(A, A)] = coll match {
        case Seq() | Seq(_) => Nil
        case seq            => seq.slidingList(2).map(_.force.tuple2) }

    // ---------------------------------------------------------------------------
    def splitHead: (A, Seq[A]) = coll.splitAt(            1).mapFirst (_.force.one)
    def splitLast: (Seq[A], A) = coll.splitAt(coll.size - 1).mapSecond(_.force.one)    
    
    // ---------------------------------------------------------------------------
    def distinctByAdjacency: Seq[A] = utils.IterableUtils.distinctByAdjacency(coll).toList // TODO: test for 1
    
    // ===========================================================================
    def mean(implicit num: Numeric[A]): Double = (num.toDouble(coll.foldLeft(num.zero)(num.plus)) / coll.size)

    // ---------------------------------------------------------------------------
    def stdev              (implicit num: Numeric[A]): Double = stdev(coll.mean(num))(num.asInstanceOf[Numeric[A]])
    def stdev(mean: Double)(implicit num: Numeric[A]): Double = utils.MathUtils.stdev(coll, mean)

    // ---------------------------------------------------------------------------
    def median               (implicit num: Numeric[A]): Double = utils.MathUtils.percentile(coll, 50)
    def percentile(n: Double)(implicit num: Numeric[A]): Double = utils.MathUtils.percentile(coll,  n)

    // ---------------------------------------------------------------------------
    def range[B >: A](implicit cmp: Ordering[B],  num: Numeric[B]): B      = num.minus(coll.max(cmp), coll.min(cmp)) // TODO: optimize; TODO: max if double?
    def IQR          (implicit                    num: Numeric[A]): Double = (coll.percentile(75) - coll.percentile(25)) // TODO: optimize

    // ===========================================================================
    def toMutableMap[K, V](implicit ev: A <:< (K, V))                   = utils.MapUtils.toMutableMap(coll)
    def toListMap   [K, V](implicit ev: A <:< (K, V))                   = utils.MapUtils.toListMap(coll)
    def toTreeMap   [K, V](implicit ev: A <:< (K, V), ord: Ordering[K]) = utils.MapUtils.toTreeMap(coll)

    // ---------------------------------------------------------------------------
    def groupByKey           [K, V](implicit ev: A <:< (K, V)                  ):               Map[K, Seq[V]] = utils.MapUtils.groupByKey           (coll.iterator.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithListMap[K, V](implicit ev: A <:< (K, V)                  ): immutable.ListMap[K, Seq[V]] = utils.MapUtils.groupByKeyWithListMap(coll.iterator.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithTreeMap[K, V](implicit ev: A <:< (K, V), ord: Ordering[K]): immutable.TreeMap[K, Seq[V]] = utils.MapUtils.groupByKeyWithTreeMap(coll.iterator.asInstanceOf[Iterator[(K, V)]])

    def countBySelf: List[(A, Int)] = coll.groupBy(identity).view.map { x => x._1 -> x._2.size }.toList.sortBy(-_._2) // TODO: t211004120452 - more efficient version
    
    // ===========================================================================
    def toOption[B](implicit ev: A <:< Option[B]): Option[Seq[B]] = if (coll.contains(None)) None else Some(coll.map(_.get))
  }

  // ===========================================================================
  implicit class Iterator_[A](val itr: Iterator[A]) extends AnyVal {
    def last(): A = itr.next().assert(_ => !itr.hasNext)
    
    // ---------------------------------------------------------------------------
    def groupByKey           [K, V](implicit ev: A <:< (K, V))                  :               Map[K, Seq[V]]  = utils.MapUtils.groupByKey              (itr.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithListMap[K, V](implicit ev: A <:< (K, V))                  : immutable.ListMap[K, Seq[V]]  = utils.MapUtils.groupByKeyWithListMap   (itr.asInstanceOf[Iterator[(K, V)]])
    def groupByKeyWithTreeMap[K, V](implicit ev: A <:< (K, V), ord: Ordering[K]): immutable.TreeMap[K, Seq[V]]  = utils.MapUtils.groupByKeyWithTreeMap   (itr.asInstanceOf[Iterator[(K, V)]])    
    def groupByPreSortedKey[K, V](implicit ev: A <:< (K, V))                    :         Iterator[(K, Seq[V])] = utils.IteratorUtils.groupByPreSortedKey(itr.asInstanceOf[Iterator[(K, V)]])
  }
  
  // ===========================================================================
  implicit class Map_[K, V](val mp: Map[K, V]) extends AnyVal {
    def force(key: K): V = mp.get(key).get // stdlib polyseme (see Option's)
    
    // ---------------------------------------------------------------------------
    def toMutableMap                        :   mutable.    Map[K, V] = utils.MapUtils.toMutableMap(mp)
    def toListMap                           : immutable.ListMap[K, V] = utils.MapUtils.toListMap   (mp)
    def toTreeMap(implicit ord: Ordering[K]): immutable.TreeMap[K, V] = utils.MapUtils.toTreeMap   (mp)
  }

  // ===========================================================================
  implicit class Option_[A](val opt: Option[A]) extends AnyVal {
    @inline def force = opt.get // stdlib polyseme (see Map's)
    
    def swap[B](f: => B): Option[B] = if (opt.isDefined) None else Some(f)
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
      def add       (n: Int): Int = nmbr + n
      def subtract  (n: Int): Int = nmbr - n
      
      def multiplyBy(n: Int)   : Int    = nmbr * n
      def multiplyBy(n: Number): Double = nmbr * n.doubleValue()
  
      def divideBy(n: Int)   : Int    = nmbr / n
      def divideBy(n: Number): Double = nmbr / n.doubleValue()    
      
      // ---------------------------------------------------------------------------
      def formatUsLocale: String = utils.NumberUtils.IntegerFormatter.format(nmbr)
      def formatExplicit: String = formatUsLocale.replace(",", "") //def formatExplicit: String = f"${int}%0f" // TODO: try      
    }

    // ---------------------------------------------------------------------------
    implicit class Long_(val nmbr: Long) extends AnyVal {
      def formatUsLocale: String = utils.NumberUtils.IntegerFormatter.format(nmbr)
      def formatExplicit: String = formatUsLocale.replace(",", "")
    }

    // ---------------------------------------------------------------------------
    implicit class Double_(val nmbr: Double) extends AnyVal {
      def isNanOrInfinity: Boolean = nmbr.isNaN || nmbr.isInfinity

      // ---------------------------------------------------------------------------
      def combine(n: Number)(f: (Double, Double) => Double): Double = f(nmbr, n.doubleValue)

        def add       (n: Number): Double = combine(n)(_ + _)
        def subtract  (n: Number): Double = combine(n)(_ - _)     
        def divideBy  (n: Number): Double = combine(n)(_ / _)
        def multiplyBy(n: Number): Double = combine(n)(_ * _)

      def log2(n: Number): Double = math.log10(n.doubleValue) / math.log10(2.0)
      
      // ---------------------------------------------------------------------------
      def formatUsLocale               : FormattedNumber = utils.NumberUtils.NumberFormatter.format(nmbr)
      def formatExplicit               : FormattedNumber = f"${nmbr}%.16f".stripTrailingZeros
      def formatDecimals(decimals: Int): FormattedNumber = s"%.${decimals}f".format(nmbr)

      //FIXME: t210123101634 - significantFigures vs maxDecimal
        def significantFigures                   : Double = significantFigures(2)
        def significantFigures(setPrecision: Int): Double = utils.NumberUtils.significantFigures(nmbr, setPrecision)
        def maxDecimals(n: Int): Double = org.apache.commons.math3.util.Precision.round(nmbr, n.require(_ >= 0))
    }

  // ===========================================================================
  implicit class Throwable_(val throwable: Throwable) extends AnyVal {
    def       stackTrace: Seq[StackTraceElement] = throwable.getStackTrace.toList              	
    def formatStackTrace: String                 = utils.ThrowableUtils.stackTraceString(throwable)  	
  }

  // ---------------------------------------------------------------------------
  implicit class Class_[A](val klass: Class[A]) extends AnyVal {
    def fullPath: String = klass.getCanonicalName.replace(".package.", ".") /* TODO */.replaceAll("\\$$", "")
  }

  // ===========================================================================
  implicit class URL_(url: java.net.URL) {
    def smartCloseabledInputStream: Closeabled[java.io.InputStream] = utils.InputStreamUtils.smartCloseabledInputStream(url.openStream())
  }

  // ---------------------------------------------------------------------------
  implicit class InputStream_(is: java.io.InputStream) {
    def closeabledBufferedReader                  : aptus.Closeabled[java.io.BufferedReader] = utils.InputStreamUtils.closeabledBufferedReader(is, UTF_8)
    def closeabledBufferedReader(charset: Charset): aptus.Closeabled[java.io.BufferedReader] = utils.InputStreamUtils.closeabledBufferedReader(is, charset)
  }

  // ===========================================================================
  implicit class ResultSet_(val rs: java.sql.ResultSet) extends AnyVal {
    def closeable = new java.io.Closeable { override def close() = { rs.close() } }
    def rawRdbmsEntries : Iterator[RawRdbmsEntries] = utils.SqlUtils.rawRdbmsEntries(rs)
  }
  
}

// ===========================================================================
