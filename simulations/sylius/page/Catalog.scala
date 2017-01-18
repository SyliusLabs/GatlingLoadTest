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
      .pause(2)
  }

  val showProduct = {
    feed(productsFeeder)
      .exec(http("Show product").get("/products/${product_slug}"))
      .pause(2)
  }

  val showAndAddProduct = {
    feed(productsFeeder)
    .exec(
      http("Show product")
        .get("/products/${product_slug}")
        .check(regex("""form name="sylius_add_to_cart" method="post" action="([^"]+)"""").saveAs("form_action"))
        .check(regex("""name="sylius_add_to_cart\[cartItem\]\[variant\]" required="required" value="([^"]+)" checked="checked"""").saveAs("variant_code"))
        .check(regex("""name="sylius_add_to_cart\[_token\]" value="(.+?)"""").saveAs("_token"))
    )
    .pause(2)
    .exec(
      http("Add product to cart")
        .post("${form_action}")
        .headers(Map(
          "Accept" -> "*/*",
          "Accept-Encoding" -> "gzip, deflate",
          "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
          "Origin" -> "http://demo.sylius.org",
          "X-Requested-With" -> "XMLHttpRequest"
        ))
        .formParam("sylius_add_to_cart[cartItem][variant]", "${variant_code}")
        .formParam("sylius_add_to_cart[cartItem][quantity]", "1")
        .formParam("sylius_add_to_cart[_token]", "${_token}")
    )
    .pause(1)
  }
}
