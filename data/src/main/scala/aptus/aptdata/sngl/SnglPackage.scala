package aptus
package aptdata

// ===========================================================================
package object sngl {

  trait DynRemoveRetainOpt { self: DynData =>
    def retainRemoveOpt(targets: Keyz): (Option[Dyn], Option[Dyn]) = {
        val (retained, removed) = data.partition(x => targets.containsKey(x.key))

        retained.in.noneIf(_.isEmpty).map(Dyn.build) ->
        removed .in.noneIf(_.isEmpty).map(Dyn.build) }

      // ---------------------------------------------------------------------------
      def retainOpt(targets: Keyz): Option[Dyn] = data.filter   (x => targets.containsKey(x.key)).in.noneIf(_.isEmpty).map(Dyn.build)
      def removeOpt(targets: Keyz): Option[Dyn] = data.filterNot(x => targets.containsKey(x.key)).in.noneIf(_.isEmpty).map(Dyn.build) }

  // ---------------------------------------------------------------------------
  trait DynAppPrepend { self: Dyn =>
    def append (that: Dyn): Dyns = asList. append(that)
    def prepend(that: Dyn): Dyns = asList.prepend(that) } }

// ===========================================================================
