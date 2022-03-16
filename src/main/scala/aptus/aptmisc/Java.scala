package aptus
package aptmisc

// ===========================================================================
object Java {

  @inline def toScala: PartialFunction[Any /* java */, Any /* scala */] =
      toScalaNonNumber
        .orElse(
      toScalaNumber)

    // ---------------------------------------------------------------------------
    @inline def toScalaNonNumber: PartialFunction[Any /* java */, Any /* scala */] = {
      case x: java.lang.String   => x // note: java.lang.String == scala.String
      case x: java.lang.Boolean  => (x: Boolean)

      // ---------------------------------------------------------------------------
      case x: java.sql.Timestamp => x.toLocalDateTime
      case x: java.sql.Date      => x.toLocalDate
      case x: java.sql.Time      => x.toLocalTime }  // TODO: t220315133528 - BLOB, CLOB, ...

    // ---------------------------------------------------------------------------
    @inline def toScalaNumber: PartialFunction[Any /* java */, Any /* scala */] = { // 201102113329
      case x: java.lang.Double  => (x: Double)
      case x: java.lang.Integer => (x: Int)

      case x: java.lang.Byte    => (x: Byte)
      case x: java.lang.Short   => (x: Short)
      case x: java.lang.Long    => (x: Long)

      case x: java.lang.Float   => (x: Float)

      case x: java.math.BigDecimal => BigDecimal(x)
      case x: java.math.BigInteger => BigInt    (x) }

  // ---------------------------------------------------------------------------
  /*
		note:
  		assert(new java.lang.String("foo") == "foo")
  		assert(new java.lang.Boolean(true) == true)

      assert(new java.lang.Integer(1)   == 1)
      assert(new java.lang.Double (1.1) == 1.1)
          
      assert(new java.lang.Long (1)   == 1L)
      assert(new java.lang.Float(1.1) == 1.1F)
      
      assert(new java.lang.Byte (1: Byte)  == (1: Byte))
      assert(new java.lang.Short(1: Short) == (1: Short))

      // not directly equals
      assert(java.math.BigInteger.valueOf(1)   == BigInt    (1)  .bigInteger)
      assert(java.math.BigDecimal.valueOf(1.1) == BigDecimal(1.1).bigDecimal)    
  */    
}

// ===========================================================================
