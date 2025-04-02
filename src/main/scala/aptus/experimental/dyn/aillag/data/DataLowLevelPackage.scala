package aptus.experimental.dyn.aillag.data

// ===========================================================================
package object lowlevel {
  def format(value: Any): String =
    aptus.aptdata.lowlevel.AnyValueFormatter.format(value) }

// ===========================================================================