package aptus
package experimental
package dyn
package data
package sngl

// ===========================================================================
trait DynValewGetter //TODO: cleanup
        extends ValewGetter /* .get and .getOrElse */ {
      self: DynData
         with DynDataEntityErrorFormatterProvider
         with gallia.GalliaBorrowedOps /* eg nest, reorderKeysRecursively, ... */ =>

    override final protected[dyn] def get(key: Key): Option[Valew] =
      data.find(_.key == key).map(_.valew)

    // ---------------------------------------------------------------------------
//TODO: choose wich name to use
def    containsKey(key: Key): Boolean = isPresent(key)
def notContainsKey(key: Key): Boolean = isMissing(key)

//TODO: choose wich name to use
      /*private[dyn] */def attemptKey (key: Key): Option[NakedValue]  = get(key).map(_.naked)
      /*private[dyn] */def forceKey   (key: Key):        NakedValue   = get(key).map(_.naked).getOrElse(Error.CanNotForceKey(deef, key).thro)

    // ---------------------------------------------------------------------------
    def nakedValueOpt(key: Key): Option[NakedValue] = get(key).map(_.naked)

    // ===========================================================================
    def size: Size = data.size

    // ---------------------------------------------------------------------------
    def isMissing(key: Key): Boolean = !keySet.contains(key)
    def isPresent(key: Key): Boolean =  keySet.contains(key)

    def isMissing(path: Path): Boolean = !containsPath(path)
    def isPresent(path: Path): Boolean =  containsPath(path) }

// ===========================================================================
