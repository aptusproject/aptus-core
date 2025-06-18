package aptus
package aptreflect
package nodes

import TypeNodeBuiltIns._

// ===========================================================================
object TypeNodeRuntimeParser {
  val Default = new TypeNodeRuntimeParser(PartialFunction.empty) }

// ---------------------------------------------------------------------------
class TypeNodeRuntimeParser(alt: PartialFunction[Any, TypeNode]) {

  def apply(vle: Any): TypeNode =
    alt
      .orElse(part)
      .apply (vle)

  // ---------------------------------------------------------------------------
  private val part: PartialFunction[Any, TypeNode] = {
      case None         => scalaNoneOfUnknownType
      case opt: Some[_] => scalaOption(apply(opt.get))

      // ---------------------------------------------------------------------------
      case Nil          => scalaNilOfUnknownType
      case seq: Seq[_]  => seq.map(apply).distinct match {
        case Seq(sole) => scalaSeq(sole)
        case _         => scalaSeq(ScalaAny) }

      // ---------------------------------------------------------------------------
      case set: Set[_]  => set.map(apply).toSeq match {
        case Seq(sole) => scalaSet(sole)
        case _         => scalaSet(ScalaAny) }

      // ---------------------------------------------------------------------------
      case arr: Array[_]  => arr.map(apply).toSeq.distinct match {
        case Seq(sole) => scalaArray(sole)
        case _         => scalaArray(ScalaAny) }

      // ---------------------------------------------------------------------------
      case map: Map[_, _] =>
        (map.keySet.map(apply).toSeq,
         map.values.map(apply).toSeq.distinct)
          match {
            case (Seq(sole1), Seq(sole2)) => scalaMap(sole1   , sole2)
            case (_         , Seq(sole2)) => scalaMap(ScalaAny, sole2)
            case (Seq(sole1), _         ) => scalaMap(sole1   , ScalaAny)
            case (_         , _         ) => scalaMap(ScalaAny, ScalaAny) }

      // ---------------------------------------------------------------------------
      case other => TypeNode.trivial(other.getClass.getName) } }

// ===========================================================================

