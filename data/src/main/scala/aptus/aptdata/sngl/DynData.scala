package aptus
package aptdata
package sngl

// ===========================================================================
trait DynData {
  protected val data: List[Entry]

  // ---------------------------------------------------------------------------
  def  keys  :  Seq[Key]   = data.map(_.key)
  def skeys  : SKeys   = keys.map(_.name)
  def  keySet:  KeySet = keys.toSet

  def  keyz  :  Keyz   = data.map(_.key).pipe(Keyz.apply)

  // ---------------------------------------------------------------------------
  def values        : Seq     [NakedValue] = data.map(_.valew)
  def valuesIterator: Iterator[NakedValue] = values.iterator
  def valewsIterator: Iterator[Valew]      = valuesIterator.map(Valew.build)

  // ---------------------------------------------------------------------------
  def entries: Entries = Entries.build(data) }

// ===========================================================================
