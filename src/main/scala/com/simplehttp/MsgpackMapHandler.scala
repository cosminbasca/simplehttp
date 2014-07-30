package com.simplehttp

import org.msgpack.ScalaMessagePack._
import org.msgpack.`type`.Value

/**
 * a [[http://msgpack.org/ MessagePack]] binary message handler trait
 * that interprets the request body as a [[Map]] of [[String]]:[[Value]] instances
 *
 * @tparam T the passed application type
 */
trait MsgpackMapHandler[T] extends MsgpackHandler[Map[String, Value], T] {
  override def empty: Map[String, Value] = Map.empty[String, Value]
}
