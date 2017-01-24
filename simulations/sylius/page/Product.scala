package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Product {
  private val configurableProductsFeeder = csv("configurable_products.csv").random
  private val simpleProductsFeeder = csv("simple_products.csv").random

  val showSimple = {
    feed(simpleProductsFeeder)
      .exec(
        http("Show simple product")
          .get("/products/${product_slug}")
          .check(form("""form[name="sylius_add_to_cart"]""").transform(fields => fields.mapValues(field => field.mkString)).saveAs("form"))
          .check(regex("""form name="sylius_add_to_cart" method="post" action="([^"]++)"""").saveAs("form_action"))
      )
  }

  val addSimple = {
    exec(
      http("Add simple product to cart")
        .post("${form_action}")
        .headers(Map(
          "Accept" -> "*/*",
          "Accept-Encoding" -> "gzip, deflate",
          "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
          "Origin" -> "http://${domain}",
          "X-Requested-With" -> "XMLHttpRequest"
        ))
        .formParamMap("${form}")
    )
  }

  val showConfigurable = {
    feed(configurableProductsFeeder)
      .exec(
        http("Show configurable product")
          .get("/products/${product_slug}")
          .check(form("""form[name="sylius_add_to_cart"]""").transform(fields => fields.mapValues(field => field.mkString)).saveAs("form"))
          .check(regex("""form name="sylius_add_to_cart" method="post" action="([^"]++)"""").saveAs("form_action"))
      )
  }

  val addConfigurable = {
    exec(
      http("Add configurable product to cart")
        .post("${form_action}")
        .headers(Map(
          "Accept" -> "*/*",
          "Accept-Encoding" -> "gzip, deflate",
          "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
          "Origin" -> "http://${domain}",
          "X-Requested-With" -> "XMLHttpRequest"
        ))
        .formParamMap("${form}")
    )
  }
}
