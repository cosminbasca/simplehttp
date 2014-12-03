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
