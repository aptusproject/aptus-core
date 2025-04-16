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
  def valewsIterator: Iterator[Valew]      = valuesIterator.map(Valew.build)

  // ---------------------------------------------------------------------------
  def entries: Entries = Entries.build(data)

  @inline def galliaPairs = entries.map(_.galliaPair) /* for compatibility with aptus' dyn */ }

// ===========================================================================
