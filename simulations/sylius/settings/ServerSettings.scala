package sylius.settings

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final class ServerSettings extends Settings {
  def load() = LoadSettings(
    numberOfUsers = System.getProperty("load.numberOfUsers", "20").toInt,
    rampingTime = System.getProperty("load.rampingTime", "30").toInt seconds,
    simulationTime = System.getProperty("load.simulationTime", "10").toInt minutes
  )

  def project() = ProjectSettings(
    name = System.getProperty("project.name", "Sylius").toString,
    domain = System.getProperty("project.domain", "demo.sylius.org").toString
  )

  def gatling() = GatlingSettings(
    http
      .baseURL("http://" + project.domain)
      .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png""", """.*\.woff2""", """.*placehold\.it.*"""), WhiteList())
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate, sdch")
      .acceptLanguageHeader("pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")
  )
}