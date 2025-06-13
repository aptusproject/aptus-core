package aptus
package aptdata
package ops
package common

// ===========================================================================
/** ARP = add, replace, put;
  only for abstract methods and methods that can be defined based on those */
trait CommonAddReplacePutTrait[Data] {
  @abstrct protected[ops] def _add    (target: NoRenarget, value: NakedValue): Data
  @abstrct protected[ops] def _replace(target: NoRenarget, value: NakedValue): Data
  @abstrct protected[ops] def _put    (target: NoRenarget, value: NakedValue): Data

  // ---------------------------------------------------------------------------
  @nonovrd final def add    (target: NoRenarget): _Add     = new _Add    (target)
  @nonovrd final def replace(target: NoRenarget): _Replace = new _Replace(target)
  @nonovrd final def put    (target: NoRenarget): _Put     = new _Put    (target)

    // ---------------------------------------------------------------------------
    class _Add     private[aptdata] (target: NoRenarget) { def     value(value: NakedValue): Data = _add    (target, value) }
    class _Replace private[aptdata] (target: NoRenarget) { def withValue(value: NakedValue): Data = _replace(target, value) }
    class _Put     private[aptdata] (target: NoRenarget) { def     value(value: NakedValue): Data = _put    (target, value) }
  
  // ---------------------------------------------------------------------------
  // add: must not exist
  @abstrct       def add(entry: Entry)                               : Data
  @abstrct       def add(entries: List[Entry])                       : Data

  @nonovrd final def add(entry1 : Entry, entry2: Entry, more: Entry*): Data = (Seq(entry1, entry2) ++ more) .toList.pipe(add)
  @nonovrd final def add(entries: ListMap[SKey, NakedValue])         : Data = entries.map(Entry._fromString).toList.pipe(add)

  // ---------------------------------------------------------------------------
  // replace: must exist
  @abstrct       def replace(entries: List[Entry]): Data
  @nonovrd final def replace(entry: Entry)        : Data = replace(entry.key, entry.valew.naked) // must exist

  @nonovrd final def add    (key: Key, value: NakedValue): Data = add(Entry.buildn(key, value)) // errors out if already present, use .put() otherwise
  @abstrct       def replace(key: Key, value: NakedValue): Data

  // ---------------------------------------------------------------------------
  // put: may or may not exist
  @abstrct       def put(key: Key, value: NakedValue): Data
  @abstrct       def put(entries: List[Entry])       : Data
  @nonovrd final def put(entry: Entry)               : Data = put(entry.key, entry.valew.naked) }

// ===========================================================================

