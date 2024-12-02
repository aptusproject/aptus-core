package aptus

// ===========================================================================
/** typically these are meant to convey intent, not enforce invariants; often sufficient to replace a comment. */
trait AptusMinimalAliases { // most useful ones
  type Name     = String
  type FilePath = String

  type Size     = Int
  type Count    = Int
  type Index    = Int

  type Nes[T]   = Seq[T] }

// ===========================================================================
trait AptusAliases extends AptusMinimalAliases {
  //TODO: t241201103337 - further split up

  type Sum               = Int
  type MirrorIndex       = Int
  type Rank              = Int // = index + 1
  type Depth             = Int

  // ---------------------------------------------------------------------------
  type Probability       = Double
  type Ratio             = Double
  type Cumul             = Double  

  // ---------------------------------------------------------------------------
  type Separator         = String

  type Label             = String

  type Line              = String
  type Content           = String

  // ---------------------------------------------------------------------------
  type FsPath            = String
  type DirPath           = String

  type FileName          = String
  type DirName           = String

    type InputPath       = String
    type InputDirPath    = String
    type InputFilePath   = String
    type InputFileName   = String
    type InputDirName    = String

    type OutputPath      = String
    type OutputDirPath   = String
    type OutputFilePath  = String
    type OutputFileName  = String
    type OutputDirName   = String

  // ---------------------------------------------------------------------------
  type JsonString        = String
  type JsonPretty        = String
  type JsonCompact       = String
  type JsonArrayString   = String
  type JsonObjectString  = String

  type HostName          = String

  type UrlString         = String
  type UriString         = String

  type TableName         = String
  type DatabaseName      = String

  type StringValue       = String
  type IdValue           = String
  type DebugString       = String
  type Cell              = String
  type Prefix            = String
  type QueryString       = String

  type ErrorMsg          = String
  type FormattedNumber   = String

  // ---------------------------------------------------------------------------
  /** just to convey intention, nothing enforced */
  type One[T] =               T
  type Opt[T] =        Option[T]
  type Pes[T] = Option[Seq   [T]]
//type Nes[T] =        Seq   [T] -- in Minimal

  // ===========================================================================
  type CharsetSelector = aptmisc.StandardCharsets.type => Charset

  // ---------------------------------------------------------------------------
  type RawRdbmsEntries = Map   [String /* field name */, Option[Any /* value */]]
  type RawRdbmsValues  = Vector[                         Option[Any /* value */]]
  
  // ===========================================================================
  type Closeabled[T] = aptmisc.Closeabled[T]
  val  Closeabled    = aptmisc.Closeabled

  type CloseabledIterator[T] = aptmisc.CloseabledIterator[T]
  val  CloseabledIterator    = aptmisc.CloseabledIterator

  type SelfClosingIterator[T] = aptmisc.SelfClosingIterator[T]
  val  SelfClosingIterator    = aptmisc.SelfClosingIterator

  // ---------------------------------------------------------------------------
  type JavaPattern = java.util.regex.Pattern

  // ===========================================================================
  @deprecated("use aptus.Minimal now")
  type AptusPrototypingImplicits = min.AptusMinimal
  type Minimal                   = min.AptusMinimal

  // ---------------------------------------------------------------------------
  type AptusFormattingTraits = aptus.apttraits.AptusFormattingTraits /* eg HasFormatDefault, HasFormatCompatJson, ... */

  // ---------------------------------------------------------------------------
  lazy val system     = aptmisc.AptusSystem
  lazy val fs         = aptmisc.Fs
  lazy val hardware   = aptmisc.Hardware
  lazy val random     = aptmisc.Random
  lazy val reflection = aptmisc.Reflection
  lazy val time       = aptmisc.Time }

// ===========================================================================
