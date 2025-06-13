package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynInferringTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  import aptus.aptdata.meta.schema._
  private val c1 = cls(foo.string, baz.int)
  private val c3 = cls(p.cls(c1))

  // ===========================================================================
  val tests = Tests {
    test(_Sngl1.inferSchema.check(c1))

    // ---------------------------------------------------------------------------
    // multiple

    test(_Mult1                .inferSchema                    .check(c1))
    test(_Mult1.asDynz         .inferSchemaAndConsumeEntirely().check(c1))
    test(_Mult1.asDynz.take(10).inferSchemaAndConsumeEntirely().check(c1))

    test(_Mult1.append(dyn(baz -> 3)).inferSchema.check(c1.toOptional(foo)))

    // ---------------------------------------------------------------------------
    // nesting
    test(_Sngl3.inferSchema.check(c3))

    // ===========================================================================
    // heterogenous data

    test(Try { _Mult1.append(dyn(foo -> 3)).inferSchema }
      // for now we error out (see task t241129144211)
      .checkException(classOf[aptus.aptdata.io.inferring.SchemaInferrerUtils.IncompatibleInfoException]))

    // ===========================================================================
    // from JSON:

    test(single            (JsonObjectFilePath).check(_Sngl1_1_0)) // 1.0
    test(fromJsonObjectFile(JsonObjectFilePath).check(_Sngl1_1_0)) // 1.0
    test(JsonObjectFilePath.dyn         .check(_Sngl1_1_0)) // 1.0
    test(JsonObjectFilePath.dyn.fromJson.check(_Sngl1)) // 1.0 -> 1
    test(_Sngl1_1_0               .fromJson.check(_Sngl1)) // 1.0 -> 1

    // ---------------------------------------------------------------------------
    test(_Mult1r                        .check(_Mult1r)) // 1.0
    test(_Mult1r               .fromJson.check(_Mult1))  // 1.0 -> 1
    test(JsonLinesFilePath.dyns.fromJson.check(_Mult1))  // 1.0 -> 1
    test(JsonArrayFilePath.dyns.fromJson.check(_Mult1))  // 1.0 -> 1

    // ===========================================================================
    // from tables

    test(TsvFilePath.dyns                           .check(_Mult1s)) // "1"
    test(TsvFilePath.dyns.convertFromTable(foo, baz).check(_Mult1a)) // "1" -> 1
    test(_Mult1s         .convertFromTable(foo, baz).check(_Mult1a)) // "1" -> 1
    test(_Mult1s         .convertFromTableCostly    .check(_Mult1a)) // "1" -> 1
  }

}

// ===========================================================================s
