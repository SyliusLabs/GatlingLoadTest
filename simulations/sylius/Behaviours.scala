package sylius

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import sylius.page._

final object Behaviours {
  private def minPause = 100 milliseconds
  private def maxPause = 500 milliseconds

  val abandonedCart = {
    exec(Homepage.show)
      .pause(minPause, maxPause)
      .exec(Catalog.browse)
      .pause(minPause, maxPause)
      .exec(Product.showSimple)
      .pause(minPause, maxPause)
      .exec(Product.addSimple)
      .pause(minPause, maxPause)
      .exec(Product.showConfigurable)
      .pause(minPause, maxPause)
      .exec(Product.addConfigurable)
      .pause(minPause, maxPause)
      .exec(Checkout.cart)
  }

  val browseCatalog = {
    exec(Homepage.show)
      .pause(minPause, maxPause)
      .exec(Catalog.browse)
      .pause(minPause, maxPause)
      .exec(Product.showSimple)
      .pause(minPause, maxPause)
      .exec(Product.showConfigurable)
  }

  val searchCatalog = {
    exec(Homepage.show)
      .pause(minPause, maxPause)
      .exec(Catalog.browse)
      .pause(minPause, maxPause)
      .exec(Catalog.search)
      .pause(minPause, maxPause)
      .exec(Product.showSimple)
      .pause(minPause, maxPause)
      .exec(Product.showConfigurable)
  }

  val checkoutAsGuest = {
    exec(abandonedCart)
      .pause(minPause, maxPause)
      .exec(Checkout.checkout)
      .pause(minPause, maxPause)
      .exec(Checkout.checkoutAddressingEmailValidation)
      .pause(minPause, maxPause)
      .exec(Checkout.checkoutAddressing)
      .pause(minPause, maxPause)
      .exec(Checkout.checkoutShipping)
      .pause(minPause, maxPause)
      .exec(Checkout.checkoutPayment)
      .pause(minPause, maxPause)
      .exec(Checkout.checkoutComplete)
  }
}
