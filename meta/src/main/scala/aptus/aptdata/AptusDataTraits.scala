package aptus
package aptdata

// ===========================================================================
trait AptusDataTraits
  extends AptusDataScalaAliases    // Date, BigDec, ...
     with AptusDataSchemaAliases   // Fld, Info, ...
     with AptusDataBooleanAliases // _Optional, ...
     with AptusDataAnnotations     // TypeMatching, ...

  // ===========================================================================
  trait AptusDataScalaAliases {
    private[aptus] type BigDec     = BigDecimal
    private[aptus] val  BigDec     = BigDecimal

    private[aptus] type ByteBuffer = java.nio.ByteBuffer

    private[aptus] type Date       = java.time.LocalDate
    private[aptus] type DateTime   = java.time.LocalDateTime
    private[aptus] type Instant    = java.time.Instant }

  // ===========================================================================
  trait AptusDataSchemaAliases {
    type Cls       = meta.schema.Cls
    type Fld       = meta.schema.Fld
    type Info      = meta.schema.Info
    type Info1     = meta.schema.Info1
    type SubInfo   = meta.schema.SubInfo

    // ---------------------------------------------------------------------------
    val Cls     = meta.schema.Cls
    val Fld     = meta.schema.Fld
    val Info    = meta.schema.Info
    val Info1   = meta.schema.Info1
    val SubInfo = meta.schema.SubInfo

    // ---------------------------------------------------------------------------
    type Container = meta.schema.Container
    val  Container = meta.schema.Container

    // ---------------------------------------------------------------------------
    type ValueType = meta.schema.ValueType

    type BasicType = meta.basic.BasicType
    val  BasicType = meta.basic.BasicType }

// ===========================================================================
trait AptusDataAnnotations {
  private[aptus] class        TypeMatching(val message: String = "") extends scala.annotation.StaticAnnotation
  private[aptus] class PartialTypeMatching(val message: String = "") extends scala.annotation.StaticAnnotation
  private[aptus] class NumberAbstraction  (val message: String = "") extends scala.annotation.StaticAnnotation }

// ===========================================================================
trait AptusDataBooleanAliases {
  private[aptus] type Multiple = Boolean
  private[aptus] type Optional = Boolean

  // ---------------------------------------------------------------------------
  private[aptus] val _Single   = false
  private[aptus] val _Multiple = true

  private[aptus] val _Optional = true
  private[aptus] val _Required = false

  // ---------------------------------------------------------------------------
  private[aptus] def formatOptional(value: Optional): String = if (value) "_Optional" else "_Required"
  private[aptus] def formatMultiple(value: Multiple): String = if (value) "_Multiple" else "_Single" }

// ===========================================================================
