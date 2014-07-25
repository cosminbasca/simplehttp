package com.simplehttp.msgpackutil

import scala.collection.immutable
import org.msgpack.`type`.Value

/**
 * an optional value formatter from a map of values
 * this trait is useful for processing optional values
 *
 * @tparam T the type of the converted value
 */
trait OptionalArgumentConverter[T] extends MapValueConverter[T] {
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
