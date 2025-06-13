package aptustesting
package dyntest

import utest._

// ===========================================================================
object DynHeterogenousTypesTest extends TestSuite {
  import aptus.dyn._

  // ---------------------------------------------------------------------------
  private val in      : Dyns = _Mult1 .append(dyn(foo -> 3, baz -> 3))
  private val expected: Dyns = _Mult1d.append(dyn(foo -> 3, baz -> 3))

  // ---------------------------------------------------------------------------
  private val f: String => String = _.toUpperCase

  // ===========================================================================
  val tests = Tests {

    //TODO: with nesting too

    // ---------------------------------------------------------------------------
    test(Try { in.transformString(foo).using(_.toUpperCase) }.check(Error.TransformSpecificType))

    // ---------------------------------------------------------------------------
    test(_Mult1.transform(foo).string(f).check(_Mult1d)) // if always String, favor this, else:

    test(Try { in.transformGuaranteed(qux).using(identity) }.check(Error.TransformGuaranteeFailure))
    test(Try { in.transformGuaranteed(foo).using(_.string) }.check(Error.AccessAsSpecificType))

    test(Try { in.transformType(BasicType._String)(foo).using(f) }.check(Error.TransformSpecificType))

    test(forAll(in, out = expected)(
        _.transform(foo).ifString(f),
        _.transformIfString(foo).using(f),
        _.transformIfType(BasicType._String)(foo).using(f),
      //_.transformIfType[BasicType._String.type](foo).using(g)

        _.transform(foo).using { v => v.stringOpt.map(f).getOrElse(v) },
        _.transform(foo).using { v => if (v.isString) v.string.pipe(f) else v },
        _.transform(foo).using { v => v.basicType match { case BasicType._String => v.string.pipe(f); case _ => v }},
      )) }

  // ---------------------------------------------------------------------------
  private def forAll[T](in: T, out: T)(values: Function[T, T]*): Unit =
    values
      .foreach { g =>
        in.pipe(g).check(out) } }

// ===========================================================================
