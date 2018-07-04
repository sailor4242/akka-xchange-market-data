package org.dzavorin.xchange.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCode}
import akka.http.scaladsl.model.StatusCodes.{NotFound, OK}
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.pattern.ask
import akka.util.Timeout
import com.google.gson.Gson
import org.dzavorin.xchange.utils.Helper._
import org.dzavorin.xchange.markets.TradeMarketActor._
import org.dzavorin.xchange.{DATA_PREFIX, ORDER, ROUTING_ACTOR, TICK, TRADE}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Defines path for requests.
  * Blocks until Future with result completes :(
  * The API corresponds to / marketdata / {tradeMarketName} / {typeOfData} / {currencyPair}
  * Currency pair must be separated by underlying score "_"
  * For example: GET marketdata/bitmex/trade/BTC_USD
  */
trait Routes {

  implicit def system: ActorSystem

  implicit lazy val gson = new Gson

  implicit lazy val timeout = Timeout(1.seconds)

  lazy val routingActor = Await.result(system.actorSelection(s"/user/$ROUTING_ACTOR").resolveOne(), timeout.duration)

  lazy val marketDataRoutes: Route =
    pathPrefix(DATA_PREFIX) {
      (get & path(Segment / Segment / Segment)) { (platform, msgType, currency) =>
        val pair = getCurrencyPair(currency)
        onComplete(routingActor ? GetMarketDataRequest(TradeMarket(platform), msgType match {
          case ORDER => GetOrder(pair)
          case TICK => GetTicker(pair)
          case TRADE => GetTrade(pair)
        })) {
          case Success(data) => complete(dataResponse(data, OK))
          case Failure(e) => complete(errorResponse(e.getMessage, NotFound))
        }
      }
    }

  def plainTextResponse(text: String, status: StatusCode): HttpResponse = {
    HttpResponse(status, entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, text))}

  def dataResponse(data: Any, status: StatusCode): HttpResponse = {
    HttpResponse(status, entity = HttpEntity(ContentTypes.`application/json`, gson.toJson(data)))
  }

  def errorResponse(error: String, status: StatusCode): HttpResponse =
    HttpResponse(status, entity = HttpEntity(ContentTypes.`application/json`, error))

}
