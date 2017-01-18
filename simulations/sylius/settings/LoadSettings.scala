package sylius.settings

import scala.concurrent.duration.FiniteDuration

case class LoadSettings(numberOfUsers: Int, rampingTime: FiniteDuration, simulationTime: FiniteDuration)
