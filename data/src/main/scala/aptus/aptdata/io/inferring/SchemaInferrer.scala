package aptus
package aptdata
package io
package inferring

import scala.{Any => AnySingleValue}

// ===========================================================================
class SchemaInferrer[$Single](
    entries   : $Single => Seq[(Symbol/*BKey*/, AnyValue)],
    nestingOpt: AnySingleValue => Option[$Single]) {
  import SchemaInferrerUtils._

  // ===========================================================================
  def klass(values: Seq[$Single]): Cls = values.iterator.pipe(klass)

  // ---------------------------------------------------------------------------
  def klass(values: Iterator[$Single]): Cls =
    values
      .map(klass(_))
      .reduceLeft(_ combine _)

  // ---------------------------------------------------------------------------
  def klass(values: aptus.CloseabledIterator[$Single]): Cls =
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
  def klass(o: $Single): Cls =
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
            if (nestingOpt(seq.head).nonEmpty) seq.map(_.asInstanceOf[$Single]).pipe(klass)
            else                               valueType(seq.head))
        case _ =>
          Info.one(valueType(value)) }

    // ---------------------------------------------------------------------------
    @PartialTypeMatching
    @NumberAbstraction
    def valueType(value: AnySingleValue): ValueType =
      nestingOpt(value) match {
        case Some(nesting) => klass(nesting)
        case None          => BasicTypeAnalyzer(value) } }

// ===========================================================================