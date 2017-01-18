package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import sylius.settings._

final class SyliusLoadTest extends Simulation {
  private val settings = new ServerSettings()

  private val initialiser = exec(session => session.set("domain", settings.project.domain))

  private val httpProtocol = http
      .baseURL("http://" + settings.project.domain)
      .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png""", """.*\.woff2"""), WhiteList())
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate, sdch")
      .acceptLanguageHeader("pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")

  private val simulation = scenario(settings.project.name + " Load Test (" + settings.load.numberOfUsers.toString + " users over " + settings.load.rampingTime.toString + " during " + settings.load.simulationTime.toString + ")")
    .during(settings.load.simulationTime) {
      randomSwitch(
        40d -> exec(initialiser).exec(Behaviours.abandonedCart),
        25d -> exec(initialiser).exec(Behaviours.browseCatalog),
        25d -> exec(initialiser).exec(Behaviours.searchCatalog),
        10d -> exec(initialiser).exec(Behaviours.checkoutAsGuest)
      )
    }

  setUp(
    simulation
      .inject(rampUsers(settings.load.numberOfUsers) over (settings.load.rampingTime))
      .protocols(httpProtocol)
  )
}
