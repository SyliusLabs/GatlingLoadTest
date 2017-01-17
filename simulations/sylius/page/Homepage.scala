package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Homepage {
  val visit = {
    exec(http("Homepage").get("/"))
      .pause(1)
  }
}
