package com.simplehttp.msgpackutil

import org.msgpack.`type`.Value
import scala.collection.immutable

/**
 * Created by basca on 04/06/14.
 */

/**
 * a value formatter from a map of values
 * @tparam T the type of the converted value
 */
trait ArgumentConverter[T] extends MapValueConverter[T] {
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



