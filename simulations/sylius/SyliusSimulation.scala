package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import sylius.page._

final class SyliusSimulation extends Simulation {
  val httpProtocol = http
    .baseURL("http://demo.sylius.org")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png""", """.*\.woff2"""), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate, sdch")
    .acceptLanguageHeader("pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")

  val simulation = scenario("Sylius Load Test")
    .during(2 minutes) {
      randomSwitch(
        40d -> exec(Behaviours.abandonedCart),
        25d -> exec(Behaviours.browseCatalog),
        25d -> exec(Behaviours.searchCatalog),
        10d -> exec(Behaviours.checkoutAsGuest)
      )
    }

  setUp(simulation.inject(rampUsers(20) over (30 seconds)).protocols(httpProtocol))
}
