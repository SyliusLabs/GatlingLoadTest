package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Homepage {
  val show = {
    exec(
      http("Homepage")
        .get("/")
    )
  }
}
