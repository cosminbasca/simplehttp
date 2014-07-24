package com.simplehttp

import org.msgpack.ScalaMessagePack._
import scala.collection.immutable
import org.msgpack.`type`.Value
import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by basca on 04/06/14.
 */

/**
 * simple [[http://msgpack.org/ MessagePack]] value converter
 * @tparam T the type of the converted value
 */
trait ValueFormatter[T] {
  /**
   * a logger
   */
  val logger = Logger(LoggerFactory getLogger "ArgFormatter")

  /**
   * format the given [[http://msgpack.org/ MessagePack]] value
   * @param value the value
   * @return the converted value of type [[T]]
   */
  protected def format(value: Value): T
}


/**
 * helper trait for binding a value to a key in a map of values
 *
 * @tparam T the type of the converted value
 */
trait MapValueFormatter[T] extends ValueFormatter[T] {
  /**
   * the values key in the values map
   * @return
   */
  def key: String
}

/**
 * a value formatter from a map of values
 * @tparam T the type of the converted value
 */
trait ArgFormatter[T] extends MapValueFormatter[T] {
  /**
   * convert the value from a map of values
   *
   * @param arguments the map of values
   * @return the converted value
   */
  def apply(arguments: immutable.Map[String, Value]): T = {
    format(arguments(key))
  }
}


/**
 * an optional value formatter from a map of values
 * this trait is useful for processing optional values
 *
 * @tparam T the type of the converted value
 */
trait OptionalArgFormatter[T] extends MapValueFormatter[T] {
  /**
   * convert the optional value from the map of values
   *
   * @param arguments the map of values
   * @return the converted optional value or None if the [[key]] if not found in the map
   */
  def apply(arguments: immutable.Map[String, Value]): Option[T] = {
    arguments.getOrElse(key, None) match {
      case value: Value => Some(format(value))
      case None => None
    }
  }
}
