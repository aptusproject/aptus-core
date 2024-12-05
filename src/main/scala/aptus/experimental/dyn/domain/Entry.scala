package aptus
package experimental
package dyn
package domain

// ===========================================================================
/** basically a bare version of Dyn */
case class Entries private (values: Seq[Entry]) extends Items[Entries, Entry] {
  val const = Entries.apply

  def keys: Keys = values.map(_.key) }

// ===========================================================================
case class Entry private (key: Key, valew: Valew) {
    def galliaPair1: (Symbol, Any) = key.und  -> valew.naked
    def galliaPair2: (String, Any) = key.name -> valew.naked

    // ===========================================================================
    def retainOpt(targets: KeySet): Option[Entry] =
      key
        .in.someIf(targets.contains)
        .map(Entry(_, valew))

    // ---------------------------------------------------------------------------
    def removeOpt(targets: KeySet): Option[Entry] =
      key
        .in.noneIf(targets.contains)
        .map(Entry(_, valew))

    // ---------------------------------------------------------------------------
    def rename(mapping: Map[Key, Key]): Entry = Entry(mapping.getOrElse(key, key), valew)

    // ---------------------------------------------------------------------------
    def rename(ren: Ren): Entry =
      Entry(
        key = if (key == ren.from) ren.to else key,
        valew) }

  // ===========================================================================
  object Entry {
    def buildw(pair: (Key, Valew))     : Entry = Entry(pair._1, pair._2)
    def buildn(pair: (Key, NakedValue)): Entry = Entry(pair._1, Valew.potentiallyUnwrap(pair._2))

    // ---------------------------------------------------------------------------
    def fromGallia(pair: (Symbol, Any)): Entry = Entry(pair._1, Valew.build(pair._2))
    def fromGallia2(pair: (String, Any)): Entry = Entry(pair._1, Valew.build(pair._2))

    // ---------------------------------------------------------------------------
    implicit def _fromSymbol(x: (Symbol, NakedValue)): Entry = Entry(x._1, Valew.build(x._2))
    implicit def _fromString(x: (String, NakedValue)): Entry = Entry(x._1, Valew.build(x._2))
    implicit def _fromKey   (x: (Key,    NakedValue)): Entry = Entry(x._1, Valew.build(x._2)) }

// ===========================================================================