/* file automatically duplicated in gallia via c250115172022 - be careful when editing */
package aptus.experimental.dyn.aillag /* do not split this line */
package inferring
package table

import aptus.{Seq_, Map_}
import io.CellConf

// ===========================================================================
object TableSchemaInferrer {

  def fullInferring(conf: CellConf, keys: Seq[Key])(z: Objs): Cls =
      infoLookup(conf)(
            keySet  = keys.toSet,
            mutable = new MutableValuesSubset(keys, max = 3 /* enough for boolean detection at least */))(
          z)
        .pipe { lookup =>
          keys
            .map { key => Fld(key, lookup(key)) }
            .pipe(Cls.apply) }

    // ---------------------------------------------------------------------------
    private def infoLookup(conf: CellConf)(keySet: Set[Key], mutable: MutableValuesSubset)(z: Objs): Map[Key, Info] =
        z .consumeSelfClosing
          .foldLeft(Set[(Key, Info)]()) { (curr, o) =>
            tmp(conf, keySet, mutable)(curr, o) }
          .toSeq
          .groupByKey
          .mapMapValues(combineInfosAll)
          .map { case (key, info) =>
            key -> mutable.potentiallyUpdateInfo(key, info) }

      // ---------------------------------------------------------------------------
      private def tmp(conf: CellConf, keySet: Set[Key], mutable: MutableValuesSubset)(curr: Set[(Key, Info)], o: Obj): Set[(Key, Info)] = {
        curr ++
          keySet
            .map { key =>
              val value = o.string(key) // guaranteed present by 201215141231
                .tap {
                  conf
                    .valueSet(_)
                    .pipe(mutable.addValues(key, _)) }

              key -> conf.inferInfo(value) } }

  // ===========================================================================
  def stringsOnly(conf: CellConf, keys: Seq[Key])(z: Objs): Cls =
    stringsOnlyInfoLookup(conf)(keys.toSet)(z)
      .pipe { lookup =>
        keys
          .map { key => Fld(key, lookup(key)) }
          .pipe(Cls.apply) }

  // ---------------------------------------------------------------------------
  private def stringsOnlyInfoLookup(conf: CellConf)(keySet: Set[Key])(z: Objs): Map[Key, Info] =
    z .consumeSelfClosing
      .foldLeft(Set[(Key, Info)]()) { (curr, o) =>
        curr ++
          keySet
            .map { key =>
              val value = o.string(key) // guaranteed present by 201215141231
              val container = conf.inferContainerOnly(value)
              key -> container.info(BasicType._String) } }
      .toSeq
      .groupByKey
      .mapMapValues(combineInfosString)

  // ===========================================================================
  private def combineInfosAll   (values: Seq[Info]): Info = _combineInfos(values)(_.map(_.forceBasicType).pipe(aptus.aptdata.meta.basic.combine))
  private def combineInfosString(values: Seq[Info]): Info = _combineInfos(values)(_ => BasicType._String)

    // ---------------------------------------------------------------------------
    private def _combineInfos(values: Seq[Info])(f: Seq[Info] => BasicType): Info =
      values
        .map(_.container1)
        .reduceLeft(Container.combine)
        .info(f(values)) }

// ===========================================================================