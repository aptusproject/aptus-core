package aptus
package aptdata
package meta
package converter

// ===========================================================================
object MetaToDataConverter { // 201222111332
  import MetaKey._
  
  // ---------------------------------------------------------------------------
  def clsToDyn(value: Cls): Dyn = Dyn.dyn(
      _fields -> value.fields.map(fld))

    // ---------------------------------------------------------------------------
    private def fld(value: Fld): Dyn = Dyn.dyn(
      _key  -> value.key.name,
      _info -> value.info.pipe(info))

      // ---------------------------------------------------------------------------
      private def info(value: Info): Dyn = Dyn.dyn(
          _optional -> value.optional,
          _union    -> value.union.map(subInfo))

          // ---------------------------------------------------------------------------
          private def subInfo(value: SubInfo): Dyn = Dyn.dyn(
              _multiple  -> value.multiple,
              _valueType -> value.valueType.pipe(valueType))

            // ---------------------------------------------------------------------------
            private def valueType(value: ValueType): Any = (value match {
                case e      : BasicType._Enm => enm(e)
                case bsc    : BasicType      => bsc.name
                case nesting: Cls            => clsToDyn(nesting) })

              // ---------------------------------------------------------------------------
              private def enm(value: _Enm): Dyn =
                Dyn.dyn(
                  "_type"  -> "_Enm",
                  "values" -> value.stringValues.ensuring(_.nonEmpty)) }

// ===========================================================================
