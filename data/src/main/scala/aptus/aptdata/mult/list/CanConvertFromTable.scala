package aptus
package aptdata
package mult
package list

import aillag.{io, inferring, data}

// ===========================================================================
trait CanConvertFromTable { self: Dyns =>
  def convertFromTableCostly                   : Dyns =  convertFromTable(keysCostly.map(_.name))
  def convertFromTable(key1: SKey, more: SKey*): Dyns =  convertFromTable(key1 +: more)
  def convertFromTable(keys: Seq[SKey])        : Dyns = _convertFromTable(keys.map(Key._fromString))

  // ---------------------------------------------------------------------------
  private def _convertFromTable(keys: Keys): Dyns = {
    val conf = new io.CellConf()
    val tableActualSchema = inferTableSchema(conf)(keys)

    endoMap(data.TableToGalliaData.convert(conf)(tableActualSchema)) }

  // ===========================================================================
  /** expects only strings in data */
  private[aptdata] def inferTableSchema(conf: io.CellConf)(keys: Keys): aptdata.meta.schema.Cls =
    inferring.table.TableSchemaInferrer.fullInferring(conf, keys)(self) }

// ===========================================================================