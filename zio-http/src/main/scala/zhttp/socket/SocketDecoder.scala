package zhttp.socket

import io.netty.handler.codec.http.websocketx.WebSocketDecoderConfig

/**
 * Frame decoder configuration
 */
final case class SocketDecoder(
  maxFramePayloadLength: Int = 65536,
  expectMaskedFrames: Boolean = true,
  allowMaskMismatch: Boolean = false,
  allowExtensions: Boolean = false,
  closeOnProtocolViolation: Boolean = true,
  withUTF8Validator: Boolean = true,
) { self =>

  /**
   * Sets Maximum length of a frame's payload. Setting this to an appropriate
   * value for you application helps check for denial of services attacks.
   */
  def withMaxFramePayloadLength(length: Int): SocketDecoder = self.copy(maxFramePayloadLength = length)

  /**
   * Web socket servers must set this to true to reject incoming masked payload.
   */
  def withRejectMaskedFrames: SocketDecoder = self.copy(expectMaskedFrames = true)

  def withAllowMaskedFrames: SocketDecoder = self.copy(expectMaskedFrames = false)

  /**
   * When set to true, frames which are not masked properly according to the
   * standard will still be accepted.
   */
  def withAllowMaskMismatch: SocketDecoder = self.copy(allowMaskMismatch = true)

  def withRejectMaskMismatch: SocketDecoder = self.copy(allowMaskMismatch = false)

  def withAllowExtensions: SocketDecoder  = self.copy(allowExtensions = true)
  def withRejectExtensions: SocketDecoder = self.copy(allowExtensions = false)

  /**
   * Flag to not send close frame immediately on any protocol violation.ion.
   */
  def withAllowProtocolViolation: SocketDecoder = self.copy(closeOnProtocolViolation = false)

  def withRejectProtocolViolation: SocketDecoder = self.copy(closeOnProtocolViolation = true)

  /**
   * Allows you to avoid adding of Utf8FrameValidator to the pipeline on the
   * WebSocketServerProtocolHandler creation. This is useful (less overhead)
   * when you use only BinaryWebSocketFrame within your web socket connection.
   */
  def withAllowSkipUTF8Validation: SocketDecoder  = self.copy(withUTF8Validator = false)
  def withRejectSkipUTF8Validation: SocketDecoder = self.copy(withUTF8Validator = true)

  def javaConfig: WebSocketDecoderConfig = WebSocketDecoderConfig
    .newBuilder()
    .maxFramePayloadLength(maxFramePayloadLength)
    .expectMaskedFrames(expectMaskedFrames)
    .allowMaskMismatch(allowMaskMismatch)
    .allowExtensions(allowExtensions)
    .closeOnProtocolViolation(closeOnProtocolViolation)
    .withUTF8Validator(withUTF8Validator)
    .build()
}

object SocketDecoder {

  /**
   * Creates an default decoder configuration.
   */
  def default: SocketDecoder = SocketDecoder()
}
