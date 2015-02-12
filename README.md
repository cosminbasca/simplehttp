simplehttp
==========

simplehttp is simplistic extender of the [Simpleframework](http://www.simpleframework.org/) api

Important Notes
---------------
This software is the product of research carried out at the [University of Zurich](http://www.ifi.uzh.ch/ddis.html) and comes with no warranty whatsoever. Have fun!

TODO's
------
* unit tests
* more documentation
* (more) examples

Examples
--------

```scala

import org.simpleframework.http.Request
import com.simplehttp.{MimeTypes, HttpRouteHandler}
import com.simplehttp.ApplicationContainer
import com.simplehttp.LocalNotifierServer


class PingContext() {
}

class PingHandler extends HttpRouteHandler[PingContext] {
  override def contentType: MimeTypes.Value = MimeTypes.Text

  override def process(request: Request, application: Option[PingContext]): Either[String, Array[Byte]] = {
    Left("pong")
  }
}

class PingContainer (val context:PingContext) extends ApplicationContainer(Some(context)) {
  routes += "/ping" -> new PingHandler
}

object AvalancheEndpoint extends LocalNotifierServer[PingContext, PingContainer] {
  override def container(sources: Seq[String]): ProtocolContainer = {
    new PingContainer(new PingContext())
  }

  override def cleanup(container: ProtocolContainer) = {}
}


```

Gotcha's
--------
Every time the project version information is changed, BuildInfo needs to be regenerated. To do that simply run:

```sh
$ sbt compile
```

Thanks a lot to
---------------
* [University of Zurich](http://www.ifi.uzh.ch/ddis.html) and the [Swiss National Science Foundation](http://www.snf.ch/en/Pages/default.aspx) for generously funding the research that led to this software.
