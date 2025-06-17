package aptus
package aptdata

// ===========================================================================
package object accessors {
  private[accessors] def multipleValuesError(target: NoRenarget) = illegalArgument(s"E250502125928 - multiple values - ${target.format}")

  // ---------------------------------------------------------------------------
  trait ValewGetterAccessors
      extends ValewGetterBasicAccessors   /* eg myDyn.string("name") */
      with    ValewGetterTextAccessors    /* eg myDyn.texts ("names") */
      with    ValewGetterNestingAccessors /* eg myDyn.texts ("names") */
      with    ValewGetterComplexAccessors /* eg myDyn.matrix("data") */ {
    self: ValewGetter => } }

// ===========================================================================