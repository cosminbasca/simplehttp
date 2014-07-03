package com.simplehttp

import org.simpleframework.http.core.Container
import scala.collection.mutable
import org.simpleframework.http.{Response, Request}

/**
 * Created by basca on 04/06/14.
 */
// ---------------------------------------------------------------------------------------------------------------------
//
// the server container
//
// ---------------------------------------------------------------------------------------------------------------------
abstract class ApplicationContainer[T](val application: Option[T]=None) extends Container {
  protected val routes: mutable.Map[String, HttpRouteHandler[T]] = mutable.Map[String, HttpRouteHandler[T]]()

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
