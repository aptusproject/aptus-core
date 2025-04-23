package aptus
package aptdata
package aliases

// ===========================================================================
trait DynErrorAliases {
  private[aptdata] val  Error = domain.errors.Error
  private[aptdata] type DataEntityErrorFormatter  = domain.errors.DataEntityErrorFormatter
  private[aptdata] val  DataEntityErrorFormatter  = domain.errors.DataEntityErrorFormatter

  // ---------------------------------------------------------------------------
  private[aptdata] type ErrorId  = String /* eg "E241126105505" */
  private[aptdata] type ErrorMsg = String /* eg "not an Int: 3.3" */ }

// ===========================================================================