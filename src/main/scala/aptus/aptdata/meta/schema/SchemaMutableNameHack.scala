package aptus
package aptdata
package meta
package schema

// ===========================================================================
// for macros and union types (see t210125111338) -> may promote - consider impact on equality
private[schema] trait ClsMutableNameHack { ignored: Cls =>
    private var _nameOpt: Option[ClsName] = None
      def   setName(name: ClsName) = { _nameOpt = Some(name); this }
      def forceName: ClsName         = _nameOpt.get
      def nameOpt  : Option[ClsName] = _nameOpt }

  // ---------------------------------------------------------------------------
  private[schema] trait FldMutableNameHack { ignored: Fld =>
    private var _enumNameOpt: Option[EnmName] = None
      def setEnumName(enumName: EnmName) = { _enumNameOpt = Some(enumName); this }
      def forceEnumName: EnmName         = _enumNameOpt.get
      def enumNameOpt  : Option[EnmName] = _enumNameOpt }

// ===========================================================================
