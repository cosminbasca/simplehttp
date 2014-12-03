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

import org.simpleframework.http.core.Container
import scala.collection.mutable
import org.simpleframework.http.{Response, Request}

/**
 * Created by basca on 04/06/14.
 */

/**
 * abstract superclass of all request route aware application containers inheriting application containers
 * should populate this map with the appropriate handlers for a given route (path)
 *
 * a simple example:
 * {{{
 *   class MyAppContainer[MyApp] = {
 *      routes += "hello" -> new HelloWorldHandler[MyApp]()
 *      routes += "other" -> new OtherHandler[MyApp]()
 *   }
 * }}}
 * @param application the application passed around to all request handlers
 * @tparam T the type of the application
 */
abstract class ApplicationContainer[T](val application: Option[T]=None) extends Container {
  /**
   * the routes [[mutable.Map]],
   */
  protected val routes: mutable.Map[String, HttpRouteHandler[T]] = mutable.Map[String, HttpRouteHandler[T]]()

  /**
   * handle the HTTP request
   *
   * if no route is found the [[com.simplehttp.DefaultRouteHandler]] is called for the given request
   *
   * @param request the request
   * @param response the response
   */
  def handle(request: Request, response: Response) = {
    try {
      routes.get(request.getPath.toString) match {
        case Some(handler: HttpRouteHandler[T]) => handler.handleRequest(request, response, application)
        case None => DefaultRouteHandler.handleRequest(request, response, application)
      }
    } catch {
      case exception: Exception => exception.printStackTrace()
    }
  }
}
