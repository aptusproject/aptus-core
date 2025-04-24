package aptus
package aptdata
package static

import aptus.Anything_

// ===========================================================================
class StaticToDynamic[$SingleEntity](
    val normalizeEntry:      BKey => AnyValue => Option[(BKey, AnyValue)],
    val builder       : Seq[(BKey, AnyValue)] => $SingleEntity) {

  // ===========================================================================
  def apply(value: Any)(info: Info): Any = {
    val nc = info.forceNestedClass

    info.container1 match {
        case Container._One => staticToObj  (nc)(value)
        case Container._Opt => staticToObj_ (nc)(value)
        case Container._Nes => staticToObjs (nc)(value)
        case Container._Pes => staticToObjs_(nc)(value) } }

    // ---------------------------------------------------------------------------
            def staticToObj  (c: Cls)(value: Any): Any = value.asInstanceOf[              Product  ]            .productIterator.pipe(valuesToObjOpt(c)).getOrElse(None)
    private def staticToObj_ (c: Cls)(value: Any): Any = value.asInstanceOf[       Option[Product] ]      .map(_.productIterator.pipe(valuesToObjOpt(c)).getOrElse(None))
            def staticToObjs (c: Cls)(value: Any): Any = value.asInstanceOf[       Seq   [Product] ]      .map(_.productIterator.pipe(valuesToObjOpt(c)).getOrElse(None))
    private def staticToObjs_(c: Cls)(value: Any): Any = value.asInstanceOf[Option[Seq   [Product]]].map(_.map(_.productIterator.pipe(valuesToObjOpt(c)).getOrElse(None)))

      // ---------------------------------------------------------------------------
      private def valuesToObjOpt(c: Cls)(itr: Iterator[AnyValue]): Option[$SingleEntity] =
        c .fields
          .map { field =>
            field.key.und ->
              potentiallyProcessNesting(field.info)(
                value = itr.next()) }
          .flatMap { case (k, v) => normalizeEntry(k)(v) }
          .in.noneIf(_.isEmpty)
          .map(x => builder(x))
          .tap(_ => assert(itr.isEmpty, c /* TODO: pass original value? */))

  // ===========================================================================
  private def potentiallyProcessNesting(info: Info)(value: AnyValue): AnyValue =
    info
      .nestedClassOpt
      .map { nestedClass =>
        info
          .container1
          .containerWrap(f = staticToObj(nestedClass))
          .apply(value) }
      .getOrElse(value) }

// ===========================================================================
