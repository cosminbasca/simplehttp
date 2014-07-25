package com.simplehttp.msgpackutil

/**
  * helper trait for binding a value to a key in a map of values
  *
  * @tparam T the type of the converted value
  */
trait MapValueConverter[T] extends ValueConverter[T] {
   /**
    * the values key in the values map
    * @return
    */
   def key: String
 }
