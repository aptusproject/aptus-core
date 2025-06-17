package aptus
package aptdata
package meta
package selectors

// ===========================================================================
package object td {

  @pseudosealed trait TargetDataEntryTypeBase

  // ---------------------------------------------------------------------------
  // for parsing only
  @pseudosealed trait PathOrRPath extends TargetDataEntryTypeBase { def target: Target }
    case class  Nesting(value:  Path) extends PathOrRPath { override final def target = Target.explicit(value)}
    case class RNesting(value: RPath) extends PathOrRPath { override final def target = Target.explicit(value)}

  // ---------------------------------------------------------------------------
  @pseudosealed trait TargetDataEntryType
      case object AsIs                extends TargetDataEntryTypeBase with TargetDataEntryType
      case class  WithRename(to: Key) extends TargetDataEntryTypeBase with TargetDataEntryType }

// ===========================================================================