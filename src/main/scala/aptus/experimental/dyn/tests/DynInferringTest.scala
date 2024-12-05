package aptus
package experimental
package dyntest

// ===========================================================================
object DynInferringTest {
  import dyn._
  import Dyn .dyn

  // ---------------------------------------------------------------------------
  import meta._
  private val c1 = cls(foo.string, baz.int)
  private val c3 = cls(p.cls(c1))

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {
    _Sngl1.inferSchema.check(c1)

    // ---------------------------------------------------------------------------
    // multiple

    _Mult1                .inferSchema                    .check(c1)
    _Mult1.asDynz         .inferSchemaAndConsumeEntirely().check(c1)
    _Mult1.asDynz.take(10).inferSchemaAndConsumeEntirely().check(c1)

    _Mult1.append(dyn(baz -> 3)).inferSchema.check(c1.toOptional(foo))

    // ---------------------------------------------------------------------------
    // nesting
    _Sngl3.inferSchema.check(c3)

    // ===========================================================================
    // heterogenous data

    Try { _Mult1.append(dyn(foo -> 3)).inferSchema }
      // for now we error out (see task t241129144211)
      .checkException(classOf[_root_.gallia.inferring.SchemaInferrerUtils.IncompatibleInfoException])

    // ===========================================================================
    // from JSON:

    JsonObjectFilePath.dyn         .check(_Sngl1r) // 1.0
    JsonObjectFilePath.dyn.fromJson.check(_Sngl1a) // 1.0 -> 1
    _Sngl1r               .fromJson.check(_Sngl1a) // 1.0 -> 1

    // ---------------------------------------------------------------------------
    _Mult1r                        .check(_Mult1r) // 1.0
    _Mult1r               .fromJson.check(_Mult1)  // 1.0 -> 1
    JsonLinesFilePath.dyns.fromJson.check(_Mult1)  // 1.0 -> 1
    JsonArrayFilePath.dyns.fromJson.check(_Mult1)  // 1.0 -> 1

    // ===========================================================================
    // from tables

    TsvFilePath.dyns                           .check(_Mult1s) // "1"
    TsvFilePath.dyns.convertFromTable(foo, baz).check(_Mult1a) // "1" -> 1
    _Mult1s         .convertFromTable(foo, baz).check(_Mult1a) // "1" -> 1
    _Mult1s         .convertFromTableCostly    .check(_Mult1a) // "1" -> 1
  }

}

// ===========================================================================s