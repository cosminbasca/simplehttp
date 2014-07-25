package com.simplehttp.msgpackutil


import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory
import org.msgpack.`type`.Value

/**
  * simple [[http://msgpack.org/ MessagePack]] value caster
  * @tparam T the type of the converted value
  */
trait ValueConverter[T] {
   /**
    * a logger
    */
   val logger = Logger(LoggerFactory getLogger "ValueConverter")

   /**
    * format the given [[http://msgpack.org/ MessagePack]] value
    * @param value the value
    * @return the converted value of type [[T]]
    */
   protected def format(value: Value): T
 }
