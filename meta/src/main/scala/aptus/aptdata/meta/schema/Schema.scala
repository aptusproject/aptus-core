package aptus
package aptdata
package meta
package schema

// ===========================================================================
case class Cls(
        fields: Seq[Fld])
      extends ValueType
         with aptus.aptitems.Items[Cls, Fld]
         with ClsTraits
         with ClsMutableNameHack {
      fields
        .requireNonEmpty()
        .requireDistinctBy(_.key) }
    object Cls
      extends ClsCompanionTrait

  // ---------------------------------------------------------------------------
  case class Fld(
            key : Key,
            info: Info)
          extends FldTraits
             with FldMutableNameHack {
        require(key.name.nonEmpty) }
      object Fld
        extends FldCompanionTrait

    // ---------------------------------------------------------------------------
    case class Info(
               optional: Optional,
               union   : Seq[SubInfo])
              extends InfoTraits {
          val required = !optional
           /* see t210125111338 (union types) */
          def   subInfo1: SubInfo   = forceUnionOption(union).force
          def container1: Container = subInfo1.multiple.pipe(Container.from(optional, _)) }
        object Info
          extends InfoCompanionTrait

      // ---------------------------------------------------------------------------
      case class Info1( // handle for most typical case (not a union field)
              optional: Optional,
              multiple : Multiple,
              valueType: ValueType)
            extends Info1Traits
        object Info1
          extends Info1CompanionTrait

    // ---------------------------------------------------------------------------
    case class SubInfo(
              multiple : Multiple,
              valueType: ValueType)
            extends SubInfoTraits {
          val single = !multiple
          def info1(optional: Optional): Info1 =
            Info1(optional, multiple, valueType) }
        object SubInfo
          extends SubInfoCompanionTrait

// ===========================================================================
