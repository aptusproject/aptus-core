package aptus
package aptmisc

import aptus.aptutils.MapUtils

// ===========================================================================
private[aptus] final class Force[A] private[aptus] (private val coll: Seq[A]) {
  def      one:        A  =      oneOrThrow(x => s"size: ${x.size}")
  def   option: Option[A] =   optionOrThrow(x => s"size: ${x.size}")
  def distinct: Seq   [A] = distinctOrThrow(x => s"size: ${x.size} (${x.duplicates.size})")
  def      set: Set   [A] =      setOrThrow(x => s"size: ${x.size} (${x.duplicates.size})")

  // ---------------------------------------------------------------------------
  def      oneOrElse(f: Seq[A] => Nothing) :        A  = if (coll.size == 1)                  coll.head       else f(coll)
  def   optionOrElse(f: Seq[A] => Nothing) : Option[A] = if (coll.size <= 1)                  coll.headOption else f(coll)
  def distinctOrElse(f: Seq[A] => Nothing) :    Seq[A] = if (coll.size == coll.distinct.size) coll            else f(coll)
  def      setOrElse(f: Seq[A] => Nothing) :    Set[A] = if (coll.size == coll.distinct.size) coll.toSet      else f(coll)

  // ---------------------------------------------------------------------------
  private def expanded(x: Seq[A]): String  = s"size: ${x.size}\n\nvalues:\n${x.joinln.newline}"

    def      oneExpanded:        A  =       oneOrThrow(expanded(_))
    def   optionExpanded: Option[A]  =   optionOrThrow(expanded(_))
    def distinctExpanded: Seq   [A]  = distinctOrThrow(expanded(_))
    def      setExpanded: Set   [A]  =      setOrThrow(expanded(_))

  // ---------------------------------------------------------------------------
  def      oneOrThrow(msg: String)          :        A  =      oneOrElse(_ => illegalArgument("E250618164000", msg))
  def   optionOrThrow(msg: String)          : Option[A] =   optionOrElse(_ => illegalArgument("E250618164001", msg))
  def distinctOrThrow(msg: String)          : Seq   [A] = distinctOrElse(_ => illegalArgument("E250618164002", msg))
  def      setOrThrow(msg: String)          : Set   [A] =      setOrElse(_ => illegalArgument("E250618164003", msg))

  // ---------------------------------------------------------------------------
  def      oneOrThrow(msg: Seq[A] => String):        A  =      oneOrThrow(msg(coll))
  def   optionOrThrow(msg: Seq[A] => String): Option[A] =   optionOrThrow(msg(coll))
  def distinctOrThrow(msg: Seq[A] => String): Seq   [A] = distinctOrThrow(msg(coll))
  def      setOrThrow(msg: Seq[A] => String): Set   [A] =      setOrThrow(msg(coll))

  // ===========================================================================
  def  tuple2 =  tuple2OrThrow(_.size.str.prepend("size: "))
  def  tuple3 =  tuple3OrThrow(_.size.str.prepend("size: "))
  def  tuple4 =  tuple4OrThrow(_.size.str.prepend("size: "))
  def  tuple5 =  tuple5OrThrow(_.size.str.prepend("size: "))
  def  tuple6 =  tuple6OrThrow(_.size.str.prepend("size: "))
  def  tuple7 =  tuple7OrThrow(_.size.str.prepend("size: "))
  def  tuple8 =  tuple8OrThrow(_.size.str.prepend("size: "))
  def  tuple9 =  tuple9OrThrow(_.size.str.prepend("size: "))
  def tuple10 = tuple10OrThrow(_.size.str.prepend("size: "))

  // ---------------------------------------------------------------------------
  def  tuple2Expanded =  tuple2OrThrow(expanded(_))
  def  tuple3Expanded =  tuple3OrThrow(expanded(_))
  def  tuple4Expanded =  tuple4OrThrow(expanded(_))
  def  tuple5Expanded =  tuple5OrThrow(expanded(_))
  def  tuple6Expanded =  tuple6OrThrow(expanded(_))
  def  tuple7Expanded =  tuple7OrThrow(expanded(_))
  def  tuple8Expanded =  tuple8OrThrow(expanded(_))
  def  tuple9Expanded =  tuple9OrThrow(expanded(_))
  def tuple10Expanded = tuple10OrThrow(expanded(_))

  // ---------------------------------------------------------------------------
  def  tuple2OrElse(f: Seq[A] => Nothing) = if (coll.size !=  2) f(coll) else  _tuple2(coll.iterator)
  def  tuple3OrElse(f: Seq[A] => Nothing) = if (coll.size !=  3) f(coll) else  _tuple3(coll.iterator)
  def  tuple4OrElse(f: Seq[A] => Nothing) = if (coll.size !=  4) f(coll) else  _tuple4(coll.iterator)
  def  tuple5OrElse(f: Seq[A] => Nothing) = if (coll.size !=  5) f(coll) else  _tuple5(coll.iterator)
  def  tuple6OrElse(f: Seq[A] => Nothing) = if (coll.size !=  6) f(coll) else  _tuple6(coll.iterator)
  def  tuple7OrElse(f: Seq[A] => Nothing) = if (coll.size !=  7) f(coll) else  _tuple7(coll.iterator)
  def  tuple8OrElse(f: Seq[A] => Nothing) = if (coll.size !=  8) f(coll) else  _tuple8(coll.iterator)
  def  tuple9OrElse(f: Seq[A] => Nothing) = if (coll.size !=  9) f(coll) else  _tuple9(coll.iterator)
  def tuple10OrElse(f: Seq[A] => Nothing) = if (coll.size != 10) f(coll) else _tuple10(coll.iterator)

  // ---------------------------------------------------------------------------
  def  tuple2OrThrow(msg: Seq[A] => String) =  tuple2OrElse(_ => illegalArgument("E250618164102", msg(coll)))
  def  tuple3OrThrow(msg: Seq[A] => String) =  tuple3OrElse(_ => illegalArgument("E250618164103", msg(coll)))
  def  tuple4OrThrow(msg: Seq[A] => String) =  tuple4OrElse(_ => illegalArgument("E250618164104", msg(coll)))
  def  tuple5OrThrow(msg: Seq[A] => String) =  tuple5OrElse(_ => illegalArgument("E250618164105", msg(coll)))
  def  tuple6OrThrow(msg: Seq[A] => String) =  tuple6OrElse(_ => illegalArgument("E250618164106", msg(coll)))
  def  tuple7OrThrow(msg: Seq[A] => String) =  tuple7OrElse(_ => illegalArgument("E250618164107", msg(coll)))
  def  tuple8OrThrow(msg: Seq[A] => String) =  tuple8OrElse(_ => illegalArgument("E250618164108", msg(coll)))
  def  tuple9OrThrow(msg: Seq[A] => String) =  tuple9OrElse(_ => illegalArgument("E250618164109", msg(coll)))
  def tuple10OrThrow(msg: Seq[A] => String) = tuple10OrElse(_ => illegalArgument("E250618164110", msg(coll)))

  // ---------------------------------------------------------------------------
  private def  _tuple2(iter: Iterator[A]) = (iter.next(), iter.next())
  private def  _tuple3(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next()                                                                                           )
  private def  _tuple4(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next()                                                                              )
  private def  _tuple5(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next(), iter.next()                                                                 )
  private def  _tuple6(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next()                                                    )
  private def  _tuple7(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next()                                       )
  private def  _tuple8(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next()                          )
  private def  _tuple9(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next()             )
  private def _tuple10(iter: Iterator[A]) = (iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next(), iter.next())

  // ===========================================================================
  // how often are we actually ok just silently discarding duplicates?
  def map[K, V]                             (implicit ev: A <:< (K, V)): Map[K, V] = { val map = MapUtils.toHashMap(coll); assert(coll.size == map.size, (coll.size, map.size))  ; map }
  def map[K, V](debug: A => Any, anys: Any*)(implicit ev: A <:< (K, V)): Map[K, V] = { val map = MapUtils.toHashMap(coll); assert(coll.size == map.size, (coll.map(debug), anys)); map }

  def mapLeft [K](f: A => K): Map[K, A]    = coll.map { x => f(x) ->   x  }.force.map
  def mapRight[V](f: A => V): Map[   A, V] = coll.map { x =>   x  -> f(x) }.force.map

  def listMap[K, V]                             (implicit ev: A <:< (K, V)): ListMap[K, V] = { val map = MapUtils.toListMap(coll); assert(coll.size == map.size, (coll.size, map.size))  ; map }
  def listMap[K, V](debug: A => Any, anys: Any*)(implicit ev: A <:< (K, V)): ListMap[K, V] = { val map = MapUtils.toListMap(coll); assert(coll.size == map.size, (coll.map(debug), anys)); map } }

// ===========================================================================
