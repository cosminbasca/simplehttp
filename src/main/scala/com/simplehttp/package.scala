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
package com

import java.net.InetSocketAddress
import org.simpleframework.http.core.ContainerSocketProcessor
import org.simpleframework.transport.SocketProcessor
import org.simpleframework.transport.connect.{SocketConnection, Connection}

/**
 * Created by basca on 02/07/14.
 */

/**
 * the [[com.simplehttp]] library provides a simple wrapper for the [[org.simpleframework]] HTTP server in scala.
 *
 * the library offers support for handling http requests with msgpack encoded content and support for simplistic
 * request route handling
 */
package object simplehttp {
  /**
   * start the [[org.simpleframework.transport.Server]]
   *
   * @param appContainer the application container (route aware handler, holding a generic application instance)
   * @param port the port run on
   * @param hostName the host, if None the host defaults to "127.0.0.1" (default None)
   * @tparam T the type parameter of the encapsulated application
   * @return the server bonded address
   */
  def startServer[T](appContainer:ApplicationContainer[T], port:Int, hostName:Option[String]=None):InetSocketAddress = {
    val server: SocketProcessor = new ContainerSocketProcessor(appContainer)
    val conn: Connection = new SocketConnection(server)
    val address: InetSocketAddress = hostName match {
      case Some(host) => new InetSocketAddress(host, port)
      case None => new InetSocketAddress(port)
    }
    conn.connect(address).asInstanceOf[InetSocketAddress]
  }
}
