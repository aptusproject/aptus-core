package aptus
package aptdata
package domain
package errors

// ===========================================================================
trait AccessAsSpecificTypeThrowers {
  import BasicType._
  import Error.AccessAsSpecificType

  // ---------------------------------------------------------------------------
  // codegened (see 241121238216)

  def throwString  (naked: NakedValue): Nothing = AccessAsSpecificType(_String  , naked).thro
  def throwBoolean (naked: NakedValue): Nothing = AccessAsSpecificType(_Boolean , naked).thro
  def throwInt     (naked: NakedValue): Nothing = AccessAsSpecificType(_Int     , naked).thro
  def throwDouble  (naked: NakedValue): Nothing = AccessAsSpecificType(_Double  , naked).thro
  def throwByte    (naked: NakedValue): Nothing = AccessAsSpecificType(_Byte    , naked).thro
  def throwShort   (naked: NakedValue): Nothing = AccessAsSpecificType(_Short   , naked).thro
  def throwLong    (naked: NakedValue): Nothing = AccessAsSpecificType(_Long    , naked).thro
  def throwFloat   (naked: NakedValue): Nothing = AccessAsSpecificType(_Float   , naked).thro
  def throwBigInt  (naked: NakedValue): Nothing = AccessAsSpecificType(_BigInt  , naked).thro
  def throwBigDec  (naked: NakedValue): Nothing = AccessAsSpecificType(_BigDec  , naked).thro
  def throwDate    (naked: NakedValue): Nothing = AccessAsSpecificType(_Date    , naked).thro
  def throwDateTime(naked: NakedValue): Nothing = AccessAsSpecificType(_DateTime, naked).thro
  def throwInstant (naked: NakedValue): Nothing = AccessAsSpecificType(_Instant , naked).thro
  def throwBinary  (naked: NakedValue): Nothing = AccessAsSpecificType(_Binary  , naked).thro }

// ===========================================================================