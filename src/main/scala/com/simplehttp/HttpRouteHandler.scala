package com.simplehttp

import scala.collection.{immutable, mutable}
import com.simplehttp.MimeTypes._
import org.simpleframework.http.{Response, Request}
import java.io.{PrintWriter, StringWriter, PrintStream}
import org.msgpack.ScalaMessagePack._
import org.msgpack.`type`.Value
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.slf4j.Logger
import scala.reflect.io.Streamable


/**
 * Created by basca on 04/06/14.
 */

/**
 * the trait of all Http Handlers for a specific route
 * @tparam T the passed application type
 */
trait HttpRouteHandler[T] {
  /**
   * internal logger
   */
  val logger = Logger(LoggerFactory getLogger "tRush")

  /**
   * request headers, inheriting classes can add specific headers to this map which will later be included in the
   * request
   */
  val headers: mutable.Map[String, String] = mutable.Map[String, String]()

  /**
   * the content type matching the content of the response of this handler
   *
   * @return the content type
   */
  def contentType: MimeTypes

  /**
   * request processor
   *
   * @param request the request
   * @param application the application
   * @return either a string or a byte array
   */
  def process(request: Request, application: Option[T]): Either[String, Array[Byte]]

  /**
   * the actual request handler.
   * Exceptions are trapped and returned in a response with HTTP code 500, else control is passed to the [[process]] method
   *
   * @param request the request
   * @param response the response
   * @param application the application
   */
  def handleRequest(request: Request, response: Response, application: Option[T]) = {
    val body: PrintStream = response.getPrintStream
    val time: Long = System.currentTimeMillis()
    response.setValue("Content-Type", contentType)
    response.setValue("Server", s"tRush HTTP handler ${BuildInfo.name}-${BuildInfo.version}")
    response.setDate("Date", time)
    response.setDate("Last-Modified", time)
    //    logger.debug(s"request path: ${request.getPath}")

    // set any extra headers defined by the user
    for ((header, value) <- headers) {
      response.setValue(header, value)
    }

    try {
      process(request, application) match {
        case Left(stringContent) => body.println(stringContent)
        case Right(binaryContent) => body.write(binaryContent)
      }
      response.setCode(200) // on success
    } catch {
      case e: Exception =>
        response.setCode(500) // on error
      val sw: StringWriter = new StringWriter()
        e.printStackTrace(new PrintWriter(sw))
        body.println(sw.toString)
    }
    finally {
      body.close()
    }
  }
}

/**
 * a [[http://msgpack.org/ MessagePack]] binary message handler trait
 *
 * @tparam T the passed application type
 * @tparam V the processed content type
 */
trait BinaryMsgPackHandler[T, V] extends HttpRouteHandler[T] {
  override def contentType: MimeTypes = MimeTypes.binarymsgpack

  def getResult(arguments: immutable.Map[String, Value], application: Option[T]): V

  override def process(request: Request, application: Option[T]): Either[String, Array[Byte]] = {
    val contentBytes: Array[Byte] = Streamable.bytes(request.getInputStream)
    if (contentBytes.length > 0) {
      val arguments: Map[String, Value] = read[Map[String, Value]](contentBytes)
      Right(write(getResult(arguments, application)))
    } else {
      Right(write(getResult(immutable.Map[String, Value](), application)))
    }
  }
}

/**
 * a default request handler. Used by the [[com.simplehttp.ApplicationContainer]] when no handler is found for the
 * current route.
 *
 * The handler simply returns the current version stored in [[com.simplehttp.BuildInfo]]
 */
object DefaultRouteHandler extends HttpRouteHandler[Any] {
  override def contentType: MimeTypes = MimeTypes.text

  override def process(request: Request, application: Option[Any]): Either[String, Array[Byte]] = {
    Left(s"${BuildInfo.name} version ${BuildInfo.version}")
  }
}

