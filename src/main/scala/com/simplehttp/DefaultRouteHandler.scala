package com.simplehttp

import org.simpleframework.http.Request

/**
 * a default request handler. Used by the [[com.simplehttp.ApplicationContainer]] when no handler is found for the
 * current route.
 *
 * The handler simply returns the current version stored in [[com.simplehttp.BuildInfo]]
 */
object DefaultRouteHandler extends HttpRouteHandler[Any] {
  override def contentType: MimeTypes.Value = MimeTypes.Text

  override def process(request: Request, application: Option[Any]): Either[String, Array[Byte]] = {
    Left(s"${BuildInfo.name} version ${BuildInfo.version}")
  }
}
