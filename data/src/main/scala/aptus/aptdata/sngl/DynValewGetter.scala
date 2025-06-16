package aptus
package aptdata
package sngl

// ===========================================================================
trait DynValewGetter //TODO: cleanup
        extends ValewGetter /* .get and .getOrElse */
        with    DynValewPresenceAbsence /* isPresent, isAbsent, ... */ {
      self: DynData
         with DynDataEntityErrorFormatterProvider =>

    override final protected[aptdata] def get(key: Key): Option[Valew] =
      data.find(_.key == key).map(_.valew)

    // ---------------------------------------------------------------------------
    //TODO: choose wich name to use
    def    containsKey(key: Key): Boolean =  keySet.contains(key)
    def notContainsKey(key: Key): Boolean = !keySet.contains(key)

    //TODO: choose wich name to use
    /*private[aptdata] */def attemptKey (key: Key): Option[NakedValue]  = get(key).map(_.naked)
    /*private[aptdata] */def forceKey   (key: Key):        NakedValue   = get(key).map(_.naked).getOrElse(Error.CanNotForceKey(deef, key).thro)

    // ---------------------------------------------------------------------------
    def nakedValueOpt(key: Key): Option[NakedValue] = get(key).map(_.naked)

    // ---------------------------------------------------------------------------
    def size: Size = data.size }

// ===========================================================================
