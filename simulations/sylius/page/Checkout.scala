package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Checkout {
  val showCart = {
    exec(http("Show cart").get("/cart/")).pause(2)
  }
}
