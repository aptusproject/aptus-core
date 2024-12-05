package aptus
package experimental
package dyn
package data
package sngl

// ===========================================================================
trait DynData {
  protected val data: List[Entry]

  // ---------------------------------------------------------------------------
  def  keys  :  Keys   = data.map(_.key)
  def skeys  : SKeys   = keys.map(_.name)
  def  keySet:  KeySet = keys.toSet

  // ---------------------------------------------------------------------------
  def values        : Seq     [NakedValue] = data.map(_.valew)
  def valuesIterator: Iterator[NakedValue] = values.iterator
  def valewsIterator: Iterator[Valew]      = valewsIterator.map(Valew.apply)

  // ---------------------------------------------------------------------------
  def entries: Entries = Entries(data) }

// ===========================================================================
