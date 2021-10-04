package aptus

// ===========================================================================
private[aptus] trait AptusCommonAliases {
  private[aptus] type Charset = java.nio.charset.Charset
  private[aptus] val  UTF_8   = java.nio.charset.StandardCharsets.UTF_8

  // ---------------------------------------------------------------------------
  private[aptus] type URI = java.net.URI
  private[aptus] type URL = java.net.URL

  // ---------------------------------------------------------------------------
  private[aptus] type Closeable = java.io.Closeable

  // ---------------------------------------------------------------------------
  private[aptus] type Regex       = scala.util.matching.Regex
}

// ===========================================================================