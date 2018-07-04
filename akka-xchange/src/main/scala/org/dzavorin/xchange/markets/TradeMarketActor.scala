package org.dzavorin.xchange.markets

import akka.actor.{Actor, ActorLogging}
import akka.util.Timeout
import info.bitrich.xchangestream.core.StreamingExchange
import org.dzavorin.xchange.markets.TradeMarketActor.{GetOrder, GetTicker, GetTrade}
import org.dzavorin.xchange.utils.LimitedQueue
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.currency.CurrencyPair.{BTC_USD, ETH_USD, LTC_USD}
import org.knowm.xchange.dto.marketdata.{OrderBook, Ticker, Trade}

import scala.concurrent.duration._
import org.dzavorin.xchange.AppConfig._

object TradeMarketActor {

  sealed trait GetMsg

  final case class GetTicker(currency: CurrencyPair) extends GetMsg

  final case class GetTrade(currency: CurrencyPair) extends GetMsg

  final case class GetOrder(currency: CurrencyPair) extends GetMsg

  final case class TradeMarket(name: String)

  final case class GetMarketDataRequest(market: TradeMarket, msg: GetMsg)

}

/**
  * Basic class which defines actor trade market actors behavior.
  * Extends it to adopt new trade market actors.
  */
abstract class TradeMarketActor extends Actor with ActorLogging {

  val exchange : StreamingExchange

  // Pairs for those we search market data, expand if needed.
  private val pairs : Set[CurrencyPair] = Set(BTC_USD, ETH_USD, LTC_USD)

  // Maps for storing last market data changes
  private val lastTrades : Map[CurrencyPair, LimitedQueue[Trade]] = getLastCurrenciesDataMap[Trade](pairs)
  private val lastOrders : Map[CurrencyPair, LimitedQueue[OrderBook]] = getLastCurrenciesDataMap[OrderBook](pairs)
  private val lastTickers : Map[CurrencyPair, LimitedQueue[Ticker]] = getLastCurrenciesDataMap[Ticker](pairs)

  implicit val timeout = Timeout(1.seconds)

  override def preStart = {
    log.info(self.toString())

    // Connect to the Exchange WebSocket API. Blocking wait for the connection.
    exchange.connect().blockingAwait()

    // Make subscriptions.
    for (pair <- pairs) {
      // Subscribe to live trades updates.
      exchange.getStreamingMarketDataService.getTrades(pair).subscribe(
        new MarketDataConsumer[Trade](lastTrades, pair), new MarketDataErrorConsumer())

      // Subscribe to live ticker updates.
      exchange.getStreamingMarketDataService.getTicker(pair).subscribe(
        new MarketDataConsumer[Ticker](lastTickers, pair), new MarketDataErrorConsumer())

      // Subscribe to live order book data updates.
      exchange.getStreamingMarketDataService.getOrderBook(pair).subscribe(
        new MarketDataConsumer[OrderBook](lastOrders, pair), new MarketDataErrorConsumer())
    }
  }

  override def postStop = {
    // Disconnect from exchange (non-blocking)
    exchange.disconnect.subscribe()
    log.info("Disconnecting from exchange subscription ...")
  }

  override def receive: Receive = {
    case GetTrade(cp) => sender() ! lastTrades(cp)
    case GetTicker(cp) => sender() ! lastTickers(cp)
    case GetOrder(cp) => sender() ! lastOrders(cp)
  }

  private def getLastCurrenciesDataMap[A](pairs : Set[CurrencyPair]) = {
    pairs.map(pair => (pair,  new LimitedQueue[A](queueSize))).toMap
  }

}
