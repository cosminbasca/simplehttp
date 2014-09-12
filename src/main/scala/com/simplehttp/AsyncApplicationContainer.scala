package com.simplehttp

import java.util.concurrent.{ExecutorService, Executors}
import org.simpleframework.http.{Request, Response}
import scala.collection.mutable

/**
 * Created by basca on 04/06/14.
 */

/**
 * asynchronous request handler (runs in a thread)
 * @param routes the routes
 * @param application the application
 * @param request the request
 * @param response the response
 * @tparam T the type of the application
 */
class AsyncRequestHandler[T](routes: mutable.Map[String, HttpRouteHandler[T]],
                             application: Option[T]=None,
                             request:Request,
                             response: Response)
  extends RequestHandler[T](routes, application, request, response) with Runnable {
  override def run(): Unit = {
    handle()
  }
}

/**
 * abstract superclass of all asynchronous request route aware application containers
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
 * @param poolSize the size of the [[ExecutorService]] thread pool size (the thread pool used to handle the async request)
 * @tparam T the type of the application
 */
abstract class AsyncApplicationContainer[T](application: Option[T]=None, val poolSize: Int=10) extends ApplicationContainer[T](application) {
  val executor: ExecutorService = Executors.newFixedThreadPool(poolSize)

  /**
   * handle the HTTP request
   *
   * if no route is found the [[com.simplehttp.DefaultRouteHandler]] is called for the given request
   *
   * @param request the request
   * @param response the response
   */
  override def handle(request: Request, response: Response) = {
    val asyncRequest = new AsyncRequestHandler[T](routes, application, request, response)
    executor.execute(asyncRequest)
  }
}
