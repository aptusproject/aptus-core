package aptus
package aptdata
package meta
package schema

// ===========================================================================
@TypeMatching
trait FldCreator {
  import BasicType.{_Enm => _, _}

  protected val _key: Key

  // ---------------------------------------------------------------------------
  def typed[T : aptreflect.lowlevel.ReflectionTypesAbstraction.WTT]: Fld = Fld(_key, Info.forceFrom[T])

  // ---------------------------------------------------------------------------
  // see t210125111338 (union types)
  def union(optional: Optional)(first: SubInfo, more: SubInfo*): Fld = Fld(_key, Info(optional , (first +: more)))
  def requiredUnion            (first: SubInfo, more: SubInfo*): Fld = Fld(_key, Info(_Required, (first +: more)))
  def optionalUnion            (first: SubInfo, more: SubInfo*): Fld = Fld(_key, Info(_Optional, (first +: more)))

  // ---------------------------------------------------------------------------
  def union        (info : Info)        : Fld = Fld(_key, info)
  def requiredUnion(union: Seq[SubInfo]): Fld = Fld(_key, Info(_Required, union))
  def optionalUnion(union: Seq[SubInfo]): Fld = Fld(_key, Info(_Optional, union))

  // ===========================================================================
  // TODO: t250109125909 - codegen
  def string    : Fld = one(_key, _String)
  def string_   : Fld = opt(_key, _String)
  def strings   : Fld = nes(_key, _String)
  def strings_  : Fld = pes(_key, _String)

  def int       : Fld = one(_key, _Int)
  def int_      : Fld = opt(_key, _Int)
  def ints      : Fld = nes(_key, _Int)
  def ints_     : Fld = pes(_key, _Int)

  def double    : Fld = one(_key, _Double)
  def double_   : Fld = opt(_key, _Double)
  def doubles   : Fld = nes(_key, _Double)
  def doubles_  : Fld = pes(_key, _Double)

  def boolean   : Fld = one(_key, _Boolean)
  def boolean_  : Fld = opt(_key, _Boolean)
  def booleans  : Fld = nes(_key, _Boolean)
  def booleans_ : Fld = pes(_key, _Boolean)

  // ---------------------------------------------------------------------------
  def byte    : Fld = one(_key, _Byte)
  def byte_   : Fld = opt(_key, _Byte)
  def bytes   : Fld = nes(_key, _Byte) // not to be confused with binary...
  def bytes_  : Fld = pes(_key, _Byte) // not to be confused with binary...

  def short   : Fld = one(_key, _Short)
  def short_  : Fld = opt(_key, _Short)
  def shorts  : Fld = nes(_key, _Short)
  def shorts_ : Fld = pes(_key, _Short)

  def long    : Fld = one(_key, _Long)
  def long_   : Fld = opt(_key, _Long)
  def longs   : Fld = nes(_key, _Long)
  def longs_  : Fld = pes(_key, _Long)

  def float   : Fld = one(_key, _Float)
  def float_  : Fld = opt(_key, _Float)
  def floats  : Fld = nes(_key, _Float)
  def floats_ : Fld = pes(_key, _Float)

  // ---------------------------------------------------------------------------
  def bigInt   : Fld = one(_key, _BigInt)
  def bigInt_  : Fld = opt(_key, _BigInt)
  def bigInts  : Fld = nes(_key, _BigInt)
  def bigInts_ : Fld = pes(_key, _BigInt)

  def bigDec   : Fld = one(_key, _BigDec)
  def bigDec_  : Fld = opt(_key, _BigDec)
  def bigDecs  : Fld = nes(_key, _BigDec)
  def bigDecs_ : Fld = pes(_key, _BigDec)

  // ---------------------------------------------------------------------------
  def date       : Fld = one(_key, _Date)
  def date_      : Fld = opt(_key, _Date)
  def dates      : Fld = nes(_key, _Date)
  def dates_     : Fld = pes(_key, _Date)

  def dateTime   : Fld = one(_key, _DateTime)
  def dateTime_  : Fld = opt(_key, _DateTime)
  def dateTimes  : Fld = nes(_key, _DateTime)
  def dateTimes_ : Fld = pes(_key, _DateTime)

  def instant   : Fld = one(_key, _Instant)
  def instant_  : Fld = opt(_key, _Instant)
  def instants  : Fld = nes(_key, _Instant)
  def instants_ : Fld = pes(_key, _Instant)

  // ---------------------------------------------------------------------------
  def binary    : Fld = one(_key, _Binary)
  def binary_   : Fld = opt(_key, _Binary)
  def binarys   : Fld = nes(_key, _Binary)
  def binarys_  : Fld = pes(_key, _Binary)

  // ===========================================================================
  def cls_ (fields: Nes[Fld]): Fld = Fld(_key, Info.opt(Cls(fields)))
  def cls  (fields: Nes[Fld]): Fld = Fld(_key, Info.one(Cls(fields)))
  def clss (fields: Nes[Fld]): Fld = Fld(_key, Info.nes(Cls(fields)))
  def clss_(fields: Nes[Fld]): Fld = Fld(_key, Info.pes(Cls(fields)))

  def cls_ (field1: Fld, more: Fld*): Fld = Fld(_key, Info.opt(Cls(field1 +: more)))
  def cls  (field1: Fld, more: Fld*): Fld = Fld(_key, Info.one(Cls(field1 +: more)))
  def clss (field1: Fld, more: Fld*): Fld = Fld(_key, Info.nes(Cls(field1 +: more)))
  def clss_(field1: Fld, more: Fld*): Fld = Fld(_key, Info.pes(Cls(field1 +: more)))

  def cls_ (c: Cls): Fld = Fld(_key, Info.opt(Cls(c.fields)))
  def cls  (c: Cls): Fld = Fld(_key, Info.one(Cls(c.fields)))
  def clss (c: Cls): Fld = Fld(_key, Info.nes(Cls(c.fields)))
  def clss_(c: Cls): Fld = Fld(_key, Info.pes(Cls(c.fields))) }

// ===========================================================================
