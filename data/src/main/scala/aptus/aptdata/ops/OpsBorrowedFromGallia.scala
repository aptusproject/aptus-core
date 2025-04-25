package aptus
package aptdata
package ops

// ===========================================================================
trait OpsBorrowedFromGallia { self: Dyn =>
  private type KPathW = Path
  private implicit class Path_(u: Path) { def value: Path = u }

  // ===========================================================================
  // borrowed from gallia (TODO: t241130165320 - refactor)

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

                                          protected def _reorderKeysRecursively(f: Keyz => Keyz)(o: aptdata.sngl.DynDataWithGetter): Dyn =
                                            o .keyz
                                              .pipe(f)
                                              .map { key =>
                                                key ->
                                                  (o.forceKey(key) match { // TODO: t210115095838 - optimization: pass nesting info from meta rather
                                                    case x: Dyn    => _reorderKeysRecursively(f)(x)
                                                    case x: Seq[_] =>
                                                      x.map {
                                                        case y: Dyn    => _reorderKeysRecursively(f)(y)
                                                        case y: Seq[_] => ???//TODO: can't happen throw illegal
                                                        case y         => y }
                                                    case x => x }) }
                                              .galliaDyn }

// ===========================================================================
