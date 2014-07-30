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
abstract class MsgpackHandler[U, T](implicit manifest: Manifest[U]) extends HttpRouteHandler[T] {
  override def contentType: MimeTypes.Value = MimeTypes.BinaryMsgpack

  def getResult(arguments: U, application: Option[T]): Any

  def empty: U

  def readRequest(request: Request): Option[U] = {
    val contentBytes: Array[Byte] = Streamable.bytes(request.getInputStream)
    if (contentBytes.length > 0) {
      Some(read[U](contentBytes))
    } else {
      None
    }
  }

  override def process(request: Request, application: Option[T]): Either[String, Array[Byte]] = {
    readRequest(request) match {
      case Some(arguments) => Right(write(getResult(arguments, application)))
      case None => Right(write(getResult(empty, application)))
    }
  }
}
