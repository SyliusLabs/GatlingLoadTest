package sylius.settings

import scala.concurrent.duration._

case class LoadSettings(numberOfUsers: Int, rampingTime: FiniteDuration, simulationTime: FiniteDuration)
