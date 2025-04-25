package aptus
package aptdata
package io
package inferring
package table

import aptus.{Map_, Seq_}
import aptus.aptdata.meta.schema.AptusMetaContainer_
import io.table.CellConf

// ===========================================================================
class TableSchemaInferrer[$Multiple, $Single](
    attemptKey: ($Single, Key) => Option[AnyValue],
    consumeSelfClosing: $Multiple => CloseabledIterator[$Single]) {

  // ---------------------------------------------------------------------------
  def fullInferring(conf: CellConf, keys: Keyz)(z: $Multiple): Cls =
      infoLookup(conf)(
            keySet  = keys.values.toSet,
            mutable = new MutableValuesSubset(keys, max = 3 /* enough for boolean detection at least */))(
          z)
        .pipe { lookup =>
          keys
            .map { key => Fld(key, lookup(key)) }
            .pipe(Cls.apply) }

    // ---------------------------------------------------------------------------
    private def infoLookup(conf: CellConf)(keySet: Set[Key], mutable: MutableValuesSubset)(z: $Multiple): Map[Key, Info] =
        consumeSelfClosing(z)
          .foldLeft(Set[(Key, Info)]()) { (curr, o) =>
            tmp(conf, keySet, mutable)(curr, o) }
          .toSeq
          .groupByKey
          .mapMapValues(TableSchemaInferrer.combineInfosAll)
          .map { case (key, info) =>
            key -> mutable.potentiallyUpdateInfo(key, info) }

      // ---------------------------------------------------------------------------
      private def tmp(conf: CellConf, keySet: Set[Key], mutable: MutableValuesSubset)(curr: Set[(Key, Info)], o: $Single): Set[(Key, Info)] = {
        curr ++
          keySet
            .map { key =>
              val value: String =
                (attemptKey(o, key) match {
                    case Some(s: String) => s
                    case _               => aptus.illegalState("guaranteed present by 201215141231") })
                  .tap {
                    conf
                      .valueSet(_)
                      .pipe(mutable.addValues(key, _)) }

              key -> conf.inferInfo(value) } } }

// ===========================================================================
object TableSchemaInferrer {

  private def combineInfosAll   (values: Seq[Info]): Info = _combineInfos(values)(_.map(_.forceBasicType).pipe(aptus.aptdata.meta.basic.combine))
          def combineInfosString(values: Seq[Info]): Info = _combineInfos(values)(_ => BasicType._String)

    // ---------------------------------------------------------------------------
    private def _combineInfos(values: Seq[Info])(f: Seq[Info] => BasicType): Info =
      values
        .map(_.container1)
        .reduceLeft(Container.combine)
        .info(f(values)) }

// ===========================================================================
