package aptus
package aptdata
package mult
package list

import io.table.CellConf

// ===========================================================================
trait CanConvertFromTable { self: Dyns =>
  def convertFromTableCostly                   : Dyns =  convertFromTable(keysCostly.map(_.name))
  def convertFromTable(key1: SKey, more: SKey*): Dyns =  convertFromTable(key1 +: more)
  def convertFromTable(keys: Keyz)             : Dyns = {
    val conf = new CellConf()
    val tableActualSchema: Cls = io.AptusTableSchemaInferrer.fullInferring(conf, keys)(self)

    endoMap { dyn => aptdata._build(
      io.AptusTableParsing.convert(conf)(tableActualSchema)(dyn)) } } }

// ===========================================================================