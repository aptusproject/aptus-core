package aptus
package experimental
package dyn
package data
package mult
package list

import _root_.gallia._

// ===========================================================================
trait CanConvertFromTable { self: Dyns =>
  def convertFromTableCostly                   : Dyns =  convertFromTable(self.keysCostly.map(_.name))
  def convertFromTable(key1: SKey, more: SKey*): Dyns =  convertFromTable(key1 +: more)
  def convertFromTable(keys: Seq[SKey])        : Dyns = _convertFromTable(keys.map(Symbol.apply))

  // ---------------------------------------------------------------------------
  private def _convertFromTable(keys: Seq[Symbol]): Dyns = {
    val conf = new io.CellConf()
    inferTableSchema(conf)(keys).pipe(tableActualSchema =>
    endoMap(data.TableToGalliaData.convert(conf)(tableActualSchema))) }

  // ===========================================================================
  /** expects only strings in data */
  private[dyn] def inferTableSchema(conf: io.CellConf)(keys: Seq[Symbol]): meta.Cls =
    inferring.table.TableSchemaInferrer.fullInferring(conf, keys)(self) }

// ===========================================================================