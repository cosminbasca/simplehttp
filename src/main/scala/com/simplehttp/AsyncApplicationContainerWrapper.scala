package com.simplehttp

import java.util.concurrent.{ExecutorService, Executors}
import org.simpleframework.http.core.Container
import org.simpleframework.http.{Request, Response}

/**
 * Created by basca on 04/06/14.
 */

/**
 * wrapper class of all asynchronous request route aware application containers
 * should populate this map with the appropriate handlers for a given route (path)
 *
 * @param container the application container to wrap asynchronously
 * @param poolSize the size of the [[ExecutorService]] thread pool size (the thread pool used to handle the async request)
 * @tparam U the type of the application container
 */
final class AsyncApplicationContainerWrapper[U <: ApplicationContainer[_]](container: U, val poolSize: Int=10) extends Container {
  val executor: ExecutorService = Executors.newFixedThreadPool(poolSize)

  class AsyncTask(container: U, request: Request, response: Response) extends Runnable {
    override def run(): Unit = {
      container.handle(request, response)
    }
  }

  /**
   * handle the HTTP request
   *
   * if no route is found the [[com.simplehttp.DefaultRouteHandler]] is called for the given request
   *
   * @param request the request
   * @param response the response
   */
  override def handle(request: Request, response: Response) = {
    val asyncRequest: AsyncTask = new AsyncTask(container, request, response)
    executor.execute(asyncRequest)
  }
}
