package aptus
package experimental
package dyn
package aliases

// ===========================================================================
trait DynErrorAliases {
  private[dyn] val  Error = domain.errors.Error
  private[dyn] type DataEntityErrorFormatter  = domain.errors.DataEntityErrorFormatter
  private[dyn] val  DataEntityErrorFormatter  = domain.errors.DataEntityErrorFormatter

  // ---------------------------------------------------------------------------
  private[dyn] type ErrorId  = String /* eg "E241126105505" */
  private[dyn] type ErrorMsg = String /* eg "not an Int: 3.3" */ }

// ===========================================================================