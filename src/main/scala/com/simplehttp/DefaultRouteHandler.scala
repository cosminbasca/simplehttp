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
