package aptus
package aptdata
package aillag
package inferring

import aptus.aptdata.meta.basic.BasicType.{_Enm => _, _}
import scala.{Any => AnySingleValue}

// ===========================================================================
class SchemaInferrer[$Jbo](
    entries   : $Jbo => Seq[(Symbol/*BKey*/, AnyValue)],
    nestingOpt: AnySingleValue => Option[$Jbo]) {
  import SchemaInferrerUtils._

  // ===========================================================================
  def klass(values: Seq[$Jbo]): Cls = values.iterator.pipe(klass)

  // ---------------------------------------------------------------------------
  def klass(values: Iterator[$Jbo]): Cls =
    values
      .map(klass(_))
      .reduceLeft(_ combine _)

  // ---------------------------------------------------------------------------
  def klass(values: aptus.CloseabledIterator[$Jbo]): Cls =
    values
      .map(klass(_))
      .reduceLeft(_ combine _)

  // ---------------------------------------------------------------------------
  def klass(
o: aptus.aptdata.sngl.DynData): Cls =
    o .galliaPairs
      .map { case (key, value) =>
        Fld(key, info(value)) }
      .pipe(Cls.apply)

  // ---------------------------------------------------------------------------
  def klass(o: $Jbo): Cls =
    o .pipe(entries)
      .map { case (key, value) =>
        Fld(key, info(value)) }
      .pipe(Cls.apply)

  // ===========================================================================
  def info(value: AnyValue): Info =
      value match {
        case seq: Seq[_] =>
          // a201113123227 - no heterogenous arrays; TODO: t250424143819 - also for dyn?
          Info.nes(//FIXME: pes/nes issue?
            if (nestingOpt(seq.head).nonEmpty) seq.map(_.asInstanceOf[$Jbo]).pipe(klass)
            else                               valueType(seq.head))
        case _ =>
          Info.one(valueType(value)) }

    // ---------------------------------------------------------------------------
    @PartialTypeMatching
    @NumberAbstraction
    def valueType(value: AnySingleValue): ValueType =
      nestingOpt(value) match {
        case Some(nesting) => klass(nesting)
        case None          => ValueTypeAnalyzer(value) } }

// ===========================================================================