package com.simplehttp

import java.io.{InputStreamReader, BufferedReader}
import java.net.InetSocketAddress

import org.simpleframework.http.core.ContainerServer
import org.simpleframework.transport.Server
import org.simpleframework.transport.connect.{SocketConnection, Connection}

/**
 * Created by basca on 30/07/14.
 */
abstract class LocalNotifierServer[T, U <: ApplicationContainer[T]] extends App {
  /**
   * this method returns the [[ApplicationContainer]]
   * @return the actual container
   */
  def container: U

  /**
   * called on exit (for cleaning up)
   * @param container the [[ApplicationContainer]]
   */
  def cleanup(container: U) = {}

  /**
   * simple server forever method. This method starts the server and blocks
   * @param port the port to bind to (only localhost)
   * @param dieOnBrokenPipe if true than call System.exit (cleanup before)
   */
  def serveForever(port: Int, dieOnBrokenPipe: Boolean) {
    val server: Server = new ContainerServer(container)
    val conn: Connection = new SocketConnection(server)
    val address: InetSocketAddress = new InetSocketAddress(port)
    val boundAddress: InetSocketAddress = conn.connect(address).asInstanceOf[InetSocketAddress]

    println(s"${boundAddress.getPort}")

    if (dieOnBrokenPipe) {
      // Exit on EOF or broken pipe.  This ensures that the server dies
      // if its parent program dies.
      val stdin: BufferedReader = new BufferedReader(new InputStreamReader(System.in))
      try {
        stdin.readLine()
        cleanup(container)
        System.exit(0)
      } catch {
        case e: java.io.IOException =>
          cleanup(container)
          System.exit(1)
      }
    }
  }

  override def main(args: Array[String]): Unit = {
    val (port: Int, die: Boolean) = args.length match {
      case 0 =>
        (0, false)
      case 1 =>
        (args(0).toInt, false)
      case 2 =>
        (args(0).toInt, true)
    }

    serveForever(port, die)
  }
}