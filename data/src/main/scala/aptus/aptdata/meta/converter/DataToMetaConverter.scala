package aptus
package aptdata
package meta
package converter

// ===========================================================================
object DataToMetaConverter { // 201222111331
  import MetaKey._

  // ---------------------------------------------------------------------------
  def dynToCls(value: Dyn): Cls =
      value
        .nestings(_fields)
        .map(fld)
        .pipe(Cls.apply)

    // ---------------------------------------------------------------------------
    private def fld(value: Dyn): Fld = Fld(
        key  = value.string(_key).symbol,
        info = value.nesting(_info).pipe(info))

      // ---------------------------------------------------------------------------
      private def info(value: Dyn): Info = Info(
          optional = value.boolean(_optional),
          union    = value.nestings(_union).map(subInfo))

        // ---------------------------------------------------------------------------
        private def subInfo(value: Dyn): SubInfo = SubInfo(
               value.boolean(_multiple),
              (value.forceKey(Symbol(_valueType)).pipe(valueType)))

          // ---------------------------------------------------------------------------
          private def valueType(value: Any) = value match { // see 210118133408
              case s: String => BasicType.withName(s)
              case o: Dyn    =>
                o.string_("_type") match {
                  case None         => dynToCls(o)
                  case Some("_Enm") => enm(o)
                  case Some(x)      => illegalArgument(s"E250424092053:${x}") } }

            // ---------------------------------------------------------------------------
            private def enm(value: Dyn): BasicType =
              value
                .strings("values")
                .map ( EnumValue.apply)
                .pipe(_Enm      .apply) }

// ===========================================================================
