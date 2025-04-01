package aptus
package aptdata

// ===========================================================================
trait AptusGalliaDataAdaptor
  extends AptusGalliaScalaAliases    // Date, BigDec, ...
     with AptusGalliaCommonAliases   // Obj & Objs
     with AptusGalliaSchemaAliases   // Fld, Info, ...
     with AptusGalliasBooleanAliases // _Optional, ...
     with AptusGalliaAnnotations     // TypeMatching, ...

  // ===========================================================================
  trait AptusGalliaScalaAliases {
    private[aptus] type BigDec     = BigDecimal
    private[aptus] val  BigDec     = BigDecimal

    private[aptus] type ByteBuffer = java.nio.ByteBuffer

    private[aptus] type Date       = java.time.LocalDate
    private[aptus] type DateTime   = java.time.LocalDateTime
    private[aptus] type Instant    = java.time.Instant }

  // ===========================================================================
  trait AptusGalliaSchemaAliases {
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
    type ValueType = meta.schema.ValueType

    type BasicType = meta.basic.BasicType
    val  BasicType = meta.basic.BasicType }

// ===========================================================================
trait AptusGalliaAnnotations {
  private[aptus] class        TypeMatching(val message: String = "") extends scala.annotation.StaticAnnotation
  private[aptus] class PartialTypeMatching(val message: String = "") extends scala.annotation.StaticAnnotation
  private[aptus] class NumberAbstraction  (val message: String = "") extends scala.annotation.StaticAnnotation }

// ===========================================================================
trait AptusGalliaCommonAliases {
  private[aptus] type Obj = aptus.experimental.dyn.data.sngl.Dyn
  private[aptus] val  Obj = aptus.experimental.dyn.data.sngl.Dyn

  private[aptus] type Objs = aptus.experimental.dyn.data.mult.list.Dyns
  private[aptus] val  Objs = aptus.experimental.dyn.data.mult.list.Dyns }

// ===========================================================================
trait AptusGalliasBooleanAliases {
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
