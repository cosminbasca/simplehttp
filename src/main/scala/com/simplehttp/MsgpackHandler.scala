//
// author: Cosmin Basca
//
// Copyright 2010 University of Zurich
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
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
