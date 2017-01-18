package sylius.settings

import scala.concurrent.duration._

final class ServerSettings extends Settings {
  def load() = LoadSettings(
    numberOfUsers = System.getProperty("load.numberOfUsers", "20").toInt,
    rampingTime = System.getProperty("load.rampingTime", "30").toInt seconds,
    simulationTime = System.getProperty("load.simulationTime", "10").toInt minutes
  )

  def project() = ProjectSettings(
    name = System.getProperty("project.name", "Sylius").toString,
    domain = System.getProperty("project.domain", "demo.sylius.org").toString,
    dataDir = System.getProperty("project.dataDir", "data").toString
  )
}