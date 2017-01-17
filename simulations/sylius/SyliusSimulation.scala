package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import sylius.page._

class SyliusSimulation extends Simulation {
  val httpProtocol = http
    .baseURL("http://demo.sylius.org")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val visiting = scenario("Just visiting").exec(Homepage.visit, Catalog.browseProducts, Catalog.showProduct)

  setUp(visiting.inject(rampUsers(10) over (10 seconds)).protocols(httpProtocol))
}
