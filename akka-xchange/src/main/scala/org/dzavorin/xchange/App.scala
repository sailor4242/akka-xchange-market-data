package org.dzavorin.xchange

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.dzavorin.xchange.markets._
import org.dzavorin.xchange.routes.{Routes, RoutingActor}

/**
  * Runner class, initializes actors, runs Http server.
  * There is no stopping method, add if needed.
  * Some of the trade markets can be unavailable at the moment, or can't provide some requested info
  */
object App extends App with Routes {
  import org.dzavorin.xchange.AppConfig._

  implicit val system: ActorSystem = ActorSystem(ACTOR_SYSTEM)

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  system.actorOf(RoutingActor.props, ROUTING_ACTOR)
  system.actorOf(BitstampActor.props, BITSTAMP)
  system.actorOf(BinanceActor.props, BINANCE)
  system.actorOf(BitfinexActor.props, BITFINEX)
  system.actorOf(BitmexActor.props, BITMEX)
  system.actorOf(OKcoinActor.props, OKCOIN)

  val bindingFuture = Http().bindAndHandle(marketDataRoutes, LOCALHOST, port).recoverWith {
    case _ => sys.exit(1)
  }

  sys.addShutdownHook {
    bindingFuture map {_.unbind()}
  }

}