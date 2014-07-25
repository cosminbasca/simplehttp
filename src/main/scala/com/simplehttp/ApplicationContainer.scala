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
        case Some(handler) => handler.handleRequest(request, response, application)
        case None => DefaultRouteHandler.handleRequest(request, response, application)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
