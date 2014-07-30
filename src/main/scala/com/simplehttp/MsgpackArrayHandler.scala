package com.simplehttp

import org.msgpack.ScalaMessagePack._
import org.msgpack.`type`.Value

/**
 * a [[http://msgpack.org/ MessagePack]] binary message handler trait
 * that interprets the request body as an [[Array]] of [[Value]] instances
 *
 * @tparam T the passed application type
 */
trait MsgpackArrayHandler[T] extends MsgpackHandler[Array[Value], T] {
  override def empty: Array[Value] = Array.empty[Value]
}
