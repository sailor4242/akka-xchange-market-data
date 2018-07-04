package org.dzavorin.xchange.routes

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.dzavorin.xchange.markets.TradeMarketActor.GetMarketDataRequest

import scala.concurrent.Await
import scala.concurrent.duration._

object RoutingActor {
  def props: Props = Props[RoutingActor]
}

/**
  * Defines which trading market actor should handle the request.
  * Finds the corresponding actor throw actor selection.
  */
class RoutingActor extends Actor with ActorLogging {

  implicit val executor = context.dispatcher
  implicit val timeout = Timeout(1.seconds)

  override def receive: Receive = {
    case GetMarketDataRequest(market, msg) =>
      val tradeMarketActorRef = Await.result(context.system.actorSelection(s"/user/${market.name}").resolveOne(), timeout.duration)
      sender() ! (tradeMarketActorRef ? msg)
  }
}
