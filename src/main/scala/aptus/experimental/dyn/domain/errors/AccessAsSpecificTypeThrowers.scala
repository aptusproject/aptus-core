package aptus
package experimental
package dyn
package domain
package errors

// ===========================================================================
trait AccessAsSpecificTypeThrowers {
  import BasicType._
  import Error.AccessAsSpecificType

  // ---------------------------------------------------------------------------
  // codegened (see 241121238316)
def throwString       (naked: NakedValue): Nothing = AccessAsSpecificType(_String       , naked).thro
  def throwBoolean      (naked: NakedValue) = AccessAsSpecificType(_Boolean      , naked).thro
  def throwInt          (naked: NakedValue) = AccessAsSpecificType(_Int          , naked).thro

  def throwDouble       (naked: NakedValue) = AccessAsSpecificType(_Double       , naked).thro
  def throwByte         (naked: NakedValue) = AccessAsSpecificType(_Byte         , naked).thro
  def throwShort        (naked: NakedValue) = AccessAsSpecificType(_Short        , naked).thro
  def throwLong         (naked: NakedValue) = AccessAsSpecificType(_Long         , naked).thro
  def throwFloat        (naked: NakedValue) = AccessAsSpecificType(_Float        , naked).thro

  def throwBigInt       (naked: NakedValue) = AccessAsSpecificType(_BigInt       , naked).thro
  def throwBigDec       (naked: NakedValue) = AccessAsSpecificType(_BigDec       , naked).thro

  def throwDate    (naked: NakedValue) = AccessAsSpecificType(_Date    , naked).thro
  def throwDateTime(naked: NakedValue) = AccessAsSpecificType(_DateTime, naked).thro
  def throwInstant      (naked: NakedValue) = AccessAsSpecificType(_Instant      , naked).thro

  def throwBinary       (naked: NakedValue) = AccessAsSpecificType(_Binary       , naked).thro }

// ===========================================================================