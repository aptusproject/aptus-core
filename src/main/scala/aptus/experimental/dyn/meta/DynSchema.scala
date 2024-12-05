package aptus
package experimental
package dyn
package meta

// ===========================================================================
case class Cls(
        fields: Seq[Fld])
      extends ValueType
         with Items[Cls, Fld] with ClsItems
         with ClsTraits
    object Cls
      extends ClsCompanionTrait

  // ---------------------------------------------------------------------------
  case class Fld(
            key : SKey,
            info: Info)
          extends FldTraits
      object Fld
        extends FldCompanionTrait

    // ---------------------------------------------------------------------------
    case class Info(
           optional: Optional,
           union   : Seq[SubInfo])
          extends InfoTraits
      object Info
        extends InfoCompanionTrait

    // ---------------------------------------------------------------------------
    case class SubInfo(
              multiple : Multiple,
              valueType: ValueType)
            extends SubInfoTraits
        object SubInfo
          extends SubInfoCompanionTrait

// ===========================================================================
