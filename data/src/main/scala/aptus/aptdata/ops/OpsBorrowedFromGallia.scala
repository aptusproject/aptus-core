package aptus
package aptdata
package ops

// ===========================================================================
trait OpsBorrowedFromGallia { self: Dyn =>
  private type Keyz   = Seq[Key]
  private type KPathW = Path
          type NakedValue = Any

  // ---------------------------------------------------------------------------
  private implicit class Path_(u: Path) { def value: Path = u }

  // ===========================================================================
  // borrowed from gallia (TODO: t241130165320 - refactor)

                                            final def put(key: Key, value: NakedValue) : Dyn =
                                                if (containsKey(key)) replace(key).withValue(value)
                                                else                      add(key,           value)

                                            // ---------------------------------------------------------------------------
                                            /* req: nesting key can't be an array */
                                            def nest(targets: Keyz, nestingKey: Key): Dyn =
                                              (retainOpt(targets), removeOpt(targets)) match {
                                                case (None        , _         ) => self
                                                case (Some(target), None      ) => aptdata.sngl.Dyn.dyn(nestingKey -> target)
                                                case (Some(target), Some(rest)) =>
                                                  attemptKey(nestingKey)
                                                    .map {
                                                      case seq: Seq[_] => rest.put(nestingKey, seq.map(_.asInstanceOf[Dyn].merge(target))) // note: denormalizes
                                                      case sgl         => rest.put(nestingKey,       sgl.asInstanceOf[Dyn].merge(target)) }
                                                    .getOrElse {          rest.put(nestingKey,                                   target ) } }


                                            // ===========================================================================
                                            def containsPath(target: KPathW): Boolean =
                                              target.value.tailPair match {
                                                case (leaf  , None      ) => containsKey(leaf)
                                                case (parent, Some(tail)) =>
                                                  attemptKey(parent) match {
                                                      case None        => false
                                                      case Some(value) =>
                                                        (value match {
                                                            case seq: Seq[_] => seq
                                                            case sgl         => Seq(sgl) })
                                                          .forall(_.asInstanceOf[Dyn].containsPath(tail)) } }

                                          // ===========================================================================
                                          // from AtomsHelper

                                          protected def _reorderKeysRecursively(f: Seq[SKey] => Seq[SKey])(o: aptdata.sngl.DynDataWithGetter): Dyn =
                                            __reorderKeysRecursively(_tmp(f))(o)

                                          // ---------------------------------------------------------------------------
                                          protected def __reorderKeysRecursively(f: Seq[Key] => Seq[Key])(o: aptdata.sngl.DynDataWithGetter): Dyn =
                                            o .keys
                                              .pipe(f)
                                              .map { key =>
                                                key ->
                                                  (o.__forceKeyScl3Hack(key) match { // TODO: t210115095838 - optimization: pass nesting info from meta rather

                                                      // scl3 - TODO: t250327101228 - not sure why
                                                      //[error] -- Error: /home/tony/scl/aptus/aptus-core/src/main/scala/gallia/ops/GalliaBorrowedOps.scala:67:53
                                                      //[error] 67 |                                                  (o.forceKey(key) match {
                                                      //[error]    |                                                     ^
                                                      //[error]    |Cannot resolve reference to type (o : aptus.aptdata.sngl.DynDataWithGetter).NakedValue.
                                                      //[error]    |The classfile defining the type might be missing from the classpath.

                                                    case x: Dyn    => __reorderKeysRecursively(f)(x)
                                                    case x: Seq[_] =>
                                                      x.map {
                                                        case y: Dyn    => __reorderKeysRecursively(f)(y)
                                                        case y: Seq[_] => ???//TODO: can't happen throw illegal
                                                        case y         => y }
                                                    case x => x }) }
                                              .galliaDyn

  // ===========================================================================
  private def _tmp(f: Seq[SKey] => Seq[SKey]): Seq[Key] => Seq[Key] =
    (keys: Seq[Key]) =>
      keys
        .map(_.name)
        .pipe(f)
        .map(Key._fromString) }

// ===========================================================================
