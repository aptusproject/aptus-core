package aptus
package experimental
package dyn
package data
package aspects

// ===========================================================================
trait DynDummies { self: DynFluentBuilding =>
    def dummy(value: NakedValue): Dyn = dyn("value" -> value)

    // ---------------------------------------------------------------------------
    lazy val Dummy : Dyn = dummy("dummy")
    lazy val Dummy2: Dyn = dyn("value1" -> "dummy1", "value2" -> "dummy2") }

  // ===========================================================================
  trait DynsDummies { self: DynsFluentBuilding =>
      lazy val Dummy: Dyns = dyns(Sngl.dummy("value1"), Sngl.dummy("value2"))

      // ---------------------------------------------------------------------------
      def dummy(value : NakedValue)                    : Dyns = dyns(Sngl.dummy(value) , Sngl.dummy(value))
      def dummy(value1: NakedValue, value2: NakedValue): Dyns = dyns(Sngl.dummy(value1), Sngl.dummy(value2)) }

  // ===========================================================================
  trait DynzDummies { self: DynzFluentBuilding =>
      lazy val Dummy: Dynz = data.Dyns.Dummy.asIterator

      // ---------------------------------------------------------------------------
      def dummy(value : NakedValue)                    : Dynz = data.Dyns.dummy(value)         .asIterator
      def dummy(value1: NakedValue, value2: NakedValue): Dynz = data.Dyns.dummy(value1, value2).asIterator }

// ===========================================================================
