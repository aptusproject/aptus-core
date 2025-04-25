package aptus
package aptdata
package ops

// ===========================================================================
trait DynOpsImpl {
    sngl: Dyn => // TODO: limit

  // ---------------------------------------------------------------------------
  /** MUST have distinct keys */ def merge(that: Dyn): Dyn = aptdata.sngl.Dyn.build((data: Iterable[Entry]) ++ (that.data: Iterable[Entry]))

  // ===========================================================================
  final override def ensurePresent(key1: Key, more: Key*): Dyn = sngl.pipeIf((key1 +: more).intersect(keys). isEmpty)(_ => Error.EnsurePresenceError(()).thro)
  final override def ensureMissing(key1: Key, more: Key*): Dyn = sngl.pipeIf((key1 +: more).intersect(keys).nonEmpty)(_ => Error.EnsureAbsenceError (()).thro)

  // ===========================================================================
  final override def rename(mapping: Map[Key, Key]): Dyn = mapEntries(_.rename(mapping))
  final override def rename(from: Key) = new _Rename(from: Key) {
    def to(to: Key): Dyn = mapEntries(_.rename(Ren(from, to))) }

  // ---------------------------------------------------------------------------
  final override def retain(targets: Set[Key]): Dyn = flatMapEntries(_.retainOpt(targets)) // TODO: specialized for 1?
  final override def remove(targets: Set[Key]): Dyn = flatMapEntries(_.removeOpt(targets)) // TODO: specialized for 1?

// 241120
final def rename(path: Path): _RenamePath = new _RenamePath(path)
  final class _RenamePath(path: Path) { def to(x :Key): Dyn = ??? /* TODO: t250402144718 */ }
final def retain(path: Path): Dyn = ???
final def remove(path: Path): Dyn = ???

  // ---------------------------------------------------------------------------
final def replace          (key: Ren): _Replace = new _Replace(???, guaranteed = false)

  final def replace          (key: Key): _Replace = new _Replace(key, guaranteed = false)
  final def replaceIfPresent (key: Key): _Replace = new _Replace(key, guaranteed = false)
  final def replaceGuaranteed(key: Key): _Replace = new _Replace(key, guaranteed = true)

    final class _Replace private[aptdata] (key: Key, guaranteed: Boolean) {
      def withValue(value: NakedValue): Dyn =
        if (guaranteed) transformGuaranteed(key).using(_ => value)
        else            transformIfPresent (key).using(_ => value) }

  // ===========================================================================
  final override def add(entry: Entry)        : Dyn = Dyn.build(data :+ entry)
  final override def add(entries: List[Entry]): Dyn = Dyn.build(data ++ entries)

  // ===========================================================================
  final def put(key: Key, value: NakedValue) : Dyn =
      if (containsKey(key)) replace(key).withValue(value)
      else                      add(key,           value)

  // ===========================================================================
  final override def nest(nestee: Key): NestOps[Dyn] =
    new SglNestOps {
      protected val _sngl        = sngl
      protected val _nestee: Key = nestee }

  // ---------------------------------------------------------------------------
  def unnest = ???

  // ===========================================================================
  // converts
final def convert(key: Path): ConvertOps[Dyn] = ???

  // ---------------------------------------------------------------------------
  final override def convert(key: Key): ConvertOps[Dyn] =
    new SglConvertOps {
      protected val _ctt: common.CommonTransformTrait[Dyn] = sngl
      protected val _key: Key = key } }

// ===========================================================================
