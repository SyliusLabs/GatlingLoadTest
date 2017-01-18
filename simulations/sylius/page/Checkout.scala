package sylius.page

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

final object Checkout {
  val showCart = {
    exec(http("Show cart").get("/cart/")).pause(2)
  }

  val placeOrder = {
    exec(
      http("Checkout")
        .get("/checkout/")
        .check(regex("""name="sylius_checkout_address\[_token\]" value="([^"]++)"""").saveAs("_token"))
    )
    .pause(1)
    .exec(
      http("Checkout addressing - AJAX province form")
        .get("/ajax/render-province-form?countryCode=US")
        .headers(Map(
          "Accept" -> "*/*",
          "X-Requested-With" -> "XMLHttpRequest"
        ))
    )
    .pause(3)
    .exec(
      http("Checkout addressing - AJAX email checking")
        .get("/ajax/users/check?email=email%40address.com")
        .headers(Map(
          "Accept" -> "application/json, text/javascript, */*; q=0.01",
          "X-Requested-With" -> "XMLHttpRequest"
        ))
        .check(status.is(404))
    )
    .pause(15)
    .exec(
      http("Checkout addressing")
        .post("/checkout/address")
        .headers(Map(
          "Accept-Encoding" -> "gzip, deflate",
          "Origin" -> "http://demo.sylius.org"
        ))
        .formParam("_method", "PUT")
        .formParam("sylius_checkout_address[customer][email]", "email@address.com")
        .formParam("sylius_checkout_address[shippingAddress][firstName]", "Krzysztof")
        .formParam("sylius_checkout_address[shippingAddress][lastName]", "Krawczyk")
        .formParam("sylius_checkout_address[shippingAddress][company]", "")
        .formParam("sylius_checkout_address[shippingAddress][street]", "Parostatku 13/42")
        .formParam("sylius_checkout_address[shippingAddress][countryCode]", "US")
        .formParam("sylius_checkout_address[shippingAddress][provinceName]", "")
        .formParam("sylius_checkout_address[shippingAddress][city]", "Las Vegas")
        .formParam("sylius_checkout_address[shippingAddress][postcode]", "71830")
        .formParam("sylius_checkout_address[shippingAddress][phoneNumber]", "")
        .formParam("sylius_checkout_address[billingAddress][firstName]", "")
        .formParam("sylius_checkout_address[billingAddress][lastName]", "")
        .formParam("sylius_checkout_address[billingAddress][company]", "")
        .formParam("sylius_checkout_address[billingAddress][street]", "")
        .formParam("sylius_checkout_address[billingAddress][countryCode]", "US")
        .formParam("sylius_checkout_address[billingAddress][provinceName]", "")
        .formParam("sylius_checkout_address[billingAddress][city]", "")
        .formParam("sylius_checkout_address[billingAddress][postcode]", "")
        .formParam("sylius_checkout_address[billingAddress][phoneNumber]", "")
        .formParam("sylius_checkout_address[_token]", "${_token}")
        .check(regex("""name="sylius_checkout_select_shipping\[shipments\]\[0\]\[method\]" required="required" value="([^"]++)" checked="checked"""").saveAs("shipping_method"))
        .check(regex("""name="sylius_checkout_select_shipping\[_token\]" value="([^"]++)"""").saveAs("_token"))
    )
    .pause(2)
    .exec(
      http("Checkout shipping")
        .post("/checkout/select-shipping")
        .headers(Map(
          "Accept-Encoding" -> "gzip, deflate",
          "Origin" -> "http://demo.sylius.org"
        ))
        .formParam("_method", "PUT")
        .formParam("sylius_checkout_select_shipping[shipments][0][method]", "${shipping_method}")
        .formParam("sylius_checkout_select_shipping[_token]", "${_token}")
        .check(regex("""name="sylius_checkout_select_payment\[payments\]\[0\]\[method\]" required="required" value="([^"]++)" checked="checked"""").saveAs("payment_method"))
        .check(regex("""name="sylius_checkout_select_payment\[_token\]" value="([^"]++)"""").saveAs("_token"))
    )
    .pause(2)
    .exec(
      http("Checkout payment")
        .post("/checkout/select-payment")
        .headers(Map(
          "Accept-Encoding" -> "gzip, deflate",
          "Origin" -> "http://demo.sylius.org"
        ))
        .formParam("_method", "PUT")
        .formParam("sylius_checkout_select_payment[payments][0][method]", "${payment_method}")
        .formParam("sylius_checkout_select_payment[_token]", "${_token}")
        .check(regex("""name="sylius_checkout_complete\[_token\]" value="([^"]++)"""").saveAs("_token"))
    )
    .pause(2)
    .exec(
      http("Checkout complete")
        .post("/checkout/complete")
        .headers(Map(
          "Accept-Encoding" -> "gzip, deflate",
          "Origin" -> "http://demo.sylius.org"
        ))
        .formParam("_method", "PUT")
        .formParam("sylius_checkout_complete[notes]", "")
        .formParam("sylius_checkout_complete[_token]", "${_token}")
    )
  }
}
