package com

import java.net.InetSocketAddress
import org.simpleframework.http.core.ContainerServer
import org.simpleframework.transport.Server
import org.simpleframework.transport.connect.{SocketConnection, Connection}

/**
 * Created by basca on 02/07/14.
 */
package object simplehttp {
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
