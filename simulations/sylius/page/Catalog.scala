package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Catalog {
  private val productsFeeder = csv("products.csv").random
  private val taxonsFeeder = csv("taxons.csv").random

  val browseProducts = {
    feed(taxonsFeeder)
      .exec(http("Browse products in taxon").get("/taxons/${taxon_slug}"))
      .pause(1)
  }

  val showProduct = {
    feed(productsFeeder)
      .exec(http("Show product").get("/products/${product_slug}"))
      .pause(1)
  }
}
