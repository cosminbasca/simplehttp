package com

import java.net.InetSocketAddress
import org.simpleframework.http.core.ContainerServer
import org.simpleframework.transport.Server
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
    val server: Server = new ContainerServer(appContainer)
    val conn: Connection = new SocketConnection(server)
    val address: InetSocketAddress = hostName match {
      case Some(host) => new InetSocketAddress(host, port)
      case None => new InetSocketAddress(port)
    }
    conn.connect(address).asInstanceOf[InetSocketAddress]
  }
}
