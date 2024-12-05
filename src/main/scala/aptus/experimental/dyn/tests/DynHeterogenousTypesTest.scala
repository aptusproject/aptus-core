package aptus
package experimental
package dyntest

// ===========================================================================
object DynHeterogenousTypesTest {
  import dyn._
  import Dyn .dyn
  import Dyns.dyns

  // ===========================================================================
  def main(args: Array[String]): Unit = { apply(); msg(getClass).p }

  def forAll[T](in: T, out: T)(values: Function[T, T]*): Unit =
    values
      .foreach { g =>
        in.pipe(g).check(out) }

  // ---------------------------------------------------------------------------
  def apply(): Unit = {

    //TODO: with nesting too
    {
      val in      : Dyns = _Mult1 .append(dyn(foo -> 3, baz -> 3))
      val expected: Dyns = _Mult1d.append(dyn(foo -> 3, baz -> 3))

      val f: String => String = _.toUpperCase

      // ---------------------------------------------------------------------------
      Try {
          in.transformString(foo).using(_.toUpperCase) }.check(Error.TransformSpecificType)

      // ---------------------------------------------------------------------------
      _Mult1.transform(foo).string(f).check(_Mult1d) // if always String, favor this, else:

      Try { in.transformGuaranteed(qux).using(identity) }.check(Error.TransformGuaranteeFailure)
      Try { in.transformGuaranteed(foo).using(_.string) }.check(Error.AccessAsSpecificType)

      forAll(in, out = expected)(
          _.transform(foo).ifString(f),
          _.transformIfString(foo).using(f),
          _.transformIfType(BasicType._String)(foo)(f),
        //_.transformIfType[BasicType._String.type](foo)(g)

          _.transform(foo).using { v => v.stringOpt.map(f).getOrElse(v) },
          _.transform(foo).using { v => if (v.isString) v.string.pipe(f) else v },
          _.transform(foo).using { v => v.basicType match { case BasicType._String => v.string.pipe(f); case _ => v }},
        ) } } }

// ===========================================================================