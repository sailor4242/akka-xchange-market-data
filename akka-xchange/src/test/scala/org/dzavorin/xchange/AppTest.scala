package org.dzavorin.xchange

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit._
import org.dzavorin.xchange.routes.Routes
import org.scalatest.{BeforeAndAfterAll, FlatSpec, MustMatchers}

class AppTest extends FlatSpec
  with MustMatchers
  with ScalatestRouteTest
  with BeforeAndAfterAll
  with App
  with Routes {

  "The Server" should "return Ok response when get bitstamp BTC_USD trades" in {

    Get("/marketdata/bitstamp/tick/BTC_USD") ~> marketDataRoutes ~> check {
      status must equal(StatusCodes.OK)
    }

  }

}