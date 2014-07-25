package com.simplehttp

import scala.collection.immutable
import org.simpleframework.http.Request
import org.msgpack.ScalaMessagePack._
import org.msgpack.`type`.Value
import scala.reflect.io.Streamable

/**
 * a [[http://msgpack.org/ MessagePack]] binary message handler trait
 *
 * @tparam T the passed application type
 */
trait BinaryMsgPackHandler[T] extends HttpRouteHandler[T] {
  override def contentType: MimeTypes.Value = MimeTypes.BinaryMsgpack

  def getResult(arguments: immutable.Map[String, Value], application: Option[T]): Any

  override def process(request: Request, application: Option[T]): Either[String, Array[Byte]] = {
    val contentBytes: Array[Byte] = Streamable.bytes(request.getInputStream)
    if (contentBytes.length > 0) {
      val arguments: Map[String, Value] = read[Map[String, Value]](contentBytes)
      Right(write(getResult(arguments, application)))
    } else {
      Right(write(getResult(immutable.Map[String, Value](), application)))
    }
  }
}
