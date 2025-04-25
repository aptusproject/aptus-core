package aptus
package aptdata
package aillag
package inferring
package table

import aptus.{Map_, Seq_}
import aptus.aptdata.meta.schema.AptusMetaContainer_
import io.CellConf

// ===========================================================================
class TableSchemaInferrer[$Multiple, $Single](
    consumeSelfClosing: $Multiple => CloseabledIterator[$Single],
    string: ($Single, Key) => String) {

  // ---------------------------------------------------------------------------
  def fullInferring(conf: CellConf, keys: Seq[Key])(z: $Multiple): Cls =
      infoLookup(conf)(
            keySet  = keys.toSet,
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
              val value = string(o, key) // guaranteed present by 201215141231
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
