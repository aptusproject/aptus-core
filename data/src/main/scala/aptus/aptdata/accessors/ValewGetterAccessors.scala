package aptus
package aptdata
package accessors

// ===========================================================================
private[aptdata] trait ValewGetterAccessors {
    self: ValewGetter => // TODO

  // ---------------------------------------------------------------------------
  protected implicit class Option__[T](diss: Option[T]) {
    def forceOrError(target: NoRenarget) = diss.getOrElse(illegalArgument(s"E241022114436 - missing - ${target.format}")) }

  // ===========================================================================
  // codegened (see 241121238315)

  def string    (target: NoRenarget): One[String    ] = string_    (target).forceOrError(target)
  def boolean   (target: NoRenarget): One[Boolean   ] = boolean_   (target).forceOrError(target)
  def int       (target: NoRenarget): One[Int       ] = int_       (target).forceOrError(target)
  def double    (target: NoRenarget): One[Double    ] = double_    (target).forceOrError(target)
  def byte      (target: NoRenarget): One[Byte      ] = byte_      (target).forceOrError(target)
  def short     (target: NoRenarget): One[Short     ] = short_     (target).forceOrError(target)
  def long      (target: NoRenarget): One[Long      ] = long_      (target).forceOrError(target)
  def float     (target: NoRenarget): One[Float     ] = float_     (target).forceOrError(target)
  def bigInt    (target: NoRenarget): One[BigInt    ] = bigInt_    (target).forceOrError(target)
  def bigDec    (target: NoRenarget): One[BigDec    ] = bigDec_    (target).forceOrError(target)
  def date      (target: NoRenarget): One[Date      ] = date_      (target).forceOrError(target)
  def dateTime  (target: NoRenarget): One[DateTime  ] = dateTime_  (target).forceOrError(target)
  def instant   (target: NoRenarget): One[Instant   ] = instant_   (target).forceOrError(target)
  def byteBuffer(target: NoRenarget): One[ByteBuffer] = byteBuffer_(target).forceOrError(target)

  def strings    (target: NoRenarget): Nes[String    ] = strings_    (target).forceOrError(target)
  def booleans   (target: NoRenarget): Nes[Boolean   ] = booleans_   (target).forceOrError(target)
  def ints       (target: NoRenarget): Nes[Int       ] = ints_       (target).forceOrError(target)
  def doubles    (target: NoRenarget): Nes[Double    ] = doubles_    (target).forceOrError(target)
  def bytes      (target: NoRenarget): Nes[Byte      ] = bytes_      (target).forceOrError(target)
  def shorts     (target: NoRenarget): Nes[Short     ] = shorts_     (target).forceOrError(target)
  def longs      (target: NoRenarget): Nes[Long      ] = longs_      (target).forceOrError(target)
  def floats     (target: NoRenarget): Nes[Float     ] = floats_     (target).forceOrError(target)
  def bigInts    (target: NoRenarget): Nes[BigInt    ] = bigInts_    (target).forceOrError(target)
  def bigDecs    (target: NoRenarget): Nes[BigDec    ] = bigDecs_    (target).forceOrError(target)
  def dates      (target: NoRenarget): Nes[Date      ] = dates_      (target).forceOrError(target)
  def dateTimes  (target: NoRenarget): Nes[DateTime  ] = dateTimes_  (target).forceOrError(target)
  def instants   (target: NoRenarget): Nes[Instant   ] = instants_   (target).forceOrError(target)
  def byteBuffers(target: NoRenarget): Nes[ByteBuffer] = byteBuffers_(target).forceOrError(target)

  def string_    (target: NoRenarget): Opt[String    ] = typed_[String    ](target)(_.string    )(_ string_     _)
  def boolean_   (target: NoRenarget): Opt[Boolean   ] = typed_[Boolean   ](target)(_.boolean   )(_ boolean_    _)
  def int_       (target: NoRenarget): Opt[Int       ] = typed_[Int       ](target)(_.int       )(_ int_        _)
  def double_    (target: NoRenarget): Opt[Double    ] = typed_[Double    ](target)(_.double    )(_ double_     _)
  def byte_      (target: NoRenarget): Opt[Byte      ] = typed_[Byte      ](target)(_.byte      )(_ byte_       _)
  def short_     (target: NoRenarget): Opt[Short     ] = typed_[Short     ](target)(_.short     )(_ short_      _)
  def long_      (target: NoRenarget): Opt[Long      ] = typed_[Long      ](target)(_.long      )(_ long_       _)
  def float_     (target: NoRenarget): Opt[Float     ] = typed_[Float     ](target)(_.float     )(_ float_      _)
  def bigInt_    (target: NoRenarget): Opt[BigInt    ] = typed_[BigInt    ](target)(_.bigInt    )(_ bigInt_     _)
  def bigDec_    (target: NoRenarget): Opt[BigDec    ] = typed_[BigDec    ](target)(_.bigDec    )(_ bigDec_     _)
  def date_      (target: NoRenarget): Opt[Date      ] = typed_[Date      ](target)(_.date      )(_ date_       _)
  def dateTime_  (target: NoRenarget): Opt[DateTime  ] = typed_[DateTime  ](target)(_.dateTime  )(_ dateTime_   _)
  def instant_   (target: NoRenarget): Opt[Instant   ] = typed_[Instant   ](target)(_.instant   )(_ instant_    _)
  def byteBuffer_(target: NoRenarget): Opt[ByteBuffer] = typed_[ByteBuffer](target)(_.byteBuffer)(_ byteBuffer_ _)

  def strings_    (target: NoRenarget): Ons[String    ] = typed_[Seq[String    ]](target)(_.strings    )(_ strings_     _)
  def booleans_   (target: NoRenarget): Ons[Boolean   ] = typed_[Seq[Boolean   ]](target)(_.booleans   )(_ booleans_    _)
  def ints_       (target: NoRenarget): Ons[Int       ] = typed_[Seq[Int       ]](target)(_.ints       )(_ ints_        _)
  def doubles_    (target: NoRenarget): Ons[Double    ] = typed_[Seq[Double    ]](target)(_.doubles    )(_ doubles_     _)
  def bytes_      (target: NoRenarget): Ons[Byte      ] = typed_[Seq[Byte      ]](target)(_.bytes      )(_ bytes_       _)
  def shorts_     (target: NoRenarget): Ons[Short     ] = typed_[Seq[Short     ]](target)(_.shorts     )(_ shorts_      _)
  def longs_      (target: NoRenarget): Ons[Long      ] = typed_[Seq[Long      ]](target)(_.longs      )(_ longs_       _)
  def floats_     (target: NoRenarget): Ons[Float     ] = typed_[Seq[Float     ]](target)(_.floats     )(_ floats_      _)
  def bigInts_    (target: NoRenarget): Ons[BigInt    ] = typed_[Seq[BigInt    ]](target)(_.bigInts    )(_ bigInts_     _)
  def bigDecs_    (target: NoRenarget): Ons[BigDec    ] = typed_[Seq[BigDec    ]](target)(_.bigDecs    )(_ bigDecs_     _)
  def dates_      (target: NoRenarget): Ons[Date      ] = typed_[Seq[Date      ]](target)(_.dates      )(_ dates_       _)
  def dateTimes_  (target: NoRenarget): Ons[DateTime  ] = typed_[Seq[DateTime  ]](target)(_.dateTimes  )(_ dateTimes_   _)
  def instants_   (target: NoRenarget): Ons[Instant   ] = typed_[Seq[Instant   ]](target)(_.instants   )(_ instants_    _)
  def byteBuffers_(target: NoRenarget): Ons[ByteBuffer] = typed_[Seq[ByteBuffer]](target)(_.byteBuffers)(_ byteBuffers_ _)

  // ---------------------------------------------------------------------------
  protected def typed_[T](target: NoRenarget)(f: Valew => T)(g: (Dyn, Path) => Option[T]): Opt[T] =
    target.und match {
      case key: Key                 => get(key) .map(f)
      case Path(Nil,          leaf) => get(leaf).map(f)
      case Path(head +: rest, leaf) => get(head).flatMap(_.nestingOpt.flatMap(x => g(x, Path(rest, leaf)))) } }

// ===========================================================================
