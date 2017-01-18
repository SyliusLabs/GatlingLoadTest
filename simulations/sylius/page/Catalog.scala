package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Catalog {
  private val taxonsFeeder = csv("taxons.csv").random

  val browse = {
    feed(taxonsFeeder)
      .exec(
        http("Browse products in taxon")
          .get("/taxons/${taxon_slug}")
      )
  }

  val search = {
    feed(taxonsFeeder)
      .exec(
        http("Search products in taxon")
          .get("/taxons/${taxon_slug}?criteria%5Bsearch%5D%5Bvalue%5D=a")
      )
  }
}
