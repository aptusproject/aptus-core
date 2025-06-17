package aptus
package aptdata
package ops
package sngl

// ===========================================================================
trait SingleAddReplacePutOps { sngl: Dyn =>

  override final protected[ops] def _add    (target: NoRenarget, value: NakedValue): Dyn = _noRenarget(target) { (x, y) => x.add    (y -> value) }
  override final protected[ops] def _replace(target: NoRenarget, value: NakedValue): Dyn = _noRenarget(target) { (x, y) => x.replace(y -> value) }
  override final protected[ops] def _put    (target: NoRenarget, value: NakedValue): Dyn = _noRenarget(target) { (x, y) => x.put    (y -> value) }

    // ---------------------------------------------------------------------------
    // add: must not exist
    override final def add(entry: Entry)        : Dyn = Dyn.build(data :+ entry)   // errors out if already present, use .put() otherwise
    override final def add(entries: List[Entry]): Dyn = Dyn.build(data ++ entries) // errors out if already present, use .put() otherwise

    // ---------------------------------------------------------------------------
    // replace: must exist
    override final def replace(key: Key, value: NakedValue): Dyn = transformGuaranteed(key.target).using { _ => value }
    override final def replace(entries: List[Entry])       : Dyn = entries.foldLeft(sngl) { (x, entry) => x.replace(entry) }

    // ---------------------------------------------------------------------------
    // put: may or may not exist
    override final def put(entries: List[Entry])       : Dyn = entries.foldLeft(sngl) { (x, entry) => x.put(entry) }
    override final def put(key: Key, value: NakedValue): Dyn =
        if (containsKey(key)) replace(key).withValue(value)
        else                      add(key,           value) }

// ===========================================================================