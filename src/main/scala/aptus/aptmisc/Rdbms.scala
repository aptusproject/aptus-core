package aptus
package aptmisc

import scala.util.chaining._
import java.sql.{Array => _, _}

// ===========================================================================
object Rdbms {
  def apply(uri: UriString)        = new UriQuerier(new URI(uri))
  def apply(uri: URI)              = new UriQuerier(uri)
  def apply(conn: Connection)      = new ConnectionQuerier(conn)

  // ---------------------------------------------------------------------------
  def apply(ps: PreparedStatement) = new PreparedStatementQuerier(ps)

  // ===========================================================================
  implicit class SqlConnection__(conn: java.sql.Connection) {
    def closeable = new Closeable { def close() = conn.close() }

    def querier = new ConnectionQuerier(conn)

    def querier(query: QueryString) = conn.prepareStatement(query).pipe(new PreparedStatementQuerier(_))
  }

  // ---------------------------------------------------------------------------
  implicit class PreparedStatement_(ps: PreparedStatement) {
    def parameterCount: Int = ps.getParameterMetaData.getParameterCount

    def set(index: Int, value: Any): PreparedStatement = { ps.setObject(index, value); ps }

    def setAll(value1: Any, more: Any*): PreparedStatement = {
      val values = value1 +: more
      require(values.size == parameterCount, (values.size, parameterCount, values.#@@))

      ps.clearParameters()
      values.zipWithRank.foldLeft(ps)((tmp, entry) => tmp.set(entry._2, entry._1))
    }
  }

  // ===========================================================================
  final class ConnectionQuerier(conn: Connection) extends BasicQuerier with Closeable {
    def columns(query: QueryString): Columns = super.columns(conn)(query)

    // ---------------------------------------------------------------------------
    def close() = { conn.close() }

    // ---------------------------------------------------------------------------
    /** beware: unsanitized! - TODO: t210114145431 - see https://stackoverflow.com/questions/4333015/does-the-preparedstatement-avoid-sql-injection */
    def query(query: QueryString): (ResultSet, Closeable) = {
      val ps   : PreparedStatement = conn.prepareStatement(query)
      val rs   : ResultSet         = ps.executeQuery()

      (rs,
       new Closeable {
          override def close() = {
            rs.close()
            ps.close() } } )
    }
  }

  // ===========================================================================
  final class UriQuerier(uri: URI) extends BasicQuerier {
    private def connection(): Connection = DriverManager.getConnection(uri.toString)

    // ---------------------------------------------------------------------------
    def columns(query: QueryString): Columns = super.columns(connection())(query)

    // ---------------------------------------------------------------------------
    def selectAll(table: TableName): (ResultSet, Closeable) =
      table
        .require(_.nonEmpty)
        .require(_.forall(aptutils.CharUtils.AlphaNumericalWithUnderscoreSet.contains)) /* cheap sanitizing.. */
        .pipe(name => query(s"SELECT * FROM ${name}"))

    // ---------------------------------------------------------------------------
    /** beware: unsanitized! TODO: t210114145431 */
    def query(query: QueryString): (ResultSet, Closeable) = {
      val conn : Connection        = connection()

      val ps   : PreparedStatement = conn.prepareStatement(query)
      val rs   : ResultSet         = ps.executeQuery()

      (rs,
       new Closeable {
          override def close() = {
            rs  .close()
            ps  .close()
            conn.close() } } )
    }
  }

  // ===========================================================================
  final class PreparedStatementQuerier(ps: PreparedStatement) extends AdvancedQuerier with Closeable {
    def close() = { ps.close() }

    /** beware: unsanitized! */
    def query(f: PreparedStatement => PreparedStatement): (ResultSet, Closeable) = f(ps).executeQuery().pipe { rs => (rs, rs.closeable ) }
  }

  // ===========================================================================
  trait BasicQuerier {
    def query(query: QueryString): (ResultSet, Closeable)

    /** beware: unsanitized! - TODO: t210114145431 - see https://stackoverflow.com/questions/4333015/does-the-preparedstatement-avoid-sql-injection */
    final def query2(query: QueryString): aptus.Closeabled[ResultSet] = this.query(query).pipe(aptus.Closeabled.fromPair)

    // ---------------------------------------------------------------------------
    protected def columns(conn: Connection)(query: QueryString): Columns = {
      val ps   : PreparedStatement = conn.prepareStatement(query)
      val rs   : ResultSet         = ps.executeQuery()

      val m = rs.getMetaData
      rs.close()
      ps.close()

      Range
        .inclusive(1, m.getColumnCount)
        .map { i =>
          Column(
            index            = i - 1,
            name             = m.getColumnName(i),
            typeCode         = m.getColumnType(i),
            originalTypeName = m.getColumnTypeName(i),
            nullableOpt      = m.isNullable(i) match { // see java.sql.ResultSetMetaData
                                case 0 /* columnNoNulls         */ => Some(false)
                                case 1 /* columnNullable        */ => Some(true)
                                case 2 /* columnNullableUnknown */ => None },
            signed           = m.isSigned(i),
            precision        = m.getPrecision(i),
            scaleOpt         = m.getScale(i).in.noneIf(_ == 0)) }
        .toList
        .pipe(Columns.apply)
    }
  }

  // ---------------------------------------------------------------------------
  trait AdvancedQuerier {
    def query(f: PreparedStatement => PreparedStatement): (ResultSet, Closeable)
    def query                                           : (ResultSet, Closeable) = query(x => x)

    final def query2(f: PreparedStatement => PreparedStatement): aptus.Closeabled[ResultSet] = query(f).pipe(aptus.Closeabled.fromPair)
    final def query2                                           : aptus.Closeabled[ResultSet] = query   .pipe(aptus.Closeabled.fromPair)
  }

  // ===========================================================================
  case class Columns(values: Seq[Column]) {
      override def toString: String = formatDefault
        def formatDefault: String =
          values.map(_.formatDefault).joinln }

    // ---------------------------------------------------------------------------
    case class Column(
        index           : aptus.Index, // 0-based
        name            : String,
        typeCode        : Int /* see java.sql.Types */,
        originalTypeName: String, /* eg INT */
        nullableOpt     : Option[Boolean] /* None is unknown */,
        signed          : Boolean,
        precision       : Int,
        scaleOpt        : Option[Int] /* None is 0 */) {
      def nullable      : Boolean = nullableOpt.getOrElse(true) // in doubt

      def formatDefault: String = toString }

}

// ===========================================================================
