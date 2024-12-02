package aptus
package aptutils

import scala.util.chaining._
import java.nio.charset.StandardCharsets

// ===========================================================================
object BinaryUtils {
  private lazy val Base64Encoder = java.util.Base64.getEncoder()
  private lazy val Base64Decoder = java.util.Base64.getDecoder()

  // ---------------------------------------------------------------------------
  private lazy val HexArray: Array[Byte] =
    "0123456789ABCDEF".getBytes(US_ASCII)

  // ===========================================================================
  def bytesToBase64(bytes: Array[Byte]): String =
    bytes
      .pipe(Base64Encoder.encode)
      .pipe(new String(_))

  // ---------------------------------------------------------------------------
  def base64ToBytes(string: String) = Base64Decoder.decode(string)

  // ===========================================================================
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

  // ===========================================================================
  import java.nio.ByteBuffer.allocate

  def byteBuffer(value: Byte)  : ByteBuffer = allocate(1).put      (value)
  def byteBuffer(value: Short) : ByteBuffer = allocate(2).putShort (value)
  def byteBuffer(value: Int)   : ByteBuffer = allocate(4).putInt   (value)
  def byteBuffer(value: Long)  : ByteBuffer = allocate(8).putLong  (value)
  def byteBuffer(value: Float) : ByteBuffer = allocate(4).putFloat (value)
  def byteBuffer(value: Double): ByteBuffer = allocate(8).putDouble(value)
  def byteBuffer(value: Char)  : ByteBuffer = allocate(2).putChar  (value) }

// ===========================================================================
