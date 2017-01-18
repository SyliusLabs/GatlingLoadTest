package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Settings {
  val dataDir = System.getProperty("dataDir", "data").toString
  val numberOfUsers = System.getProperty("users", "20").toInt
  val rampingTime = System.getProperty("ramp", "30").toInt seconds
  val simulationTime = System.getProperty("during", "10").toInt minutes
  val applicationDomain = System.getProperty("domain", "demo.sylius.org").toString
  val projectName = System.getProperty("project", "Sylius").toString

  val httpProtocol = {
    http
      .baseURL("http://" + applicationDomain)
      .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png""", """.*\.woff2"""), WhiteList())
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate, sdch")
      .acceptLanguageHeader("pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")
  }

  val initialiser = exec(session => session.set("domain", applicationDomain))
}
