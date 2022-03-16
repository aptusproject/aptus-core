package aptus
package aptutils

import scala.util.chaining._
import java.nio.charset.StandardCharsets

// ===========================================================================
object BinaryUtils {
  private lazy val Base64Encoder = java.util.Base64.getEncoder()

  // ---------------------------------------------------------------------------
  private lazy val HexArray: Array[Byte] =
    "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII)

  // ===========================================================================
  def bytesToBase64(bytes: Array[Byte]) =
    bytes
      .pipe(Base64Encoder.encode)
      .pipe(new String(_))

  // ---------------------------------------------------------------------------
  // see https://stackoverflow.com/a/9855338/4228079 - so as to not require commons-codec (not needed for anything else for now)
  def bytesToHexString(bytes: Array[Byte]): String = {
    val hexChars = new Array[Byte](_length = bytes.length * 2)

    Range(0, bytes.length)
      .foreach { j =>
        val v: Int = bytes(j) & 0xFF;
        hexChars.update(j * 2    , HexArray(v >>> 4))
        hexChars.update(j * 2 + 1, HexArray(v & 0x0F)) }

    new String(hexChars, StandardCharsets.UTF_8)
  }

}

// ===========================================================================
