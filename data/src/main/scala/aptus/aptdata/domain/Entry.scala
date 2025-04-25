package aptus
package aptdata
package domain

// ===========================================================================
/** basically a bare version of Dyn */
case class Entries private (values: Seq[Entry])
      extends Items[Entries, Entry] {
    val const = Entries.apply

    // ---------------------------------------------------------------------------
    def keys: Seq[Key] = values.map(_.key)
    def keyz:     Keyz = values.map(_.key) }

  // ---------------------------------------------------------------------------
  object Entries {
    def build(values: Seq[Entry]): Entries = Entries(values) }

// ===========================================================================
case class Entry private (key: Key, valew: Valew) {
    def galliaPair: (Symbol, Any) = key.und -> valew.naked

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
    def fromGallia(pair: (Key, Any)): Entry = Entry(pair._1, Valew.build(pair._2))

    // ---------------------------------------------------------------------------
    implicit def _fromSymbol(x: (Symbol, NakedValue)): Entry = Entry(x._1, Valew.build(x._2))
    implicit def _fromString(x: (String, NakedValue)): Entry = Entry(x._1, Valew.build(x._2))
    implicit def _fromKey   (x: (Key,    NakedValue)): Entry = Entry(x._1, Valew.build(x._2)) }

// ===========================================================================