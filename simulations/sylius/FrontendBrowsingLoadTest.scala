package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import sylius.settings._

final class FrontendBrowsingLoadTest extends Simulation {
  private val settings = new ServerSettings()

  setUp(
    Simulations.frontendBrowsingLoadTest(settings)
      .inject(rampUsers(settings.load.numberOfUsers) over (settings.load.rampingTime))
      .protocols(settings.gatling.protocol)
  )
}
