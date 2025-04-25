package aptus
package aptdata
package mult
package list

import io.table.CellConf

// ===========================================================================
trait CanConvertFromTable { self: Dyns =>
  def convertFromTableCostly                   : Dyns =  convertFromTable(keysCostly.map(_.name))
  def convertFromTable(key1: SKey, more: SKey*): Dyns =  convertFromTable(key1 +: more)
  def convertFromTable(keys: Keyz)             : Dyns = _convertFromTable(keys)

  // ---------------------------------------------------------------------------
  private def _convertFromTable(keys: Keyz): Dyns = {
    val conf = new CellConf()
    val tableActualSchema: Cls = inferTableSchema(conf)(keys)

    endoMap(io.AptusTableParsing.convert(conf)(tableActualSchema)(_).pipe(aptdata._build)) }

  // ===========================================================================
  /** expects only strings in data */
  private[aptdata] def inferTableSchema(conf: CellConf)(keys: Keyz): aptdata.meta.schema.Cls =
    io.AptusTableSchemaInferrer.fullInferring(conf, keys)(self) }

// ===========================================================================