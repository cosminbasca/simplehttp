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
