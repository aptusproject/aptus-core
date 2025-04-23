package aptustesting
package testmeta

import aptus.aptdata.meta.schema._

// ===========================================================================
object TestMeta {

  case class Person(name: String, age: Int, phones: Seq[String], addresses: Seq[Address])
      case class Address(street: String, city: String, primary: Boolean)

    // ---------------------------------------------------------------------------
    case class PersonAlt(first: String, last: String, phones: Seq[String], addresses: Seq[Address])

  // ---------------------------------------------------------------------------
  val PersonSchema: Cls =
    cls(
      "name"      .string,
      "age"       .int,
      "phones"    .strings,
      "addresses" .clss(
          "street" .string,
          "city"   .string,
          "primary".boolean))

  // ===========================================================================
  case class MyCaseClass(myString: String, myInt: Int)

  // ---------------------------------------------------------------------------
  type MyCaseClassAlias = MyCaseClass

  // ---------------------------------------------------------------------------
       class MyAnyVal1(val b: Boolean) extends AnyVal
  case class MyAnyVal2(    b: Boolean) extends AnyVal

  // ===========================================================================
  case class MyComplexData(
        b            : Boolean,

        myString     :            String   ,
        myOptInt     : Option[    Int     ],
        myDoubles    :        Seq[Double ] ,
        myOptBooleans: Option[Seq[Boolean]],

        myObj        :            MyComplexSubData ,
        myOptObj     : Option[    MyComplexSubData],
        myObjs       :        Seq[MyComplexSubData],
        myOptObjs    : Option[Seq[MyComplexSubData]],

        myClosing    : Boolean)

      // ---------------------------------------------------------------------------
      case class MyComplexSubData(
          mySubString: String,
          mySubInt   : Int)

  // ---------------------------------------------------------------------------
  lazy val MyComplexDataSchema: Cls =
      cls(
        "b"               	.boolean  ,
        "myString"        	.string   ,
        "myOptInt"        	.int_     ,
        "myDoubles"       	.doubles  ,
        "myOptBooleans"   	.booleans_,
        "myObj"           	.cls  (MyComplexSubDataSchema),
        "myOptObj"        	.cls_ (MyComplexSubDataSchema),
        "myObjs"          	.clss (MyComplexSubDataSchema),
        "myOptObjs"       	.clss_(MyComplexSubDataSchema),
        "myClosing"       	.boolean)

    // ---------------------------------------------------------------------------
    lazy val MyComplexSubDataSchema: Cls =
      cls(
        "mySubString".string,
        "mySubInt"   .int) }

// ===========================================================================