package aptus
package apttraits

// ===========================================================================
object AptusWritingTraits
    extends AptusWritingTraits

  // ---------------------------------------------------------------------------
  trait AptusWritingTraits {
    trait HasWriteOutputFile {
      @abstrct def write(path: OutputFilePath): OutputFilePath } }

// ===========================================================================