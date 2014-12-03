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

import com.typesafe.scalalogging.slf4j.Logger
import org.msgpack.`type`.Value
import org.slf4j.LoggerFactory

/**
  * simple [[http://msgpack.org/ MessagePack]] value caster
  * @tparam T the type of the converted value
  */
trait MsgPackValueConverter[T] {
   /**
    * a logger
    */
   val logger = Logger(LoggerFactory getLogger getClass.getName)

   /**
    * convert the given [[http://msgpack.org/ MessagePack]] value
    * @param value the value
    * @return the converted value of type [[T]]
    */
   protected def convert(value: Value): T

  /**
   * convert the given [[http://msgpack.org/ MessagePack]] value
   * @param value the value
   * @return the converted value of type [[T]]
   */
  def apply(value: Value): T = {
    convert(value)
  }

  /**
   * convert the given [[http://msgpack.org/ MessagePack]] optional value
   * @param value the optional value
   * @return the converted value of type [[T]] of None, if no value was given
   */
  def apply(value: Option[Value]): Option[T] = {
    value match {
      case Some(concreteValue) =>
        Option(concreteValue) match {
          case Some(nonNullValue) => Some(convert(nonNullValue))
          case None => None
        }
      case None =>
        None
    }
  }
 }
