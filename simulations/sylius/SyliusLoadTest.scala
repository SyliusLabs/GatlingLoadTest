package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final class SyliusLoadTest extends Simulation {
  val simulation = scenario(Settings.projectName + " Load Test (" + Settings.numberOfUsers.toString + " users over " + Settings.rampingTime.toString + " during " + Settings.simulationTime.toString + ")")
    .during(Settings.simulationTime) {
      randomSwitch(
        40d -> exec(Settings.initialiser).exec(Behaviours.abandonedCart),
        25d -> exec(Settings.initialiser).exec(Behaviours.browseCatalog),
        25d -> exec(Settings.initialiser).exec(Behaviours.searchCatalog),
        10d -> exec(Settings.initialiser).exec(Behaviours.checkoutAsGuest)
      )
    }

  setUp(
    simulation
      .inject(rampUsers(Settings.numberOfUsers) over (Settings.rampingTime))
      .protocols(Settings.httpProtocol)
  )
}
