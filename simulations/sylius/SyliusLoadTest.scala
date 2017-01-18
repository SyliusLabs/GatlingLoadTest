package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import sylius.settings._

final class SyliusLoadTest extends Simulation {
  private val settings = new ServerSettings()

  private val initialiser = exec(session => session.set("domain", settings.project.domain))

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
      .protocols(settings.gatling.protocol)
  )
}
