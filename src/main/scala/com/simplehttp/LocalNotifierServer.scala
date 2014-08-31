package com.simplehttp

import java.io.{InputStreamReader, BufferedReader}
import java.net.InetSocketAddress

import org.simpleframework.http.core.ContainerServer
import org.simpleframework.transport.Server
import org.simpleframework.transport.connect.{SocketConnection, Connection}

/**
 * Created by basca on 30/07/14.
 */
case class ServerConfig(port: Int = -1,
                        dieOnBrokenPipe: Boolean = true,
                        portNotificationPrefix: String = "SERVER PORT=",
                        numThreads: Int = 10,
                        other: Seq[String] = Seq())


abstract class LocalNotifierServer[T, U <: ApplicationContainer[T]] extends App {
  /**
   * this method returns the [[ApplicationContainer]]
   * @return the actual container
   */
  def container(other: Seq[String]): U

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
  def serveForever(port: Int, dieOnBrokenPipe: Boolean, portNotificationPrefix: String, numThreads: Int, other: Seq[String]) {
    val appContainer: U = container(other)
    val server: Server = new ContainerServer(appContainer, numThreads)
    val conn: Connection = new SocketConnection(server)
    val address: InetSocketAddress = new InetSocketAddress(port)
    val boundAddress: InetSocketAddress = conn.connect(address).asInstanceOf[InetSocketAddress]

    println(s"$portNotificationPrefix${boundAddress.getPort}")

    if (dieOnBrokenPipe) {
      // Exit on EOF or broken pipe.  This ensures that the server dies
      // if its parent program dies.
      val stdin: BufferedReader = new BufferedReader(new InputStreamReader(System.in))
      try {
        stdin.readLine()
        cleanup(appContainer)
        System.exit(0)
      } catch {
        case e: java.io.IOException =>
          cleanup(appContainer)
          System.exit(1)
      }
    }
  }

  override def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[ServerConfig]("localNotifierServer") {
      head(BuildInfo.name, BuildInfo.version)

      opt[Int]('p', "port") action {
        (x, c) => c.copy(port = x)
      } text "port is the port to bind to"

      opt[Unit]("die_on_broken_pipe") action {
        (_, c) => c.copy(dieOnBrokenPipe = true)
      } text "if set to true (default) the server will exit when the parent starting process exists"

      opt[String]('n', "port_notification_prefix") action {
        (x, c) => c.copy(portNotificationPrefix = x)
      } text "the port notification prefix used (to detect the port reporting line)"

      opt[Int]('n', "num_threads") action {
        (x, c) => c.copy(numThreads = x)
      } text "the number of threads to allocate for the worker pool (default=10)"

      arg[String]("<other>...") unbounded() optional() action {
        (x, c) => c.copy(other = c.other :+ x)
      } text "optional unbounded args"
    }

    parser.parse(args, ServerConfig()) map { config =>
      serveForever(config.port, config.dieOnBrokenPipe, config.portNotificationPrefix, config.numThreads, config.other)
    } getOrElse {
      println("arguments could not be parsed.")
    }
  }
}