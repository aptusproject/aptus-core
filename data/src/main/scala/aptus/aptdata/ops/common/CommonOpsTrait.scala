package aptus
package aptdata
package ops
package common

// ===========================================================================
/* only for abstract methods and methods that can be defined based on those */
trait CommonOpsTrait[Data] {
  @abstrct        def ensurePresent(key1: Key, more: Key*): Data
  @abstrct        def ensureMissing(key1: Key, more: Key*): Data

  // ===========================================================================
  @abstrct       def rename(from: Key): _Rename; abstract class _Rename private[aptdata] (from: Key) { def to(to: Key): Data } // TODO: to trait rather
  @abstrct       def rename(mapping: Map[Key, Key]): Data
  @nonovrd final def rename(ren1: Ren, more: Ren*) : Data = Rens(ren1 +: more).toMap.pipe(rename)

  // ===========================================================================
  @abstrct       def retain(targets: Set[Key])       : Data
  @nonovrd final def retain(target1: Key, more: Key*): Data = retain((target1 +: more).toSet)
  @nonovrd final def retain(targets: Seq[Key])       : Data = retain(targets.toSet)

  // ---------------------------------------------------------------------------
  @abstrct       def remove(targets: Set[Key])       : Data
  @nonovrd final def remove(targets: Seq[Key])       : Data = remove(targets.toSet)
  @nonovrd final def remove(target1: Key, more: Key*): Data = remove((target1 +: more).toSet)

  // ===========================================================================
  @abstrct       def add(entry: Entry)               : Data
  @nonovrd final def add(key: Key, value: NakedValue): Data = value.pipe(Valew.build).pipe(Entry.buildw(key, _)).pipe(add)

  @abstrct       def add(entries: List[Entry])                       : Data
  @nonovrd final def add(entry1 : Entry, entry2: Entry, more: Entry*): Data = (Seq(entry1, entry2) ++ more) .toList.pipe(add)
  @nonovrd final def add(entries: ListMap[SKey, NakedValue])         : Data = entries.map(Entry._fromString).toList.pipe(add)

  // ===========================================================================
  @abstrct def convert(key   : Key): ConvertOps[Data]
  @abstrct def nest   (nestee: Key):    NestOps[Data] }

// ===========================================================================
