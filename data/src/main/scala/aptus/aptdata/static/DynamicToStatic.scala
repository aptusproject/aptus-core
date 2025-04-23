package aptus
package aptdata
package static

import aptreflect.Instantiator

// ===========================================================================
class DynamicToStatic[$SingleEntity](
    instantiator: Instantiator,
    attemptKey  : ($SingleEntity, Key) => Option[Any]) {

def asList(values: Any): List[Any] = values match { case l: List[_] => l; case d: Dyns => d.valueList .toList }

  // ---------------------------------------------------------------------------
  def dynamicToStatic(value: Any)(info: Info): Any =
    info.container1 match {
      case Container._One => value.asInstanceOf[              $SingleEntity  ]      .pipe(instantiateStaticRecursively(info.forceNestedClass))
      case Container._Opt => value.asInstanceOf[       Option[$SingleEntity] ]      .map (instantiateStaticRecursively(info.forceNestedClass))
      case Container._Nes => value.asInstanceOf[       Seq   [$SingleEntity] ]      .map (instantiateStaticRecursively(info.forceNestedClass))
      case Container._Pes => value.asInstanceOf[Option[Seq   [$SingleEntity]]].map(_.map (instantiateStaticRecursively(info.forceNestedClass))) }

  // ===========================================================================
  def instantiateStaticRecursively(c: Cls)(o: $SingleEntity): Any =
      c .fields // for order
        .map (processField(o))
        .pipe(instantiator.construct)

    // ---------------------------------------------------------------------------
    private def processField(o: $SingleEntity)(field: Fld): AnyRef =
        (field.info.nestedClassOpt match {
            case None =>
              if (field.info.required) attemptKey(o, field.key).force
              else                     attemptKey(o, field.key)
            case Some(nc) => processContainedObj(nc, field, o) })
          .asInstanceOf[AnyRef /* TODO: safe? */]

      // ---------------------------------------------------------------------------
      private def processContainedObj(c2: Cls, field: Fld, o: $SingleEntity): Any =
          field.info.container1 match { // TODO: use Container.wrap now?
            case Container._One => attemptKey(o, field.key).get                     .asInstanceOf[$SingleEntity].pipe(processNesting(c2, field))
            case Container._Opt => attemptKey(o, field.key)                   .map(_.asInstanceOf[$SingleEntity].pipe(processNesting(c2, field)))
            case Container._Nes => attemptKey(o, field.key).get  .pipe(asList).map(_.asInstanceOf[$SingleEntity].pipe(processNesting(c2, field)))
            case Container._Pes => attemptKey(o, field.key).map(_.pipe(asList).map(_.asInstanceOf[$SingleEntity].pipe(processNesting(c2, field)))) }

        // ---------------------------------------------------------------------------
        private def processNesting(nc: Cls, field: Fld)(value: $SingleEntity): Any =
          instantiator
            .nesting(field.skey) // guaranteed if nested class
            .pipe(new DynamicToStatic(_, attemptKey))
            .instantiateStaticRecursively(nc)(value) }

// ===========================================================================
