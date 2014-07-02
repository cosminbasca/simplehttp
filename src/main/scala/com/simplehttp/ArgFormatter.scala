package com.simplehttp

import org.msgpack.ScalaMessagePack._
import scala.collection.immutable
import org.msgpack.`type`.Value
import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by basca on 04/06/14.
 */
trait ValueFormatter[T] {
  val logger = Logger(LoggerFactory getLogger "ArgFormatter")

  protected def format(value: Value): T

  def key: String
}

trait ArgFormatter[T] extends ValueFormatter[T] {
  def apply(arguments: immutable.Map[String, Value]): T = {
    format(arguments(key))
  }
}


trait OptionalArgFormatter[T] extends ValueFormatter[T] {
  def apply(arguments: immutable.Map[String, Value]): Option[T] = {
    arguments.getOrElse(key, None) match {
      case value: Value => Some(format(value))
      case None => None
    }
  }
}
