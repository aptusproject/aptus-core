package aptus
package aptdata

// ===========================================================================
package object sngl {
  type DynDataWithGetter = DynData with DynValewGetter

  // ---------------------------------------------------------------------------
  trait DynRemoveRetainOpt { self: DynData =>
      def retainOpt(targets: Keys): Option[Dyn] = data.filter   (x => targets.contains(x.key)).in.noneIf(_.isEmpty).map(Dyn.build)
      def removeOpt(targets: Keys): Option[Dyn] = data.filterNot(x => targets.contains(x.key)).in.noneIf(_.isEmpty).map(Dyn.build) }

  // ---------------------------------------------------------------------------
  trait DynAppPrepend { self: Dyn =>
    def append (that: Dyn): Dyns = asList. append(that)
    def prepend(that: Dyn): Dyns = asList.prepend(that) }
}

// ===========================================================================
